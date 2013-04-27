package com.dk.utils.web.interceptor;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dk.utils.web.session.SessionUtils;

/**
 * @author eduardo
 * 
 */
@WebFilter("/forms/*")
public class CheckUserAccess implements Filter {

//	@Inject
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);

		boolean redirect = false;

		
		if (request.getRequestURI().indexOf("login.jsf") != -1) {
			chain.doFilter(request, response);
		}
		else {
			
			if (session == null || session.isNew() || request.getSession().getAttribute(SessionUtils.CURRENT_USER) == null) {
				redirect = true;
			}
	
			// Caso não tenha usuário, redirecionar para login
			if (redirect) {
				SessionUtils.getFacesContext(request, response);
				ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
				extContext.getSessionMap().put("redirectURL", request.getRequestURI());
				response.sendRedirect(getPage(request, "/login/login.jsf"));
			}
			// Caso tenha usuário, validando se não foi acesso via GET
			else {
	
				// Caso tenha sido, jogar para tela inicial (main)
				String url = request.getRequestURI();
	
				if (request.getMethod().equals("GET")) {
	
					url = url.substring(url.indexOf("/forms"));
	
					// Sempre permite direcionar para a página principal
					if (url.equals("/forms/main.jsf")) {
						chain.doFilter(request, response);
					}
					// Caso contrário, validar
					else {
						
						System.out.println("Acesso via GET: " + url);
	
//						// Caso tenha um session controller para validar
//						if (sessionControl != null) {
//	
//							// Valida o acesso, caso possua, segue
//							if (sessionControl.validateAccess(url)) {
//								chain.doFilter(request, response);
//							}
//							// Senão joga para a tela principal
//							else {
//								response.sendRedirect(getPage(request, "main.jsf"));
//							}
//	
//						}
//						// Caso não possual um sessionControl, joga para login
//						else {
//							response.sendRedirect(getPage(request, "/login/login.jsf"));
//						}
					}
				}
				// Caso contrário, seguir o fluxo
				else {
					chain.doFilter(request, response);
				}
			}
		}
	}

	/**
	 * Recupera uma url
	 * 
	 * @param request
	 * @param page
	 * @return
	 */
	private String getPage(HttpServletRequest request, String page) {
		return request.getContextPath() + "/forms/" + page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
