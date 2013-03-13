/**
 * 
 */
package com.dk.utils.web.controller.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.primefaces.model.MenuModel;
import org.reflections.Reflections;

import com.dk.utils.domain.system.Resource;
import com.dk.utils.domain.system.User;
import com.dk.utils.service.common.GenericServiceFactory;
import com.dk.utils.service.system.UserService;
import com.dk.utils.web.controller.BasicController;
import com.dk.utils.web.controller.FlowConstants;
import com.dk.utils.web.controller.ViewConfig;
import com.dk.utils.web.message.MessageUtils;
import com.dk.utils.web.session.SessionUtils;

/**
 * Realiza o controle de sessão do usuário.
 * 
 * @author eduardo
 * 
 */
@Named
@SessionScoped
public class SessionController implements Serializable, FlowConstants {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 6733159110255087535L;

	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(SessionController.class);

	/**
	 * {@link LoginServiceLocal}.
	 */
	UserService loginService;

	/**
	 * Fabrica de serviços.
	 */
	@EJB
	protected GenericServiceFactory serviceFactory;

	/**
	 * {@link MenuBean}.
	 */
	@Inject
	MenuBean menuBean;

	/**
	 * Usuário da tela de login.
	 */
	private User userLogin = new User();

	/**
	 * Usuário logado.
	 */
	private User user;

	/**
	 * Controla a barra de menu.
	 */
	private MenuModel userMenu;

	/**
	 * View atual.
	 */
	private BasicController currentView;

	@javax.annotation.Resource
	protected UserTransaction userTransaction;

	/**
	 * Forms disponiveis no sistema para o user logado.
	 */
	private List<FormAccessBean> formsDisponiveis;

	/**
	 * Forms disponiveis no sistema.
	 */
	private static Set<Class<?>> controllerClasses;

	/**
	 * Recupera o valor da propriedade userLogin.
	 * 
	 * @return userLogin
	 */
	public User getUserLogin() {
		return userLogin;
	}

	/**
	 * Atribui valor a propriedade userLogin.
	 * 
	 * @param userLogin
	 *            novo valor para userLogin
	 */
	public void setUserLogin(User userLogin) {
		this.userLogin = userLogin;
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
	 * Recupera o valor da propriedade user.
	 * 
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Atribui valor a propriedade user.
	 * 
	 * @param user
	 *            novo valor para user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Recupera o valor da propriedade menuBean.
	 * 
	 * @return menuBean
	 */
	public MenuBean getMenuBean() {
		return menuBean;
	}

	/**
	 * Atribui valor a propriedade menuBean.
	 * 
	 * @param menuBean
	 *            novo valor para menuBean
	 */
	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}

	/**
	 * Recupera o valor da propriedade userMenu.
	 * 
	 * @return userMenu
	 * @throws Exception
	 */
	public MenuModel getUserMenu() throws Exception {

		if (userMenu == null) {
			userMenu = menuBean.getMenuModel(user);
		}

		return userMenu;
	}

	/**
	 * Atribui valor a propriedade userMenu.
	 * 
	 * @param userMenu
	 *            novo valor para userMenu
	 */
	public void setUserMenu(MenuModel userMenu) {
		this.userMenu = userMenu;
	}

	/**
	 * Recupera o valor da propriedade currentView.
	 * 
	 * @return currentView
	 */
	public BasicController getCurrentView() {
		return currentView;
	}

	/**
	 * Atribui valor a propriedade currentView.
	 * 
	 * @param currentView
	 *            novo valor para currentView
	 */
	public void setCurrentView(BasicController currentView) {

		if (this.currentView != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().remove(this.currentView.getBeanName());
		}

		this.currentView = currentView;

		SessionUtils.put("currentView", currentView);
	}

