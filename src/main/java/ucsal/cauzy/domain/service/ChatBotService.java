package ucsal.cauzy.domain.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

    private final SystemMetricsAutoConfiguration systemMetricsAutoConfiguration;
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhone;

    public ChatBotService(SystemMetricsAutoConfiguration systemMetricsAutoConfiguration) {
        this.systemMetricsAutoConfiguration = systemMetricsAutoConfiguration;
    }

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void responderParaWhatsApp(String mensagemRecebida, String numeroDestino) {
        // Aqui só repete a mensagem + o número do destinatário
        String resposta = mensagemRecebida + " (enviado para: " + numeroDestino + ")";
        enviarMensagem(numeroDestino, resposta);
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
