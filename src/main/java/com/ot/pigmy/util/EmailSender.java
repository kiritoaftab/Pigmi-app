package com.ot.pigmy.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendSimpleEmail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("cmoinahmed@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);

		javaMailSender.send(message);
		System.out.println("Mail send");
	}

	public void sendSimpleEmailwithAttchment(String toEmail, String body, String subject, String attachment)
			throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		mimeMessageHelper.setFrom("cmoinahmed@gmail.com");
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);
		FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));

		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

		javaMailSender.send(message);
		System.out.println("Mail Send");
	}

}