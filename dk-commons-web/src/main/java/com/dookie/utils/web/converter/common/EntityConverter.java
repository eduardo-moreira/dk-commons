package com.dookie.utils.web.converter.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.log4j.Logger;

import com.dookie.utils.reflection.ReflectionUtils;

/**
 * Converte entidades para o padrão do primefaces.
 * 
 * @author eduardo
 * 
 * @param <T>
 */
public abstract class EntityConverter<T> implements Converter {

	/**
	 * Logger - logger.
	 */
	private static final Logger logger = Logger.getLogger(EntityConverter.class);

	public abstract List<T> getValues();

	/**
	 * Recupera a classe atual de trabalho.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

		try {
			if (value != null) {

				// Criando um objeto
				// e setando o id passado
				T obj = getEntityClass().newInstance();
				ReflectionUtils.setId(obj, Long.parseLong(value));

				// Buscando na lista pelo objeto
				// a entidade deverá sobrepor o método equals,
				// igualando via id.
				int pos = getValues().indexOf(obj);

				if (pos > -1) {
					return getValues().get(pos);
				}
			}
		} catch (Exception e) {
			logger.error("Erro ao recuperar objeto: " + e.getMessage());
		}

		return null;
	}

	/**
	 * Recupera o campo descriptor de uma entidade.
	 */
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {

		if (object == null) {
			return null;
		}

		try {
			return String.valueOf(ReflectionUtils.getIdValue(object));
		} catch (Exception e) {
			logger.error("Erro ao recuperar objeto: " + e.getMessage());
		}

		return null;
	}
}