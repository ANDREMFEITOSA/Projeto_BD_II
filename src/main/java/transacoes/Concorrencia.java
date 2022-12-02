package transacoes;

import java.security.SecureRandom;
import java.sql.Date;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexao.HibernateUtil;
import crudannotations.Contato;
import crudannotations.ContatoCrudAnnotations;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Concorrencia {
	
	public static ArrayList<Long> tempo = new ArrayList<Long>();
	
	public static long tempoAcumulado;
	
	public static void main(String[] args) throws InterruptedException {
		
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
							
						contato = contatoCrud.buscaContato(random.nextInt(500)+1); //operação de consulta
							
						//System.out.println("Transacao - Consulta - " + contato.getCodigo() + " : " + contato.getNome() + " : " + contato.getTelefone() + " : " + contato.getEmail() + " : " + contato.getDataCadastro() + " : " + contato.getObservacao());
												
						contato.setNome("nome_teste");
						contato.setTelefone("telefone_teste");
						contato.setEmail("email_teste");
						contato.setDataCadastro(new Date(System.currentTimeMillis()));
						contato.setObservacao("obs_teste");
													
						contatoCrud.atualizar(contato); //operação de escrita
						
						transacao.commit();
						
						long fim = System.nanoTime();
						
						tempo.add((fim - inicio)/1000000);
						
						System.out.println("Tempo de Transacao: " + (fim - inicio)/1000000 + "ms");
					}
				});
			}
		
		// SOLUCAO RUIM
		// DEVER DE CASA: PARA HOJE
		// BUSCAR COMO FAZER COM QUE O EXECUTOR AGUARDE PELA COMPLETUDE DAS THREADS
		
		Thread.sleep(10000);
		
		for(int k = 0; k < tempo.size(); k++) {
			System.out.println("Tempo : " + tempo.get(k));
			tempoAcumulado += tempo.get(k);
		}
		
		System.out.println("Média de Tempo : " + tempoAcumulado/tempo.size());
	}
}
