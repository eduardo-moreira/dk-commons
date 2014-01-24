package com.dookie.utils.domain.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.dookie.utils.annotations.Descriptor;

/**
 * Representa um recurso do sistema.<br>
 * Um recurso poderá contemplar a descricao de alguma funcionalidade, um menu,
 * um widget...
 * 
 * @author eduardo
 * 
 */
@Entity
public class Resource implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 8680069928033134138L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Tipo do recurso.
	 */
	private ResourceType resourceType;

	/**
	 * Nome do recurso.
	 */
	@Descriptor
	private String name;

	/**
	 * Descricao.
	 */
	private String description;

	/**
	 * Endereço de ajuda.
	 */
	private String helpURL;

	/**
	 * Tipo da ação para chamar o recurso. Defautl ACTION.
	 */
	private TargetType targetType;

	/**
	 * Url ou Action.
	 */
	private String target;

	/**
	 * Icone que representa o recurso.
	 */
	private String icon;

	/**
	 * Caminho do menu.
	 */
	private String menuPath;

	/**
	 * Dominio. Exemplo: intranet.
	 */
	private String dominio;

	/**
	 * Label que apresentará na tela (menu).
	 */
	@Transient
	private String displayLabel;

	/**
	 * Ajax?
	 */
	@Column(nullable = true)
	private Boolean ajax;

	/**
	 * 
	 * Cria uma nova instancia de Resource.
	 */
	public Resource() {

	}

	/**
	 * Cria uma nova instancia de Resource.
	 * 
	 * @param name
	 * @param targetType
	 * @param target
	 */
	public Resource(String name, TargetType targetType, String target) {
		super();
		this.name = name;
		this.targetType = targetType;
		this.target = target;
	}

	/**
	 * Cria uma nova instancia de Resource.
	 * 
	 * @param name
	 * @param targetType
	 * @param target
	 */
	public Resource(String name, TargetType targetType, String target, String icon) {
		super();
		this.name = name;
		this.targetType = targetType;
		this.target = target;
		this.icon = icon;
	}

	/**
	 * Cria uma nova instancia de Resource, assumindo tipo Link.
	 * 
	 * @param name
	 * @param target
	 */
	public Resource(String name, String target) {
		super();
		this.name = name;
		this.targetType = TargetType.LINK;
		this.target = target;
	}

	/**
	 * 
	 * Cria uma nova instancia de Resource.
	 * 
	 * @param name
	 * @param description
	 * @param menuPath
	 * @param targetType
	 * @param target
	 */
	public Resource(String name, String description, String menuPath, TargetType targetType, String target) {
		setDescription(description);
		setName(name);
		setMenuPath(menuPath);
		setTargetType(targetType);
		setTarget(target);
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
	 * Recupera o valor da propriedade description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Atribui valor a propriedade description.
	 * 
	 * @param description
	 *            novo valor para description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Recupera o valor da propriedade helpURL.
	 * 
	 * @return helpURL
	 */
	public String getHelpURL() {
		return helpURL;
	}

	/**
	 * Atribui valor a propriedade helpURL.
	 * 
	 * @param helpURL
	 *            novo valor para helpURL
	 */
	public void setHelpURL(String helpURL) {
		this.helpURL = helpURL;
	}

	/**
	 * Recupera o valor da propriedade targetType.
	 * 
	 * @return targetType
	 */
	public TargetType getTargetType() {
		return targetType;
	}

	/**
	 * Atribui valor a propriedade targetType.
	 * 
	 * @param targetType
	 *            novo valor para targetType
	 */
	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	/**
	 * Recupera o valor da propriedade target.
	 * 
	 * @return target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Atribui valor a propriedade target.
	 * 
	 * @param target
	 *            novo valor para target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Recupera o valor da propriedade icon.
	 * 
	 * @return icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Atribui valor a propriedade icon.
	 * 
	 * @param icon
	 *            novo valor para icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Recupera o valor da propriedade resourceType.
	 * 
	 * @return resourceType
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * Atribui valor a propriedade resourceType.
	 * 
	 * @param resourceType
	 *            novo valor para resourceType
	 */
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * Recupera o valor da propriedade menuPath.
	 * 
	 * @return menuPath
	 */
	public String getMenuPath() {
		return menuPath;
	}

	/**
	 * Atribui valor a propriedade menuPath.
	 * 
	 * @param menuPath
	 *            novo valor para menuPath
	 */
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	/**
	 * Recupera o valor da propriedade displayLabel.
	 * 
	 * @return displayLabel
	 */
	public String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * Atribui valor a propriedade displayLabel.
	 * 
	 * @param displayLabel
	 *            novo valor para displayLabel
	 */
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	/**
	 * Recupera o valor da propriedade ajax.
	 * 
	 * @return ajax
	 */
	public Boolean getAjax() {
		return ajax;
	}

	/**
	 * Recupera o valor da propriedade ajax.
	 * 
	 * @return ajax
	 */
	public Boolean isAjax() {
		return ajax;
	}

	/**
	 * Atribui valor a propriedade ajax.
	 * 
	 * @param ajax
	 *            novo valor para ajax
	 */
	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
	}

	/**
	 * Recupera o valor da propriedade dominio.
	 * 
	 * @return dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * Atribui um novo valor para dominio
	 * 
	 * @param dominio
	 *            novo valor para dominio
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Resource other = (Resource) obj;
		if (other == null) {
			return false;
		}
		return other.getId() == getId();
	}
}