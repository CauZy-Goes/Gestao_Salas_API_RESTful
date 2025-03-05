package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Solicitacoes;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void enviarNotificacaoAlteracao(Solicitacoes solicitacao) {
        String emailProfessor = solicitacao.getUsuarioSolicitante().getEmail();

        String assunto = solicitacao.getStatus().getIdStatus().equals(1) ?  "Notificação de Alteração na Solicitação de Sala" :
                "Sua solicitação foi " +  solicitacao.getStatus().getNomeStatus() +" !" ;

        String texto = "Olá " + solicitacao.getUsuarioSolicitante().getNomeUsuario() + ",\n\n" +
                "Sua solicitação de sala foi atualizada. Detalhes:\n" +
                "Descrição: " + solicitacao.getDescricao() + "\n" +
                "Status: " + solicitacao.getStatus().getNomeStatus() + "\n" +
                "Atenciosamente,\nEquipe de Gestão de Espaços Da CauZy Enterprise";

        sendEmail(emailProfessor, assunto, texto);
    }
}
