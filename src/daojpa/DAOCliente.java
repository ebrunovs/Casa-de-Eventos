/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;


import java.util.List;


import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import modelo.Evento;

public class DAOCliente extends DAO<Cliente>{

	public Cliente read (Object chave){
		try{
			String nome = (String) chave;
			TypedQuery<Cliente> q = manager.createQuery("select c from Cliente c where c.nome=:n", Cliente.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}		
	}
	
	
	public List<Evento> customerEvents(String nome){
		TypedQuery<Evento> q = manager.createQuery("select e from Evento e join e.senhas s join s.cliente c where c.nome =:n", Evento.class);
		q.setParameter("n", nome);
		return q.getResultList();
	}

	}

