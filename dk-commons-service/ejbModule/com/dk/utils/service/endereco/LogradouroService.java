package com.dk.utils.service.endereco;

import java.util.List;

import com.dk.utils.domain.endereco.Localidade;
import com.dk.utils.domain.endereco.Logradouro;
import com.dk.utils.exception.BusinessException;
import com.dk.utils.persistence.endereco.LogradouroDAO;
import com.dk.utils.service.common.GenericService;

/**
 * @author eduardo
 * 
 */
public class LogradouroService extends GenericService<Logradouro> {

	/**
	 * long - serialVersionUID
	 */
	private static final long serialVersionUID = -8667485814549736825L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dk.utils.service.common.GenericService#getDao()
	 */
	@Override
	public LogradouroDAO getDao() {
		return (LogradouroDAO) dao;
	}

	/**
	 * Carrega um logradouro pelo cep.
	 * 
	 * @param cep
	 * @return
	 * @throws Exception
	 */
	public Logradouro loadByCep(String cep) throws Exception {

		// Removendo caracteres
		cep = cep.replaceAll("-", "").replace(".", "").trim();

		Logradouro filter = new Logradouro();
		filter.setCep(Integer.parseInt(cep));

		List<Logradouro> ruas = list(filter, false);

		if (ruas == null || ruas.isEmpty()) {
			throw new BusinessException("cepNaoEncontrado");
		}

		return ruas.get(0);
	}

	/**
	 * Carrega CEPs pelo endereço. Parametros: nome, localidade.cidade e
	 * localidade.uf.
	 * 
	 * @param endereco
	 * @return lista de logradouros.
	 */
	public List<Logradouro> loadCEPsByAdrress(String logradouro, String cidade, String uf) throws Exception {
		if (logradouro == null) {
			throw new BusinessException("InformeLogradouro");
		}

		if (cidade == null) {
			throw new BusinessException("InformeCidade");
		}

		if (uf == null) {
			throw new BusinessException("InformeUF");
		}

		Logradouro l = new Logradouro();
		Localidade loc = new Localidade();
		l.setNome(logradouro);
		loc.setCidade(cidade);
		loc.setUf(uf);
		l.setLocalidade(loc);

		List<Logradouro> logradouros = getDao().recuperarCEP(l);

		if (logradouros == null || logradouros.size() == 0) {
			throw new BusinessException("enderecoNaoEncontrado");
		}

		return logradouros;
	}

}
