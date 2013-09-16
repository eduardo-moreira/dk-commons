package com.dk.utils.service.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.dk.utils.annotations.History;
import com.dk.utils.domain.system.EntityOperationType;
import com.dk.utils.domain.system.User;
import com.dk.utils.persistence.common.GenericDAO;
import com.dk.utils.persistence.system.resource.EntityHistoryDAO;
import com.dk.utils.reflection.ReflectionUtils;
import com.dk.utils.service.system.resource.EntityHistoryService;

/**
 * Classe de Serviço Genérico, que irá executar ações padrão de uma entidade.
 * 
 * @author eduardo
 * @param <T>
 */
public class GenericService<T> implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 5772803465752621277L;

	/**
	 * Service Factory.
	 */
	protected GenericServiceFactory serviceFactory;

	/**
	 * {@link GenericDAO}.
	 */
	protected GenericDAO<T> dao;

	/**
	 * Usuário que esta executando o serviço.<br>
	 * O usuário já foi validado pela interface que o chama.
	 */
	protected User currentUser;

	/**
	 * Logger.
	 */
	protected Logger logger;

	/**
	 * Cria uma nova instancia de GenericService.
	 */
	public GenericService() {
		super();
		logger = Logger.getLogger(this.getClass());
	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Recupera o valor da propriedade serviceFactory.
	 * 
	 * @return serviceFactory
	 */
	public GenericServiceFactory getServiceFactory() {
		return serviceFactory;
	}

	/**
	 * Atribui valor a propriedade serviceFactory.
	 * 
	 * @param serviceFactory
	 *            novo valor para serviceFactory
	 */
	public void setServiceFactory(GenericServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * Recupera o valor da propriedade dao.
	 * 
	 * @return dao
	 */
	public GenericDAO<T> getDao() {
		return dao;
	}

	/**
	 * Atribui valor a propriedade dao.
	 * 
	 * @param dao
	 *            novo valor para dao
	 */
	@SuppressWarnings("unchecked")
	public void setDao(GenericDAO<?> dao) {
		this.dao = (GenericDAO<T>) dao;
	}

	/**
	 * Recupera o valor da propriedade currentUser.
	 * 
	 * @return currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Atribui valor a propriedade currentUser.
	 * 
	 * @param currentUser
	 *            novo valor para currentUser
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Salva uma entidade no banco de dados
	 * 
	 * @param obj
	 *            objeto
	 * @throws Exception
	 */
	public T save(T obj) throws Exception {

		boolean newReg = (ReflectionUtils.getIdValue(obj) == 0);

		// Executando chamada
		obj = dao.merge(obj);

		// Salvando histórico, caso
		if (obj.getClass().isAnnotationPresent(History.class)) {
			EntityHistoryService service = new EntityHistoryService();
			service.dao = new EntityHistoryDAO();
			service.dao.setManager(dao.getManager());
			service.insertHistory(currentUser, obj, newReg ? EntityOperationType.INSERT : EntityOperationType.UPDATE);
		}

		return obj;
	}

	/**
	 * Remove entity from database.
	 * 
	 * @param obj
	 */
	public void remove(T obj) throws Exception {

		// Removendo
		dao.remove(obj);

		// Salvando histórico, caso
		if (obj.getClass().isAnnotationPresent(History.class)) {
			EntityHistoryService service = new EntityHistoryService();
			service.dao = new EntityHistoryDAO();
			service.dao.setManager(dao.getManager());
			service.insertHistory(currentUser, obj, EntityOperationType.DELETE);
		}
	}

	/**
	 * Carrega um objeto a partir de seu id
	 * 
	 * @param id
	 * @return
	 */
	public T load(String id) {
		return dao.load(id);
	}

	/**
	 * Carrega um objeto a partir de seu id
	 * 
	 * @param id
	 * @return
	 */
	public T load(long id) {
		return dao.load(id);
	}

	/**
	 * Lista todos os registros.<br>
	 * Caso a entidade possua Estabelecimento para filtro, o estabelecimento do
	 * usuário dono do serviço sera injetado na pesquisa.
	 * 
	 * @return
	 */
	public List<T> listAll() throws Exception {
		return dao.listAll();
	}

	/**
	 * Lista todos os registros encontrados para o filtro da entidade.
	 * 
	 * @param filterRecord
	 *            filtro (entidade)
	 * 
	 * @param any
	 *            caso seja true, irá usar OR entre as condições, caso contrário
	 *            AND.
	 * 
	 * @return
	 */
	public List<T> list(T filterRecord, boolean any) throws Exception {
		return dao.list(filterRecord, any);
	}

	/**
	 * Conta quantos registros uma coleçao de uma classe possui para um
	 * determindo id.
	 * 
	 * @param id
	 * @param collection
	 * @return
	 */
	public int countCollection(long id, String collection) {
		return dao.countCollection(id, collection);
	}
	
	/**
	 * Conta quantos registros uma coleçao de uma classe possui para um
	 * determindo id.
	 * 
	 * @param id
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getCollection(long id, String collection) {
		return dao.getCollection(id, collection);
	}

	/**
	 * Loga uma mensagem no nivel de debug.
	 * 
	 * @param message
	 * @param params
	 */
	protected void logDebug(String message, Object... params) {

		if (message == null) {
			return;
		}

		if (params != null) {
			message = MessageFormat.format(message, params);
		}

		this.logger.debug(this.getClass().getSimpleName() + " --> " + message);
	}

	/**
	 * Loga uma mensagem no nivel de error.
	 * 
	 * @param message
	 * @param params
	 */
	protected void logError(String message, Object... params) {

		if (params != null) {
			message = MessageFormat.format(message, params);
		}

		this.logger.error(this.getClass().getSimpleName() + " --> " + message);
	}

}