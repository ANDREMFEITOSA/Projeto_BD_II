package crudannotations;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.List;
import org.hibernate.*;
import conexao.HibernateUtil;
public class ContatoCrudAnnotations {
	private Session sessao;
	public ContatoCrudAnnotations(Session sessao) {
		this.sessao = sessao;
	}
	public void salvar(Contato contato) {
		sessao.save(contato); 
	}
	public void atualizar(Contato contato) {
		sessao.update(contato); 
	}
	public void excluir(Contato contato) {
		sessao.delete(contato); 
	}
	public List<Contato> listar() { 
		Query consulta = sessao.createQuery("from Contato");
		return consulta.list();
	}
	public Contato buscaContato(int valor) {
		Query consulta = sessao.createQuery("from Contato where codigo = :parametro"); 
		consulta.setInteger("parametro", valor); 
		return (Contato) consulta.uniqueResult(); 
	}
	public static void main(String[] args) {
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		Transaction transacao = sessao.beginTransaction(); 
		ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);

		int i;
		int j = 50;
		
		List<String> nomes = new ArrayList<String>();
		nomes.add("Andre");
		nomes.add("Beatriz");
		nomes.add("Carlos");
		nomes.add("Debora");
		nomes.add("Elias");
		nomes.add("Fábio");
		
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
		
		SecureRandom random = new SecureRandom();
		
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
		 
		System.out.println("Total de registros cadastrados: " + contatoCrud.listar().size()); 
	}

}
