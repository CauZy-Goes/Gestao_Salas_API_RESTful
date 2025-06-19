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
import java.util.ArrayList;
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
            }
             else if ("listSpace".equals(action)) {

                 List<String> lista = listaEspacosPaginar();

                 for(String str : lista) {
                     enviarMensagem(numeroDestino, str);
                 }
            }
            else if ("chat".equals(action)) {
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
                    Você é uma IA assistente do *Sistema de Gestão de Solicitações e Espaços Físicos*, desenvolvido por Cauã.
                    
                    seja bastante carismático, legal e descolado
                    
                    Sua principal missão é auxiliar os professores da universidade a:
                    ✅ Listar todas as suas solicitações de uso de espaços físicos;
                    ✅ Listar todos os espaços físicos disponíveis para uso;
                    ✅ Criar novas solicitações de uso de espaço físico com base nas informações fornecidas.
                    
                    Quem vai falar com voce seram os professores, eles vao querer ver quais as salas estam disponiveis para fazer os agendamentos deles, logo 
                    eles vao querer agendar/solicitar agendamentos/solicitacoes de salas para fazer suas atividades, vc é capas de fazer essa agendamento e tb é capaz de mostrar
                    as solicitacoes/ agendamentos do professor.
                
                    Quando o usuário enviar uma mensagem, responda *somente* com um JSON válido, conforme os exemplos abaixo:
                
                    - Para listar/exibir/mostrar solicitações de espaços físicos/salas/aulas:
                      {
                        "action": "list"
                      }
                
                    - Para solicitar/agendar/fazer solicitacao/solicitar um espaço/sala/aula/espaco fisico:
                      {
                        "action": "add_solicitacao",
                        "idEspacoFisico": <ID do espaço>,
                        "dataHoraLocacao": "<Data e hora no formato ISO: yyyy-MM-ddTHH:mm:ss>",
                        "descricao": "<Descrição da solicitação>"
                      }
                
                    - Para listar/exibir/mostrar todos os espaços físicos / salas do :
                      {
                        "action": "listSpace"
                      }
                
                    ⚠️ Não adicione explicações, comentários ou qualquer texto fora do JSON.
                
                    Se a mensagem não for relacionada a solicitações ou espaços físicos, ou não contiver dados suficientes, responda com:
                      {
                        "action": "chat",
                        "content": "<resposta natural>"
                      }
                
                    O nome do professor com quem você está conversando é: """ + nome + """
                    use o vocativo sendo o nome do professor, exemplos : Olá, Professor """ + nome + """
                    Seja sempre cordial, útil em suas respostas. Sua função é tornar a interação com o sistema mais simples e rápida.
                """;


        ChatMessage systemMessage = new ChatMessage("system", contexto);
        ChatMessage userMessage = new ChatMessage("user", mensagem);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(systemMessage, userMessage))
                .maxTokens(600)
                .temperature(0.9)
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
            sb.append("📋 *Detalhes da Solicitação:*").append("\n")
                    .append("🏫 *Sala:* ").append(s.getEspacoFisico().getNumero()).append("\n")
                    .append("📂 *Tipo de Sala:* ").append(s.getEspacoFisico().getTipoSala().getNomeSala()).append("\n")
                    .append("📅 *Data da Locação:* ").append(s.getDataHoraLocacao().toLocalDate()).append("\n")
                    .append("🔖 *Status:* ").append(s.getStatus().getNomeStatus()).append("\n")
                    .append("📝 *Descrição:* ").append(s.getDescricao()).append("\n")
                    .append("────────────────────────────").append("\n\n");

        }

        return sb.toString();
    }

    private List<String> listaEspacosPaginar() {
        var espacos = espacoFisicoService.findAll();
        List<String> mensagens = new ArrayList<>();

        if (espacos.isEmpty()) {
            mensagens.add("Você ainda não fez nenhuma solicitação.");
            return mensagens;
        }

        StringBuilder sb = new StringBuilder();
        for (EspacoFisico e : espacos) {
            String info = "✨ *Informações dos Espaços*\n" +
                    "🆔 *ID da sala:* " + e.getIdEspacoFisico() + "\n" +
                    "🏫 *Número da Sala:* " + e.getNumero() + "\n" +
                    "🏷️ *Tipo de Sala:* " + e.getTipoSala().getNomeSala() + "\n" +
                    "────────────────────────────\n\n";

            // Se ultrapassar 1500 caracteres, envia e reinicia o builder
            if (sb.length() + info.length() > 1500) {
                mensagens.add(sb.toString());
                sb = new StringBuilder();
            }

            sb.append(info);
        }

        if (!sb.isEmpty()) {
            mensagens.add(sb.toString());
        }

        return mensagens;
    }

    private void enviarMensagem(String numeroDestino, String mensagem) {
                System.out.println(mensagem);
                Message.creator(
                new PhoneNumber(numeroDestino),
                new PhoneNumber(fromPhone),
                mensagem
        ).create();
    }
}
