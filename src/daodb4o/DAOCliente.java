package daodb4o;


import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class DAOCliente  extends DAO<Cliente>{

	public Cliente read (Object chave) {
		int id = (Integer) chave;	
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("id").constrain(id);
		List<Cliente> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	public void create(Cliente obj){
		int novoid = super.gerarId(Cliente.class);  	
		obj.setId(novoid);				
		manager.store( obj );
	}
	
	public List<String> eventosdocliente(String cpf){
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("cpf").constrain(cpf);
		List<Cliente> cliente = q.execute();
		List<String> eventos = new ArrayList<>();
		if(!cliente.isEmpty()) {
			for(Senha s: cliente.get(0).getListaSenhas()) {
				eventos.add(s.getEvento().getnomeevento());
			}
		}
		return eventos;
	}
}



