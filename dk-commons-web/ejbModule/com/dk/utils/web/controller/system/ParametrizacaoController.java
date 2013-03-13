package com.dk.utils.web.controller.system;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.dk.utils.domain.system.Parametrizacao;
import com.dk.utils.service.system.ParametrizacaoService;
import com.dk.utils.web.controller.GenericViewController;
import com.dk.utils.web.controller.ViewConfig;

/**
 * @author silvio
 * 
 */
@Named
@SessionScoped
@ViewConfig(page = "/forms/system/parametrizacao.jsf")
public class ParametrizacaoController extends GenericViewController<Parametrizacao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2102396381677390720L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fun.web.controller.common.GenericViewController#getService()
	 */
	@Override
	protected ParametrizacaoService getService() throws Exception {
		return serviceFactory.getParametrizacaoService();
	}

}
