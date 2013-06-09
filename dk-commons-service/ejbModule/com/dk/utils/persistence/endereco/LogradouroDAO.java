package com.dk.utils.persistence.endereco;

import java.util.List;

import javax.persistence.Query;

import com.dk.utils.domain.endereco.Logradouro;
import com.dk.utils.persistence.common.GenericDAO;

/**
 * @author eduardo
 *
 */
public class LogradouroDAO extends GenericDAO<Logradouro> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -3816309904677837685L;
	
	/**
	 * Recupera o CEP pelo logradouro, cidade e estado.
	 * 
	 * @param l
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Logradouro> recuperarCEP(Logradouro l) {
		StringBuilder sql = new StringBuilder("SELECT l ");
		sql.append("FROM Logradouro l, Localidade loc ");
		sql.append("WHERE l.nome like ?1 AND ");
		sql.append("loc.cidade = ?2 AND ");
		sql.append("loc.uf = ?3 AND ");
		sql.append("l.localidade = loc.id");
		
		Query query = manager.createQuery(sql.toString());

		query.setParameter(1, "%" + l.getNome());
		query.setParameter(2, l.getLocalidade().getCidade());
		query.setParameter(3, l.getLocalidade().getUf());

		return query.getResultList();
	}

}
