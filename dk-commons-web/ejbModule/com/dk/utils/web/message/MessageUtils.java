/**
 * 
 */
package com.dk.utils.web.message;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Classe utilitária para mensagens.
 * 
 * @author eduardo
 * 
 */
public class MessageUtils {

	/**
	 * 
	 * @param severity
	 * 
	 * @param titleId
	 * 
	 * @param messageId
	 * 
	 */
	public static Message addMessage(Severity severity, String titleId, String messageId) {
		Message message = new Message(severity, titleId, messageId);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message.getTitle(), message.getText()));
		return message;
	}

	/**
	 * Adiciona mensagem de erro.
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addErrorMessage(String messageId, Object[] params) {
		return addErrorMessage(null, messageId, params);
	}

	/**
	 * Adiciona mensagem de erro.
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addErrorMessage(String messageId) {
		return addErrorMessage(null, messageId, null);
	}

	/**
	 * Adiciona mensagem de erro.
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addGenericErrorMessage() {
		return addErrorMessage(null, "genericError", null);
	}

	/**
	 * Adiciona mensagem de erro.
	 * 
	 * @param titleId
	 *            titulo
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addErrorMessage(String titleId, String messageId, Object[] params) {
		Message message = new Message(FacesMessage.SEVERITY_ERROR, titleId, messageId, params);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message.getTitle(), message.getText()));
		return message;
	}

	/**
	 * Adiciona mensagem de alerta.
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addWarningMessage(String messageId) {
		return addMessage(FacesMessage.SEVERITY_WARN, null, messageId);
	}

	/**
	 * Adiciona mensagem de alerta.
	 * 
	 * @param titleId
	 *            titulo
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addWarningMessage(String titleId, String messageId) {
		return addMessage(FacesMessage.SEVERITY_WARN, titleId, messageId);
	}

	/**
	 * Adiciona mensagem de informação.
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addInformationMessage(String messageId) {
		return addMessage(FacesMessage.SEVERITY_INFO, null, messageId);
	}

	/**
	 * Adiciona mensagem de informação.
	 * 
	 * @param titleId
	 *            titulo
	 * 
	 * @param messageId
	 *            mensagem
	 * 
	 */
	public static Message addInformationMessage(String titleId, String messageId) {
		return addMessage(FacesMessage.SEVERITY_INFO, titleId, messageId);
	}

}
