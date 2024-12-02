package appconsole;

import regras_negocio.Fachada;


public class Deletar {

	public Deletar(){
		Fachada.inicializar();
		try {
			Fachada.apagarCliente("Eduardo Jorge");
			System.out.println("apagou o cliente Eduardo e suas senhas orfas");
			
			Fachada.apagarEvento("SECT");
			System.out.println("apagou o evento SECT e suas senhas orfas");
			
			Fachada.apagarSenha("863423");
			System.out.println("apagou a senha 863423");
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}

