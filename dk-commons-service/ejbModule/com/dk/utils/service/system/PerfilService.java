/**
 * 
 */
package com.dk.utils.service.system;

import java.util.List;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.persistence.system.PerfilDAO;
import com.dk.utils.service.common.GenericService;

/**
 * @author eduardo
 * 
 */
public class PerfilService extends GenericService<Perfil> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -7467138247997581638L;

	/* (non-Javadoc)
	 * @see com.dk.utils.service.common.GenericService#getDao()
	 */
	@Override
	public PerfilDAO getDao() {
		return (PerfilDAO) dao;
	}
	
	/**
	 * Lista módulos disponiveis para o perfil.
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public List<Module> getModules(Perfil p) throws Exception {
		return getDao().getModules(p);
	}
}
