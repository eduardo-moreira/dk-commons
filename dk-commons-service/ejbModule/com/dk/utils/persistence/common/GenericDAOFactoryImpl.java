/**
 * 
 */
package com.dk.utils.persistence.common;

import com.dk.utils.persistence.endereco.LogradouroDAO;
import com.dk.utils.persistence.system.ParametrizacaoDAO;
import com.dk.utils.persistence.system.PerfilDAO;
import com.dk.utils.persistence.system.UserDAO;
import com.dk.utils.persistence.system.resource.EntityHistoryDAO;
import com.dk.utils.persistence.system.resource.ModuleDAO;
import com.dk.utils.persistence.system.resource.ResourceDAO;

/**
 * Implementacao para {@link GenericDAOFactory}.
 * 
 * @author eduardo
 */
public abstract class GenericDAOFactoryImpl implements GenericDAOFactory {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 4350679528480281836L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fan.persistence.DAOFactory#getDAO(java.lang.Class)
	 */
	@Override
	public GenericDAO<?> getDAO(Class<? extends GenericDAO<?>> classe) throws Exception {
		try {
			GenericDAO<?> dao = (GenericDAO<?>) classe.newInstance();
			dao.setManager(getEntityManager());
			return dao;
		} catch (Exception e) {
			e.printStackTrace(); // TODO logger
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getEntityHistoryDAO()
	 */
	@Override
	public EntityHistoryDAO getEntityHistoryDAO() {
		EntityHistoryDAO dao = new EntityHistoryDAO();
		dao.setManager(getEntityManager());
		return dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getModuleDAO()
	 */
	@Override
	public ModuleDAO getModuleDAO() {
		ModuleDAO dao = new ModuleDAO();
		dao.setManager(getEntityManager());
		return dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getResourceDAO()
	 */
	@Override
	public ResourceDAO getResourceDAO() {
		ResourceDAO dao = new ResourceDAO();
		dao.setManager(getEntityManager());
		return dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getParametrizacaoDAO()
	 */
	@Override
	public ParametrizacaoDAO getParametrizacaoDAO() {
		ParametrizacaoDAO dao = new ParametrizacaoDAO();
		dao.setManager(getEntityManager());
		return dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getPerfilDAO()
	 */
	@Override
	public PerfilDAO getPerfilDAO() {
		PerfilDAO dao = new PerfilDAO();
		dao.setManager(getEntityManager());
		return dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getUserDAO()
	 */
	@Override
	public UserDAO getUserDAO() {
		UserDAO dao = new UserDAO();
		dao.setManager(getEntityManager());
		return dao;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.persistence.GenericDAOFactory#getLogradouroDAO()
	 */
	@Override
	public LogradouroDAO getLogradouroDAO() {
		LogradouroDAO dao = new LogradouroDAO();
		dao.setManager(getEntityManager());
		return dao;
	}

}
