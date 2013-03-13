/**
 * 
 */
package com.dk.utils.service.system.resource;

import java.util.List;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.domain.system.Resource;
import com.dk.utils.persistence.system.resource.ModuleDAO;
import com.dk.utils.service.common.GenericService;

/**
 * Controla os módulos do sistema.
 * 
 * @author eduardo
 * 
 */
public class ModuleService extends GenericService<Module> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -7467138247997581638L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.service.common.GenericService#getDao()
	 */
	@Override
	public ModuleDAO getDao() {
		return (ModuleDAO) super.getDao();
	}

	/**
	 * Lista os formulários de um módulo.
	 * 
	 * @param module
	 * @return
	 */
	public List<Resource> getResources(Module module) {
		return getDao().getResources(module);
	}

	/**
	 * @param perfil
	 * @return
	 */
	public List<Module> list(Perfil perfil) {
		return getDao().list(perfil);
	}
}
