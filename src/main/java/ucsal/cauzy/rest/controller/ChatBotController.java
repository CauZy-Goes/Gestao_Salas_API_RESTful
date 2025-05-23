package ucsal.cauzy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ucsal.cauzy.domain.service.ChatBotService;

@RestController
@RequestMapping("/webhook")
public class ChatBotController {
    @Autowired
    private ChatBotService chatBotService;

    @PostMapping
    public void receberMensagem(@RequestParam("Body") String body,
                                @RequestParam("From") String from) {
        System.out.println("Mensagem de " + from + ": " + body);
        chatBotService.responderParaWhatsApp(body, from);
    }
}
