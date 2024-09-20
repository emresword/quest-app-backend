package com.example.questApp.business.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.questApp.core.mail.MailService;
@Service
public class MailManager implements MailService {
	private JavaMailSender mailSender;

	@Autowired
	public MailManager(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	@Override
	public String sendMail(String userMail) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("questApp@mail.com");
		message.setTo(userMail);
		message.setText("Welcome to QuestApp :)");
		message.setSubject("Registered");
		mailSender.send(message);
		return "Registered";
	}

}
