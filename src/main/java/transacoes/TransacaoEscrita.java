package transacoes;

import org.hibernate.*;
import conexao.HibernateUtil;
import crudannotations.Contato;
import crudannotations.ContatoCrudAnnotations;

public class TransacaoEscrita implements Runnable {
	
	private final Contato contato;
	
	private final ContatoCrudAnnotations contatoCrud;
		
	public TransacaoEscrita(Contato contato, ContatoCrudAnnotations contatoCrud) {
			this.contato = contato;
			this.contatoCrud = contatoCrud;
	}

	public void run() {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		Transaction transacao = sessao.beginTransaction(); 
		contatoCrud.atualizar(contato);
		
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
