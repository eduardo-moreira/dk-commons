/**
 * 
 */
package com.dk.utils.web.controller;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.primefaces.model.SelectableDataModel;

import com.dk.utils.annotations.History;
import com.dk.utils.domain.system.EntityHistory;
import com.dk.utils.reflection.ReflectionUtils;
import com.dk.utils.service.common.GenericService;
import com.dk.utils.service.system.resource.EntityHistoryService;
import com.dk.utils.web.message.MessageUtils;
import com.dk.utils.web.text.ResourcesUtils;

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
public abstract class GenericViewController<T> extends BasicController {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 7067338252712326535L;

	/**
	 * Lista de formulários.
	 */
	protected List<T> records;

	/**
	 * {@link SelectableDataModel}.
	 */
	protected SelectableDataModel<T> model;

	/**
	 * Registro de edição atual.
	 */
	protected T currentRecord;

	/**
	 * Registro para realizar a pesquisa.
	 */
	protected T filterRecord;

	/**
	 * Flag para pesquisar em qualquer campo.
	 */
	protected boolean anyFieldSearch;

	/**
	 * Registro selecionado na tabela de pesquisa.
	 */
	protected T selectedRecord;

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
	 * Controla a barra de menu.
	 */
	private MenuModel recentMenuModel;

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
	 * Recupera o valor da propriedade recentMenuModel.
	 * 
	 * @return recentMenuModel
	 */
	public MenuModel getRecentMenuModel() {
		return recentMenuModel;
	}

