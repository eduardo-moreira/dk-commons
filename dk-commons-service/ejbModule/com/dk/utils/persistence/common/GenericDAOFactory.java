/**
 * 
 */
package com.dk.utils.persistence.common;

import java.io.Serializable;

import javax.ejb.Local;
import javax.persistence.EntityManager;

import com.dk.utils.persistence.endereco.LogradouroDAO;
import com.dk.utils.persistence.system.ParametrizacaoDAO;
import com.dk.utils.persistence.system.PerfilDAO;
import com.dk.utils.persistence.system.UserDAO;
import com.dk.utils.persistence.system.resource.EntityHistoryDAO;
import com.dk.utils.persistence.system.resource.ModuleDAO;
import com.dk.utils.persistence.system.resource.ResourceDAO;

/**
 * Interfce para implementação de uma fabrica de DAOs para a parte
 * genérica/comum.
 * 
 * @author eduardo
 * 
 */
@Local
public interface GenericDAOFactory extends Serializable {

	/**
	 * Recupera o {@link EntityManager} da factory.
	 * 
	 * @return
	 */
	EntityManager getEntityManager();

	/**
	 * Atribui valor a propriedade entityManager.
	 * 
	 * @param entityManager
	 *            novo valor para entityManager
	 */
	void setEntityManager(EntityManager entityManager);

	/**
	 * Recupera uma nova instancia do DAO informado.
	 * 
	 * @param classe
	 * @return
	 * @throws Exception
	 */
	GenericDAO<?> getDAO(Class<? extends GenericDAO<?>> classe) throws Exception;

	/**
	 * Recupera uma nova instancia de {@link EntityHistoryDAO}.
	 * 
	 * @return
	 */
	EntityHistoryDAO getEntityHistoryDAO();

	/**
	 * Recupera uma nova instancia de {@link ModuleDAO}.
	 * 
	 * @return
	 */
	ModuleDAO getModuleDAO();

	/**
	 * Recupera uma nova instancia de {@link ResourceDAO}.
	 * 
	 * @return
	 */
	ResourceDAO getResourceDAO();

	/**
	 * Recupera uma nova instancia de {@link ParametrizacaoDAO}.
	 * 
	 * @return
	 */
	ParametrizacaoDAO getParametrizacaoDAO();

	/**
	 * Recupera uma nova instancia de {@link PerfilDAO}.
	 * 
	 * @return
	 */
	PerfilDAO getPerfilDAO();

	/**
	 * Recupera uma nova instancia de {@link UserDAO}.
	 * 
	 * @return
	 */
	UserDAO getUserDAO();

	/**
	 * Recupera uma nova instancia de {@link LogradouroDAO}.
	 * 
	 * @return
	 */
	LogradouroDAO getLogradouroDAO();
}
