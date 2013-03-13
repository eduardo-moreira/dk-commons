/**
 * 
 */
package com.dk.utils.service.common;

import java.io.Serializable;

import javax.ejb.Local;

import com.dk.utils.persistence.common.GenericDAOFactory;
import com.dk.utils.service.system.ParametrizacaoService;
import com.dk.utils.service.system.PerfilService;
import com.dk.utils.service.system.UserService;
import com.dk.utils.service.system.resource.EntityHistoryService;
import com.dk.utils.service.system.resource.ModuleService;
import com.dk.utils.service.system.resource.ResourceService;

/**
 * Fábrica de serviços padrão.<br>
 * Um serviço padrão é aquele que irá executar as operações padrões.
 * 
 * @see GenericService
 * 
 * @author eduardo
 */
@Local
public interface GenericServiceFactory extends Serializable {
	
	/**
	 * Recupera a fabrica de DAOs.
	 * @return
	 */
	GenericDAOFactory getDAOFactory();

	/**
	 * Recupera a classe de serviço requisitada.
	 * 
	 * @param serviceClass
	 *            classe de serviço
	 * 
	 * @return {@link GenericService}
	 * 
	 * @throws Exception
	 */
	GenericService<?> getService(Class<? extends GenericService<?>> serviceClass) throws Exception;

	/**
	 * Recupera uma nova instancia de {@link EntityHistoryService}.
	 * 
	 * @return
	 */
	EntityHistoryService getEntityHistoryService();

	/**
	 * Recupera uma nova instancia de {@link ModuleService}.
	 * 
	 * @return
	 */
	ModuleService getModuleService();

	/**
	 * Recupera uma nova instancia de {@link ResourceService}.
	 * 
	 * @return
	 */
	ResourceService getResourceService();

	/**
	 * Recupera uma nova instancia de {@link ParametrizacaoService}.
	 * 
	 * @return
	 */
	ParametrizacaoService getParametrizacaoService();

	/**
	 * Recupera uma nova instancia de {@link PerfilService}.
	 * 
	 * @return
	 */
	PerfilService getPerfilService();

	/**
	 * Recupera uma nova instancia de {@link UserService}.
	 * 
	 * @return
	 */
	UserService getUserService();
}
