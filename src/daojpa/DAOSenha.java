/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Senha;
import modelo.Telefone;

public class DAOSenha extends DAO<Senha> {

	public Senha read(Object chave) {
		try {
			String numero = (String) chave;
			TypedQuery<Senha> q = manager.createQuery("select t from Telefone t where t.numero = :n ",
					Senha.class);
			q.setParameter("n", numero);

			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	public List<Senha> readLikeNumero(String digitos) {
		// carregar telefone com seus relacionamentos
		TypedQuery<Senha> q = manager.createQuery("""
				select t from Telefone t 
				where t.numero like :x """, Senha.class);
		q.setParameter("x", "%" + digitos + "%");
		return q.getResultList();
	}

	// --------------------------------------------
	// consultas
	// --------------------------------------------

}
