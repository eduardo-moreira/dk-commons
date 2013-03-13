package com.dk.utils.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configure field/Class to use Pojow
 * 
 * @author Eduardo Jaremicki Moreira
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= { ElementType.FIELD, ElementType.TYPE })
public @interface  Required {
	
		
}