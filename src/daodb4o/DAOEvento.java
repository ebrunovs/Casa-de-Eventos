package daodb4o;

import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class DAOEvento extends DAO<Evento> {
	public Evento read (String nome) {	
		Query q = manager.query();
		q.constrain(Evento.class);
		q.descend("nome").constrain(nome);
		List<Evento> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	public void create(Evento obj){
		int novoid = super.gerarId(Evento.class);  	
		obj.setId(novoid);				
		manager.store( obj );
	}
	
	public List<Senha> passwordsByDate(String data){
		Query q = manager.query();
		q.constrain(Evento.class);
		q.descend("data").constrain(data);
		List<Cliente> dataEvento = q.execute();
		if(!dataEvento.isEmpty()) {
			return dataEvento.getFirst().getSenhas();
		}
		return null;
	}
	
	public List<Evento> passwordsByEvent(int n){
		Query q = manager.query();
		q.constrain(Evento.class);
		q.constrain(new Filtro(n));
		return q.execute();
	}
}


/*-------------------------------------------------*/
@SuppressWarnings("serial")
class Filtro  implements Evaluation {
	private int n;
	public Filtro (int n) {
		this.n=n;
	}
	public void evaluate(Candidate candidate) {
		Evento e = (Evento) candidate.getObject();
		candidate.include( e.getSenhas().size() > n );
	}
}
