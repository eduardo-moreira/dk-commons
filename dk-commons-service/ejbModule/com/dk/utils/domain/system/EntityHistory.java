/**
 * 
 */
package com.dk.utils.domain.system;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Armazena o historico da entidade.<br>
 * Serão salvos dados do usuário, a operação que ele executou e em que data
 * (hora).<br>
 * Da entidade serão salvos a classe que a representa e seu id.<br>
 * Um campo adicional de descricao será atribuido, porém não acoplado na
 * persistência.<br>
 * 
 * @author eduardo
 * 
 */
@Entity
public class EntityHistory implements Serializable {

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
	 * Nome do usuário.
	 */
	private String userName;

	/**
	 * ID do usuário.
	 */
	private long userId;

	/**
	 * Data da operação.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	/**
	 * Tipo da operação.
	 * 
	 * @see EntityOperationType
	 */
	private String entityOperationType;

	/**
	 * Nome da entidade base (ou funcionalidade).
	 */
	private String name;

	/**
	 * Id da entidade (caso seja uma).
	 */
	private long entityId;

	/**
	 * Descrição da entidade.
	 */
	private String descriptor;

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
	 * Recupera o valor da propriedade data.
	 * 
	 * @return data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Atribui valor a propriedade data.
	 * 
	 * @param data
	 *            novo valor para data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Recupera o valor da propriedade entityOperationType.
	 * 
	 * @return entityOperationType
	 */
	public String getEntityOperationType() {
		return entityOperationType;
	}

	/**
	 * Atribui valor a propriedade entityOperationType.
	 * 
	 * @param entityOperationType
	 *            novo valor para entityOperationType
	 */
	public void setEntityOperationType(String entityOperationType) {
		this.entityOperationType = entityOperationType;
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
	 * Recupera o valor da propriedade entityId.
	 * 
	 * @return entityId
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Atribui valor a propriedade entityId.
	 * 
	 * @param entityId
	 *            novo valor para entityId
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Recupera o valor da propriedade descriptor.
	 * 
	 * @return descriptor
	 */
	public String getDescriptor() {
		return descriptor;
	}

	/**
	 * Atribui valor a propriedade descriptor.
	 * 
	 * @param descriptor
	 *            novo valor para descriptor
	 */
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Recupera o valor da propriedade userName.
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Atribui valor a propriedade userName.
	 * 
	 * @param userName
	 *            novo valor para userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Recupera o valor da propriedade userId.
	 * 
	 * @return userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Atribui valor a propriedade userId.
	 * 
	 * @param userId
	 *            novo valor para userId
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");

		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(data));
		sb.append(" ");
		sb.append(entityOperationType);

		if (this.getDescriptor() != null) {
			sb.append(" -- ");
			sb.append(getDescriptor());
		}

		return sb.toString();
	}

}
