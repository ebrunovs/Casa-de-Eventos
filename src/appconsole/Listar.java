package appconsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */


import modelo.Cliente;
import regras_negocio.Fachada;


public class Listar {
	public Listar(){
		Fachada.inicializar();
		listar();
		Fachada.finalizar();
	}

	public void listar(){
		System.out.println("-------Lista Vagas--------");
		for (Cliente v : Fachada.listarVagas()) {
			System.out.println(v);
		}
	}

	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}


