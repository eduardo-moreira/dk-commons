/**
 * 
 */
package com.dookie.utils.persistence.system.resource;

import java.util.List;

import javax.persistence.Query;

import com.dookie.utils.domain.system.Module;
import com.dookie.utils.domain.system.Perfil;
import com.dookie.utils.domain.system.Resource;
import com.dookie.utils.persistence.common.GenericDAO;

/**
 * @author eduardo
 * 
 */
public class ModuleDAO extends GenericDAO<Module> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3816309904677837685L;

	/**
	 * Lista os formulários de um módulo.
	 * 
	 * @param module
	 *            modulo
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getResources(Module module) {

		Query query = manager.createQuery("select m.resources FROM Module m WHERE m.id = ?1");
		query.setParameter(1, module.getId());

		return query.getResultList();
	}

	/**
	 * @param perfil
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Module> list(Perfil perfil) {
		Query query = manager.createQuery("select p.modules FROM Perfil p WHERE p.id = ?1");
		query.setParameter(1, perfil.getId());

		return query.getResultList();
	}
}
