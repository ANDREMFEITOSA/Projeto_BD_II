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
				
		ExecutorService executorService = Executors.newCachedThreadPool();
			
			executorService.execute(new Runnable() {
				public void run() {
					
					SecureRandom random = new SecureRandom();
					
					Contato contato = new Contato();
					
					//Contato contato = (Contato)Session.get(Contato.class, codigo, LockOptions.UPGRADE);
					
					Session sessao = HibernateUtil.getSessionFactory().openSession();
					
					ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);
					
					for(int i = 0; i < 5; i++) {
					
						contato = contatoCrud.buscaContato(random.nextInt(100));
						
						Transaction transacao1 = sessao.beginTransaction(); 
					
						System.out.println("Contato:" + contato.getCodigo() + "-" + contato.getNome() + "-" + contato.getTelefone() + "-" + contato.getEmail() + "-" + contato.getDataCadastro() + "-" + contato.getObservacao());
					
						contato.setNome("t1_nome_teste_escrita");
						contato.setTelefone("t1_telefone_teste_escrita");
						contato.setEmail("t1_email_teste_escrita");
						contato.setDataCadastro(new Date(System.currentTimeMillis()));
						contato.setObservacao("t1_obs_teste_escrita");
						//contato.setCodigo(random.nextInt(100));
					
						contatoCrud.atualizar(contato);
					
						System.out.println("Contato:" + contato.getCodigo() + "-" + contato.getNome() + "-" + contato.getTelefone() + "-" + contato.getEmail() + "-" + contato.getDataCadastro() + "-" + contato.getObservacao());
					
						transacao1.commit();
					
						Transaction transacao2 = sessao.beginTransaction();
					
						contato.setNome("t2_nome_teste_escrita");
						contato.setTelefone("t2_telefone_teste_escrita");
						contato.setEmail("t2_email_teste_escrita");
						contato.setDataCadastro(new Date(System.currentTimeMillis()));
						contato.setObservacao("t2_obs_teste_escrita");
						//contato.setCodigo(random.nextInt(100));
					
						contatoCrud.atualizar(contato);
					
						System.out.println("Contato:" + contato.getCodigo() + "-" + contato.getNome() + "-" + contato.getTelefone() + "-" + contato.getEmail() + "-" + contato.getDataCadastro() + "-" + contato.getObservacao());
					
						transacao2.commit();
					}
				}
			});
			
		//}
		
		/*TransacaoEscrita te = new TransacaoEscrita(contatoE, contatoCrud);
			
		TransacaoConsulta tc = new TransacaoConsulta(contatoCrud, random.nextInt(100));*/

		
		//executorService.execute(te);
		
		//executorService.execute(tc);
		
		//transacao.commit();

		/*int i;
		int j = 50;
		
			
		for(i = 0; i < j; i++) {
			
			Contato contato = new Contato();
			
			contato.setNome(nomes.get(random.nextInt(nomes.size())));
			contato.setTelefone(telefones.get(random.nextInt(telefones.size())));
			contato.setEmail(emails.get(random.nextInt(emails.size())));
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setObservacao(obs.get(random.nextInt(obs.size())));
			contatoCrud.salvar(contato);
		}
		
		transacao.commit();
		 
		System.out.println("Total de registros cadastrados: " + contatoCrud.listar().size());*/ 
	}
}
