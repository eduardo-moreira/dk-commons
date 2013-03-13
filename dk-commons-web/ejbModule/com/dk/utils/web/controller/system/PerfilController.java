/**
 * 
 */
package com.dk.utils.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import com.dk.utils.domain.system.Module;
import com.dk.utils.domain.system.Perfil;
import com.dk.utils.service.common.GenericService;
import com.dk.utils.web.controller.GenericViewController;
import com.dk.utils.web.controller.ViewConfig;
import com.dk.utils.web.converter.common.EntityConverter;
import com.dk.utils.web.message.MessageUtils;

/**
 * @author eduardo
 * 
 */
@Named
@SessionScoped
@ViewConfig(page = "/forms/system/perfil.jsf")
public class PerfilController extends GenericViewController<Perfil> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 4361837683109661738L;

	/**
	 * Lista de módulos disponiveis para selecionar.
	 */
	private List<Module> modulesAvaliables;

	/**
	 * Configura o elemento de tela.
	 */
	private DualListModel<Module> modulesSource;

	/**
	 * Recupera o valor da propriedade modulesAvaliables.
	 * 
	 * @return modulesAvaliables
	 */
	public List<Module> getModulesAvaliables() {
		return modulesAvaliables;
	}

	/**
	 * Atribui valor a propriedade modulesAvaliables.
	 * 
	 * @param modulesAvaliables
	 *            novo valor para modulesAvaliables
	 */
	public void setModulesAvaliables(List<Module> modulesAvaliables) {
		this.modulesAvaliables = modulesAvaliables;
	}

	/**
	 * Recupera o valor da propriedade modulesSource.
	 * 
	 * @return modulesSource
	 */
	public DualListModel<Module> getModulesSource() {
		return modulesSource;
	}

	/**
	 * Atribui valor a propriedade modulesSource.
	 * 
	 * @param modulesSource
	 *            novo valor para modulesSource
	 */
	public void setModulesSource(DualListModel<Module> modulesSource) {
		this.modulesSource = modulesSource;
	}

	/**
	 * Converter para perfil.
	 * 
	 * @return
	 */
	public EntityConverter<Module> getPerfilModuleConverter() {
		
		return new EntityConverter<Module>() {
			
			/*
			 * (non-Javadoc)
			 * 
			 * @see com.fun.web.converter.common.EntityConverter#getValues()
			 */
			@Override
			public List<Module> getValues() {
				return getModulesAvaliables();
			}

		};
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#getService()
	 */
	@Override
	protected GenericService<Perfil> getService() throws Exception {
		return serviceFactory.getPerfilService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#createView()
	 */
	@Override
	public String createView() {

		try {
			
			modulesAvaliables = serviceFactory.getModuleService().listAll();

			// Zerando lista de selecionados
			modulesSource = new DualListModel<Module>(modulesAvaliables, new ArrayList<Module>());

			// Segue o processo normal
			return super.createView();

		} catch (Exception e) {
			return gotoErrorPage(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#prepareInsert()
	 */
	@Override
	public void prepareInsert() {

		// Zerando lista de selecionados
		modulesSource = new DualListModel<Module>(modulesAvaliables, new ArrayList<Module>());

		super.prepareInsert();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#prepareEdit()
	 */
	@Override
	public void prepareEdit() {

		if (selectedRecord == null) {
			MessageUtils.addErrorMessage("editNoSelected");

		} else {
			currentRecord = selectedRecord;
			prepareFormList();
			showEditDialog();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#loadRecent(long)
	 */
	@Override
	public void loadRecent(long id) {
		super.loadRecent(id, false);
		prepareFormList();
		showEditDialog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fun.web.controller.common.GenericViewController#loadRecentHistory()
	 */
	@Override
	protected void loadRecentHistory() {
		loadRecentHistory(getEntityClass(), "#{" + getBeanName() + ".loadRecent(\"?\")}", "form:tabView1, form:tabView1:formList");
	}

	/**
	 * Prepara a lista de formulários, removendo da lista de disponiveis os já
	 * selecionados.
	 */
	private void prepareFormList() {

		try {

			// Removendo todos os itens da lista
			List<Module> avaliable = new ArrayList<Module>();

			if (modulesAvaliables != null) {
				avaliable.addAll(modulesAvaliables);
				avaliable.removeAll(currentRecord.getModules());
			}

			modulesSource = new DualListModel<Module>(avaliable, currentRecord.getModules());

		} catch (Exception e) {
			handleErrors(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#save()
	 */
	@Override
	public void save() {

		// setando na entidade a lista de perfis selecionados
		getCurrentRecord().setModules(modulesSource.getTarget());

		// Salvando
		super.save();
	}
}
