/**
 * 
 */
package com.dookie.utils.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import com.dookie.utils.domain.system.User;
import com.dookie.utils.exception.BusinessException;
import com.dookie.utils.service.common.GenericServiceFactory;
import com.dookie.utils.web.beans.SessionBean;
import com.dookie.utils.web.message.Message;
import com.dookie.utils.web.message.MessageUtils;

/**
 * @author eduardo
 * 
 */
public class BasicController implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 870859372098839903L;

	/**
	 * Controller de sessão.
	 */
	@Inject
	protected SessionBean sessionBean;

	/**
	 * Fabrica de serviços.
	 */
	@EJB
	protected GenericServiceFactory serviceFactory;

	/**
	 * Transacao.
	 */
	@Resource
	protected UserTransaction userTransaction;

	/**
	 * Anotações que irão configurar o view.
	 */
	protected ViewConfig viewConfig;

	/**
	 * Mensagem ao usuário.
	 */
	protected Message message;

	/**
	 * Logger.
	 */
	protected Logger logger;

	/**
	 * Cria uma nova instancia de BasicController.
	 */
	public BasicController() {
		super();
		logger = Logger.getLogger(this.getClass());
	}

	/**
	 * Recupera o valor da propriedade sessionBean.
	 * 
	 * @return sessionBean
	 */
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	/**
	 * Atribui valor a propriedade sessionBean.
	 * 
	 * @param sessionBean
	 *            novo valor para sessionBean
	 */
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	/**
	 * Recupera o valor da propriedade userTransaction.
	 * 
	 * @return userTransaction
	 */
	public UserTransaction getUserTransaction() {
		return userTransaction;
	}

	/**
	 * Atribui valor a propriedade userTransaction.
	 * 
	 * @param userTransaction
	 *            novo valor para userTransaction
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	/**
	 * Retorna o nome do bean.
	 * 
	 * @return
	 */
	public String getBeanName() {
		Named named = this.getClass().getAnnotation(Named.class);
		String name = named.value();

		if (name == null || name.length() == 0) {
			name = this.getClass().getSimpleName();
			name = name.substring(0, 1).toLowerCase().concat(name.substring(1));
		}

		return name;

	}

	/**
	 * Recupera o usuário logado.
	 * 
	 * @return
	 */
	public User getUser() {

		if (sessionBean == null) {
			return null;
		}
		
		return sessionBean.getUser();
	}

	/**
	 * Cria a tela.
	 * 
	 * @return
	 */
	public String createView() {
		this.sessionBean.setCurrentView(this);
		return null;
	}

	/**
	 * Recupera o valor da propriedade message.
	 * 
	 * @return message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * Recupera o valor da propriedade message.
	 * 
	 * @return message
	 */
	public boolean getMessage(boolean clear) {
		if (clear) {
			message = null;
		}
		return clear;
	}

	/**
	 * Valida se irá mostrar uma mensagem. Após isto, zera a mensagem.
	 * 
	 * @return
	 */
	public boolean isShowMessage() {
		System.out.println("isShowMessage");
		boolean h = message != null;
		message = null;
		return h;
	}

	/**
	 * Atribui valor a propriedade message.
	 * 
	 * @param message
	 *            novo valor para message
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * Altera o fluxo para a tela de erro.
	 * 
	 * @param e
	 * @return
	 */
	protected String gotoErrorPage(Exception e) {
		this.sessionBean.setCurrentView(this);
		handleErrors(e);
		return FlowConstants.PAGE_ERROR;
	}

	/**
	 * Recupera as configurações da view.
	 * 
	 * @return
	 */
	public ViewConfig getViewConfig() {

		if (viewConfig == null) {
			viewConfig = this.getClass().getAnnotation(ViewConfig.class);
		}

		return viewConfig;
	}

	/**
	 * Redireciona o fluxo para uma nova url.
	 * 
	 * @param url
	 *            url
	 * 
	 * @return
	 */
	protected boolean redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		try {
			ec.redirect(url);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Trata erros, exibindo mensagem na tela.
	 * 
	 * @param exception
	 */
	protected void handleErrors(Exception exception) {

		StackTraceElement stack = exception.getStackTrace()[0];

		// Caso seja um erro negocial experado
		if ((exception instanceof BusinessException)) {
			BusinessException be = (BusinessException) exception;
			logDebug("Ocorreu um erro em {0}.{1} [{2}] --> {3}", new Object[] { stack.getClassName(), stack.getMethodName(), Integer.valueOf(stack.getLineNumber()), be.getCode() });
			this.message = MessageUtils.addErrorMessage(be.getCode(), be.getParams());
		} else {
			logError("Ocorreu um erro fatal {4} em {0}.{1} [{2}] --> {3}", new Object[] { stack.getClassName(), stack.getMethodName(), Integer.valueOf(stack.getLineNumber()), exception.getMessage(),
					exception.getClass().getName() });
			this.message = MessageUtils.addErrorMessage("genericError");
		}
	}

	/**
	 * Loga uma mensagem no nivel de debug.
	 * 
	 * @param message
	 * @param params
	 */
	protected void logDebug(String message, Object... params) {

		if (message == null) {
			return;
		}

		if (params != null) {
			message = MessageFormat.format(message, params);
		}

		if ((this.sessionBean != null) && (this.sessionBean.getUser() != null)) {
			this.logger.debug(this.sessionBean.getUser().getEmail() + " --> " + message);
		} else {
			this.logger.debug("(NL) --> " + message);
		}
	}

	/**
	 * Loga uma mensagem no nivel de error.
	 * 
	 * @param message
	 * @param params
	 */
	protected void logError(String message, Object... params) {

		if (params != null) {
			message = MessageFormat.format(message, params);
		}

		if ((this.sessionBean != null) && (this.sessionBean.getUser() != null)) {
			this.logger.error(this.sessionBean.getUser() + " --> " + message);
		} else {
			this.logger.error("(NL) --> " + message);
		}
	}

	public Date getDataAtual() {
		return new Date();
	}
	
	
	protected void addInformationMessage(String messageId) {
		MessageUtils.addInformationMessage(messageId);
	}
}
