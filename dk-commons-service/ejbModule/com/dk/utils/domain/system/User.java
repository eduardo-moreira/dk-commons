/**
 * 
 */
package com.dk.utils.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.dk.utils.annotations.Descriptor;
import com.dk.utils.annotations.History;

/**
 * Representa um cliente do sistema.
 * 
 * @author eduardo
 * 
 */
@Entity
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@History
public class User implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 495152697208871370L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * E-mail.
	 */
	private String email;

	/**
	 * Senha para acesso.
	 */
	private String password;

	/**
	 * Nome.
	 */
	@Descriptor
	private String nome;

	/**
	 * Status.
	 */
	@Column(nullable = true)
	private Integer status;

	/**
	 * Data de cadastro no site.
	 */
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	/**
	 * Data de cadastro atualizado no site.
	 */
	@Temporal(TemporalType.DATE)
	private Date dataAtualizado;

	/**
	 * Perfil de acesso do cliente.
	 */
	@Transient
	private Perfil perfil;

	/**
	 * Perfis associados ao usuário.
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Perfil> perfis;

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
	 * Recupera o valor da propriedade email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Atribui valor a propriedade email.
	 * 
	 * @param email
	 *            novo valor para email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Recupera o valor da propriedade password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Atribui valor a propriedade password.
	 * 
	 * @param password
	 *            novo valor para password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * Recupera o valor da propriedade status.
	 * 
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Atribui valor a propriedade status.
	 * 
	 * @param status
	 *            novo valor para status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Recupera o valor da propriedade dataCadastro.
	 * 
	 * @return dataCadastro
	 */
	public Date getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * Atribui valor a propriedade dataCadastro.
	 * 
	 * @param dataCadastro
	 *            novo valor para dataCadastro
	 */
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * Recupera o valor da propriedade perfil.
	 * 
	 * @return perfil
	 */
	public Perfil getPerfil() {

		if (perfil == null && perfis != null && !perfis.isEmpty()) {
			perfil = perfis.get(0);
		}

		return perfil;
	}

	/**
	 * Atribui valor a propriedade perfil.
	 * 
	 * @param perfil
	 *            novo valor para perfil
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/**
	 * Recupera o valor da propriedade perfis.
	 * 
	 * @return perfis
	 */
	public List<Perfil> getPerfis() {
		return perfis;
	}

	/**
	 * Atribui valor a propriedade perfis.
	 * 
	 * @param perfis
	 *            novo valor para perfis
	 */
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	/**
	 * Recupera o valor da propriedade dataAtualizado.
	 * 
	 * @return dataAtualizado
	 */
	public Date getDataAtualizado() {
		return dataAtualizado;
	}

	/**
	 * Atribui um novo valor para dataAtualizado
	 * 
	 * @param dataAtualizado
	 *            novo valor para dataAtualizado
	 */
	public void setDataAtualizado(Date dataAtualizado) {
		this.dataAtualizado = dataAtualizado;
	}

	/**
	 * Adiciona um ou mais perfis ao usuario.
	 * 
	 * @param perfils
	 */
	public void addPerfil(Perfil perfil) {
		if (this.perfis == null) {
			this.perfis = new ArrayList<>();
		}
		this.perfis.add(perfil);
	}

	/**
	 * Adiciona um ou mais perfis ao usuario.
	 * 
	 * @param perfils
	 */
	public void addPerfil(List<Perfil> perfils) {
		if (this.perfis == null) {
			this.perfis = new ArrayList<>();
		}
		this.perfis.addAll(perfis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.model.system.User#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		User other = (User) obj;
		if (other == null) {
			return false;
		}
		return other.id == id;
	}

}
