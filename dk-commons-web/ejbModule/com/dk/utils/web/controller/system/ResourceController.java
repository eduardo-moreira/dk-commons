/**
 * 
 */
package com.dk.utils.web.controller.system;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.dk.utils.domain.system.Resource;
import com.dk.utils.domain.system.ResourceType;
import com.dk.utils.domain.system.TargetType;
import com.dk.utils.service.system.resource.ResourceService;
import com.dk.utils.web.controller.GenericViewController;
import com.dk.utils.web.controller.ViewConfig;

/**
 * Controla as ações para {@link Resource} do sistema.
 * 
 * @author eduardo
 * 
 */
@Named
@SessionScoped
@ViewConfig(page="/forms/system/form.jsf")
public class ResourceController extends GenericViewController<Resource> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -6395854090779744363L;

	/* (non-Javadoc)
	 * @see com.fun.web.controller.common.GenericViewController#getService()
	 */
	@Override
	protected ResourceService getService() throws Exception {
		return serviceFactory.getResourceService();
	}

	/**
	 * Tipos de alvo para tela.
	 * 
	 * @return {@link TargetType}
	 */
	public TargetType[] getTargetTypeValues() {
		return TargetType.values();
	}
	
	/**
	 * Tipos de recursos para tela.
	 * 
	 * @return {@link TargetType}
	 */
	public ResourceType[] getResourceTypeValues() {
		return ResourceType.values();
	}
	
}
