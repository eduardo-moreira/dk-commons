/**
 * 
 */
package com.dk.utils.persistence.system.resource;

import java.util.List;

import javax.persistence.Query;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Resource;
import com.dk.utils.persistence.common.GenericDAO;

/**
 * @author eduardo
 * 
 */
public class ResourceDAO extends GenericDAO<Resource> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3816309904677837685L;

	/**
	 * @param m
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> list(Module m) {
		Query query = manager.createQuery("select m.resources FROM Module m WHERE m.id = ?1");
		query.setParameter(1, m.getId());

		return query.getResultList();

	}

}
