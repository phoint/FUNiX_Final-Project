package edu.funix.Utils;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
    public static String send(String to, String sub, String message) {
	ResourceBundle rb = ResourceBundle.getBundle("mailConfig");
	String status = null;
	Properties prop = new Properties();
	prop.put("mail.smtp.host", rb.getString("HostSend"));
	prop.put("mail.smtp.socketFactory.port", rb.getString("SSL_PORT"));
	prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	prop.put("mail.smtp.auth", "true");
	prop.put("mail.smtp.port", rb.getString("SSL_PORT"));
	
	PasswordAuthentication Auth = new PasswordAuthentication("phunt2806.invest@gmail.com", "iPhone6.exe");
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return Auth;
            }
        });
	
	try {
	    MimeMessage email = new MimeMessage(session);
	    email.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	    email.setSubject(sub);
	    email.setText(message);
	    email.setSender(new InternetAddress("phunt2806.invest@gmail.com"));
	    
	    Transport.send(email);
	    status = "message sent successfully";
	    System.out.println("Success!");
	} catch (MessagingException e) {
	    status = e.getMessage();
	    throw new RuntimeException(e);
	}
	return status;
    }
    
    public static String getTemplate(String append) {
	ResourceBundle rs = ResourceBundle.getBundle("registerMail");
	StringBuilder content = new StringBuilder(rs.getString("MAILCONTENT"));
	content.append(append);
	return content.toString();
    }
}
