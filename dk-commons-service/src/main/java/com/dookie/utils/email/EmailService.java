package com.dookie.utils.email;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;

import com.dookie.utils.domain.system.User;

@Stateless
public class EmailService implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -8820452148179597520L;

	/**
	 * Usuário que irá receber o email.
	 */
	protected User receipt;

	/**
	 * Assunto do email
	 */
	protected String subject;

	/**
	 * Conteudo.
	 */
	protected String body;

	/**
	 * Texto de mensagem.
	 */
	protected Message message;

	/**
	 * Conteudo.
	 */
	protected MimeBodyPart messageBodyPart;

	/**
	 * Recupera o valor da propriedade receipt.
	 * 
	 * @return receipt
	 */
	public User getReceipt() {
		return receipt;
	}

	/**
	 * Atribui valor a propriedade receipt.
	 * 
	 * @param receipt
	 *            novo valor para receipt
	 */
	public void setReceipt(User receipt) {
		this.receipt = receipt;
	}

	/**
	 * Recupera o valor da propriedade subject.
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Atribui valor a propriedade subject.
	 * 
	 * @param subject
	 *            novo valor para subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Recupera o valor da propriedade messageBodyPart.
	 * 
	 * @return messageBodyPart
	 */
	public MimeBodyPart getMessageBodyPart() {
		return messageBodyPart;
	}

	/**
	 * Atribui valor a propriedade messageBodyPart.
	 * 
	 * @param messageBodyPart
	 *            novo valor para messageBodyPart
	 */
	public void setMessageBodyPart(MimeBodyPart messageBodyPart) {
		this.messageBodyPart = messageBodyPart;
	}

	/**
	 * Recupera o valor da propriedade body.
	 * 
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Atribui valor a propriedade body.
	 * 
	 * @param body
	 *            novo valor para body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	public void send() throws Exception {

		InitialContext ctx = new InitialContext();
		Session session = (Session) ctx.lookup("mail/gmail");

		// Create email and headers.
		message = new MimeMessage(session);
		message.setSubject(subject);
		message.setRecipient(RecipientType.TO, new InternetAddress(receipt.getEmail(), receipt.getNome()));
		// msg.setFrom(new InternetAddress("jack@email.com", "Jack"));

		// Multipart message.
		Multipart multipart = new MimeMultipart();

		// Caso nao tenha sido substituido o message body part
		if (messageBodyPart == null) {
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body, "UTF-8", "html");
		}
		multipart.addBodyPart(messageBodyPart);

		// Attachment file from string.
		// messageBodyPart = new MimeBodyPart();
		// messageBodyPart.setFileName("README1.txt");
		// messageBodyPart.setContent(new String("file 1 content"),
		// "text/plain");
		// multipart.addBodyPart(messageBodyPart);
		//
		// // Attachment file from file.
		// messageBodyPart = new MimeBodyPart();
		// messageBodyPart.setFileName("README2.txt");
		// DataSource src = new FileDataSource("file.txt");
		// messageBodyPart.setDataHandler(new DataHandler(src));
		// multipart.addBodyPart(messageBodyPart);
		//
		// // Attachment file from byte array.
		// messageBodyPart = new MimeBodyPart();
		// messageBodyPart.setFileName("README3.txt");
		// src = new ByteArrayDataSource("file 3 content".getBytes(),
		// "text/plain");
		// messageBodyPart.setDataHandler(new DataHandler(src));
		// multipart.addBodyPart(messageBodyPart);

		// Add multipart message to email.
		message.setContent(multipart);

		// Send email.
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Transport.send(message);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO log
				}
			}
		}).start();

	}
}