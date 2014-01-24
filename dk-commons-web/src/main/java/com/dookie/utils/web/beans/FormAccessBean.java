/**
 * 
 */
package com.dookie.utils.web.beans;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;

import com.dookie.utils.web.controller.ViewConfig;

/**
 * @author eduardo
 * 
 */
public class FormAccessBean {

	/**
	 * Classe.
	 */
	private Class<?> classe;

	/**
	 * Nome do control gerado.
	 */
	private String controlName;

	/**
	 * Link a ser chamado.
	 */
	private String url;

	/**
	 * Flag de acesso.
	 */
	private boolean permite;

	/**
	 * 
	 * Cria uma nova instancia de FormAccessBean.
	 */
	public FormAccessBean(String pUrl, String pControl) {
		url = pUrl;
		controlName = pControl;
	}

	/**
	 * 
	 * Cria uma nova instancia de FormAccess.
	 */
	public FormAccessBean(Class<?> pClass) {

		// Setando classe
		classe = pClass;

		// Setando nome do control
		Named named = this.getClass().getAnnotation(Named.class);
		if (named != null) {
			controlName = named.value();
		}

		if (controlName == null || controlName.length() == 0) {
			controlName = classe.getSimpleName();
			controlName = controlName.substring(0, 1).toLowerCase().concat(controlName.substring(1));
		}

		// Setando url
		ViewConfig viewConfig = classe.getAnnotation(ViewConfig.class);
		url = viewConfig.page();

		// Default: nega acesso
		permite = false;

	}

	/**
	 * Recupera o valor da propriedade classe.
	 * 
	 * @return classe
	 */
	public Class<?> getClasse() {
		return classe;
	}

	/**
	 * Atribui valor a propriedade classe.
	 * 
	 * @param classe
	 *            novo valor para classe
	 */
	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	/**
	 * Recupera o valor da propriedade controlName.
	 * 
	 * @return controlName
	 */
	public String getControlName() {
		return controlName;
	}

	/**
	 * Atribui valor a propriedade controlName.
	 * 
	 * @param controlName
	 *            novo valor para controlName
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	/**
	 * Recupera o valor da propriedade url.
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Atribui valor a propriedade url.
	 * 
	 * @param url
	 *            novo valor para url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Recupera o valor da propriedade permite.
	 * 
	 * @return permite
	 */
	public boolean isPermite() {
		return permite;
	}

	/**
	 * Atribui valor a propriedade permite.
	 * 
	 * @param permite
	 *            novo valor para permite
	 */
	public void setPermite(boolean permite) {
		this.permite = permite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		
		if (obj instanceof String) {
			String aux = (String) obj;
			return url.equals(aux) || controlName.equals(aux);
		}

		FormAccessBean other = (FormAccessBean) obj;
		return other.controlName.equals(controlName) || other.url.equals(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return super.toString();
		}
	}
}
