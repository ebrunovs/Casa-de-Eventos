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
import modelo.Senha;

public class DAOEvento extends DAO<Evento> {

	public Evento read(Object chave) {
		try {
			String nome = (String) chave;
			TypedQuery<Evento> q = manager.createQuery("select p from Evento p where p.nome=:n", Evento.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}


	// --------------------------------------------
	// consultas
	// --------------------------------------------
	public List<Senha> passwordsByDate(String data) {
		TypedQuery<Senha> q = manager.createQuery(
				"""
				select s from Senha s join s.evento e where e.data =:d
				""",Senha.class);
		q.setParameter("d", data);
		return q.getResultList();
	}
	
	public List<Evento> passwordsByEvent(int n) {
	    TypedQuery<Evento> q = manager.createQuery(
	        """
	        select e from Evento e where e.id = :n
	        """, Evento.class);
	    q.setParameter("n", n);
	    return q.getResultList();
	}
	
	public List<Evento> NPasswords(){
		 TypedQuery<Evento> q = manager.createQuery(
			        """
			       select e from Evento e where size(e.senhas) > 4
			        """, Evento.class);
			    return q.getResultList();
	}

	public List<Cliente> customersInEvent(String nome){
		TypedQuery<Cliente> q = manager.createQuery("select c from Cliente c join c.senhas s join s.evento e where e.nome = :n", Cliente.class);
		q.setParameter("n", nome);
		return q.getResultList();
	}


}


	