	/**
	 * Login como admin.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String autoLogin() throws Exception {
		userLogin.setEmail("admin");
		userLogin.setPassword("123");
		return verifyUser();
	}

	/**
	 * Verifica senha e usuario.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String verifyUser() throws Exception {

		logger.debug("Executando login para " + userLogin.getEmail());

		// Limpando sessao
		this.currentView = null;
		this.formsDisponiveis = null;

		// Invocando serviço de login
		user = serviceFactory.getUserService().doLogin(userLogin.getEmail(), userLogin.getPassword());

		// Caso seja um usuário válido
		if (user != null) {

			logger.debug(userLogin.getEmail() + " encontrado!");

			// TODO
			// Zerando menu
			userMenu = null;

			// Listando recursos do usuário
			prepareUserResources();

			// Setando usuário na sessão
			SessionUtils.setCurrentUser(user);

			// Caso tenha vindo de uma url, tentar direcionar para ela:
			String url = (String) SessionUtils.get("redirectURL");

			if (url != null) {
				// return url.substring(url.indexOf("/forms/"));
			}

			// Caso contrário, redirecionar para a página inicial
			return "/forms/main.jsf";
		}

		logger.debug("Erro ao executar login para " + userLogin.getEmail());

		// Exibe mensagem de erro de login
		MessageUtils.addErrorMessage("login.error");
		return ERROR;
	}

	/**
	 * Executa o logout
	 * 
	 * @return
	 */
	public String logout() {
		user = new User();
		SessionUtils.setCurrentUser(null);
		SessionUtils.invalidateSession();
		return "/forms/login/login.jsf";
	}

	/**
	 * Valida se esta rodando localmente.
	 * 
	 * @return
	 */
	public boolean isLocal() {
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
		return request.getRequestURL().toString().contains("localhost");
	}

	/**
	 * Limpa a mensagem do usuário.
	 */
	public void cleanMessage() {
		if (this.currentView != null) {
			this.currentView.setMessage(null);
		}
	}

	/**
	 * Prepara a lista de recursos disponiveis para o usuário.
	 */
	protected void prepareUserResources() {

		if (formsDisponiveis == null) {

			formsDisponiveis = new ArrayList<FormAccessBean>();

			// Recuperando todos os controllers, caso não tenha sido preenchido
			// antes
			if (controllerClasses == null) {
				Reflections reflections = new Reflections("com.dookie.tagtips.web.controller");
				controllerClasses = reflections.getTypesAnnotatedWith(ViewConfig.class);
			}

			// Percorrendo lista, populando
			for (Class<?> classe : controllerClasses) {
				FormAccessBean f = new FormAccessBean(classe);
				// logger.debug("Classe/Recurso de sistema: " +
				// classe.getName());
				formsDisponiveis.add(f);
			}

			// Setando formulários disponiveis do usuário
			for (Resource resource : user.getPerfil().getResources(null)) {
				int pos = formsDisponiveis.indexOf(new FormAccessBean("", resource.getTarget()));

				if (pos != -1) {
					FormAccessBean fa = formsDisponiveis.get(pos);
					logger.debug("Recurso disponivel para " + user.getEmail() + " -> " + fa.getUrl() + "(" + fa.getControlName() + ")");
					fa.setPermite(true);
				} else {
					logger.debug("Recurso disponivel para " + user.getEmail() + " -> " + resource.getTarget());
					FormAccessBean fab = new FormAccessBean(resource.getTarget(), "");
					fab.setPermite(true);
					formsDisponiveis.add(fab);
				}
			}
		}
	}

	/**
	 * Valida o acesso a um recurso do sistema.
	 * 
	 * @param url
	 * @return
	 */
	public boolean validateAccess(String url) {

		logger.debug("Validando " + url + "...");

		if (url == null) {
			logger.debug("Valor nulo para validação de acesso");
			return false;
		}

		if (user == null) {
			logger.debug("User nulo para validação de acesso");
			return false;
		}

		// Localizando recurso
		for (FormAccessBean f : formsDisponiveis) {
			logger.debug(f.getUrl() + "///" + f.getControlName() + " -->" + url);
			if (url.equals(f.getUrl()) || url.equals(f.getControlName())) {
				logger.debug("Validando " + url + "=" + f.isPermite());
				return f.isPermite();
			}
		}

		logger.debug("Validando " + url + "= false");
		return false;
	}
}
