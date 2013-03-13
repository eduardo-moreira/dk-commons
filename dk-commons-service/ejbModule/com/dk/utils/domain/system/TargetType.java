/**
 * 
 */
package com.dk.utils.domain.system;

/**
 * @author eduardo
 * 
 */
public enum TargetType {

	LINK("Link"), ACTION("Ação");

	private String label;
	
	TargetType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
}

