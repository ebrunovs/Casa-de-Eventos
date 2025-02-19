package appconsole;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;


public class Listar {
	
	private EntityManager manager;

	public Listar(){
		try {
			
			manager = Util.conectarBanco();
			System.out.println("*** Listagem de Eventos:");
			
			TypedQuery<Evento> query = manager.createQuery("select e from Evento  e order by e.id", Evento.class);
			List<Evento> resultados1 = query.getResultList();
			for (Evento e : resultados1) {
				System.out.println(e);
			}

			System.out.println("\n*** Listagem de Clientes:");
			TypedQuery<Cliente> query2 = manager.createQuery("select c from Cliente c order by c.id", Cliente.class);
			List<Cliente> resultados2 = query2.getResultList();
			for (Cliente c : resultados2) {
				System.out.println(c);
			}

			System.out.println("\n*** Listagem de Senhas:");
			TypedQuery<Senha> query3 = manager.createQuery("select s from Senha s order by s.id", Senha.class);
			List<Senha> resultados3 = query3.getResultList();
			for (Senha s : resultados3) {
				System.out.println(s);
			}
		

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Util.fecharBanco();
		}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}