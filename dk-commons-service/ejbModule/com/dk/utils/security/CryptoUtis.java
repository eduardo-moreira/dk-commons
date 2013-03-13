package com.dk.utils.security;

import java.security.MessageDigest;

import org.apache.commons.lang3.RandomStringUtils;

public class CryptoUtis {

	/**
	 * Gera uma senha.
	 * 
	 * @return
	 */
	public static String generateRandomPwd() {
		return RandomStringUtils.random(5, true, true);
	}

	/**
	 * Gera uma senha numerica.
	 * 
	 * @return
	 */
	public static String generateRandomNumericPwd() {
		return RandomStringUtils.random(4, false, true);
	}

	/**
	 * Codifica uma senha para o padrão salvo em base de dados.
	 * 
	 * @param senha
	 * 
	 * @return
	 */
	public static String encode(String senha) {
		try {

			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}

			return hexString.toString();

		} catch (Exception ns) {
			ns.printStackTrace();
			return senha;
		}
	}
}
