/**
 * 
 */
package com.dk.utils.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.dk.utils.domain.system.User;
import com.dk.utils.exception.BusinessException;
import com.dk.utils.service.common.GenericServiceFactory;
import com.dk.utils.web.controller.system.MenuBean;
import com.dk.utils.web.controller.system.SessionController;
import com.dk.utils.web.message.Message;
import com.dk.utils.web.message.MessageUtils;
import com.dk.utils.web.session.SessionUtils;

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
	protected SessionController session;
	
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
	 * Auxilia na ação de manipular/criar menus para telas.
	 */
	@Inject
	protected MenuBean menuBean;

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
	 * Recupera o valor da propriedade session.
	 * 
	 * @return session
	 */
	public SessionController getSession() {
		return session;
	}

	/**
	 * Atribui valor a propriedade session.
	 * 
	 * @param session
	 *            novo valor para session
	 */
	public void setSession(SessionController session) {
		this.session = session;
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
	protected User getUser() {
		return SessionUtils.getCurrentUser();
	}

	/**
	 * Cria a tela.
	 * 
	 * @return
	 */
	public String createView() {
		session.setCurrentView(this);
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
		session.setCurrentView(this);
		handleErrors(e);
		return FlowConstants.PAGE_ERROR;
	}

	/**
	 * Executa um comando javascript no cliente.<br>
	 */
	protected void executeJS(String js) {
		RequestContext.getCurrentInstance().execute(js);
	}

	/**
	 * Exibe o dialogo para editar/inserir registros.<br>
	 * Default: dialog.show();
	 */
	protected void showDialog(String name) {
		RequestContext.getCurrentInstance().execute(name + ".show()");
	}

	/**
	 * Oculta o dialogo para editar/inserir registros.<br>
	 * Default: dialog.show();
	 */
	protected void hideDialog(String name) {
		RequestContext.getCurrentInstance().execute(name + ".hide()");
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
		if (exception instanceof BusinessException) {
			BusinessException be = (BusinessException) exception;
			logDebug("Ocorreu um erro em {0}.{1} [{2}] --> {3}", stack.getClassName(), stack.getMethodName(), stack.getLineNumber(), be.getCode());
			message = MessageUtils.addErrorMessage(be.getCode(), be.getParams());
		} else {
			logError("Ocorreu um erro fatal {4} em {0}.{1} [{2}] --> {3}", stack.getClassName(), stack.getMethodName(), stack.getLineNumber(), exception.getMessage(), exception.getClass().getName());
			message = MessageUtils.addErrorMessage("genericError");
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

		logger.debug(getUser().getEmail() + " --> " + message);
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

		logger.error(getUser().getEmail() + " --> " + message);
	}
}
