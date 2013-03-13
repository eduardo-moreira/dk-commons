package com.dk.utils.web.converter.common;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * converts an enum in a way that makes the conversion reversible (sometimes)
 * <ul>
 * <li>input: uses its classname and ordinal, reversible
 * <li>
 * <li>else: uses its name, non reversible
 * <li>
 * </ul>
 */
public class EnumConverter implements Converter {

	private static final String ATTRIBUTE_ENUM_TYPE = "org.primefaces.extensions.converter.EnumConverter.ENUM_TYPE";

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value instanceof Enum<?>) {
			component.getAttributes().put(ATTRIBUTE_ENUM_TYPE, value.getClass());
			return ((Enum<?>) value).name();
		}

		throw new ConverterException("Value is not an enum: " + value.getClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		final Class<Enum> enumType = (Class<Enum>) component.getAttributes().get(ATTRIBUTE_ENUM_TYPE);
		try {
			return Enum.valueOf(enumType, value);
		} catch (IllegalArgumentException e) {
			throw new ConverterException("Value is not an enum of type: " + enumType);
		}
	}
}