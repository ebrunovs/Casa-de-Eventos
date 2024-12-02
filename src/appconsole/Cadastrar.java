package appconsole;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar(){
		try {
			Fachada.inicializar();
			System.out.println("cadastrando eventos...");
			
			Fachada.criarEvento("ENEX 7", "01/01/2025",100.0);
			Fachada.criarEvento("Casamento", "01/02/2025",0);
			Fachada.criarEvento("SECT","11/12/2025",50.0);
			
			
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}
		
		try {
			System.out.println("cadastrando clientes...");
			Fachada.criarCliente("12345678911", "Bruno Eneas");
			Fachada.criarCliente("98765432199", "Mariana Pereira");
			Fachada.criarCliente("12312312333", "Kaue Henrique");
			Fachada.criarCliente("32132132111", "Eduardo Jorge");
			Fachada.criarCliente("45665445644", "Roberta Emilia");	
			} catch (Exception e) 	{
				System.out.println(e.getMessage());
			}
		

		try {
			System.out.println("cadastrando senhas...");
			Fachada.criarSenha("123445","ENEX 7","Bruno Eneas");
			Fachada.criarSenha("543211","Casamento","Bruno Eneas");	
			Fachada.criarSenha("135689","Casamento","Mariana Pereira");
			Fachada.criarSenha("245654","Casamento","Kaue Henrique");
			Fachada.criarSenha("914624","Casamento","Eduardo Jorge");
			Fachada.criarSenha("863423","Casamento","Roberta Emilia");
			Fachada.criarSenha("653611","SECT","Bruno Eneas");
			Fachada.criarSenha("567423","SECT","Kaue Henrique");
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("fim do programa");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}
