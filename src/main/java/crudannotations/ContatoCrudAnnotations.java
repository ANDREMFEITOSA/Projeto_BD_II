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
}
