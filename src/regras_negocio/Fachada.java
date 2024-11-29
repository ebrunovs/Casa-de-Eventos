/**IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

package regras_negocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOCliente;
import daodb4o.DAOEvento;
import daodb4o.DAOSenha;
import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class Fachada {
	private static DAOCliente daoCliente = new DAOCliente();  
	private static DAOSenha daoSenha = new DAOSenha();  
	private static DAOEvento daoEvento = new DAOEvento();  
	private static ArrayList<Senha> senhasUsadas = new ArrayList<Senha>();
	private static ArrayList<String> datasOcupadas = new ArrayList<>();
	

	// Senha já usada Boolean
	
	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

	public static void criarEvento(String nome, String data, double preco) throws  Exception{
		//Um evento não poderá ocorrer no mesmo dia de outro
		
		List<Evento> listaEventos = daoEvento.readAll() ;
		
		if(preco < 0.0) {
			throw new Exception("O evento não pode ter preço negativo.");
		}
		
		for(Evento ev : listaEventos) {
			if(ev.getData().equals(data)) {
				throw new Exception("Evento não pode ocorrer no mesmo dia");
			}
			
		
			//um evento não poderá ocorrer em uma data que já passou


			if(compararData(data)) {
				throw new Exception("O evento não pode ser criado em uma data que já passou.");
			}
			
		}
		
		try {
	        DAO.begin();
	        Evento e = new Evento(nome, data, preco);
	        daoEvento.create(e);
	        DAO.commit();
	    } catch (Exception e) {
	        DAO.rollback();
	        throw new Exception("Erro ao criar o evento: " + e.getMessage());
	    }
	}
	
	
	public static void apagarEvento(int idEvento) throws  Exception{
		//Um evento so poderá ser apagado se não houver senhas		
		    Evento e = daoEvento.read(idEvento);

		    // Verificar se o evento existe
		    if (e == null) {
		        throw new Exception("Evento não encontrado.");
		    }

		    // Verificar se o evento tem senhas
		    if (!e.getSenhas().isEmpty()) {
		        throw new Exception("Evento com senhas, não pode ser apagado.");
		    }

		    // Iniciar a transação e apagar o evento
		    try {
		        DAO.begin();
		        daoEvento.delete(e);
		        DAO.commit();
		    } catch (Exception ex) {
		        DAO.rollback();
		        throw new Exception("Erro ao apagar o evento: " + ex.getMessage());
		    }
	}
		
	
		
		/**List<Cliente> lista = daovaga.readAll();
		for(Cliente v : lista) {
			DAO.begin();	
			daovaga.delete(v);
			DAO.commit();
		}**/
	
	
	public static void criarCliente(String nome, String cpf ) throws Exception{
		DAO.begin();
		Cliente c = new Cliente(cpf,nome);
		daoCliente.create(c);
	}
	
	public static void apagarCliente(String cpf) throws Exception{
		// O cliente so pode ser apagado se nã tiver nenhuma senha
		
		Cliente c = daoCliente.read(cpf);
		
		if(!c.getListaSenhas().isEmpty()) {
			throw new Exception("Cliente não pode ser apagado pois possui senha");
		}
		
		
		 try {
		        DAO.begin();
		        daoCliente.delete(c);
		        DAO.commit();
		    } catch (Exception ex) {
		        DAO.rollback();
		        throw new Exception("Erro ao apagar o Cliente: " + ex.getMessage());
		    }
	}
	
	public static void criarSenha(int codigo, Evento evento, Cliente cliente) throws Exception{
		// Senha so pode ser criada se não houver uma mesma no evento X
		// Senha so pode ser criada se não houver uma mesma no cliente X
		
		List<Senha> listaDeSenhas = daoSenha.readAll();
		
		for(Senha s : listaDeSenhas) {
			if(s.getCodigo() == codigo && s.getCliente().getCPF().equals(cliente.getCPF())) {
				throw new Exception("Um cliente já possui a senha");
			}
			
			if(s.getCodigo() == codigo && s.getEvento().getId() == evento.getId()) {
				throw new Exception("A senha já existe no evento");
			}	
		}
		 try {
		        DAO.begin();
		        Senha s = new Senha(codigo,evento,cliente);
		        daoSenha.create(s);
		        DAO.commit();
		    } catch (Exception ex) {
		        DAO.rollback();
		        throw new Exception("Erro ao apagar o Senha: " + ex.getMessage());
		    }
		}
	
	public static void apagarSenha(int idSenha) throws Exception{
		//Senha so pode ser apagada se for usada
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
	
	
	public static boolean dataOcupada(String data) throws  Exception{
		if(datasOcupadas.contains(data)) {
			return true;
		}
		return false;
	}
	
	
	  public static boolean compararData(String data) { 
	  LocalDate hoje = LocalDate.now();
	  LocalDate dataComparar = LocalDate.parse(data,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	  boolean dataAnteriorOuNao = dataComparar.isBefore(hoje);
	  return dataAnteriorOuNao;
	   }
	 
}
