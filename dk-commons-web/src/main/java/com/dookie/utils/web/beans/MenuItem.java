/**
 * 
 */
package com.dookie.utils.web.beans;

import java.io.Serializable;

/**
 * @author eduardo
 * 
 */
public class MenuItem implements Serializable {

	private String label;

	private String action;

	/**
	 * Recupera o valor da propriedade label.
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Atribui valor a propriedade label.
	 * 
	 * @param label
	 *            novo valor para label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Recupera o valor da propriedade action.
	 * 
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Atribui valor a propriedade action.
	 * 
	 * @param action
	 *            novo valor para action
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
