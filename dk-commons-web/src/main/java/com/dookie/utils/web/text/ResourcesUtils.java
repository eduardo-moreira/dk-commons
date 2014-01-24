package com.dookie.utils.web.text;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Classe utilitária para capturar propriedades de arquivos.
 * 
 * @author eduardo
 * 
 */
public class ResourcesUtils {

	/**
	 * Variavel que armazena os menus.
	 */
	private static final String MENU_VAR = "menus";

	/**
	 * Variavel que armazena os labels de formularios.
	 */
	private static final String FORM_LABEL_VAR = "forms";

	/**
	 * Recupera o label de um menu.
	 * 
	 * @param menuId
	 *            id do menu
	 * 
	 * @return String
	 */
	public static String getFormLabel(String label) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, FORM_LABEL_VAR);
		
		try {
			String value = bundle.getString(label);
			return value;
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Recupera o label de um menu.
	 * 
	 * @param menuId
	 *            id do menu
	 * 
	 * @return String
	 */
	public static String getMenuLabel(String menuId) {

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, MENU_VAR);
		
		try {
			
			String value = bundle.getString(menuId);
			return value;
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Recupera o icone de um menu.
	 * 
	 * @param menuId
	 *            id do menu
	 * 
	 * @return String
	 */
	public static String getMenuIcon(String menuId) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, MENU_VAR);
		
		try {
			String value = bundle.getString(menuId + ".icon");
			return value;
		}
		catch(Exception e) {
			return null;
		}
	}
}