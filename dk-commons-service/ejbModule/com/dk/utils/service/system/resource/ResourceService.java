/**
 * 
 */
package com.dk.utils.service.system.resource;

import java.util.List;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Resource;
import com.dk.utils.persistence.system.resource.ResourceDAO;
import com.dk.utils.service.common.GenericService;

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
