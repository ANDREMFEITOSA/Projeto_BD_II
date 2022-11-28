package transacoes;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexao.HibernateUtil;
import crudannotations.Contato;
import crudannotations.ContatoCrudAnnotations;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ConcorrenciaDeTransacoes {
	
	public static void main(String[] args) {
		
		int numThreads = Runtime.getRuntime().availableProcessors();
				
		System.out.println(numThreads);
		
		ExecutorService executorService = Executors.newCachedThreadPool();
			
		for(int i = 0; i < numThreads; i++) {
			
			executorService.execute(new Runnable() {
				public void run() {
						
						SecureRandom random = new SecureRandom();
						
						Contato contato = new Contato();
						
						//Contato contato = (Contato)Session.get(Contato.class, codigo, LockOptions.UPGRADE);
						
						Session sessao1 = HibernateUtil.getSessionFactory().openSession();
						
						//Session sessao2 = HibernateUtil.getSessionFactory().openSession();
						
						ContatoCrudAnnotations contatoCrud1 = new ContatoCrudAnnotations(sessao1);
						
						//ContatoCrudAnnotations contatoCrud2 = new ContatoCrudAnnotations(sessao2);
						
						//Transaction transacao1 = sessao1.beginTransaction();
						
						for(int j = 0; j < 5; j++) {
							
							Transaction transacao1 = sessao1.beginTransaction();
							
							contato = contatoCrud1.buscaContato(random.nextInt(100));
							//contato = contatoCrud1.buscaContato(50);
							
							System.out.println("Transacao " + j + " - Teste consulta" + " - Contato:" + contato.getCodigo() + " : " + contato.getNome() + " : " + contato.getTelefone() + " : " + contato.getEmail() + " : " + contato.getDataCadastro() + " : " + contato.getObservacao());
							
							contato.setNome("nome_teste");
							contato.setTelefone("telefone_teste");
							contato.setEmail("email_teste");
							contato.setDataCadastro(new Date(System.currentTimeMillis()));
							contato.setObservacao("obs_teste");
							//contato.setCodigo(random.nextInt(100));
							
							contatoCrud1.atualizar(contato);
							
							System.out.println("Transacao " + j + " - Teste escrita" + " - Contato:" + contato.getCodigo() + " : " + contato.getNome() + " : " + contato.getTelefone() + " : " + contato.getEmail() + " : " + contato.getDataCadastro() + " : " + contato.getObservacao());
							
							transacao1.commit();
							
							/*Transaction transacao2 = sessao2.beginTransaction();
							
							contato.setNome("t2_nome_teste_escrita");
							contato.setTelefone("t2_telefone_teste_escrita");
							contato.setEmail("t2_email_teste_escrita");
							contato.setDataCadastro(new Date(System.currentTimeMillis()));
							contato.setObservacao("t2_obs_teste_escrita");
							//contato.setCodigo(random.nextInt(100));
							
							contatoCrud2.atualizar(contato);
							
							System.out.println("Contato:" + contato.getCodigo() + "-" + contato.getNome() + "-" + contato.getTelefone() + "-" + contato.getEmail() + "-" + contato.getDataCadastro() + "-" + contato.getObservacao());
							
							transacao2.commit();*/
						}
						//transacao1.commit();
					}
				});
			}
		
		/*TransacaoEscrita te = new TransacaoEscrita(contatoE, contatoCrud);
			
		TransacaoConsulta tc = new TransacaoConsulta(contatoCrud, random.nextInt(100));

		executorService.execute(te);
		
		executorService.execute(tc);*/		
	}
}
