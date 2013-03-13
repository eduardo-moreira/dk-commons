/**
 * 
 */
package com.dk.utils.web.controller.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.el.ExpressionFactory;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.dk.utils.domain.system.Resource;
import com.dk.utils.domain.system.ResourceType;
import com.dk.utils.domain.system.TargetType;
import com.dk.utils.domain.system.User;
import com.dk.utils.web.text.ResourcesUtils;

/**
 * @author eduardo
 * 
 */
@Named
@SessionScoped
public class MenuBean implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -7817545216278776544L;

	private long uniqueID = 0L;

	/**
	 * 
	 * @param menus
	 * @return
	 */
	public MenuModel getMenuModel(User user) {

		MenuModel model = new DefaultMenuModel();

		createSystemMenu(model, user);

		// Montando Mapa de caminhos de recurso
		Map<String, List<Resource>> itens = new TreeMap<String, List<Resource>>();

		for (Resource resource : user.getPerfil().getResources(ResourceType.FORM)) {
			
			System.out.println(".." + resource.getName());

			String key = resource.getMenuPath();

			// Ignorando outros recursos (nao forms) do sistema.
			if (!StringUtils.isEmpty(key)) {

				// Pegando até o último nivel
				if (key.indexOf(';') > -1) {
					key = key.substring(0, key.lastIndexOf(';'));
				}
				
				List<Resource> itensAux = itens.get(key);

				// vendo se tem algo para o caminho
				if (itensAux == null) {
					itensAux = new ArrayList<Resource>();
					itens.put(key, itensAux);
				}

				// Evitando itens duplicados
				if (!itensAux.contains(resource)) {
					resource.setDisplayLabel(getItemLabel(resource));
					itensAux.add(resource);
				}
			}
		}

		Map<String, Submenu> submenus = new HashMap<String, Submenu>();

		for (String key : itens.keySet()) {

			String[] path = key.split(";");
			Submenu parent = null;
			String source = "";
			Submenu submenu = null;

			for (String p : path) {

				submenu = submenus.get(p);

				if (source.length() > 0) {
					source += "." + p;
				} else {
					source += p;
				}

				// Caso ainda nao tenha sido criado
				if (submenu == null) {
					if (parent == null) {
						submenu = addSubMenu(model, source, null);
					} else {
						submenu = addSubMenu(parent, source, null);
					}
				}

				submenus.put(source, submenu);

				parent = submenu;

			}

			// Adicionando itens
			List<Resource> resources = itens.get(key);

			if (resources != null) {

				// Ordenando pelo nome do menu
				Collections.sort(resources, new Comparator<Resource>() {

					@Override
					public int compare(Resource f1, Resource f2) {
						return f1.getDisplayLabel().compareToIgnoreCase(f2.getDisplayLabel());
					}
				});

				for (Resource resource : resources) {
					addMenuItem(submenu, resource);
				}
			}
		}

		return model;
	}

	/**
	 * Cria um novo system Menu.
	 * 
	 * @param model
	 */
	private void createSystemMenu(MenuModel model, User user) {

		Submenu mSystem = addSubMenu(model, "system", null);

		// Menu do usuário
		String[] nome = user.getNome().split(" ");
		Submenu mSystemUser = addSubMenu(mSystem, nome[0], "ui-icon-person");

		// Menu Alterar dados do usuario
		Resource resAlterarDados = new Resource("system.changeData", "#{sessionController.changeUserData}");
		resAlterarDados.setIcon("ui-icon-pencil");
		addMenuItem(mSystemUser, resAlterarDados);

		// Alterar senha
		Resource resAlterarSenha = new Resource("system.changePassword", TargetType.ACTION, "#{sessionController.changePassword}", null);
		resAlterarSenha.setIcon("ui-icon-locked");
		resAlterarSenha.setAjax(true);
		addMenuItem(mSystemUser, resAlterarSenha);

		// Menu Sair
		Resource resSair = new Resource("system.exit", TargetType.ACTION, "#{sessionController.logout}", null);
		resSair.setIcon("ui-icon-power");
		addMenuItem(mSystem, resSair);
	}

	/**
	 * Adiciona um submenu ao menu principal.
	 * 
	 * @param pMenu
	 * 
	 * @return
	 */
	public Submenu addSubMenu(MenuModel model, String label, String icon) {
		Submenu menu = createSubMenu(label, icon);
		model.addSubmenu(menu);
		return menu;
	}

	/**
	 * Adiciona um submenu a um submenu existe.
	 * 
	 * @param submenu
	 * @param label
	 * @param icon
	 * @return
	 */
	public Submenu addSubMenu(Submenu submenu, String label, String icon) {
		Submenu menu = createSubMenu(label, icon);
		submenu.getChildren().add(menu);
		return menu;
	}

	/**
	 * Cria um submenu.
	 * 
	 * @param pLabel
	 *            label
	 * 
	 * @param pIcon
	 *            icone
	 * 
	 * @return
	 */
	public Submenu createSubMenu(String pLabel, String pIcon) {
		Submenu submenu = new Submenu();
		submenu.setId("mnuItem" + uniqueID++);

		String label = ResourcesUtils.getMenuLabel(pLabel);

		if (label == null) {
			submenu.setLabel(pLabel);
		} else {
			submenu.setLabel(label);
		}

		String icon = ResourcesUtils.getMenuIcon(pLabel);

		if (icon != null) {
			submenu.setIcon(icon);
		} else if (pIcon != null) {
			submenu.setIcon(pIcon);
		}

		return submenu;
	}

	/**
	 * Recupera o label de um item de menu.
	 * 
	 * @param resource
	 * 
	 * @return
	 */
	private String getItemLabel(Resource resource) {

		String label = null;

		if (resource.getMenuPath() != null) {
			label = ResourcesUtils.getMenuLabel(resource.getMenuPath().replaceAll(";", "."));
		} else {
			label = ResourcesUtils.getMenuLabel(resource.getName());
		}

		if (label == null) {
			label = resource.getName();
		}

		return label;

	}

	/**
	 * 
	 * @param submenu
	 * @param resource
	 */
	public MenuItem createMenuItem(Resource resource) {

		MenuItem item = new MenuItem();
		item.setId("mnuItem" + uniqueID++);

		if (resource.getDisplayLabel() == null) {
			item.setValue(getItemLabel(resource));
		} else {
			item.setValue(resource.getDisplayLabel());
		}

		if (resource.getIcon() != null) {
			item.setIcon(resource.getIcon());
		}

		// Ação JSF
		if (resource.getTargetType() == TargetType.ACTION) {

			String target = resource.getTarget();

			if (!target.startsWith("#{")) {
				target = "#{" + target + ".createView}";
			}

			final Class<?>[] paramTypes = new Class<?>[0];

			FacesContext fc = FacesContext.getCurrentInstance();
			ExpressionFactory ef = fc.getApplication().getExpressionFactory();
			item.setActionExpression(ef.createMethodExpression(fc.getELContext(), target, String.class, paramTypes));
		}
		// Link
		else {
			item.setUrl(resource.getTarget());
		}

		if (resource.isAjax() != null && resource.isAjax()) {
			item.setAjax(true);
		} else {
			item.setAjax(false);
		}

		if (!item.isAjax()) {
			item.setUpdate("@all");
		}

		return item;
	}

	/**
	 * Adiciona um item de menu a um submenu.
	 * 
	 * @param submenu
	 * @param resource
	 */
	public void addMenuItem(Submenu submenu, Resource resource) {
		submenu.getChildren().add(createMenuItem(resource));
	}

	/**
	 * Adiciona um item de menu ao root.
	 * 
	 * @param submenu
	 * @param resource
	 */
	public void addMenuItem(MenuModel menuModel, Resource resource) {
		menuModel.addMenuItem(createMenuItem(resource));
	}

}
