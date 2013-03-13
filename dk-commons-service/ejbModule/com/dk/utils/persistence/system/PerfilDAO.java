/**
 * 
 */
package com.dk.utils.persistence.system;

import java.util.List;

import javax.persistence.Query;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.persistence.common.GenericDAO;

/**
 * @author eduardo
 * 
 */
public class PerfilDAO extends GenericDAO<Perfil> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3816309904677837685L;

	/**
	 * Lista os formulários de um perfil.
	 * 
	 * @param p
	 *            perfil
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Module> getModules(Perfil p) throws Exception {

		Query query = manager.createQuery("select p.modules FROM Perfil p WHERE p.id = ?1");
		query.setParameter(1, p.getId());

		return query.getResultList();
	}

}
