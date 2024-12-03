package daodb4o;


import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class DAOCliente  extends DAO<Cliente>{

	public Cliente read (String nome) {	
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("nome").constrain(nome);
		List<Cliente> resultados = q.execute();
		if (resultados.size()>0) {
			return resultados.get(0);}
		else {
			return null;}
	}

	public void create(Cliente obj){
		int novoid = super.gerarId(Cliente.class);  	
		obj.setId(novoid);				
		manager.store( obj );
	}
	
	public List<Evento> customerEvents(String nome){
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("nome").constrain(nome);
		List<Cliente> cliente = q.execute();
		List<Evento> eventos = new ArrayList<>();
		if(!cliente.isEmpty()) {
			for(Senha s: cliente.get(0).getSenhas()) {
				eventos.add(s.getEvento());
			}
		}
		return eventos;
	}
	
	public  List<Cliente> readAll(String caracteres) {
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("nome").constrain(caracteres).like();		//insensitive
		List<Cliente> result = q.execute(); 
		return result;
	}
}



