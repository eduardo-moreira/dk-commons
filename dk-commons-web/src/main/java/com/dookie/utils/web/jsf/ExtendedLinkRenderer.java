package com.dookie.utils.web.jsf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.html_basic.CommandLinkRenderer;

public class ExtendedLinkRenderer extends CommandLinkRenderer {

	@Override
	protected void writeCommonLinkAttributes(ResponseWriter writer, UIComponent component) throws IOException {
		super.writeCommonLinkAttributes(writer, component);
		writer.writeAttribute("data-icon", component.getAttributes().get("data-icon"), null);
		writer.writeAttribute("data-theme", component.getAttributes().get("data-theme"), null);
		writer.writeAttribute("data-ajax", component.getAttributes().get("data-ajax"), null);
		writer.writeAttribute("data-role", component.getAttributes().get("data-role"), null);
		writer.writeAttribute("data-inline", component.getAttributes().get("data-inline"), null);
		writer.writeAttribute("data-rel", component.getAttributes().get("data-rel"), null);
		writer.writeAttribute("data-transition", component.getAttributes().get("data-transition"), null);
	}

	
}