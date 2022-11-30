package transacoes;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexao.HibernateUtil;
import crudannotations.Contato;
import crudannotations.ContatoCrudAnnotations;

import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class ConcorrenciaDeTransacoes {
	
	public static void main(String[] args) {
		
		final ArrayList<Long> tempo = new ArrayList<Long>();
		
		int numThreads = Runtime.getRuntime().availableProcessors();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		for(int i = 0; i < numThreads; i++) {
			
			//executorService.execute(new Runnable() {
			executorService.execute(new Transacao(tempo) {
				public void run() {
					
						long inicio = System.nanoTime();
						
						SecureRandom random = new SecureRandom();
						
						Contato contato = new Contato();
						
						Session sessao = HibernateUtil.getSessionFactory().openSession();
												
						ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);
						
						for(int j = 0; j < 25; j++) {
							
							Transaction transacao = sessao.beginTransaction();
							
							contato = contatoCrud.buscaContato(random.nextInt(100)); //operação de consulta
							
							System.out.println("Transacao " + j + " - Consulta" + " - Contato:" + contato.getCodigo() + " : " + contato.getNome() + " : " + contato.getTelefone() + " : " + contato.getEmail() + " : " + contato.getDataCadastro() + " : " + contato.getObservacao());
							
							contato.setNome("nome_teste");
							contato.setTelefone("telefone_teste");
							contato.setEmail("email_teste");
							contato.setDataCadastro(new Date(System.currentTimeMillis()));
							contato.setObservacao("obs_teste");
							
							contatoCrud.atualizar(contato); //operação de escrita
							
							transacao.commit();
						}
						
						long fim = System.nanoTime();
						
						tempo.add(fim - inicio);
					}
				});
			}
	}
}
