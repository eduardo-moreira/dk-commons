package com.dookie.utils.web.jsf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.TextRenderer;

public class InputRender extends TextRenderer {

	private static final Attribute[] INPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXT);
	private static final Attribute[] OUTPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTTEXT);

	// ---------------------------------------------------------- Public Methods

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

		rendererParamsNotNull(context, component);

	}

	// ------------------------------------------------------- Protected Methods

	@Override
	protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		assert (writer != null);
		boolean shouldWriteIdAttribute = false;
		boolean isOutput = false;

		String style = (String) component.getAttributes().get("style");
		String styleClass = (String) component.getAttributes().get("styleClass");
		String dir = (String) component.getAttributes().get("dir");
		String lang = (String) component.getAttributes().get("lang");
		String title = (String) component.getAttributes().get("title");
		
		String type = (String) component.getAttributes().get("type");
		String placeholder = (String) component.getAttributes().get("placeholder");
		String dataClearBtn = (String) component.getAttributes().get("data-clear-btn");
		String dataClearBtnText = (String) component.getAttributes().get("data-clear-btn-text");
		String dataMini = (String) component.getAttributes().get("data-mini");
		String dataRole = (String) component.getAttributes().get("data-role");
		String dataTheme = (String) component.getAttributes().get("data-theme");
		String dataHighlight = (String) component.getAttributes().get("data-highlight");
		String min = (String) component.getAttributes().get("min");
		String max = (String) component.getAttributes().get("max");
		
		if (component instanceof UIInput) {
			writer.startElement("input", component);
			writeIdAttributeIfNecessary(context, writer, component);

			if (type != null) {
				writer.writeAttribute("type", type, null);
			}
			else {
				writer.writeAttribute("type", "text", null);
			}
			
			writer.writeAttribute("name", (component.getClientId(context)), "clientId");

			// only output the autocomplete attribute if the value
			// is 'off' since its lack of presence will be interpreted
			// as 'on' by the browser
			if ("off".equals(component.getAttributes().get("autocomplete"))) {
				writer.writeAttribute("autocomplete", "off", "autocomplete");
			}

			// render default text specified
			if (currentValue != null) {
				writer.writeAttribute("value", currentValue, "value");
			}
			if (null != styleClass) {
				writer.writeAttribute("class", styleClass, "styleClass");
			}

			if (placeholder != null) {
				writer.writeAttribute("placeholder", placeholder, null);
			}

			if (dataClearBtn != null) {
				writer.writeAttribute("data-clear-btn", dataClearBtn, null);
			}
			
			if (dataClearBtnText != null) {
				writer.writeAttribute("data-clear-btn-text", dataClearBtnText, null);
			}
			
			if (dataMini != null) {
				writer.writeAttribute("data-mini", dataMini, null);
			}
			
			if (dataRole != null) {
				writer.writeAttribute("data-role", dataRole, null);
			}
			
			if (dataTheme != null) {
				writer.writeAttribute("data-theme", dataTheme, null);
			}

			if (dataHighlight != null) {
				writer.writeAttribute("data-highlight", dataHighlight, null);
			}
			
			if (min != null) {
				writer.writeAttribute("min", min, null);
			}
			
			if (max != null) {
				writer.writeAttribute("max", max, null);
			}
			
			// style is rendered as a passthur attribute
			RenderKitUtils.renderPassThruAttributes(context, writer, component, INPUT_ATTRIBUTES, getNonOnChangeBehaviors(component));
			RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);

			RenderKitUtils.renderOnchange(context, component, false);

			writer.endElement("input");

		} else if (isOutput = (component instanceof UIOutput)) {
			if (styleClass != null || style != null || dir != null || lang != null || title != null || (shouldWriteIdAttribute = shouldWriteIdAttribute(component))) {
				writer.startElement("span", component);
				writeIdAttributeIfNecessary(context, writer, component);
				if (null != styleClass) {
					writer.writeAttribute("class", styleClass, "styleClass");
				}
				// style is rendered as a passthru attribute
				RenderKitUtils.renderPassThruAttributes(context, writer, component, OUTPUT_ATTRIBUTES);

			}
			if (currentValue != null) {
				Object val = component.getAttributes().get("escape");
				if ((val != null) && Boolean.valueOf(val.toString())) {
					writer.writeText(currentValue, component, "value");
				} else {
					writer.write(currentValue);
				}
			}
		}
		if (isOutput && (styleClass != null || style != null || dir != null || lang != null || title != null || (shouldWriteIdAttribute))) {
			writer.endElement("span");
		}

	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {

		boolean renderChildren = WebConfiguration.getInstance().isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.AllowTextChildren);

		if (!renderChildren) {
			return;
		}

		rendererParamsNotNull(context, component);

		if (!shouldEncodeChildren(component)) {
			return;
		}

		if (component.getChildCount() > 0) {
			for (UIComponent kid : component.getChildren()) {
				encodeRecursive(context, kid);
			}
		}

	}

}
