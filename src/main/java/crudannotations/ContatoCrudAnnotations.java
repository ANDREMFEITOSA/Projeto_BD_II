package crudannotations;
import java.util.List;
import org.hibernate.*;
public class ContatoCrudAnnotations {
	
	private Session sessao;
	
	public ContatoCrudAnnotations(Session sessao) {
		this.sessao = sessao;
	}
	
	public Session getSessao() {
		return sessao;
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
		consulta.setLockMode("PESSIMISTIC_WRITE", LockMode.PESSIMISTIC_WRITE); //lock exclusivo, bloqueia a leitura
		//consulta.setLockMode("PESSIMISTIC_WRITE", LockMode.PESSIMISTIC_READ); //lock compartilhado, bloqueia o dado de ser alterado ou deletado
		consulta.setInteger("parametro", valor); 
		return (Contato) consulta.uniqueResult(); 
	}
}
