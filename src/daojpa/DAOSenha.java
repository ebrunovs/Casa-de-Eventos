/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Evento;
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
	
	public boolean PasswordExistInEvent(String senha){	
		try {
		TypedQuery<Senha> q = manager.createQuery("select s from Senha s join s.evento e where e.nome = 'Casamento' and s.codigo = :s", Senha.class);
		q.setParameter("s", senha);
		if (q.getSingleResult() != null) {
			return true;
			}
		}catch(NoResultException e) {
			return false;			
		}
		return false;	
	}

//	System.out.println("\n---Senha ja existe no evento? (CASO NEGATIVO) ");
//	jpql = "select s from Senha s " +
//		   "join s.evento e " +
//	       "where e.nome = 'Casamento' and s.codigo = '12345'";
//	query3 = manager.createQuery(jpql, Senha.class);
//	senhas = query3.getResultList();
//	if (senhas.isEmpty())
//	    System.out.println("Senha não existe no evento");
//	else
//	    System.out.println("Senha já existe no evento");
}
