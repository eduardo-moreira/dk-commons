/**
 * 
 */
package com.dk.utils.web.controller;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import com.dk.utils.annotations.History;
import com.dk.utils.domain.system.EntityHistory;
import com.dk.utils.reflection.ReflectionUtils;
import com.dk.utils.service.common.GenericService;
import com.dk.utils.service.system.resource.EntityHistoryService;
import com.dk.utils.web.message.MessageUtils;

/**
 * ViewController genérico.<br>
 * Ao extender esta classe um controller irá ter as seguintes funcionalidades:
 * <ul>
 * <li>Listar
 * <li>Procurar
 * <li>Inserir (com ou sem histórico)
 * <li>Atualizar (com ou sem histórico)
 * <li>Remover (com ou sem histórico)
 * <li>Listar histórico
 * <li>Listar recentes
 * </ul>
 * 
 * @author eduardo
 * 
 * @param <T>
 * 
 */
public abstract class MobileViewController<T> extends BasicController {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 7067338252712326535L;

	/**
	 * Lista de formulários.
	 */
	protected List<T> records;

	/**
	 * Registro de edição atual.
	 */
	protected T currentRecord;

	/**
	 * Lista de historico.
	 */
	protected List<EntityHistory> history;

	/**
	 * Lista de historico recente.
	 */
	protected List<EntityHistory> recentHistory;

	/**
	 * Serviço.
	 */
	protected GenericService<T> service;

	/**
	 * Tipo da entidade.
	 */
	protected Class<T> tipoEntidade;

	/**
	 * Recupera o valor da propriedade history.
	 * 
	 * @return history
	 */
	public List<EntityHistory> getHistory() {
		return history;
	}

	/**
	 * Atribui valor a propriedade history.
	 * 
	 * @param history
	 *            novo valor para history
	 */
	public void setHistory(List<EntityHistory> history) {
		this.history = history;
	}

	/**
	 * Recupera o valor da propriedade recentHistory.
	 * 
	 * @return recentHistory
	 */
	public List<EntityHistory> getRecentHistory() {
		return recentHistory;
	}

	/**
	 * Atribui valor a propriedade recentHistory.
	 * 
	 * @param recentHistory
	 *            novo valor para recentHistory
	 */
	public void setRecentHistory(List<EntityHistory> recentHistory) {
		this.recentHistory = recentHistory;
	}

	/**
	 * Recupera o valor da propriedade records.
	 * 
	 * @return records
	 */
	public List<?> getRecords() {
		return records;
	}

	/**
	 * Atribui valor a propriedade records.
	 * 
	 * @param records
	 *            novo valor para records
	 */
	public void setRecords(List<T> records) {
		this.records = records;
	}

	/**
	 * Recupera o valor da propriedade currentRecord.
	 * 
	 * @return currentRecord
	 */
	public T getCurrentRecord() {
		return currentRecord;
	}

	/**
	 * Atribui valor a propriedade currentRecord.
	 * 
	 * @param currentRecord
	 *            novo valor para currentRecord
	 */
	public void setCurrentRecord(T currentRecord) {
		this.currentRecord = currentRecord;
	}

	/**
	 * Recupera o serviço que irá controlar as operações.
	 * 
	 * @return
	 */
	protected abstract GenericService<T> getService() throws Exception;

