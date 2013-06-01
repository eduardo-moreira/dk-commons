/**
 * 
 */
package com.dk.utils.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eduardo
 * 
 */
public class MenuGroup implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3279465647257125583L;

	/**
	 * Id.
	 */
	private String id;

	/**
	 * Label.
	 */
	private String label;

	/**
	 * Itens.
	 */
	private List<MenuItem> itens;

	/**
	 * Recupera o valor da propriedade id.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Atribui valor a propriedade id.
	 * 
	 * @param id
	 *            novo valor para id
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * Recupera o valor da propriedade itens.
	 * 
	 * @return itens
	 */
	public List<MenuItem> getItens() {
		return itens;
	}

	/**
	 * Atribui valor a propriedade itens.
	 * 
	 * @param itens
	 *            novo valor para itens
	 */
	public void setItens(List<MenuItem> itens) {
		this.itens = itens;
	}

	/**
	 * Adiicona um item.
	 */
	public void addItem(MenuItem item) {
		if (itens == null) {
			itens = new ArrayList<>();
		}

		itens.add(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		MenuGroup other = (MenuGroup) obj;

		return id.equals(other.id);
	}
}
