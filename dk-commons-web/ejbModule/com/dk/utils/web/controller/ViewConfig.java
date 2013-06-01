package com.dk.utils.web.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dk.utils.service.common.GenericService;

/**
 * Método que será executado antes de salvar uma entidade.
 * 
 * @see GenericService
 * 
 * @author Eduardo Jaremicki Moreira
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface ViewConfig {

	/**
	 * Caminho completo para a página a ser exibida pelo control.
	 */
	String page() default "";

	/**
	 * Caminho completo para página de listagem.
	 * 
	 * @return
	 */
	String listView() default "";

	/**
	 * Caminho completo para página de edição.
	 * 
	 * @return
	 */
	String editView() default "";
}