/**
 * 
 */
package com.dookie.utils.service.common;

import com.dookie.utils.persistence.common.GenericDAO;
import com.dookie.utils.persistence.common.GenericDAOFactory;
import com.dookie.utils.service.endereco.LogradouroService;
import com.dookie.utils.service.system.ParametrizacaoService;
import com.dookie.utils.service.system.PerfilService;
import com.dookie.utils.service.system.UserService;
import com.dookie.utils.service.system.resource.EntityHistoryService;
import com.dookie.utils.service.system.resource.ModuleService;
import com.dookie.utils.service.system.resource.ResourceService;

/**
 * Implementa {@link GenericServiceFactory}.
 * 
 * @author eduardo
 * 
 */
public abstract class GenericServiceFactoryImpl implements GenericServiceFactory {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -7828700700556210332L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fan.service.ServiceFactory#getService(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GenericService<?> getService(Class<? extends GenericService<?>> serviceClass) throws Exception {

		try {
			GenericService<?> service = (GenericService<?>) serviceClass.newInstance();

			String className = serviceClass.getName();

			// Removendo pacote de serviço
			String servicePackage = GenericServiceFactory.class.getName();
			servicePackage = servicePackage.substring(0, servicePackage.lastIndexOf('.'));

			String daoPackage = GenericDAOFactory.class.getName();
			daoPackage = daoPackage.substring(0, daoPackage.lastIndexOf('.'));

			// Trocando o nome do pacote service por dao
			className = serviceClass.getName().replaceAll(servicePackage, daoPackage);

			className = className.substring(0, className.lastIndexOf('.') + 1);

			className += service.getEntityClass().getSimpleName() + "DAO";

			// Setando dao
			service.setDao(getDAOFactory().getDAO((Class<? extends GenericDAO<?>>) Class.forName(className)));

			return service;

		} catch (Exception e) {
			e.printStackTrace(); // TODO logger
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dk.utils.service.common.GenericServiceFactory#getEntityHistoryService
	 * ()
	 */
	@Override
	public EntityHistoryService getEntityHistoryService() {
		EntityHistoryService service = new EntityHistoryService();
		service.setDao(getDAOFactory().getEntityHistoryDAO());
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.service.common.GenericServiceFactory#getModuleService()
	 */
	@Override
	public ModuleService getModuleService() {
		ModuleService service = new ModuleService();
		service.setDao(getDAOFactory().getModuleDAO());
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dk.utils.service.common.GenericServiceFactory#getResourceService()
	 */
	@Override
	public ResourceService getResourceService() {
		ResourceService service = new ResourceService();
		service.setDao(getDAOFactory().getResourceDAO());
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dk.utils.service.common.GenericServiceFactory#getParametrizacaoService
	 * ()
	 */
	@Override
	public ParametrizacaoService getParametrizacaoService() {
		ParametrizacaoService service = new ParametrizacaoService();
		service.setDao(getDAOFactory().getParametrizacaoDAO());
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.service.common.GenericServiceFactory#getPerfilService()
	 */
	@Override
	public PerfilService getPerfilService() {
		PerfilService service = new PerfilService();
		service.setDao(getDAOFactory().getPerfilDAO());
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.service.common.GenericServiceFactory#getService()
	 */
	@Override
	public UserService getUserService() {
		UserService service = new UserService();
		service.setDao(getDAOFactory().getUserDAO());
		return service;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.service.common.GenericServiceFactory#getService()
	 */
	@Override
	public LogradouroService getLogradouroService() {
		LogradouroService service = new LogradouroService();
		service.setDao(getDAOFactory().getLogradouroDAO());
		return service;
	}

}
