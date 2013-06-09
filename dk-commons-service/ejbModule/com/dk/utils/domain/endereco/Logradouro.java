package com.dk.utils.domain.endereco;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author eduardo
 * 
 */
@Entity
public class Logradouro implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 9068122963774072118L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Cidade.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "localidade_id")
	private Localidade localidade = new Localidade();

	/**
	 * Cep.
	 */
	private int cep;

	/**
	 * Bairro.
	 */
	private String bairro;

	/**
	 * Nome da rua/avenida/praça...
	 */
	private String nome;

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
	 * Recupera o valor da propriedade localidade.
	 * 
	 * @return localidade
	 */
	public Localidade getLocalidade() {
		return localidade;
	}

	/**
	 * Atribui valor a propriedade localidade.
	 * 
	 * @param localidade
	 *            novo valor para localidade
	 */
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	/**
	 * Recupera o valor da propriedade cep.
	 * 
	 * @return cep
	 */
	public int getCep() {
		return cep;
	}

	/**
	 * Atribui valor a propriedade cep.
	 * 
	 * @param cep
	 *            novo valor para cep
	 */
	public void setCep(int cep) {
		this.cep = cep;
	}

	/**
	 * Recupera o valor da propriedade nome.
	 * 
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Atribui valor a propriedade nome.
	 * 
	 * @param nome
	 *            novo valor para nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Recupera o valor da propriedade bairro.
	 * 
	 * @return bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * Atribui valor a propriedade bairro.
	 * 
	 * @param bairro
	 *            novo valor para bairro
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
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