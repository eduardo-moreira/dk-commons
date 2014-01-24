/**
 * 
 */
package com.dookie.utils.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.el.ExpressionFactory;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.dookie.utils.domain.system.Resource;
import com.dookie.utils.domain.system.ResourceType;
import com.dookie.utils.domain.system.TargetType;
import com.dookie.utils.domain.system.User;
import com.dookie.utils.web.text.ResourcesUtils;

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

	/**
	 * Menu do usuário.
	 */
	private List<MenuGroup> userMenu;

	/**
	 * Recupera o valor da propriedade userMenu.
	 * 
	 * @return userMenu
	 */
	public List<MenuGroup> getUserMenu() {
		return userMenu;
	}

	/**
	 * Atribui valor a propriedade userMenu.
	 * 
	 * @param userMenu
	 *            novo valor para userMenu
	 */
	public void setUserMenu(List<MenuGroup> userMenu) {
		this.userMenu = userMenu;
	}

	public void createUserMenu(User user, String dominio) {

		try {
			userMenu = new ArrayList<>();

			List<Resource> userResources = user.getPerfil().getResources(ResourceType.FORM, dominio);

			createSystemMenu(userResources, user);

			for (Resource resource : userResources) {

				String key = resource.getMenuPath();

				// Ignorando outros recursos (nao forms) do sistema.
				if (!StringUtils.isEmpty(key)) {

					key = key.split(";")[0];

					MenuGroup aux = new MenuGroup();
					aux.setId(key);

					int pos = userMenu.indexOf(aux);

					if (pos > -1) {
						aux = userMenu.get(pos);
					} else {
						aux.setLabel(ResourcesUtils.getMenuLabel(key));
						userMenu.add(aux);
					}

					MenuItem item = new MenuItem();
					item.setAction(resource.getTarget());
					item.setLabel(ResourcesUtils.getMenuLabel(resource.getMenuPath().replaceAll(";", ".")));
					aux.addItem(item);

				}
			}

			// Ordenando pelo nome do menu
			Collections.sort(userMenu, new Comparator<MenuGroup>() {

				@Override
				public int compare(MenuGroup f1, MenuGroup f2) {
					return f1.getLabel().compareToIgnoreCase(f2.getLabel());
				}
			});

			// Ordenando submenus
			for (MenuGroup group : userMenu) {

				// Ordenando pelo nome do menu
				Collections.sort(group.getItens(), new Comparator<MenuItem>() {

					@Override
					public int compare(MenuItem f1, MenuItem f2) {
						return f1.getLabel().compareToIgnoreCase(f2.getLabel());
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clear() {
		this.userMenu = null;
	}

	public String execute(String action) {

		// Ação JSF
		// if (resource.getTargetType() == TargetType.ACTION) {
		//
		// String target = resource.getTarget();
		//
		// if (!target.startsWith("#{")) {
		// target = "#{" + target + ".createView}";
		// }
		//
		// final Class<?>[] paramTypes = new Class<?>[0];
		//
		// FacesContext fc = FacesContext.getCurrentInstance();
		// ExpressionFactory ef = fc.getApplication().getExpressionFactory();
		// item.setActionExpression(ef.createMethodExpression(fc.getELContext(),
		// target, String.class, paramTypes));
		// }
		// // Link
		// else {
		// item.setUrl(resource.getTarget());
		// }
		//
		// Ação JSF
		final Class<?>[] paramTypes = {};
		
		if (action.split("\\.").length == 1) {
			action += ".createListView";
		}
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExpressionFactory ef = fc.getApplication().getExpressionFactory();
		return (String) ef.createMethodExpression(fc.getELContext(), "#{" + action + "}", String.class, paramTypes).invoke(fc.getELContext(), null);
	}

	/**
	 * Cria um novo system Menu.
	 * 
	 * @param model
	 */
	private void createSystemMenu(List<Resource> userResources, User user) {

		if (userResources == null) {
			userResources = new ArrayList<>();
		}

		// Menu Alterar dados do usuario
		Resource resAlterarDados = new Resource("system.changeData", "#{sessionController.changeUserData}");
		resAlterarDados.setIcon("ui-icon-pencil");
		userResources.add(resAlterarDados);

		// Alterar senha
		Resource resAlterarSenha = new Resource("system.changePassword", TargetType.ACTION, "#{sessionController.changePassword}", null);
		resAlterarSenha.setIcon("ui-icon-locked");
		resAlterarSenha.setAjax(Boolean.valueOf(true));
		userResources.add(resAlterarSenha);

		// Menu Sair
		Resource resSair = new Resource("system.exit", TargetType.ACTION, "#{loginController.logout}", null);
		resSair.setIcon("ui-icon-power");
		resSair.setAjax(Boolean.valueOf(false));
		userResources.add(resSair);
	}

	// String icon = ResourcesUtils.getMenuIcon(pLabel);

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

	private String getLabel(String pLabel) {
		String label = ResourcesUtils.getMenuLabel(pLabel);

		if (label == null) {
			return pLabel;
		} else {
			return label;
		}
	}

}
