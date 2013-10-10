/**
 * 
 */
package com.dk.utils.service.system;

import java.util.List;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.exception.BusinessException;
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

	/*
	 * (non-Javadoc)
	 * 
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

	@Override
	public Perfil save(Perfil perfil) throws Exception {

		if (perfil.getName() == null || perfil.getName().isEmpty()) {
			throw new BusinessException("perfil.nomeInvalido");
		}

		// Validando o nome do perfil
		Perfil p = loadByName(perfil.getName());

		if (p != null && p.getId() != perfil.getId()) {
			throw new BusinessException("perfil.nomeDuplicado");
		}

		return super.save(perfil);
	}

	/**
	 * Carrega um perfil pelo nome dele.
	 * 
	 * @param name
	 * 
	 * @return
	 */
	public Perfil loadByName(String name) throws Exception {

		Perfil perfilCliente = new Perfil();
		perfilCliente.setName(name);

		List<Perfil> consultaPerfil = getDao().list(perfilCliente, false);

		if (consultaPerfil == null || consultaPerfil.isEmpty()) {
			return consultaPerfil.get(0);
		}

		return null;

	}
}
