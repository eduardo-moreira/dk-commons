package com.dk.utils.web.session;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dk.utils.domain.system.User;

/**
 * @author eduardo
 * 
 */
public class SessionUtils {

	/**
	 * Constante para armazenar usuario
	 */
	public static String CURRENT_USER = "currentUser";

	/**
	 * Recupera o usuário atual da sessão.
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		return (User) get(CURRENT_USER);
	}

	/**
	 * Seta o usuário na sessão atual.
	 * 
	 * @param user
	 */
	public static void setCurrentUser(User user) {
		put(CURRENT_USER, user);
	}

	public static void put(String key, Object value) {
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		extContext.getSessionMap().put(key, value);
	}

	public static Object get(String key) {
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		return extContext.getSessionMap().get(key);
	}

	public static void invalidateSession() {
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		extContext.invalidateSession();
	}

	public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
		// Get current FacesContext.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Check current FacesContext.
		if (facesContext == null) {

			// Create new Lifecycle.
			LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
			Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

			// Create new FacesContext.
			FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
			facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);

			// Create new View.
			UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
			facesContext.setViewRoot(view);

			// Set current FacesContext.
			FacesContextWrapper.setCurrentInstance(facesContext);
		}

		return facesContext;
	}

	// Wrap the protected FacesContext.setCurrentInstance() in a inner class.
	private static abstract class FacesContextWrapper extends FacesContext {
		protected static void setCurrentInstance(FacesContext facesContext) {
			FacesContext.setCurrentInstance(facesContext);
		}
	}
}