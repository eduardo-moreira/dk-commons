package com.dk.utils.persistence.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.ElementCollection;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Transient;

import com.dk.utils.annotations.Descriptor;
import com.dk.utils.reflection.ReflectionUtils;

/**
 * Classe responsável pelo servicos
 */
@SuppressWarnings({ "unchecked" })
public class GenericDAO<T> implements Serializable {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link EntityManager}.
	 */
	protected EntityManager manager;

	/**
	 * Classe de persistencia.
	 */
	private Class<?> persistentClass;

	/**
	 * 
	 * Cria uma nova instancia de GenericDAO.
	 */
	public GenericDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Cria instancia da classe
	 * 
	 * @param unit
	 * @param persistentClass
	 */
	public GenericDAO(EntityManager entityManager) {
		this();
		manager = entityManager;
	}

	/**
	 * Recupera o EJB DAOFactory.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static GenericDAOFactory getDAOFactory() throws Exception {
		InitialContext ctx = new InitialContext();
		return (GenericDAOFactory) ctx.lookup("DAOFactory");
	}

	/**
	 * Insert/Update entity on database.
	 * 
	 * @param obj
	 */
	public void save(T obj) throws Exception {

		// Carregando o objeto na memória
		long id = ReflectionUtils.getIdValue(obj);

		if (id == 0) {
			insert(obj);
		} else {
			merge(obj);
		}
	}

	/**
	 * Insert new entity on database.
	 * 
	 * @param obj
	 *            entity instance
	 */
	public void insert(T obj) throws Exception {
		manager.persist(obj);
	}

	/**
	 * Update entity on database.
	 * 
	 * @param obj
	 */
	public T merge(T obj) throws Exception {
		return manager.merge(obj);
	}

	/**
	 * Remove entity from database.
	 * 
	 * @param obj
	 */
	public void remove(T obj) throws Exception {

		// Carregando o objeto na memória
		long id = ReflectionUtils.getIdValue(obj);

		obj = (T) manager.find(this.persistentClass, id);

		manager.remove(obj);

	}

	/**
	 * Remove one entity, loaded by id param, from database.
	 * 
	 * @param id
	 */
	public void remove(long id) throws Exception {

		// Carregando o objeto na memória
		T obj = (T) manager.find(this.persistentClass, id);

		manager.remove(obj);

	}

	/**
	 * Remove all entities loaded by ids params, from database.
	 * 
	 * @param ids
	 */
	public void remove(long[] ids) throws Exception {

		for (long id : ids) {
			remove(id);
		}
	}

	/**
	 * Load entity from database.
	 * 
	 * @param id
	 * @return
	 */
	public T load(String id) {
		return load(new Long(id));
	}

	/**
	 * Load entity from database.
	 * 
	 * @param id
	 * @return
	 */
	public T load(long id) {
		T obj = null;

		obj = (T) manager.find(this.persistentClass, id);

		manager.refresh(obj);

		return obj;
	}

	/**
	 * Lista todos os registros da entidade.
	 * 
	 * @return
	 */
	public List<T> listAll() throws Exception {

		Class<T> myClass = (Class<T>) getPersistentClass();
		StringBuilder where = new StringBuilder("SELECT obj FROM " + myClass.getName() + " obj ");

		long id = -1;

		Field fieldOrder = ReflectionUtils.getDescriptor(persistentClass);

		if (fieldOrder != null) {
			where.append(" ORDER BY " + fieldOrder.getName());
		}

		Query queryData = manager.createQuery(where.toString());

		if (id != -1) {
			queryData.setParameter(1, id);
		}

		return queryData.getResultList();
	}

	/**
	 * Lista todos os registros encontrados para o filtro da entidade.
	 * 
	 * @param filter
	 *            filtro (entidade)
	 * 
	 * @param any
	 *            caso seja true, irá usar OR entre as condições, caso contrário
	 *            AND.
	 * 
	 * @return
	 */
	public List<T> list(T filter, boolean any) throws Exception {
		return list(filter, any, null);
	}

	/**
	 * Lista todos os registros encontrados para o filtro da entidade.
	 * 
	 * @param filter
	 *            filtro (entidade)
	 * 
	 * @param any
	 *            caso seja true, irá usar OR entre as condições, caso contrário
	 *            AND.
	 * 
	 * @param orderBy ordem do sql
	 * 
	 * @return
	 */
	public List<T> list(T filter, boolean any, String orderBy) throws Exception {

		if (filter == null) {
			return listAll();
		}

		Class<T> myClass = (Class<T>) getPersistentClass();

		StringBuilder query = new StringBuilder();
		query.append("SELECT obj FROM ");
		query.append(myClass.getName());
		query.append(" obj ");

		// Where
		StringBuilder where = new StringBuilder();

		// Capturando campos da entidade
		List<Field> fields = ReflectionUtils.getDeclaredFields(this.persistentClass);
		List<Object> values = new ArrayList<Object>();

		// Verificando campos
		if (fields != null) {

			for (Field field : fields) {

				if ((orderBy == null) && (field.isAnnotationPresent(Descriptor.class))) {
					orderBy = field.getName();
			    } 

				if (!field.isAnnotationPresent(Id.class) // Descartando atributo
															// ID
						&& !field.isAnnotationPresent(Transient.class) // Descartando
																		// atributos
																		// nao
																		// mapeados
						&& !field.isAnnotationPresent(OneToOne.class) // Descartando
																		// atributos
																		// complexos
						&& !field.isAnnotationPresent(OneToMany.class) // Descartando
						&& !field.isAnnotationPresent(ElementCollection.class) // Descartando
						&& !field.isAnnotationPresent(ManyToOne.class) // Descartando
																		// coleções
						&& !Modifier.isStatic(field.getModifiers()) // Descartar
																	// estáticos
				) {

					Object pValue = ReflectionUtils.getValue(filter, field.getName());

					if (pValue != null) {

						if (field.getType() == String.class) {

							String value = (String) pValue;

							if (value.trim().length() != 0) {

								if (where.length() > 0) {
									if (any) {
										where.append(" OR ");
									} else {
										where.append(" AND ");
									}
								}

								if (value.indexOf('*') > -1) {
									where.append(" obj.");
									where.append(field.getName());
									where.append(" LIKE '");
									where.append(value.replaceAll("\\*", "%"));
									where.append("' ");
								} else {
									values.add(value);
									where.append(" obj.");
									where.append(field.getName());
									where.append(" = ?");
									where.append(values.size());
								}
							}
						} else {

							if (where.length() > 0) {
								if (any) {
									where.append(" OR ");
								} else {
									where.append(" AND ");
								}
							}

							values.add(pValue);

							where.append(" obj.");
							where.append(field.getName());
							where.append(" = ?");
							where.append(values.size());
						}

					}

				}

			}
		}

		if (where.length() > 0) {
			query.append(" WHERE ");
			query.append(where);
		}
		
		if (orderBy != null) {
		      query.append(" ORDER BY " + orderBy);
		} 

		Query queryData = manager.createQuery(query.toString());

		for (int i = 0; i < values.size(); i++) {
			queryData.setParameter(i + 1, values.get(i));
		}

		return queryData.getResultList();
	}

	/**
	 * Conta uma colecao da entidade;
	 * 
	 * @param id
	 * @param collection
	 * @return
	 */
	public int countCollection(long id, String collection) {
		Class<T> myClass = (Class<T>) getPersistentClass();
		StringBuilder where = new StringBuilder("SELECT SIZE(obj.");
		where.append(collection);
		where.append(") FROM ");
		where.append(myClass.getName());
		where.append(" obj ");
		where.append("WHERE obj.id = ?1");

		Query queryData = manager.createQuery(where.toString());
		queryData.setParameter(1, id);

		Number countResult = (Number) queryData.getSingleResult();
		return countResult.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	public void finalize() throws Throwable {

		try {

			this.manager.close();
			super.finalize();
		} catch (Exception e) {
		}
	}

	/**
	 * Get the manager value
	 * 
	 * @return the manager
	 */
	public EntityManager getManager() {
		return manager;
	}

	/**
	 * Set the manager value
	 * 
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * Get the persistentClass value
	 * 
	 * @return the persistentClass
	 */
	public Class<?> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * Set the persistentClass value
	 * 
	 * @param persistentClass
	 *            the persistentClass to set
	 */
	public void setPersistentClass(Class<?> persistentClass) {
		this.persistentClass = persistentClass;
	}

}