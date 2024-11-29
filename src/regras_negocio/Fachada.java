/**IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

package regras_negocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import daodb4o.DAO;
import daodb4o.DAOCliente;
import daodb4o.DAOEvento;
import daodb4o.DAOSenha;
import modelo.Cliente;
import modelo.Senha;

public class Fachada {
	private static DAOCliente daoCliente = new DAOCliente();  
	private static DAOSenha daoSenha = new DAOSenha();  
	private static DAOEvento daoEvento = new DAOEvento();  
	private static ArrayList<Senha> senhasUsadas = new ArrayList<Senha>();
	private static ArrayList<String> datasOculpadas = new ArrayList<>();
	

	// Senha já usada Boolean
	
	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

	public static void criarEvento(String nome, String data, double preco) throws  Exception{
		//Um evento não poderá ocorrer no mesmo dia de outro
		if(dataOculpada(data)) {
			throw new Exception("Data indisponivel!");
		}else {
			datasOculpadas.add(data);
		}
		//um evento não poderá ter preço negativo
		if(preco < 0.0) {
			throw new Exception("O evento não pode ter preço negativo.");
		}
		//um evento não poderá ocorrer em uma data que já passou
		String dataAtual = gerarData();
		if(!data.equals(dataAtual)) {
			throw new Exception("O evento não pode ser criado em uma data que já passou.");
		}
		//DAO.begin();
		//daovaga.create(v);
		//DAO.commit();
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
	
	public static void acessarEvento(Senha senha) throws Exception{
		if (senhaUsada(senha)) {
			throw new Exception("Senha já utilizada!");
		}
		senhasUsadas.add(senha);
	}
	
	public static boolean senhaUsada(Senha senha) throws  Exception{
		if(senhasUsadas.contains(senha)) {
			return true;
		}
		return false;
	}
	
	
	public static boolean dataOculpada(String data) throws  Exception{
		if(datasOculpadas.contains(data)) {
			return true;
		}
		return false;
	}
	
	public static String gerarData() {
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = hoje.format(formato);
		return dataFormatada;
	}

}