	/**
	 * Atribui valor a propriedade recentMenuModel.
	 * 
	 * @param recentMenuModel
	 *            novo valor para recentMenuModel
	 */
	public void setRecentMenuModel(MenuModel recentMenuModel) {
		this.recentMenuModel = recentMenuModel;
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
	 * Recupera o valor da propriedade model.
	 * 
	 * @return model
	 */
	public SelectableDataModel<T> getModel() {
		return model;
	}

	/**
	 * Atribui valor a propriedade model.
	 * 
	 * @param model
	 *            novo valor para model
	 */
	public void setModel(SelectableDataModel<T> model) {
		this.model = model;
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
	 * Recupera o valor da propriedade filterRecord.
	 * 
	 * @return filterRecord
	 */
	public T getFilterRecord() {
		return filterRecord;
	}

	/**
	 * Atribui valor a propriedade filterRecord.
	 * 
	 * @param filterRecord
	 *            novo valor para filterRecord
	 */
	public void setFilterRecord(T filterRecord) {
		this.filterRecord = filterRecord;
	}

	/**
	 * Recupera o valor da propriedade anyFieldSearch.
	 * 
	 * @return anyFieldSearch
	 */
	public boolean isAnyFieldSearch() {
		return anyFieldSearch;
	}

	/**
	 * Atribui valor a propriedade anyFieldSearch.
	 * 
	 * @param anyFieldSearch
	 *            novo valor para anyFieldSearch
	 */
	public void setAnyFieldSearch(boolean anyFieldSearch) {
		this.anyFieldSearch = anyFieldSearch;
	}

	/**
	 * Recupera o valor da propriedade selectedRecord.
	 * 
	 * @return selectedRecord
	 */
	public T getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * Atribui valor a propriedade selectedRecord.
	 * 
	 * @param selectedRecord
	 *            novo valor para selectedRecord
	 */
	public void setSelectedRecord(T selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * Recupera o serviço que irá controlar as operações.
	 * 
	 * @return
	 */
	protected abstract GenericService<T> getService() throws Exception;

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
	 * Exibe o dialogo para editar/inserir registros.<br>
	 * Default: dialog.show();
	 */
	protected void showEditDialog() {
		RequestContext.getCurrentInstance().execute("dialog.show()");
	}

	/**
	 * Oculta o dialogo para editar/inserir registros.<br>
	 * Default: dialog.show();
	 */
	protected void hideEditDialog() {
		RequestContext.getCurrentInstance().execute("dialog.hide()");
	}

	/**
	 * Exibe o dialogo para remover de registro.<br>
	 * Default: confirmRemove.show();
	 */
	protected void showRemoveDialog() {
		RequestContext.getCurrentInstance().execute("confirmRemove.show()");
	}

	/**
	 * Cria a tela.
	 * 
	 * @return
	 */
	public String createView() {

		try {

			// Atualizando usuario no service
			service.setCurrentUser(getUser());

			logDebug("Exibindo tela de {0}", getEntityClass().getSimpleName());

			// Listando forms existentes
			listAll();

			// Zerando o registro de edição/busca atual.
			currentRecord = getEntityClass().newInstance();
			filterRecord = getEntityClass().newInstance();

			// Carrendo historico, caso esteja ativado
			loadRecentHistory();

			super.createView();

		} catch (Exception e) {
			return gotoErrorPage(e);
		}

		return getViewConfig().page();
	}

	/**
	 * Lista todos os registros.
	 * 
	 * @return lista de registros
	 * 
	 * @throws Exception
	 *             erro de execução
	 */
	public void listAll() throws Exception {

		logDebug("Listando todos {0}", getEntityClass().getSimpleName());

		filterRecord = null;
		search();
		filterRecord = getEntityClass().newInstance(); // evitando null pointer

		logDebug("Listou todos os registros");
	}

	/**
	 * Realiza a busca de acordo com o filtro selecionado.<br>
	 * Caso não tenha um filtro (null) retorna a lista de todos os registros.
	 */
	public void search() {

		try {

			logDebug("Pesquisando por {0} com filtro.", getEntityClass().getSimpleName());

			service.setCurrentUser(getUser());
			records = service.list(filterRecord, anyFieldSearch);
			model = new DataModel();

			logDebug("Pesquisa realizada com sucesso!");

		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Retorna se a flag para salvar historico esta ativa.
	 * 
	 * @return
	 */
	public boolean isActivatedHistory() {
		return getEntityClass().isAnnotationPresent(History.class);
	}

	/**
	 * Carrega um registro recente
	 */
	public void loadRecent(long id) {
		loadRecent(id, true);
	}

	/**
	 * Carrega um registro recente
	 */
	public void loadRecent(long id, boolean showOnComplete) {

		try {

			logDebug("Carregando registro {0} : {1}", getEntityClass().getSimpleName(), id);

			T selected = getEntityClass().newInstance();
			ReflectionUtils.setId(selected, id);
			int pos = records.indexOf(selected);

			if (pos == -1) {
				message = MessageUtils.addGenericErrorMessage();
			}

			// Selecionando
			currentRecord = (T) records.get(pos);

			// Exibindo formulário de edição
			if (showOnComplete) {
				showEditDialog();
				logDebug("Exibindo registro lido.");
			}

		} catch (Exception e) {
			handleErrors(e);
		}

	}

	/**
	 * Prepara para criar um novo formulário.
	 */
	public void prepareInsert() {
		try {
			currentRecord = getEntityClass().newInstance();
			showEditDialog();
			logDebug("Exibindo tela de novo {0}", getEntityClass().getSimpleName());
		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Prepara para criar um novo formulário.
	 */
	public void prepareEdit() {

		if (selectedRecord == null) {
			message = MessageUtils.addErrorMessage("editNoSelected");
		} else {
			try {
				userTransaction.begin();

				long pId = ReflectionUtils.getIdValue(selectedRecord);

				logDebug("Carregando dados para edição de {0}; id: {1}...", getEntityClass().getSimpleName(), pId);

				currentRecord = getService().load(pId);

				userTransaction.commit();

				showEditDialog();

				logDebug("Exibindo tela de edição.");

			} catch (Exception e) {
				handleErrors(e);
			}
		}
	}

	/**
	 * Salva o registro atual.<br>
	 * Caso o id seja ZERO irá criar um novo registro.<br>
	 * Caso seja diferente, atualiza o registro já existente.<br>
	 * Após gravar o registro com sucesso, realiza nova busca para atualizar
	 * tabela de pesquisa.
	 */
	public void save() {

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
			search();

			// Limpando atual
			currentRecord = getEntityClass().newInstance();
			selectedRecord = null;

			if (filterRecord == null) {
				filterRecord = getEntityClass().newInstance();
			}

			// Carrendo historico, caso esteja ativado
			loadRecentHistory();

			// Adicionando mensagem de sucesso
			message = MessageUtils.addInformationMessage("saveSuccess");

			hideEditDialog();

		} catch (Exception be) {
			handleErrors(be);
		}
	}

	/**
	 * Valida seleção, caso tenha registro selecionado, exibe confirmação.
	 */
	public void prepareRemove() {

		long pId = ReflectionUtils.getIdValue(selectedRecord);

		if (selectedRecord == null || pId == 0L) {
			message = MessageUtils.addErrorMessage("removeNoSelected");
		} else {
			showRemoveDialog();
			logDebug("Confirmando remoçao de {0}; id: {1}...", getEntityClass().getSimpleName(), pId);
		}
	}

	/**
	 * Remove o regitro selecionado.<br>
	 * Atualiza a lista de registros.<br>
	 * Remove seleção de registro.
	 */
	public boolean remove() {

		try {

			// Atualizando usuario no service
			service.setCurrentUser(getUser());

			// Removendo
			userTransaction.begin();

			long pId = ReflectionUtils.getIdValue(currentRecord);

			logDebug("Removendo registro de {0}; id: {1}...", getEntityClass().getSimpleName(), pId);

			service.remove(selectedRecord);

			// Commit da transacao
			userTransaction.commit();

			logDebug("Registro removido com sucesso!");

			// Atualizando listagem
			search();

			// Limpando selecionado
			selectedRecord = null;

			// Carrendo historico, caso esteja ativado
			loadRecentHistory();

			// Adicionando mensagem de sucesso
			message = MessageUtils.addInformationMessage("removeSuccess");

			return true;

		} catch (Exception e) {
			handleErrors(e);
		}

		return false;
	}

	/**
	 * Exibe histórico do registro selecionado.
	 */
	public void showHistory() {
		try {
			EntityHistoryService service = serviceFactory.getEntityHistoryService();

			logDebug("Carregando historico de {0}...", getEntityClass().getSimpleName());

			history = service.listHistory(getEntityClass(), ReflectionUtils.getIdValue(selectedRecord));

			logDebug("Historico carregado com sucesso!");

		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Carrega a lista de registros recentes.
	 */
	protected void loadRecentHistory() {
		loadRecentHistory(getEntityClass(), "#{" + getBeanName() + ".loadRecent(\"?\")}", "form:display");
	}

	/**
	 * Carrega a lista de registros recentes.
	 * 
	 * @param classe
	 * @param action
	 * @param update
	 */
	protected void loadRecentHistory(Class<?> classe, String action, String update) {

		if (!isActivatedHistory()) {
			return;
		}

		try {
			EntityHistoryService service = serviceFactory.getEntityHistoryService();

			logDebug("Carregando lista de {0} recentes...", getEntityClass().getSimpleName());

			recentHistory = service.recentHistory(classe, getUser(), 5);

			logDebug("Lista carregada com sucesso!");

			// Criando ou zerando menu
			if (recentMenuModel == null) {
				recentMenuModel = new DefaultMenuModel();
			} else {
				recentMenuModel.getContents().clear();
			}

			Submenu submenu = new Submenu();
			submenu.setLabel(ResourcesUtils.getFormLabel("recents"));
			recentMenuModel.addSubmenu(submenu);

			if (recentHistory != null) {

				int uniqueID = 0;

				for (EntityHistory e : recentHistory) {
					MenuItem item = new MenuItem();
					item.setId("mnuItemRecent" + uniqueID++);
					item.setValue(e.getDescriptor());
					item.setIcon("ui-icon-triangle-1-e");

					// Ação JSF
					final Class<?>[] paramTypes = { String.class };
					String myAction = action.replaceAll("\\?", String.valueOf(e.getEntityId()));

					FacesContext fc = FacesContext.getCurrentInstance();
					ExpressionFactory ef = fc.getApplication().getExpressionFactory();
					item.setActionExpression(ef.createMethodExpression(fc.getELContext(), myAction, String.class, paramTypes));
					item.setUpdate(update);

					submenu.getChildren().add(item);
				}
			}

		} catch (Exception e) {
			handleErrors(e);
		}
	}

	/**
	 * Classe genérica para realizar o controle da tabela.
	 * 
	 * @author eduardo
	 * 
	 */
	public class DataModel extends ListDataModel<T> implements SelectableDataModel<T> {

		public DataModel() {
			this.setWrappedData(records);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
		 */
		@Override
		public T getRowData(String rowKey) {

			long id = Long.parseLong(rowKey);

			for (T reg : records) {
				if (ReflectionUtils.getIdValue(reg) == id) {
					return reg;
				}
			}

			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
		 */
		@Override
		public Object getRowKey(T obj) {
			return ReflectionUtils.getIdValue(obj);
		}

	}

}