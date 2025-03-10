/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Aluno;
import modelo.Cliente;

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

	}

