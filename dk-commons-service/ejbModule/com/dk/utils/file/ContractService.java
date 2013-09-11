package com.dk.utils.file;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dk.utils.constantes.ConstantesGenerica;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

@Stateless
public class ContractService implements Serializable {

	/**
	 * @service
	 */
	private static final long serialVersionUID = 6124420506123559821L;
	
	public static void main(String[] args) throws JRException {
		ContractService cs = new ContractService();
		cs.test();
	}

	public void test() throws JRException {
		gerarPdf();
		
	}
	
	/**
	 * @throws JRException 
	 *
	 */
	public void gerarPdf() throws JRException{
		//PARAMETROS
		//  Chave cliente.
		//DADOS CONTRATO
		//  Nome
		//  Data Nascimento
		//  Documentos
		//  
		long start = System.currentTimeMillis();
		JasperFillManager.fillReportToFile(ConstantesGenerica.CAMINHO_MODELO_CONTRATOS + "report1.jasper", null, new JREmptyDataSource());
		System.out.println("Filling time : " + (System.currentTimeMillis() - start));
		JasperExportManager.exportReportToPdfFile(ConstantesGenerica.CAMINHO_MODELO_CONTRATOS + "report1.jrprint");
		System.out.println("PDF creation time : " + (System.currentTimeMillis() - start));
		
	}



}
