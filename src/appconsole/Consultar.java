package appconsole;

import jakarta.persistence.EntityManager;
import modelo.Cliente;
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
			
			System.out.println("\n---quais os eventos que tem mais de 3 senhas ?");
			for (Evento e : Fachada.EventosComNSenhas(3))
				System.out.println(e);
				
			System.out.println("\n---Senha ja existe no evento?");
			System.out.println(Fachada.senhaExisteNoEvento("543211", "Casamento"));
			
			System.out.println("\n---Lista de convidados para o Casamento: ");
			for (Cliente c : Fachada.clientesEmEvento("Casamento"))
				System.out.println(c);
			
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