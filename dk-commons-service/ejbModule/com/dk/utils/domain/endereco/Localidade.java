package com.dk.utils.domain.endereco;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Representa uma cidade.
 * 
 * @author eduardo
 * 
 */
@Entity
public class Localidade implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -4239554627836336898L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/**
	 * Sigla da UF.
	 */
	private String uf;

	/**
	 * Cidade.
	 */
	private String cidade;

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
	 * Recupera o valor da propriedade uf.
	 * 
	 * @return uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * Atribui valor a propriedade uf.
	 * 
	 * @param uf
	 *            novo valor para uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * Recupera o valor da propriedade cidade.
	 * 
	 * @return cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Atribui valor a propriedade cidade.
	 * 
	 * @param cidade
	 *            novo valor para cidade
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
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