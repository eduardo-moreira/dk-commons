package com.dk.utils.service.system;

import java.util.List;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.domain.system.User;
import com.dk.utils.exception.BusinessException;
import com.dk.utils.persistence.system.UserDAO;
import com.dk.utils.persistence.system.resource.ResourceDAO;
import com.dk.utils.security.CryptoUtis;
import com.dk.utils.service.common.GenericService;

/**
 * @author eduardo
 * 
 */
public class UserService extends GenericService<User> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3816309904677837685L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.service.generic.GenericService#getDao()
	 */
	@Override
	public UserDAO getDao() {
		return (UserDAO) super.getDao();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.service.system.LoginService#doLogin(java.lang.String)
	 */
	public User doLogin(String email, String password) throws Exception {

		User user = null;

		User filter = new User();
		filter.setEmail(email);

		List<User> login = getDao().list(filter, false);

		if ((login != null) && (login.size() == 1)) {
			
			password = CryptoUtis.encode(password);

			if (!login.get(0).getPassword().equals(password)) {
				throw new BusinessException("login.senhaInvalida");
			}

			// Tudo
			ResourceDAO resourceDAO = new ResourceDAO();

			if ("admin".equals(email)) {

				// Admin irá assumir todos os perfis
				user = new User();
				user.setId(login.get(0).getId());
				user.setEmail(login.get(0).getEmail());
				user.setNome("Administrador Master");
				
				Perfil perfilAdmin = new Perfil();
				resourceDAO.setManager(getDao().getManager());
				Module module = new Module();
				module.setResources(resourceDAO.listAll());
				perfilAdmin.addModule(module);
				user.addPerfil(perfilAdmin);
			} else {

				user = login.get(0);

				// Inicializando modulos.
				for (Perfil p : user.getPerfis()) {
					for (Module m : p.getModules()) {
						m.getResources().size();
					}
				}
			}

		} else {
			throw new BusinessException("login.userInvalido");
		}

		return user;
	}

}
