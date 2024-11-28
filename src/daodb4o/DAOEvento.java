package daodb4o;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class DAOEvento extends DAO<Evento> {
	public Evento read (Object chave) {
		int id = (Integer) chave;	
		Query q = manager.query();
		q.constrain(Evento.class);
		q.descend("id").constrain(id);
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
	
	public List<Senha> senhasdoevento(String data){
		Query q = manager.query();
		q.constrain(Evento.class);
		q.descend("data").constrain(data);
		List<Cliente> dataEvento = q.execute();
		if(!dataEvento.isEmpty()) {
			return dataEvento.getFirst().getListaSenhas();
		}
		return null;
	}
}
