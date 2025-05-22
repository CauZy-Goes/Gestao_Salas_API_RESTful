package ucsal.cauzy.domain.service;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatBotService {

    private final SystemMetricsAutoConfiguration systemMetricsAutoConfiguration;

    private final UsuarioService usuarioService;

    private final SolicitacoesService solicitacoesService;

    private final EspacoFisicoService espacoFisicoService;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhone;

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private OpenAiService openAiService;

    private final ObjectMapper mapper = new ObjectMapper();

    public ChatBotService(SystemMetricsAutoConfiguration systemMetricsAutoConfiguration,
                          UsuarioService usuarioService, SolicitacoesService solicitacoesService, EspacoFisicoService espacoFisicoService) {
        this.systemMetricsAutoConfiguration = systemMetricsAutoConfiguration;
        this.usuarioService = usuarioService;
        this.solicitacoesService = solicitacoesService;
        this.espacoFisicoService = espacoFisicoService;
    }

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
        openAiService = new OpenAiService(openAiApiKey);
    }

    private String formatarNumero(String numeroBruto) {
        // Remove o prefixo "whatsapp:" e o "+"
        return numeroBruto.replace("whatsapp:", "").trim();
    }

    public void responderParaWhatsApp(String mensagemRecebida, String numeroDestino) {
        String numeroFormatado = formatarNumero(numeroDestino);
        Usuario usuario = usuarioService.findByNumero(numeroFormatado);

        if (usuario == null) {
            enviarMensagem(numeroDestino, "Usuário não encontrado.");
            return;
        }

        String respostaIA = chamarChatGPT(mensagemRecebida, usuario);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(respostaIA);

            String action = jsonNode.get("action").asText();

            if ("add_solicitacao".equals(action)) {
                int idEspacoFisico = jsonNode.get("idEspacoFisico").asInt();
                String dataHoraLocacaoStr = jsonNode.get("dataHoraLocacao").asText();
                LocalDateTime dataHoraLocacao = LocalDateTime.parse(dataHoraLocacaoStr);
                String descricao = jsonNode.get("descricao").asText();

                Solicitacoes novaSolicitacao = new Solicitacoes();
                novaSolicitacao.setEspacoFisico(espacoFisicoService.findById(idEspacoFisico) );
                novaSolicitacao.setDataHoraLocacao(dataHoraLocacao);
                novaSolicitacao.setDataHoraSolicitacao(LocalDateTime.now());
                novaSolicitacao.setUsuarioSolicitante(usuarioService.findById(usuario.getIdUsuario()) );
                novaSolicitacao.setDescricao(descricao);

                solicitacoesService.salvar(novaSolicitacao);

                enviarMensagem(numeroDestino, "Sua solicitação foi criada com sucesso! ✅");
            } else if ("list".equals(action)) {
                enviarMensagem(numeroDestino, gerarResumoSolicitacoes(usuario));
            } else if ("chat".equals(action)) {
                String resposta = jsonNode.get("content").asText();
                enviarMensagem(numeroDestino, resposta);
            } else {
                enviarMensagem(numeroDestino, "Não entendi a solicitação.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            enviarMensagem(numeroDestino, "Erro ao interpretar a resposta da IA.");
        }
    }

    private String chamarChatGPT(String mensagem, Usuario usuario) {
        String nome = (usuario != null) ? usuario.getNomeUsuario() : "professor";

        String contexto = """
            Você é uma IA assistente da API de Gestão de Espaços Físicos da universidade.
            Seu papel é auxiliar professores a visualizar solicitações e realizar novas.
            
            Quando o usuário enviar uma mensagem, responda SOMENTE com um JSON válido com a seguinte estrutura:
            - Para listar solicitações: {"action": "list"}
            
            - Para solicitar espaço: 
                {
                  "action": "add_solicitacao",
                  "idEspacoFisico": <ID do espaço>,
                  "dataHoraLocacao": "<Data e hora no formato ISO: yyyy-MM-ddTHH:mm:ss>",
                  "descricao": <texto da descricao da solicitacao>
                }
                
            - Não adicione comentários, nem explicações, nem texto fora do JSON.
                  Se a mensagem não for sobre criar solicitação ou listar solicitacoe, ou não contiver dados suficientes, responda:
                  {
                    "action": "chat",
                    "content": "<resposta natural>"
                  }
            
            O nome do professor que está falando com você é: """ + nome + """
          
            Não adicione texto extra. Apenas o JSON válido.
        """;

        ChatMessage systemMessage = new ChatMessage("system", contexto);
        ChatMessage userMessage = new ChatMessage("user", mensagem);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(systemMessage, userMessage))
                .maxTokens(200)
                .temperature(0.85)
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(request);
        return result.getChoices().get(0).getMessage().getContent();
    }

    private String gerarResumoSolicitacoes(Usuario usuario) {
        var solicitacoes = solicitacoesService.pesquisa(
                null,
                usuario.getIdUsuario(),
                null,
                null,
                0,
                5
        );

        if (solicitacoes.isEmpty()) {
            return "Você ainda não fez nenhuma solicitação.";
        }

        StringBuilder sb = new StringBuilder("Aqui estão suas últimas solicitações:\n\n");

        for (Solicitacoes s : solicitacoes) {
            sb.append(" |Número da sala: ").append(s.getEspacoFisico().getNumero()).append("\n")
                    .append(" |Espaço: ").append(s.getEspacoFisico().getTipoSala().getNomeSala()).append("\n")
                    .append(" | Status: ").append(s.getStatus().getNomeStatus()).append("\n")
                    .append(" | Data: ").append(s.getDataHoraLocacao().toLocalDate()).append("\n")
                    .append(" | Descrição: ").append(s.getDescricao()).append("\n")
                    .append("\n");
        }

        return sb.toString();
    }

    private void enviarMensagem(String numeroDestino, String mensagem) {
                System.out.println(numeroDestino);
                Message.creator(
                new PhoneNumber(numeroDestino),
                new PhoneNumber(fromPhone),
                mensagem
        ).create();
    }
}
