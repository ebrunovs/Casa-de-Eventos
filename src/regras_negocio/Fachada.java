/**IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

package regras_negocio;

import java.time.LocalDateTime;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOCliente;
import daodb4o.DAOEvento;
import daodb4o.DAOSenha;
import modelo.Cliente;

public class Fachada {
	private static DAOCliente daocliente = new DAOCliente();  
	private static DAOSenha daosenha = new DAOSenha();  
	private static DAOEvento daoevento = new DAOEvento();  
	

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

	public static void criarEvento() throws  Exception{
		//Um evento não poderá ocorrer no mesmo dia de outro
		//um evento não poderá ter preço negativo
		//um evento não poderá ocorrer em uma data que já passou
		//DAO.begin();
		//daovaga.create(v);
		//DAO.commit();
	}
	
	public static void verificarSenha() throws  Exception{
		//senha booblean
	}
	
	public static void apagarEvento() throws  Exception{
		//Um evento so poderá ser apagado se não houver senhas
		
		/**List<Cliente> lista = daovaga.readAll();
		for(Cliente v : lista) {
			DAO.begin();	
			daovaga.delete(v);
			DAO.commit();
		}**/
	}
	
	public static void teste(){
		
	}

}
