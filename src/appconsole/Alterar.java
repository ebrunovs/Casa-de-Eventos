package appconsole;

import regras_negocio.Fachada;

public class Alterar {

	public Alterar(){
		Fachada.inicializar();
		//alteracao 1
		try {
			Fachada.alterarDataEvento("SECT","12/12/2024");
			System.out.println("alterado data do evento 'SECT' para dia 12");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//alteracao 2
				try {
					Fachada.alterarEvento("ENEX 7", "ENEX 7 - FEIRAO", "10/02/2025",135.0);
					System.out.println("alterado nome do evento, data e preço");
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
		
		
		//alteracao 3
		try {
			Fachada.alterarNomeCliente("Mariana Pereira", "Mariana Eneas");
			System.out.println("alterado nome Mariana Pereira para Mariana Eneas");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//alteracao 4
		try {
			Fachada.alterarCPFCliente("Kaue Henrique", "99999999999");
			System.out.println("alterado o cpf do cliente Kaue Henrique para 99999999999");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}