/**
 * 
 */
package com.dookie.utils.service.system.resource;

import java.util.Date;
import java.util.List;

import com.dookie.utils.domain.system.EntityHistory;
import com.dookie.utils.domain.system.EntityOperationType;
import com.dookie.utils.domain.system.User;
import com.dookie.utils.persistence.system.resource.EntityHistoryDAO;
import com.dookie.utils.reflection.ReflectionUtils;
import com.dookie.utils.service.common.GenericService;

/**
 * @author eduardo
 * 
 */
public class EntityHistoryService extends GenericService<EntityHistory> {

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
	public EntityHistoryDAO getDao() {
		return (EntityHistoryDAO) super.getDao();
	}

	/**
	 * Insere um registro de operação de um usuário para uma entidade.
	 * 
	 * @param user
	 * @param entity
	 * @param type
	 * @throws Exception
	 */
	public void insertHistory(User user, Object entity, String type) throws Exception {
		EntityHistory reg = new EntityHistory();
		reg.setData(new Date());
		reg.setEntityOperationType(type);
		reg.setName(entity.getClass().getName());
		reg.setEntityId(ReflectionUtils.getIdValue(entity));

		if (user == null) {
			user = new User();
			user.setNome("Usuário Não Logado");
		}

		reg.setUserId(user.getId());
		reg.setUserName(user.getNome());

		getDao().insert(reg);
	}

	/**
	 * Insere um registro de operação de um usuário para uma entidade.
	 * 
	 * @param user
	 * @param entity
	 * @param type
	 * @throws Exception
	 */
	public void insertHistory(User user, Object entity, EntityOperationType type) throws Exception {
		String typeDesc = "Criado";

		if (type == EntityOperationType.DELETE) {
			typeDesc = "Removido";
		} else if (type == EntityOperationType.UPDATE) {
			typeDesc = "Atualizado";
		}

		insertHistory(user, entity, typeDesc);
	}

	/**
	 * Lista o historico de uma entidade.
	 * 
	 * @param classe
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public List<EntityHistory> recentHistory(Class<?> classe, User user, int size) throws Exception {
		return getDao().recentHistory(classe, user, size);
	}

	/**
	 * Lista o historico de uma entidade.
	 * 
	 * @param classe
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public List<EntityHistory> listHistory(Class<?> classe, long entityId) throws Exception {
		return getDao().listHistory(classe, entityId);
	}

	/**
	 * Carrega o historico para uma entidade, de acordo com os tipos passados.
	 * 
	 * @param classe
	 * 
	 * @param entityId
	 * 
	 * @param entityOperationType
	 * 
	 * @return
	 */
	public List<EntityHistory> listHistory(Class<?> classe, long entityId, List<String> entityOperationType) throws Exception {
		return getDao().listHistory(classe, entityId, entityOperationType);
	}

}
