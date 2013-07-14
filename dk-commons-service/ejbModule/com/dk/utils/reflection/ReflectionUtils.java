package com.dk.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Id;

import com.dk.utils.annotations.Descriptor;

/**
 * Classe com várias utilidades relacionadas a reflexão de objetos
 * 
 * @author eduardo
 */
public class ReflectionUtils {

	/**
	 * Recupera o field anotado como id
	 * 
	 * @param persistentClass
	 * @return
	 */
	public static Field getId(Class<?> persistentClass) throws Exception {

		for (Field field : getDeclaredFields(persistentClass)) {

			if (field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}

		return null;
	}
	
	/**
	 * Seta field anotado como id
	 * 
	 * @param persistentClass
	 * @return
	 */
	public static void setId(Object obj, long value) throws Exception {
		
		for (Field field : getDeclaredFields(obj.getClass())) {
		
			if (field.isAnnotationPresent(Id.class)) {
				setter(obj, field.getName(), value);
				break;
			}
		}
	}
	
	/**
	 * Recupera o field anotado como {@link Descriptor}.
	 * 
	 * @param persistentClass
	 * @return
	 */
	public static Field getDescriptor(Class<?> persistentClass) throws Exception {
		
		for (Field field : getDeclaredFields(persistentClass)) {
			
			if (field.isAnnotationPresent(Descriptor.class)) {
				return field;
			}
		}
		
		return null;
	}

	/**
	 * Recupera o id de um objeto
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static long getIdValue(Object obj) {

		try {

			List<Field> fields = new ArrayList<Field>();
			Collections.addAll(fields, obj.getClass().getDeclaredFields());

			Class<?> parent = obj.getClass().getSuperclass();

			while (parent != Object.class) {
				Collections.addAll(fields, parent.getDeclaredFields());
				parent = parent.getSuperclass();
			}

			for (Field field : fields) {

				if (field.isAnnotationPresent(Id.class)) {
					Long idL = (Long) ReflectionUtils.getValue(obj, field.getName());
					return idL.longValue();
				}
			}

		} catch (Exception e) {
			System.out.println("ERRO" + e.getMessage());
		}

		return 0L;
	}
	
	/**
	 * Recupera o id de um objeto
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getDescriptorValue(Object obj) {
		
		try {
			
			List<Field> fields = new ArrayList<Field>();
			Collections.addAll(fields, obj.getClass().getDeclaredFields());
			
			Class<?> parent = obj.getClass().getSuperclass();
			
			while (parent != Object.class) {
				Collections.addAll(fields, parent.getDeclaredFields());
				parent = parent.getSuperclass();
			}
			
			for (Field field : fields) {
				
				if (field.isAnnotationPresent(Descriptor.class)) {
					return (String) ReflectionUtils.getValue(obj, field.getName());
				}
			}
			
		} catch (Exception e) {
			// TODO log
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Recupera um field declarado em uma classe (ou super classe)
	 * 
	 * @param classe
	 *            classe
	 * @param fieldName
	 *            nome da propriedade
	 * @return Field
	 * @throws SecurityException
	 *             SecurityException
	 * @throws NoSuchFieldException
	 *             NoSuchFieldException
	 */
	public static Field getField(Class<?> classe, String fieldName) throws SecurityException, NoSuchFieldException {

		Field field = null;

		try {
			field = classe.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {

			classe = classe.getSuperclass();

			if (classe == Object.class) {
				throw e;
			}

			// Chamando novamente a classe
			return getField(classe, fieldName);

		}

		return field;
	}

	/**
	 * Recupera um método declarado em uma classe (ou super classe)
	 * 
	 * @param classe
	 *            classe
	 * @param methodName
	 *            nome do método
	 * @param paramTypes
	 *            paramTypes
	 * @return Method
	 * @throws SecurityException
	 *             SecurityException
	 * @throws NoSuchFieldException
	 *             NoSuchFieldException
	 */
	public static Method getMethod(Class<?> classe, String methodName, Class<?>[] paramTypes) throws SecurityException, NoSuchMethodException {

		Method method = null;

		try {
			method = classe.getDeclaredMethod(methodName, paramTypes);
		} catch (NoSuchMethodException e) {

			classe = classe.getSuperclass();

			if (classe == Object.class) {
				throw e;
			}

			// Chamando novamente a classe
			return getMethod(classe, methodName, paramTypes);

		}

		return method;
	}

	/**
	 * Executa os métodos declarados em uma classe (ou super classe)
	 * 
	 * @param classe
	 *            classe
	 * 
	 * @param annotationClass
	 *            annotationClass
	 * 
	 * @return Method
	 */
	public static void executeMethod(Object obj, Class<? extends Annotation> annotationClass, Object param) throws Exception {
		

		List<Method> methods = new ArrayList<Method>();
		Collections.addAll(methods, obj.getClass().getDeclaredMethods());

		Class<?> parent = obj.getClass().getSuperclass();

		while (parent != Object.class) {
			Collections.addAll(methods, parent.getDeclaredMethods());
			parent = parent.getSuperclass();
		}

		for (Method method : methods) {

			if (method.isAnnotationPresent(annotationClass)) {
				method.invoke(obj, new Object[] { param });
			}
		}
		
	}

	/**
	 * Recupera o valor de uma propriedade.
	 * 
	 * @param obj
	 *            objeto
	 * @param property
	 *            propriedade
	 * @return valor da propriedade
	 * @throws Exception
	 *             Exceções de falha na reflexão
	 */
	public static Object getter(Object obj, String property) throws Exception {

		Object result = null;

		if (obj != null) {

			Class<?> classe = obj.getClass();

			// Recuperando o tipo
			Field field = getField(classe, property);
			String prefix = "get";

			if (field.getType().getName().equals("boolean")) {
				prefix = "is";
			}

			// garantindo que o property esta correto
			property = property.substring(0, 1).toUpperCase() + property.substring(1);

			Method method = getMethod(classe, prefix + property, new Class[] {});
			result = method.invoke(obj, new Object[] {});
		}

		return result;
	}

	/**
	 * Atribui um valor a uma propriedade de um objeto.
	 * 
	 * @param obj
	 *            objeto
	 * @param property
	 *            propriedade
	 * @param value
	 *            valor
	 * @throws Exception
	 *             Exceção de falha na reflexão
	 */
	public static void setter(Object obj, String property, Object value) throws Exception {

		if (obj != null) {

			Class<?> classe = obj.getClass();

			// Recuperando o tipo da operacao
			Class<?> tipo = getField(classe, property).getType();

			// garantindo que o property esta correto
			property = property.substring(0, 1).toUpperCase() + property.substring(1);

			Method method = getMethod(classe, "set" + property, new Class[] { tipo });
			method.invoke(obj, new Object[] { value });
		}
	}

	/**
	 * Recupera todos os fields declarados em uma classe e da super classe.
	 * 
	 * @param className
	 * @return
	 */
	public static List<Field> getDeclaredFields(String className) throws Exception {
		return getDeclaredFields(Class.forName(className));
	}

	/**
	 * Recupera todos os fields declarados em uma classe e da super classe.
	 * 
	 * @param classe
	 *            classe
	 * @return
	 */
	public static List<Field> getDeclaredFields(Class<?> classe) throws Exception {

		List<Field> fields = new ArrayList<Field>();

		// Recuperando todos os campos declarados
		do {

			Field[] aux = classe.getDeclaredFields();
			fields.addAll(Arrays.asList(aux));

			classe = classe.getSuperclass();

		} while (classe != Object.class);

		return fields;
	}

	/**
	 * Recupera o valor de uma propriedade de um objeto
	 * 
	 * @param object
	 *            objeto
	 * @param fieldName
	 *            nome do campo
	 * @return valor do objeto
	 * @throws Exception
	 *             Exception
	 */
	@SuppressWarnings("all")
	public static Object getValue(Object object, String fieldName) throws Exception {

		String tmp[] = fieldName.split("\\.");

		Object result = new Object();

		if (tmp.length == 1) {

			Method met = null;
			
			try {
				met = object.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[] {});
			}
			catch(NoSuchMethodException e) {
				try {
					met = object.getClass().getMethod("is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[] {});
				}
				catch(Exception e1) {}
			}
			
			if (met != null) {
				result = met.invoke(object, new Object[] {});
			}
			else {
				result = null;
			}
		} else {

			Method met = object.getClass().getMethod("get" + tmp[0].substring(0, 1).toUpperCase() + tmp[0].substring(1), new Class[] {});

			result = met.invoke(object, new Object[] {});

			fieldName = "";

			for (int i = 1; i < tmp.length; i++) {
				if (!fieldName.equals(""))
					fieldName += ".";
				fieldName += tmp[i];
			}

			result = getValue(result, fieldName);
		}

		return result;
	}

	/**
	 * Executa um método em objeto.
	 * 
	 * @param object
	 *            objeto
	 * @param metod
	 *            método
	 * @param param
	 *            parametro
	 * @param args
	 *            argumentos
	 * @return objeto
	 * @throws Exception
	 *             Exception
	 */
	public static Object execute(Object object, String metod, Class<?>[] param, Object args[]) throws Exception {

		Method met = object.getClass().getMethod(metod, param);

		return met.invoke(object, args);

	}

}