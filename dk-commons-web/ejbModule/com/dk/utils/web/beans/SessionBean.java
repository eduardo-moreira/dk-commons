package com.dk.utils.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import com.dk.utils.domain.system.Resource;
import com.dk.utils.domain.system.User;
import com.dk.utils.web.controller.BasicController;
import com.dk.utils.web.controller.ViewConfig;

@Named
@SessionScoped
public class SessionBean implements Serializable {
	private static final long serialVersionUID = 6900297680274546712L;
	private static final Logger logger = Logger.getLogger(SessionBean.class);

	private User user;

	private BasicController currentView;

	private List<FormAccessBean> formsDisponiveis;

	private static Set<Class<?>> controllerClasses;

	@Inject
	MenuBean menuBean;

	public User getUser() {
		return this.user;
	}

	public void setUser(User user, String dominio) {
		this.user = user;
		prepareUserResources(dominio);
		this.menuBean.createUserMenu(user, dominio);
	}

	public BasicController getCurrentView() {
		return this.currentView;
	}

	public void setCurrentView(BasicController currentView) {
		this.currentView = currentView;
	}

	public List<FormAccessBean> getFormsDisponiveis() {
		return this.formsDisponiveis;
	}

	public void setFormsDisponiveis(List<FormAccessBean> formsDisponiveis) {
		this.formsDisponiveis = formsDisponiveis;
	}

	public void clear() {
		this.user = null;
		this.currentView = null;
		this.formsDisponiveis = null;
		this.menuBean.clear();
	}

	protected void prepareUserResources(String dominio) {
		
		if (this.formsDisponiveis == null) {
		
			this.formsDisponiveis = new ArrayList<>();

			if (controllerClasses == null) {
				Reflections reflections = new Reflections("com.dookie.tagtips.web.controller", new Scanner[0]);
				controllerClasses = reflections.getTypesAnnotatedWith(ViewConfig.class);
			}

			for (Class classe : controllerClasses) {
				FormAccessBean f = new FormAccessBean(classe);
				this.formsDisponiveis.add(f);
			}

			for (Resource resource : this.user.getPerfil().getResources(null, dominio)) {
				int pos = this.formsDisponiveis.indexOf(new FormAccessBean("", resource.getTarget()));

				if (pos != -1) {
					FormAccessBean fa = (FormAccessBean) this.formsDisponiveis.get(pos);
					logger.debug("Recurso disponivel para " + this.user.getEmail() + " -> " + fa.getUrl() + "(" + fa.getControlName() + ")");
					fa.setPermite(true);
				} else {
					logger.debug("Recurso disponivel para " + this.user.getEmail() + " -> " + resource.getTarget());
					FormAccessBean fab = new FormAccessBean(resource.getTarget(), "");
					fab.setPermite(true);
					this.formsDisponiveis.add(fab);
				}
			}
		}
	}

	public boolean validateAccess(String url) {
		logger.debug("Validando " + url + "...");

		if (url == null) {
			logger.debug("Valor nulo para validação de acesso");
			return false;
		}

		if (this.user == null) {
			logger.debug("User nulo para validação de acesso");
			return false;
		}

		for (FormAccessBean f : this.formsDisponiveis) {
			logger.debug(f.getUrl() + "///" + f.getControlName() + " -->" + url);
			if ((url.equals(f.getUrl())) || (url.equals(f.getControlName()))) {
				logger.debug("Validando " + url + "=" + f.isPermite());
				return f.isPermite();
			}
		}

		logger.debug("Validando " + url + "= false");
		return false;
	}

	public void cleanMessage() {
		if (this.currentView != null) {
			this.currentView.setMessage(null);
		}
	}
}