package com.example.facultades.service;

import com.example.facultades.dto.EmailDtoContacto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${correo.destinatario}")
    private String correoDestinatario;

    //@Autowired
    //private TemplateEngine templateEngine;
    @Override
    public void sendMail(EmailDtoContacto email) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(correoDestinatario);
            helper.setSubject(email.getAsunto());
            helper.setText(email.getMensaje() + "\n" + "\nCorreo enviado por: " + email.getNombre() + " " + email.getApellido() + "\nDirección de correo: " + email.getEmisor());


            //Context context = new Context();
            //context.setVariable("mensaje", email.getMensaje());
            //String contentHTML = templateEngine.process("email.html", context);

            //helper.setText(contentHTML, true);
            javaMailSender.send(message);
        }catch (Exception ex){
            throw new RuntimeException("error al enviar el correo: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void enviarCorreoVerificacionEmail(String email, String token, Long idTokenVerificador) {
        String link = "http://localhost:8080/usuario/verificarEmail/"+token+"/"+idTokenVerificador;
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Verifica tu email");
            helper.setText("Haz clic en el siguiente enlace para verificar tu cuenta: " + link);


            //Context context = new Context();
            //context.setVariable("mensaje", email.getMensaje());
            //String contentHTML = templateEngine.process("email.html", context);

            //helper.setText(contentHTML, true);
            javaMailSender.send(message);
        }catch (Exception ex){
            throw new RuntimeException("error al enviar el correo: " + ex.getMessage(), ex);
        }
    }


}
