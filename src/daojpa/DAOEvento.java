/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.Evento;
import modelo.Evento;

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
	public List<Evento> readLikeNome(String caracteres) {
		TypedQuery<Evento> q = manager.createQuery(
				"""
				select p from Evento p 
				where p.nome like :x  
				""",Evento.class);
		q.setParameter("x", "%" + caracteres + "%");
		return q.getResultList();
	}
	
	public List<Evento> readByNTelefones(int n) {
		TypedQuery<Evento> q = manager.createQuery(
				"""
				select p from Evento p 
				where SIZE(p.telefones) = :x
				""", Evento.class);
		q.setParameter("x", n);
		return q.getResultList();
	}

	public List<Evento> readByMes(int mes) {
		TypedQuery<Evento> q = manager
				.createQuery(
						"""
						select p from Evento p 
						where extract(month from p.dtnascimento) = :m
						""", Evento.class);
		q.setParameter("m", mes);
		return q.getResultList();

	}

	public boolean temTelefoneCelular(String nome) {
		try {
			nome = nome.toUpperCase();
			Query q = manager.createQuery(
					"select count(t) from Evento p join p.telefones t where p.nome = :x and t.numero like :y");
			q.setParameter("x", nome);
			q.setParameter("y", "9%"); // inicia com 9
			long cont = (Long) q.getSingleResult();
			return cont > 0;
		} catch (NoResultException e) {
			return false;
		}
	}

	public boolean temTelefoneFixo(String nome) {
		try {
			nome = nome.toUpperCase();
			Query q = manager.createQuery(
					"select count(t) from Evento p join p.telefones t where p.nome = :x and t.numero like :y");
			q.setParameter("x", nome);
			q.setParameter("y", "3%"); // inicia com 3

			long cont = (Long) q.getSingleResult();
			return cont > 0;
		} catch (NoResultException e) {
			return false;
		}
	}

	public List<Evento> consultarApelido(String apel) {
		TypedQuery<Evento> q = manager.createQuery("select p from Evento p JOIN p.apelidos a where a like :x",
				Evento.class);
		q.setParameter("x", "%" + apel + "%");
		return q.getResultList();
	}

}
