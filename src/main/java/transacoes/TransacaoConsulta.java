package transacoes;

import java.security.SecureRandom;

import org.hibernate.*;
import conexao.HibernateUtil;
import crudannotations.Contato;
import crudannotations.ContatoCrudAnnotations;

public class TransacaoConsulta implements Runnable {
	
	private final ContatoCrudAnnotations contatoCrud;
	
	private final int codigoContato;
	
	public TransacaoConsulta(ContatoCrudAnnotations contatoCrud, int codigoContato) {
			this.contatoCrud = contatoCrud;
			this.codigoContato = codigoContato;
	}

	public void run() {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		Transaction transacao = sessao.beginTransaction();
		Contato contato = new Contato();
		contato = contatoCrud.buscaContato(codigoContato);
		System.out.println("Contato:" + contato.getCodigo() + "-" + contato.getNome() + "-" + contato.getTelefone() + "-" + contato.getEmail() + "-" + contato.getDataCadastro() + "-" + contato.getObservacao());
		/*try
		{
			
			
			
		}
		catch(InterruptedException exception)
		{
			exception.printStackTrace();
			Thread.currentThread().interrupt();
		}*/


	}

}
