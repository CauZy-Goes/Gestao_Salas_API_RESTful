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
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Usuario;

import java.util.List;

@Service
public class ChatBotService {

    private final SystemMetricsAutoConfiguration systemMetricsAutoConfiguration;

    private final UsuarioService usuarioService;

    private final SolicitacoesService solicitacoesService;

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
                          UsuarioService usuarioService, SolicitacoesService solicitacoesService) {
        this.systemMetricsAutoConfiguration = systemMetricsAutoConfiguration;
        this.usuarioService = usuarioService;
        this.solicitacoesService = solicitacoesService;
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

        String respostaJson = chamarChatGPT(mensagemRecebida, usuario);

        try {
            JsonNode json = mapper.readTree(respostaJson);
            String action = json.get("action").asText();

            switch (action) {
                case "list" -> {
                    String resumo = gerarResumoSolicitacoes(usuario);
                    enviarMensagem(numeroDestino, resumo);
                }
                case "chat" -> {
                    String content = json.get("content").asText();
                    enviarMensagem(numeroDestino, content);
                }
                default -> {
                    enviarMensagem(numeroDestino, "Desculpe, não entendi o que você deseja.");
                }
            }
        } catch (Exception e) {
            enviarMensagem(numeroDestino, "Erro ao interpretar resposta da IA.");
            e.printStackTrace();
        }
    }

    private String chamarChatGPT(String mensagem, Usuario usuario) {
        String nome = (usuario != null) ? usuario.getNomeUsuario() : "professor";

        String contexto = """
            Você é uma IA assistente da API de Gestão de Espaços Físicos da universidade.
            Seu papel é auxiliar professores a visualizar solicitações e realizar novas.
            
            Quando o usuário enviar uma mensagem, responda SOMENTE com um JSON válido com a seguinte estrutura:
            - Para listar solicitações: {"action": "list"}
            - Para solicitar espaço: {"action": "add", "espacoId": <id>, "data": "<data>", "descricao": "<texto>"}
            - Se a mensagem for apenas uma conversa: {"action": "chat", "content": "<resposta natural>"}
            
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
