package br.com.minhaempresa.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Empresa;

@Service
public class SmtpEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Value("${default.sender}")
	private String sender;
	
	public SmtpEmailService() {}
	
	public void sendNewPasswordEmail(Empresa empresa, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(empresa, newPass);
		sendEmail(sm);
	}
	
	private SimpleMailMessage prepareNewPasswordEmail(Empresa empresa, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(empresa.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
	
	private void sendEmail(SimpleMailMessage msg) {
		System.out.println("Enviando email");
		
		if(mailSender == null) {
			System.out.println("Mail sender está nulo");
		}
		
		mailSender.send(msg);
		System.out.println("Email enviado");;
	}
}