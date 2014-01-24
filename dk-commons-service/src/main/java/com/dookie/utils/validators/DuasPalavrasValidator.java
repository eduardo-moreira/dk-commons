package com.dookie.utils.validators;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe de validação para que a propriedade tenha pelo menos duas strings.
 * Principal utilização, validar nome completo.
 * 
 */
public abstract class DuasPalavrasValidator extends Object {

	public static boolean validate(String valor) {
		
		valor = valor.trim();
		//Valor em branco ou null.
		if (StringUtils.isEmpty(valor)){
			return false;
		}
		//Apenas uma palavra.
		String aux[] = valor.split(" ");
		if (aux.length == 1) {
			return false;
		}
		//Primeira palavra menor igual a tamanho 2.
		if (aux[0].length() <= 2){
			return false;
		}

		return true;
	}
}
