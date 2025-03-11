/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Senha;

public class DAOSenha extends DAO<Senha> {

	public Senha read(Object chave) {
		try {
			String numero = (String) chave;
			TypedQuery<Senha> q = manager.createQuery("select s from Senha s where s.codigo = :n ",
					Senha.class);
			q.setParameter("n", numero);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
