package com.dk.utils.web.jsf;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.config.WebConfiguration.BooleanWebContextInitParameter;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.FormRenderer;

public class ExtendedFormRenderer extends FormRenderer {

	private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.FORMFORM);

	private boolean writeStateAtEnd;

	// ------------------------------------------------------------ Constructors

	public ExtendedFormRenderer() {
		WebConfiguration webConfig = WebConfiguration.getInstance();
		writeStateAtEnd = webConfig.isOptionEnabled(BooleanWebContextInitParameter.WriteStateAtFormEnd);

	}

	// ---------------------------------------------------------- Public Methods

	@Override
	public void decode(FacesContext context, UIComponent component) {

		rendererParamsNotNull(context, component);

		// Was our form the one that was submitted? If so, we need to set
		// the indicator accordingly..
		String clientId = component.getClientId(context);
		Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
		if (requestParameterMap.containsKey(clientId)) {
			((UIForm) component).setSubmitted(true);
		} else {
			((UIForm) component).setSubmitted(false);
		}

	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}

		ResponseWriter writer = context.getResponseWriter();
		assert (writer != null);
		String clientId = component.getClientId(context);
		// since method and action are rendered here they are not added
		// to the pass through attributes in Util class.
		writer.write('\n');
		writer.startElement("form", component);
		writer.writeAttribute("id", clientId, "clientId");
		writer.writeAttribute("name", clientId, "name");
		writer.writeAttribute("method", "post", null);
		writer.writeAttribute("action", getActionStr(context), null);
		String styleClass = (String) component.getAttributes().get("styleClass");
		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, "styleClass");
		}

		String dataajax = (String) component.getAttributes().get("data-ajax");
		if (dataajax != null) {
			writer.writeAttribute("data-ajax", dataajax, null);
		}
		else {
			writer.writeAttribute("data-ajax", "false", null);
		}

		String acceptcharset = (String) component.getAttributes().get("acceptcharset");
		if (acceptcharset != null) {
			writer.writeAttribute("accept-charset", acceptcharset, "acceptcharset");
		}

		RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
		writer.writeText("\n", component, null);

		// this hidden field will be checked in the decode method to
		// determine if this form has been submitted.
		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", "type");
		writer.writeAttribute("name", clientId, "clientId");
		writer.writeAttribute("value", clientId, "value");
		writer.endElement("input");
		writer.write('\n');

		if (!writeStateAtEnd) {
			context.getApplication().getViewHandler().writeState(context);
			writer.write('\n');
		}

	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

		rendererParamsNotNull(context, component);
		if (!shouldEncode(component)) {
			return;
		}

		// Render the end tag for form
		ResponseWriter writer = context.getResponseWriter();
		assert (writer != null);
		if (writeStateAtEnd) {
			context.getApplication().getViewHandler().writeState(context);
		}
		writer.writeText("\n", component, null);
		writer.endElement("form");

	}

	// --------------------------------------------------------- Private Methods

	/**
	 * @param context
	 *            FacesContext for the response we are creating
	 * 
	 * @return Return the value to be rendered as the <code>action</code>
	 *         attribute of the form generated for this component.
	 */
	private static String getActionStr(FacesContext context) {

		String viewId = context.getViewRoot().getViewId();
		String actionURL = context.getApplication().getViewHandler().getActionURL(context, viewId);
		return (context.getExternalContext().encodeActionURL(actionURL));

	}
}