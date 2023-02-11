/**
 * DATN_FALL2022, 2022
 * SmtpMailSenderIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Setting;
import com.pro2111.service.SettingService;
import com.pro2111.service.SmtpMailSender;

/**
 * Thực thi lại các phương thức của class SmtpMailSender và xử lý
 * 
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class SmtpMailSenderImpl implements SmtpMailSender {

//	@Value("${spring.mail.username}")
//	private String from;

//	@Autowired
//	private JavaMailSender javaMailSender;

	@Autowired
	private SettingService settingService;

	/**
	 * Send mail
	 * 
	 * @param to:      email người nhận
	 * @param subject: tiêu đề mail
	 * @param body:    nội dung
	 */
	@Override
	public synchronized void sendMail(String to, String subject, String body) throws MessagingException {
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		helper = new MimeMessageHelper(message, true);// true indicates multipart message
//
//		helper.setFrom(from); // <--- THIS IS IMPORTANT
//		helper.setSubject(subject);
//		helper.setTo(to);
//		helper.setText(body, true);// true indicates body is html
//		javaMailSender.send(message);

		// Recipient's email ID needs to be mentioned.
//        String to = "bui.quang.hieu2910@gmail.com";

		Setting setting = settingService.getAllInforSetting();

		// Sender's email ID needs to be mentioned
		String from = setting.getEmail();

		// Assuming you are sending email from through gmails smtp
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.allow8bitmime", "true");
		properties.setProperty("mail.smtps.allow8bitmime", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(setting.getEmail(), setting.getPassword());

			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);

		try {
//			String subjectEncoding = new String(subject.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
//			String contentEncoding = new String(content.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			message.setContent(body, "text/plain; charset=UTF-8");

//			// Set From: header field of the header.
//			message.setFrom(new InternetAddress(from));
//
//			// Set To: header field of the header.
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//			// Set Subject: header field
//			message.setSubject(subject);
//
//			// Now set the actual message
//			message.setText(body);

			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true, "UTF-8");// true indicates multipart message
			helper.setFrom(from); // <--- THIS IS IMPORTANT
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body, true);// true indicates body is html

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
			throw mex;
		}

	}

}
