package ucsal.cauzy.domain.service;

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
import ucsal.cauzy.domain.entity.Usuario;

import java.util.List;

@Service
public class ChatBotService {

    private final SystemMetricsAutoConfiguration systemMetricsAutoConfiguration;

    private final UsuarioService usuarioService;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhone;

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private OpenAiService openAiService;

    public ChatBotService(SystemMetricsAutoConfiguration systemMetricsAutoConfiguration,
                          UsuarioService usuarioService) {
        this.systemMetricsAutoConfiguration = systemMetricsAutoConfiguration;
        this.usuarioService = usuarioService;
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
        String resposta = chamarChatGPT(mensagemRecebida, usuario);
        enviarMensagem(numeroDestino, resposta);
    }

    private String chamarChatGPT(String mensagem, Usuario usuario) {
        String nome = (usuario != null) ? usuario.getNomeUsuario() : "professor";

        String contexto = "Você é uma IA assistente da API de Gestão de Espaços Físicos da universidade. "
                + "Seu papel é auxiliar professores a visualizar solicitações de uso de espaços e realizar novas solicitações. "
                + "O nome do professor que está falando com você é " + nome + ". "
                + "Seja cordial, objetivo e útil nas respostas.";

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

    private void enviarMensagem(String numeroDestino, String mensagem) {
                System.out.println(numeroDestino);
                Message.creator(
                new PhoneNumber(numeroDestino),
                new PhoneNumber(fromPhone),
                mensagem
        ).create();
    }
}
