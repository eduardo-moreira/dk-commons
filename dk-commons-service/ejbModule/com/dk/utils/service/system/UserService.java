package com.dk.utils.service.system;

import java.util.List;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.domain.system.User;
import com.dk.utils.exception.BusinessException;
import com.dk.utils.persistence.system.UserDAO;
import com.dk.utils.persistence.system.resource.ModuleDAO;
import com.dk.utils.security.CryptoUtis;
import com.dk.utils.service.common.GenericService;
import com.dk.utils.service.system.resource.ModuleService;

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

		// Retorno
		User user = null;

		// Verificando login de admin
		if ("admin".equals(email)) {

			// TODO recuperar de outro lugar a senha (criptografada)
			if ("123".equals(password)) {

				// Criando user para admin
				user = new User();
				user.setNome("Administrador");

				// Criando perfil
				Perfil perfilAdmin = new Perfil();

				// Criando service para modulo
				ModuleService moduleService = new ModuleService();
				moduleService.setDao(new ModuleDAO());
				moduleService.getDao().setManager(getDao().getManager());
				
				// Adicionado todos os modulos
				perfilAdmin.setModules(moduleService.listAll());

				user.addPerfil(perfilAdmin);

				// Inicializando modulos
				for (Module m : user.getPerfil().getModules()) {
					m.setResources(moduleService.getResources(m));
				}


			} else {
				throw new BusinessException("login.senhaAdminInvalida");
			}

		} else {

			User filter = new User();
			filter.setEmail(email);


			List<User> login = getDao().list(filter, false);

			// Caso tenha encontrado
			if (login != null && login.size() == 1) {
				// Validando senha
				password = CryptoUtis.encode(password);
				user = login.get(0);

				if (!user.getPassword().equals(password)) {
					throw new BusinessException("login.senhaInvalida");
				}
			} else {
				throw new BusinessException("login.userInvalido");
			}

		}

		return user;
	}

}
