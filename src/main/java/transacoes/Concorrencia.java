package transacoes;

import java.security.SecureRandom;
import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexao.HibernateUtil;
import crudannotations.Contato;
import crudannotations.ContatoCrudAnnotations;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Concorrencia {
	
	public static void main(String[] args) {
		
		int numThreads = 100;
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		for(int i = 0; i < numThreads; i++) {
			
			executorService.execute(new Runnable() {

				public void run() {
						
						SecureRandom random = new SecureRandom();
						
						Contato contato = new Contato();
						
						Session sessao = HibernateUtil.getSessionFactory().openSession();
												
						ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);
						
						long inicio = System.nanoTime();
							
						Transaction transacao = sessao.beginTransaction();
							
						contato = contatoCrud.buscaContato(random.nextInt(100)); //operação de consulta
							
						//System.out.println("Transacao - Consulta - " + contato.getCodigo() + " : " + contato.getNome() + " : " + contato.getTelefone() + " : " + contato.getEmail() + " : " + contato.getDataCadastro() + " : " + contato.getObservacao());
												
						contato.setNome("nome_teste");
						contato.setTelefone("telefone_teste");
						contato.setEmail("email_teste");
						contato.setDataCadastro(new Date(System.currentTimeMillis()));
						contato.setObservacao("obs_teste");
													
						contatoCrud.atualizar(contato); //operação de escrita
						
						transacao.commit();
						
						long fim = System.nanoTime();
						
						System.out.println("Tempo de Transacao: " + (fim - inicio)/1000000 + "ms");
					}
				});
			}
	}
}
