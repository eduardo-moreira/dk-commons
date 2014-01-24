package com.dookie.utils.funcoes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Coleção de funcções para Data
 */
public class DataFuncao {
	
	/**
	 * Metodo que irá adicionar quantidade de dias a data informada.
	 * 
	 * @param data
	 * @param quantidadeDias
	 * @return
	 */
	public static Date adicionaDiaData(Date data, int quantidadeDias) {
		
		if (data != null && quantidadeDias > 0) {
			Calendar c = new GregorianCalendar();
			c.setTime(data);
			c.add(Calendar.DATE, quantidadeDias);
			
			return c.getTime();
		}
		
		return null;
	}
	
	/**
	 * Metodo que irá retornar a proxima da util. Caso a data informada seja util retorna a data.
	 * 
	 * @param data
	 * @return
	 */
	public static Date retornaProximoDiaUtil(Date data) {
		
		if (data != null) {
			Calendar c = new GregorianCalendar();
			c.setTime(data);
			
			if (c.get(Calendar.DAY_OF_WEEK) == 0) {
				return adicionaDiaData(data, 1);
			}
		}
		
		return data;
	}

}
