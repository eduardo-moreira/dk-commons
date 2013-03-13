/**
 * 
 */
package com.dk.utils.domain.system;

/**
 * @author eduardo
 * 
 */
public enum ResourceType {

	FORM("Formulário"), ACCESS("Acesso a Recurso"), WIDGET("Widget");

	private String label;
	
	ResourceType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
}

