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
		
		List<String> nomes = new ArrayList<String>();
		nomes.add("Andre");
		nomes.add("Beatriz");
		nomes.add("Carlos");
		nomes.add("Debora");
		nomes.add("Elias");
		nomes.add("F�bio");
		
		List<String> telefones = new ArrayList<String>();
		telefones.add("99999-1111");
		telefones.add("99999-2222");
		telefones.add("99999-3333");
		telefones.add("99999-4444");
		telefones.add("99999-5555");

		List<String> emails = new ArrayList<String>();
		emails.add("aaa@xxx.br");
		emails.add("bbb@xxx.br");
		emails.add("ccc@xxx.br");
		emails.add("ddd@xxx.br");
		emails.add("eee@xxx.br");
		
		List<String> obs = new ArrayList<String>();
		obs.add("obs1");
		obs.add("obs2");
		obs.add("obs3");
		obs.add("obs4");
		obs.add("obs5");
		
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		Transaction transacao = sessao.beginTransaction(); 
		ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);
		
		SecureRandom random = new SecureRandom();
		
		Contato contatoE = new Contato();
		
		contatoE.setNome("nome_teste_escrita");
		contatoE.setTelefone("telefone_teste_escrita");
		contatoE.setEmail("email_teste_escrita");
		contatoE.setDataCadastro(new Date(System.currentTimeMillis()));
		contatoE.setObservacao("obs_teste_escrita");
		contatoE.setCodigo(random.nextInt(100));
		
		TransacaoEscrita te = new TransacaoEscrita(contatoE, contatoCrud);
		
		TransacaoConsulta tc = new TransacaoConsulta(contatoCrud, random.nextInt(100));
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		executorService.execute(te);
		
		executorService.execute(tc);
		
		transacao.commit();

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
