package com.dk.utils.domain.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dk.utils.annotations.Descriptor;
import com.dk.utils.annotations.History;

/**
 * Representa os campos parametrizaveis do Sistema.<br>
 * 
 * @author silvio
 * 
 */
@Entity
@History
@Table (name="parametrizacao")
public class Parametrizacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 559082010430556855L;

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
	 * value.
	 */
	@Descriptor
	private String value;

	/**
	 * value.
	 */
	@Descriptor
	private String descricao;
	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * Cria uma nova instancia de Parametrizacao.
	 */
	public Parametrizacao() {

	}
	
	/**
	 * Cria uma nova instancia de Parametrizacao.
	 * 
	 * @param id
	 * @param name
	 * @param value
	 * @param descricao
	 */
	public Parametrizacao(long id, String name, String value, String descricao) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.descricao = descricao;
	}
	
	/**
	 * Cria uma nova instancia de Parametrizacao.
	 * 
	 * @param id
	 * @param name
	 * @param value
	 */
	public Parametrizacao(String name, String value, String descricao) {
		super();
		this.name = name;
		this.value = value;
		this.value = descricao;
	}	
}
