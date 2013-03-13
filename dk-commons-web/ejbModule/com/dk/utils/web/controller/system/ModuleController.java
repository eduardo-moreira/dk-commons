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
import com.dk.utils.domain.system.Resource;
import com.dk.utils.service.system.resource.ModuleService;
import com.dk.utils.service.system.resource.ResourceService;
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
@ViewConfig(page = "/forms/system/module.jsf")
public class ModuleController extends GenericViewController<Module> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 4361837683109661738L;

	/**
	 * Lista de formulários disponiveis para selecionar.
	 */
	private List<Resource> resourcesAvaliables;

	/**
	 * Configura o elemento de tela.
	 */
	private DualListModel<Resource> resourceSource;

	/**
	 * Service.
	 */
	private ModuleService service;

	/**
	 * Recupera o valor da propriedade formsAvaliables.
	 * 
	 * @return formsAvaliables
	 */
	public List<Resource> getResourcesAvaliables() {
		return resourcesAvaliables;
	}

	/**
	 * Atribui valor a propriedade formsAvaliables.
	 * 
	 * @param formsAvaliables
	 *            novo valor para formsAvaliables
	 */
	public void setResourcesAvaliables(List<Resource> formsAvaliables) {
		this.resourcesAvaliables = formsAvaliables;
	}

	/**
	 * Recupera o valor da propriedade formSource.
	 * 
	 * @return formSource
	 */
	public DualListModel<Resource> getResourceSource() {
		return resourceSource;
	}

	/**
	 * Atribui valor a propriedade formSource.
	 * 
	 * @param formSource
	 *            novo valor para formSource
	 */
	public void setResourceSource(DualListModel<Resource> formSource) {
		this.resourceSource = formSource;
	}

	/**
	 * Converter de resources.
	 * 
	 * @return
	 */
	public EntityConverter<Resource> getModuleFormConverter() {
		return new EntityConverter<Resource>() {

			@Override
			public List<Resource> getValues() {
				return getResourcesAvaliables();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#getService()
	 */
	@Override
	protected ModuleService getService() throws Exception {

		if (service == null) {
			service = serviceFactory.getModuleService();
		}

		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#createView()
	 */
	@Override
	public String createView() {

		try {
			// Preparando a lista de formulários disponiveis.
			ResourceService formService = serviceFactory.getResourceService();
			resourcesAvaliables = formService.listAll();

			resourceSource = new DualListModel<Resource>(resourcesAvaliables, new ArrayList<Resource>());

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

		resourceSource = new DualListModel<Resource>(resourcesAvaliables, new ArrayList<Resource>());

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
			prepareResourceList();
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
		prepareResourceList();
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
	private void prepareResourceList() {

		try {

			// Removendo todos os itens da lista
			List<Resource> avaliable = new ArrayList<Resource>();
			List<Resource> selected = getService().getResources(currentRecord);

			if (resourcesAvaliables != null) {
				avaliable.addAll(resourcesAvaliables);
				avaliable.removeAll(selected);
			}

			resourceSource = new DualListModel<Resource>(avaliable, selected);

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
		getCurrentRecord().setResources(resourceSource.getTarget());

		// Salvando
		super.save();
	}
}
