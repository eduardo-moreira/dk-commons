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
 * Representa o perfil de acesso de um usuário ao sistema.<br>
 * Um perfil tem informações que ajudam a delimitar o acesso do usuário ao
 * sistema.<br>
 * 
 * @author eduardo
 * 
 */
@Entity
@History
public class Perfil implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 6534884699750603212L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Nome.
	 */
	@Descriptor
	private String name;

	/**
	 * Lista de módulos cadastrados para o perfil.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Module> modules;

	/**
	 * 
	 * Cria uma nova instancia de Perfil.
	 */
	public Perfil() {

	}

	/**
	 * Cria uma nova instancia de Perfil.
	 * 
	 * @param id
	 * @param name
	 */
	public Perfil(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * Cria uma nova instancia de Perfil.
	 * 
	 * @param name
	 * @param module
	 */
	public Perfil(String name, Module... module) {
		super();
		this.name = name;
		this.modules = new ArrayList<>();
		Collections.addAll(modules, module);
	}

	/**
	 * Cria uma nova instancia de Perfil.
	 * 
	 * @param name
	 * @param modules
	 */
	public Perfil(String name, List<Module> modules) {
		this.name = name;
		this.modules = modules;
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
	 * Recupera o valor da propriedade modules.
	 * 
	 * @return modules
	 */
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * Atribui valor a propriedade modules.
	 * 
	 * @param modules
	 *            novo valor para modules
	 */
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Perfil another = (Perfil) obj;
		return another.id == id;
	}

	/**
	 * Recupera a lista de forms do perfil.
	 * 
	 * @return
	 */
	public List<Resource> getResources(ResourceType resourceType) {

		List<Resource> result = new ArrayList<Resource>();

		if (modules != null) {

			for (Module m : modules) {

				// Caso não tenha filtro, selecionar tudo
				if (resourceType == null) {
					result.addAll(m.getResources());
				}
				// Filtrar pelo tipo informado
				else {

					if (m.getResources() != null) {

						for (Resource r : m.getResources()) {
							if (r.getResourceType() == resourceType) {
								result.add(r);
							}
						}

					}
				}
			}
		}

		return result;
	}

	/**
	 * Adiciona novo acesso a recurso ao perfil.
	 * 
	 * @param resource
	 * @param write
	 */
	public void addModule(Module m) {

		if (modules == null) {
			modules = new ArrayList<>();
		}

		modules.add(m);
	}

	/**
	 * Recupera um recurso de um perfil.
	 * 
	 * @param resourceType
	 * @param target
	 */
	public Resource getResource(ResourceType resourceType, String target) {

		List<Resource> lista = getResources(resourceType);

		for (Resource r : lista) {
			if (r.getTarget().equals(target)) {
				return r;
			}
		}

		return null;
	}

}
