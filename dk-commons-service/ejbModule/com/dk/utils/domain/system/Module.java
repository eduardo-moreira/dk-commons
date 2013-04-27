/**
 * 
 */
package com.dk.utils.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.dk.utils.annotations.Descriptor;
import com.dk.utils.annotations.History;

/**
 * Representa um grupo de resources que estará disponivel para um perfil.
 * 
 * @author eduardo
 * 
 */
@Entity
@History
public class Module implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -2077076969646257708L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Nome do módulo.
	 */
	@Descriptor
	private String name;

	/**
	 * Formulários.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Resource> resources;

	/**
	 * 
	 * Cria uma nova instancia de Module.
	 */
	public Module() {

	}

	/**
	 * Cria uma nova instancia de Module.
	 * 
	 * @param name
	 * @param forms
	 */
	public Module(String name, Resource... pResources) {
		super();
		this.name = name;
		this.resources = new ArrayList<>();
		Collections.addAll(this.resources, pResources);
	}

	/**
	 * Recupera o valor da propriedade id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Atribui valor a propriedade id.
	 * 
	 * @param id
	 *            novo valor para id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Recupera o valor da propriedade name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Atribui valor a propriedade name.
	 * 
	 * @param name
	 *            novo valor para name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Recupera o valor da propriedade resources.
	 * 
	 * @return resources
	 */
	public List<Resource> getResources() {
		return resources;
	}

	/**
	 * Atribui valor a propriedade resources.
	 * 
	 * @param resources
	 *            novo valor para resources
	 */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Module another = (Module) obj;
		return another.id == id;
	}

	/**
	 * Adiciona um form ao modulo.
	 * 
	 * @param f
	 */
	public void addForm(Resource f) {
		if (resources == null) {
			resources = new ArrayList<>();
		}
		resources.add(f);
	}
}
