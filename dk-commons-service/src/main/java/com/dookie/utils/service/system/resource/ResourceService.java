/**
 * 
 */
package com.dookie.utils.service.system.resource;

import java.util.List;

import com.dookie.utils.domain.system.Module;
import com.dookie.utils.domain.system.Resource;
import com.dookie.utils.persistence.system.resource.ResourceDAO;
import com.dookie.utils.service.common.GenericService;

/**
 * Controla a entrada de formulários do sistema.
 * 
 * @author eduardo
 * 
 */
public class ResourceService extends GenericService<Resource> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -7467138247997581638L;


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.service.generic.GenericService#getDao()
	 */
	@Override
	public ResourceDAO getDao() {
		return (ResourceDAO) super.getDao();
	}

	/**
	 * Lista os recursos de um módulo.
	 * 
	 * @param m
	 * @return
	 */
	public List<Resource> list(Module m) {
		return getDao().list(m);
	}

}
