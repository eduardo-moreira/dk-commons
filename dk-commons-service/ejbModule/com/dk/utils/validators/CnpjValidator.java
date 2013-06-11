package com.dk.utils.validators;

public abstract class CnpjValidator extends Object {

	public static boolean validate(String cnpj) {

		cnpj = cnpj.replaceAll("[^0-9]", "");

		System.out.println(cnpj);
		if (cnpj.length() != 14)
			return false;

		int soma = 0;
		String cnpjAux = cnpj.substring(0, 12);

		char cnpjArray[] = cnpj.toCharArray();
		for (int i = 0; i < 4; i++)
			if (cnpjArray[i] - 48 >= 0 && cnpjArray[i] - 48 <= 9)
				soma += (cnpjArray[i] - 48) * (6 - (i + 1));

		for (int i = 0; i < 8; i++)
			if (cnpjArray[i + 4] - 48 >= 0 && cnpjArray[i + 4] - 48 <= 9)
				soma += (cnpjArray[i + 4] - 48) * (10 - (i + 1));

		int dig = 11 - soma % 11;
		cnpjAux = (new StringBuilder(String.valueOf(cnpjAux))).append(
				dig != 10 && dig != 11 ? Integer.toString(dig) : "0")
				.toString();
		soma = 0;
		for (int i = 0; i < 5; i++)
			if (cnpjArray[i] - 48 >= 0 && cnpjArray[i] - 48 <= 9)
				soma += (cnpjArray[i] - 48) * (7 - (i + 1));

		for (int i = 0; i < 8; i++)
			if (cnpjArray[i + 5] - 48 >= 0 && cnpjArray[i + 5] - 48 <= 9)
				soma += (cnpjArray[i + 5] - 48) * (10 - (i + 1));

		dig = 11 - soma % 11;
		cnpjAux = (new StringBuilder(String.valueOf(cnpjAux))).append(
				dig != 10 && dig != 11 ? Integer.toString(dig) : "0")
				.toString();

		return cnpj.equals(cnpjAux);

	}
}
