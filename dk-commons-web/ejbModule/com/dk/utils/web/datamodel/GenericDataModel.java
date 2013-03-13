package com.dk.utils.web.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.dk.utils.reflection.ReflectionUtils;

/**
 * @author eduardo
 * 
 */
public class GenericDataModel extends ListDataModel<Object> implements SelectableDataModel<Object> {

	List<?> data;
	
	public GenericDataModel(List<?> data) {
		this.setWrappedData(data);
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
	 */
	@Override
	public Object getRowData(String rowKey) {

		long id = Long.parseLong(rowKey);

		for (Object reg : data) {
			if (ReflectionUtils.getIdValue(reg) == id) {
				return reg;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(Object obj) {
		return String.valueOf(ReflectionUtils.getIdValue(obj));
	}

}