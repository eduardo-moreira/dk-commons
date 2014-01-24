package com.dookie.utils.web.message;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

@Stateless
@LocalBean
public class Message {

	/**
	 * Constante para titulo padrão de mensagens.
	 */
	public static final String DEFAULT_TITLE_MESSAGES_ID = "messages.default_title";

	/**
	 * Variavel que armazena as mensagens.
	 */
	public static final String MESSAGE_VAR = "msgs";

	private String text;

	private String title;

	private Severity severity;

	private Object[] params;

	/**
	 * 
	 * Cria uma nova instancia de Message.
	 * 
	 * @param severity
	 * @param titleId
	 * @param messageId
	 * @parama pParams
	 */
	public Message(Severity severity, String titleId, String messageId, Object[] pParams) {
		this(titleId, messageId);
		this.severity = severity;
		if (pParams != null) {
			text = MessageFormat.format(text, pParams);
		}
	}

	public Message() {
		
	}
	
	/**
	 * 
	 * Cria uma nova instancia de Message.
	 * 
	 * @param severity
	 * @param titleId
	 * @param messageId
	 */
	public Message(Severity severity, String titleId, String messageId) {
		this(titleId, messageId);
		this.severity = severity;
	}

	/**
	 * Cria uma nova instancia de Message.
	 * 
	 * @param text
	 * @param title
	 */
	public Message(String titleId, String messageId) {

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, MESSAGE_VAR);
		text = bundle.getString(messageId);

		if (text == null) {
			text = messageId;
		}

		if (titleId != null) {
			title = bundle.getString(titleId);
		} else {
			title = bundle.getString(DEFAULT_TITLE_MESSAGES_ID);
		}
	}

	/**
	 * Recupera o valor da propriedade text.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Atribui valor a propriedade text.
	 * 
	 * @param text
	 *            novo valor para text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Recupera o valor da propriedade title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Atribui valor a propriedade title.
	 * 
	 * @param title
	 *            novo valor para title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Recupera o valor da propriedade severity.
	 * 
	 * @return severity
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * Atribui valor a propriedade severity.
	 * 
	 * @param severity
	 *            novo valor para severity
	 */
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	/**
	 * Recupera o valor da propriedade params.
	 * 
	 * @return params
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * Atribui valor a propriedade params.
	 * 
	 * @param params
	 *            novo valor para params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

};