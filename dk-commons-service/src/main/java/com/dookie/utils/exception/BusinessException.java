package com.dookie.utils.exception;

/**
 * @author eduardo
 * 
 */
public class BusinessException extends Exception {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 6104362805882772454L;

	/**
	 * Código do erro.
	 */
	private String code;

	/**
	 * Mensagem do erro.
	 */
	private String message;

	/**
	 * Parametros que ocasionaram o erro.
	 */
	private Object[] params;

	/**
	 * Cria uma nova instancia de BusinessException.
	 * 
	 * @param code
	 * @param message
	 * @param params
	 */
	public BusinessException(String code, String message, Object[] params) {
		super();
		this.code = code;
		this.message = message;
		this.params = params;
	}

	/**
	 * Cria uma nova instancia de BusinessException.
	 * 
	 * @param code
	 * @param message
	 */
	public BusinessException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * Cria uma nova instancia de BusinessException.
	 * 
	 * @param code
	 */
	public BusinessException(String code) {
		super();
		this.code = code;
	}
	
	/**
	 * Cria uma nova instancia de BusinessException.
	 * 
	 * @param code
	 */
	public BusinessException(String code, Object... params) {
		super();
		this.code = code;
		this.params = params;
	}

	/**
	 * Cria uma nova instancia de BusinessException.
	 */
	public BusinessException() {
		super();
	}

	/**
	 * Recupera o valor da propriedade code.
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Atribui valor a propriedade code.
	 * 
	 * @param code
	 *            novo valor para code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Recupera o valor da propriedade message.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Atribui valor a propriedade message.
	 * 
	 * @param message
	 *            novo valor para message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Recupera o valor da propriedade params.
	 * 
	 * @return params
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * Atribui valor a propriedade params.
	 * 
	 * @param params
	 *            novo valor para params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

}
