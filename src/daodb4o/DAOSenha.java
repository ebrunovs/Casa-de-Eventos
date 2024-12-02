package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Evento;
import modelo.Senha;


public class DAOSenha extends DAO<Senha>{
	public Senha read (String cod) {
		Query q = manager.query();
		q.constrain(Senha.class);
		q.descend("codigo").constrain(cod);
		List<Senha> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	public void create(Senha obj){
		int novoid = super.gerarId(Senha.class);  	
		obj.setId(novoid);				
		manager.store( obj );
	}
}