	/**
	 * Seta o service.
	 */
	@PostConstruct
	public void setService() {
		try {
			service = getService();
			this.service.setCurrentUser(this.sessionBean.getUser());
		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Recupera a classe atual de trabalho.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {

		if (tipoEntidade != null) {
			return tipoEntidade;
		}

		Class<?> atual = getClass();

		while (true) {
			try {
				tipoEntidade = (Class<T>) ((ParameterizedType) atual.getGenericSuperclass()).getActualTypeArguments()[0];
				return tipoEntidade;
			} catch (Exception e) {
				atual = atual.getClass().getSuperclass();

				if (atual == Object.class) {
					return null;
				}
			}
		}
	}

	/**
	 * Cria a tela.
	 * 
	 * @return
	 */
	public String createListView() {

		try {

			// Atualizando usuario no service
			service.setCurrentUser(getUser());

			logDebug("Exibindo tela de {0}", getEntityClass().getSimpleName());

			// Listando forms existentes
			listAll();

			// Zerando o registro de edição/busca atual.
			currentRecord = getEntityClass().newInstance();

			super.createView();

		} catch (Exception e) {
			return gotoErrorPage(e);
		}

		return getViewConfig().listView();
	}

	/**
	 * Lista todos os registros.
	 * 
	 * @return lista de registros
	 * 
	 * @throws Exception
	 *             erro de execução
	 */
	protected void listAll() throws Exception {

		try {

			logDebug("Listando todos {0}", getEntityClass().getSimpleName());

			service.setCurrentUser(getUser());
			records = service.list(getEntityClass().newInstance(), true);

			logDebug("Pesquisa realizada com sucesso!");

		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Exibe o formulário de novo registro.
	 * 
	 * @return String
	 */
	public String createInsertView() {

		try {
			currentRecord = getEntityClass().newInstance();
			logDebug("Exibindo tela para inserir {0}", getEntityClass().getSimpleName());
		} catch (Exception e) {
			handleErrors(e);
		}

		return getViewConfig().editView();
	}

	/**
	 * Exibe o formulário para editar um registro.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public String load(long id) {

		try {

			logDebug("Carregando registro {0} : {1}", getEntityClass().getSimpleName(), id);

			userTransaction.begin();

			currentRecord = getService().load(id);

			logDebug("Registro carregado, direcionando para tela de edição.");

			return getViewConfig().editView();

		} catch (Exception e) {

			handleErrors(e);

			// Exibindo tela de pesquisa com mensagem
			return getViewConfig().listView();
		}

	}

	/**
	 * Salva o registro atual.<br>
	 * Caso o id seja ZERO irá criar um novo registro.<br>
	 * Caso seja diferente, atualiza o registro já existente.<br>
	 * Após gravar o registro com sucesso, realiza nova busca para atualizar
	 * tabela de pesquisa.
	 */
	public String salvar() {

		try {

			// Atualizando usuario no service
			service.setCurrentUser(getUser());

			// Salvando formulário na base de dados
			userTransaction.begin();

			long pId = ReflectionUtils.getIdValue(currentRecord);

			logDebug("Salvando registro de {0}; id: {1}...", getEntityClass().getSimpleName(), pId);

			// Salvando entidade
			service.save(currentRecord);

			// Commit da transacao
			userTransaction.commit();

			logDebug("Registro salvo com sucesso!");

			// Atualizando listagem
			listAll();

			// Limpando atual
			currentRecord = null;

			// Adicionando mensagem de sucesso
			message = MessageUtils.addInformationMessage("saveSuccess");

			return getViewConfig().listView();

		} catch (Exception be) {

			handleErrors(be);

			return getViewConfig().editView();
		}
	}

	/**
	 * Remove o regitro selecionado.<br>
	 * Atualiza a lista de registros.<br>
	 * Remove seleção de registro.
	 */
	public String remover() {

		try {

			// Atualizando usuario no service
			service.setCurrentUser(getUser());

			// Removendo
			userTransaction.begin();

			long pId = ReflectionUtils.getIdValue(currentRecord);

			logDebug("Removendo registro de {0}; id: {1}...", getEntityClass().getSimpleName(), pId);

			service.remove(currentRecord);

			// Commit da transacao
			userTransaction.commit();

			logDebug("Registro removido com sucesso!");

			// Atualizando listagem
			listAll();

			// Limpando atual
			currentRecord = null;

			// Adicionando mensagem de sucesso
			message = MessageUtils.addInformationMessage("removeSuccess");

			return getViewConfig().listView();

		} catch (Exception be) {

			handleErrors(be);

			return getViewConfig().editView();
		}

	}

	/**
	 * Exibe histórico do registro selecionado.
	 */
	public void showHistory() {
		try {
			EntityHistoryService service = serviceFactory.getEntityHistoryService();

			logDebug("Carregando historico de {0}...", getEntityClass().getSimpleName());

			history = service.listHistory(getEntityClass(), ReflectionUtils.getIdValue(currentRecord));

			logDebug("Historico carregado com sucesso!");

		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Carrega a lista de registros recentes.
	 * 
	 * @param classe
	 * @param action
	 * @param update
	 */
	protected void loadRecentHistory(Class<?> classe, String action, String update) {

		if (getEntityClass().isAnnotationPresent(History.class)) {

			try {
				EntityHistoryService service = serviceFactory.getEntityHistoryService();

				logDebug("Carregando lista de {0} recentes...", getEntityClass().getSimpleName());

				recentHistory = service.recentHistory(classe, getUser(), 5);

				logDebug("Lista carregada com sucesso!");

			} catch (Exception e) {
				handleErrors(e);
			}
		}
	}

}
