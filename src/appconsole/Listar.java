package appconsole;


import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;
import regras_negocio.Fachada;


public class Listar {

	public Listar(){
		try {
			Fachada.inicializar();

			System.out.println("*** Listagem de Eventos:");
			for(Evento e : Fachada.listarEventos())		
				System.out.println(e);

			System.out.println("\n*** Listagem de Clientes:");
			for(Cliente c : Fachada.listarClientes())		
				System.out.println(c);

			System.out.println("\n*** Listagem de Senhas:");
			for(Senha s : Fachada.listarSenhas())	
				System.out.println(s);
			
			System.out.println("\n*** Listagem de Eventos por Cliente:");
			System.out.println(Fachada.eventosCliente("Kaue Henrique"));
			
			
			System.out.println("\n*** Listagem de Senhas por Data:");
			System.out.println(Fachada.senhasPorData("01/01/2025"));
			
			System.out.println("\n*** Listagem de Senhas por Evento:");
			System.out.println(Fachada.senhasPorEvento(3));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
	}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}