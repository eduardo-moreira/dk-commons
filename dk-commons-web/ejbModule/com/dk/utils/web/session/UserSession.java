package com.dk.utils.web.session;

import javax.enterprise.context.SessionSc
ped;

@SessionScoped
public class UserSession {
import com.dk.utils.domain.system.User;


	/**
	 * Usuário logado.
	 */
	private User user;

	/**
	 * Recupera o valor da propriedade user.
	 *
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Atribui um novo valor para user 
	 *
	 * @param user novo valor para user 
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public void invalidate() {
		this.user = null;
	}
	
}
