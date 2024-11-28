package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Senha;


public class DAOSenha extends DAO<Senha>{
	public Senha read (Object chave) {
		int id = (Integer) chave;	
		Query q = manager.query();
		q.constrain(Senha.class);
		q.descend("id").constrain(id);
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
