package appconsole;



import jakarta.persistence.EntityManager;
import modelo.Evento;
import modelo.Senha;
import regras_negocio.Fachada;

public class Consultar {
	protected EntityManager manager;

	public Consultar() {
		try {
			Fachada.inicializar();
			
			System.out.println("\n---quais os eventos do cliente Bruno Eneas?");
			for (Evento e : Fachada.eventosCliente("Bruno Eneas"))
				System.out.println(e);

			
			System.out.println("\n---quais as senhas do evento na data x ?");
			for (Senha s : Fachada.senhasPorData("11/12/2025"))
				System.out.println(s);
			
			System.out.println("\n---quais os eventos que tem mais de n senhas ?");
			for (Evento e : Fachada.EventosComNSenhas())
				System.out.println(e);
			
//			System.out.println("\n---Senha ja existe no evento? (CASO NEGATIVO) ");
//			jpql = "select s from Senha s " +
//				   "join s.evento e " +
//			       "where e.nome = 'Casamento' and s.codigo = '12345'";
//			query3 = manager.createQuery(jpql, Senha.class);
//			senhas = query3.getResultList();
//			if (senhas.isEmpty())
//			    System.out.println("Senha não existe no evento");
//			else
//			    System.out.println("Senha já existe no evento");
//			
//			System.out.println("\n---Senha ja existe no evento? (CASO POSITIVO)");
//			jpql = "select s from Senha s " +
//				   "join s.evento e " +
//			       "where e.nome = 'Casamento' and s.codigo = '245654'";
//			query3 = manager.createQuery(jpql, Senha.class);
//			senhas = query3.getResultList();
//			if (senhas.isEmpty())
//			    System.out.println("Senha não existe no evento");
//			else
//			    System.out.println("Senha já existe no evento");
//			
//			System.out.println("\n---Lista de convidados para o Casamento: ");
//			jpql = "select c from Cliente c " +
//			       "join c.senhas s join s.evento e " +
//			       "where e.nome = 'Casamento'";
//			query2 = manager.createQuery(jpql, Cliente.class);
//			clientes = query2.getResultList();
//			for (Cliente c : clientes)
//			    System.out.println(c.getNome());
			
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