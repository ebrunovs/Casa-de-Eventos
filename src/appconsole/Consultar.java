package appconsole;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Evento;
import modelo.Cliente;
import modelo.Senha;

public class Consultar {
	protected EntityManager manager;

	public Consultar() {
		try {
			manager = Util.conectarBanco();
			
			TypedQuery<Evento> query1 ;
			TypedQuery<Cliente> query2 ;
			TypedQuery<Senha> query3 ;
			List<Evento> eventos;
			List<Cliente> clientes;
			List<Senha> senhas;
			String jpql;

			System.out.println("\n---quais os eventos do cliente Bruno Eneas?");
			jpql = "select e from Evento e "
					+ "join e.senhas s  "
					+ "join s.cliente c "
					+ "where c.nome like 'Bruno Eneas'  ";
			query1 = manager.createQuery(jpql, Evento.class);
			eventos = query1.getResultList();
			for (Evento e : eventos)
				System.out.println(e);

			
			System.out.println("\n---quais as senhas do evento na data x ?");
			jpql = "select s from Senha s join s.evento e where e.data = '11/12/2025' ";
			query3 = manager.createQuery(jpql, Senha.class);
			senhas = query3.getResultList();
			for (Senha s : senhas)
				System.out.println(s);
			
			System.out.println("\n---quais os eventos que tem mais de n senhas ?");
			jpql = "select e from Evento e where size(e.senhas) > 4";
			query1 = manager.createQuery(jpql, Evento.class);
			eventos = query1.getResultList();
			for (Evento e : eventos)
				System.out.println(e);
			
			System.out.println("\n---Senha ja existe no evento? (CASO NEGATIVO) ");
			jpql = "select s from Senha s " +
				   "join s.evento e " +
			       "where e.nome = 'Casamento' and s.codigo = '12345'";
			query3 = manager.createQuery(jpql, Senha.class);
			senhas = query3.getResultList();
			if (senhas.isEmpty())
			    System.out.println("Senha não existe no evento");
			else
			    System.out.println("Senha já existe no evento");
			
			System.out.println("\n---Senha ja existe no evento? (CASO POSITIVO)");
			jpql = "select s from Senha s " +
				   "join s.evento e " +
			       "where e.nome = 'Casamento' and s.codigo = '245654'";
			query3 = manager.createQuery(jpql, Senha.class);
			senhas = query3.getResultList();
			if (senhas.isEmpty())
			    System.out.println("Senha não existe no evento");
			else
			    System.out.println("Senha já existe no evento");
			
			System.out.println("\n---Lista de convidados para o Casamento: ");
			jpql = "select c from Cliente c " +
			       "join c.senhas s join s.evento e " +
			       "where e.nome = 'Casamento'";
			query2 = manager.createQuery(jpql, Cliente.class);
			clientes = query2.getResultList();
			for (Cliente c : clientes)
			    System.out.println(c.getNome());
			
		} catch (Exception e) {
			System.out.println("excecao=" + e.getMessage());
		}
		Util.fecharBanco();
		System.out.println("\nfim da aplica��o");
	}

	// =================================================
	public static void main(String[] args) {
		new Consultar();
	}
}
