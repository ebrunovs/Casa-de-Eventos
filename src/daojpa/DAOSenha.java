
package daojpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Senha;

public class DAOSenha extends DAO<Senha> {

	public Senha read(Object chave) {
		try {
			String numero = String.valueOf(chave);
			TypedQuery<Senha> q = manager.createQuery("select s from Senha s where s.id = :n ",
					Senha.class);
			q.setParameter("n", numero);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void create(Senha obj) {
		manager.persist(obj);
	}
	
	public boolean PasswordExistInEvent(String senha, String evento){	
		try {
		TypedQuery<Senha> q = manager.createQuery("select s from Senha s join s.evento e where e.nome = :e and s.codigo = :s", Senha.class);
		q.setParameter("s", senha);
		q.setParameter("e", evento);
		if (q.getSingleResult() != null) {
			return true;
			}
		}catch(NoResultException e) {
			return false;			
		}
		return false;	
	}

}
