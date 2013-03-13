/**
 * 
 */
package com.dk.utils.persistence.system.resource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.dk.utils.domain.system.EntityHistory;
import com.dk.utils.domain.system.User;
import com.dk.utils.persistence.common.GenericDAO;
import com.dk.utils.reflection.ReflectionUtils;

/**
 * @author eduardo
 * 
 */
public class EntityHistoryDAO extends GenericDAO<EntityHistory> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3816309904677837685L;

	/**
	 * Carrega o historico para uma entidade.
	 * 
	 * @param classe
	 * @param entityId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EntityHistory> listHistory(Class<?> classe, long entityId) {

		Query query = manager.createQuery("SELECT reg FROM EntityHistory reg WHERE reg.entityId = ?1 and reg.name = ?2 ORDER BY reg.data DESC");
		query.setParameter(1, entityId);
		query.setParameter(2, classe.getName());

		return query.getResultList();
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
	@SuppressWarnings("unchecked")
	public List<EntityHistory> listHistory(Class<?> classe, long entityId, List<String> entityOperationType) {
		
		Query query = manager.createQuery("SELECT reg FROM EntityHistory reg WHERE reg.entityId = ?1 and reg.name = ?2 and reg.entityOperationType in (?3) ORDER BY reg.data DESC");
		query.setParameter(1, entityId);
		query.setParameter(2, classe.getName());
		query.setParameter(3, entityOperationType);
		
		return query.getResultList();
	}

	/**
	 * Lista as
	 * 
	 * @param classe
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EntityHistory> recentHistory(Class<?> classe, User user, int size) {

		StringBuilder sql = new StringBuilder("SELECT distinct reg.entityId FROM EntityHistory reg WHERE reg.name = ?1 and reg.userId = ?2 ");

		sql.append(" ORDER BY reg.data DESC");
		
		Query query = manager.createQuery(sql.toString());
		query.setParameter(1, classe.getName());
		query.setParameter(2, user.getId());
		
		if (size > 0) {
			query.setMaxResults(size);
		}

		List<Long> registros = query.getResultList();
		List<EntityHistory> result = new ArrayList<>();
		
		// Carregando a descricao da entidade
		if (registros != null) {

			try {
				Field descriptor = ReflectionUtils.getDescriptor(classe);
				Field id = ReflectionUtils.getId(classe);

				// Caso tenha encontrado as anotações para definir a pesquisa
				if (descriptor != null && id != null) {
					
					// Criando parte não dinâmica da query
					StringBuilder sb = new StringBuilder();
					sb.append("SELECT obj.");
					sb.append(descriptor.getName());
					sb.append(" FROM ");
					sb.append(classe.getName());
					sb.append(" obj WHERE obj.");
					sb.append(id.getName());
					sb.append(" = ?1");
					
					for (Long registroId : registros) {
						query = manager.createQuery(sb.toString());
						query.setParameter(1, registroId);
						
						
						List<String> resultDescr = query.getResultList();
						if (resultDescr != null && resultDescr.size() > 0) {
							EntityHistory reg = new EntityHistory();
							reg.setDescriptor(resultDescr.get(0));
							reg.setEntityId(registroId);
							result.add(reg);
						}
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;
	}
}
