package regras_negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import daojpa.DAO;
import daojpa.DAOCliente;
import daojpa.DAOEvento;
import daojpa.DAOSenha;
import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class Fachada {
	private static DAOCliente daoCliente = new DAOCliente();  
	private static DAOSenha daoSenha = new DAOSenha();  
	private static DAOEvento daoEvento = new DAOEvento();  
	private static ArrayList<Integer> senhasUsadas = new ArrayList<>();
	private static ArrayList<String> datasOcupadas = new ArrayList<>();
	
	
	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

	public static Cliente localizarCliente(String nome) throws Exception {
		Cliente c = daoCliente.read(nome);
		if (c == null) {
			throw new Exception("Cliente inexistente:" + nome);
		}
		return c;
	}
	public static Evento localizarEvento(String nome) throws Exception {
		Evento e = daoEvento.read(nome);
		if (e == null) {
			throw new Exception("Evento inexistente:" + nome);
		}
		return e;
	}
	
	public static Senha localizarSenha(Integer id) throws Exception {
		Senha s = daoSenha.read(id);
		if (s == null) {
			throw new Exception("Senha inexistente:" + id);
		}
		return s;
	}
	
	public static void criarEvento(String nome, String data, double preco) throws  Exception{
		
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("Formato data invalido:" + data);
		}
		Evento c = daoEvento.read(nome);
		if (c != null) {
			DAO.rollback();
			throw new Exception("Evento ja existe:" + nome);
		}
		//Um evento não pode ter preço negativo.
		if(preco < 0.0) {
			DAO.rollback();
			throw new Exception("O evento não pode ter preço negativo.");
		}
		//Um evento não poderá ocorrer no mesmo dia de outro.
		if(c == null) {
			for(Evento ev : listarEventos()) {
				if(ev.getData().equals(data)) {
					DAO.rollback();
					throw new Exception("Evento não pode ocorrer no mesmo dia que outro.");
				}
			}
		}	//Um evento não poderá ocorrer em uma data que já passou.
		if(compararData(data)) {
				DAO.rollback();
				throw new Exception("O evento não pode ser criado em uma data que já passou.");
			}

			DAO.begin();
	        Evento e = new Evento(nome);
	        e.setData(data);
	        e.setPreco(preco);
	        daoEvento.create(e);
	        DAO.commit();
	}
	
	public static void alterarEvento(String nome, String novoNome, String data, double preco) throws Exception {
		// permite alterar nome, data e preco
		DAO.begin();
		Evento e = daoEvento.read(nome);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar Evento - evento inexistente:" + nome);
		}
		
		if(preco < 0.0) {
			DAO.rollback();
			throw new Exception("Alterar Evento - preco negativo");
		}
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			} catch (DateTimeParseException ex) {
				DAO.rollback();
				throw new Exception("Alterar Evento - formato data invalido:" + data);
			}
		}
		if(dataOcupada(data)) {
			DAO.rollback();
			throw new Exception("Alterar Evento - essa data já possui um evento cadastrado:" + data);
		}
		if(compararData(data)) {
			DAO.rollback();
			throw new Exception("Alterar Evento - data já passou:" + data);
		}

		e.setData(data);
		e.setNome(novoNome);
		e.setPreco(preco);
		daoEvento.update(e);
		DAO.commit();
	}
	
	public static void alterarDataEvento(String nome, String data) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Evento e = daoEvento.read(nome);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar Evento - evento inexistente:" + nome);
		}
		
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			} catch (DateTimeParseException ex) {
				DAO.rollback();
				throw new Exception("Alterar Evento - formato data invalido:" + data);
			}
		}
		if(dataOcupada(data)) {
			DAO.rollback();
			throw new Exception("Alterar Evento - essa data já possui um evento cadastrado:" + data);
		}
		if(compararData(data)) {
			DAO.rollback();
			throw new Exception("Alterar Evento - data já passou:" + data);
		}

		e.setData(data);
		e.setNome(nome);
		daoEvento.update(e);
		DAO.commit();
	}
	
	public static void apagarEvento(String nomeEven) throws  Exception{	
		DAO.begin();
		Evento e = daoEvento.read(nomeEven);

		// Verificar se o evento existe
		if (e == null) {
			DAO.rollback();
			throw new Exception("Apagar Evento - nome inexistente:" + nomeEven);
		}

		 // Faz uma cópia da lista de senhas
	    List<Senha> senhas = new ArrayList<>(e.getSenhas());

	    for (Senha s : senhas) {
	        Cliente c = s.getCliente();

	        // Remove a senha do cliente e atualiza
	        e.remover(s);
	        daoEvento.update(e);

	        // Remove a senha do evento e atualiza
	        if (c != null) {
	            c.remover(s);
	            daoCliente.update(c);
	        }

	        // Apaga a senha do banco
	        daoSenha.delete(s); 
	    }

	    // Após remover todas as associações, exclui o cliente
	    daoEvento.delete(e);
	    DAO.commit();    
	}

	
	
	public static void criarCliente(String cpf, String nome ) throws Exception{
		List<Cliente> lista = listarClientes();
		Cliente ce = daoCliente.read(nome);
		if (ce != null) {
			for (Cliente c : lista) {
				if (c.getCPF().equals(cpf)) {
					throw new Exception("Cpf já cadastrado: " + cpf);
				};
			}
		}
		DAO.begin();
		Cliente c = new Cliente(nome);
		c.setCPF(cpf);
		daoCliente.create(c);
		DAO.commit();
	}

	public static void alterarNomeCliente(String nome, String novoNome) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Cliente c = daoCliente.read(nome);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Alterar Cliente - cliente inexistente:" + nome);
		}

		c.setNome(novoNome);
		daoCliente.update(c);
		DAO.commit();
	}
	
	public static void alterarCPFCliente(String nome, String novoCPF) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Cliente c = daoCliente.read(nome);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Alterar Cliente - cliente inexistente:" + nome);
		}


		c.setCPF(novoCPF);
		daoCliente.update(c);
		DAO.commit();
	}
	
	public static void apagarCliente(String nomeCli) throws  Exception{	
		DAO.begin();
		Cliente c = daoCliente.read(nomeCli);

		// Verificar se o evento existe
		if (c == null) {
			DAO.rollback();
			throw new Exception("Apagar Cliente - nome inexistente:" + nomeCli);
		}

		 // Faz uma cópia da lista de senhas
	    List<Senha> senhas = new ArrayList<>(c.getSenhas());

	    for (Senha s : senhas) {
	        Evento e = s.getEvento();

	        // Remove a senha do cliente e atualiza
	        c.remover(s);
	        daoCliente.update(c);

	        // Remove a senha do evento e atualiza
	        if (e != null) {
	            e.remover(s);
	            daoEvento.update(e);
	        }

	        // Apaga a senha do banco
	        daoSenha.delete(s); 
	    }

	    // Após remover todas as associações, exclui o cliente
	    daoCliente.delete(c);
	    DAO.commit();    
	}

	
	public static void criarSenha(String codigo, String evento, String cliente) throws Exception{
		Evento e = daoEvento.read(evento);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Criar Senha - evento inexistente " + evento);
		}
		Cliente c = daoCliente.read(cliente);
		if (c == null) {
			DAO.rollback();
			throw new Exception("Criar Senha - cliente inexistente " + cliente);
		}
		Senha s = daoSenha.read(codigo);
//		if (s != null) {
//			DAO.rollback();
//			throw new Exception("Criar Senha - Senha já cadastrada:" + idigo);
//		}
		if (codigo.isEmpty()) {
			DAO.rollback();
			throw new Exception("Criar Senha - senha vazia:" + codigo);
		}
		if(s != null) {
			for(Senha senha : listarSenhas()) {
				// Senha so pode ser criada se não houver uma mesma no evento X
				if(senha.getCodigo().equals(codigo) && senha.getEvento().getId() == e.getId()) {
					DAO.rollback();
					throw new Exception("Criar Senha - senha já cadastrada nesse evento");
				}	
			}
		}
		DAO.begin();
		s = new Senha(codigo);
		c.adicionar(s);
		e.adicionar(s);
		s.setCliente(c);
		s.setEvento(e);
		daoSenha.create(s);
		DAO.commit();	
	}
	
	public static void apagarSenha(int idSenha) throws Exception{
		DAO.begin();
		Senha s = daoSenha.read(idSenha);
		if(s == null) {
			DAO.rollback();
			throw new Exception("Apagar senha - senha inexistente:" + idSenha);
		}
		Cliente c = s.getCliente();
		Evento e = s.getEvento();
		c.remover(s);
		e.remover(s);
		daoSenha.delete(s);
		daoCliente.update(c);
		daoEvento.update(e);
		DAO.commit();
	}
	
	public static List<Cliente> listarClientes() {
		DAO.begin();
		List<Cliente> result = daoCliente.readAll();
		DAO.commit();
		return result;
	}

	public static List<Evento> listarEventos() {
		DAO.begin();
		List<Evento> result = daoEvento.readAll();
		DAO.commit();
		return result;
	}

	public static List<Senha> listarSenhas() {
		DAO.begin();
		List<Senha> result = daoSenha.readAll();
		DAO.commit();

		return result;
	}

	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/
	
	public static List<Evento> eventosCliente(String nome){
		List<Evento> resultado;
		resultado = daoCliente.customerEvents(nome);
		return resultado;
	}
	
	public static List<Senha> senhasPorData(String data){
		List<Senha> resultado;
		resultado =  daoEvento.passwordsByDate(data);
		return resultado;
	}

	public static List<Evento> senhasPorEvento(int n){
		List<Evento> resultado;
		resultado = daoEvento.passwordsByEvent(n);
		return resultado;
	}
	
	
	
	public static List<Cliente> consultarClientes(String caracteres) {
		List<Cliente> result = new ArrayList<>();
		if (caracteres.isEmpty())
			result = daoCliente.readAll();
		else
			result.add(daoCliente.read(caracteres));
		return result;
	}
	
	public static List<Evento> consultarEventos(String caracteres) {
		List<Evento> result = new ArrayList<>();
		if (caracteres.isEmpty())
			result = daoEvento.readAll();
		else
			result.add(daoEvento.read(caracteres));
		return result;
	}
	//uma
	public static List<Evento> EventosComNSenhas(int n) {
		List<Evento> resultado;
		resultado = daoEvento.NPasswords(n);
		return resultado;
	}
	
	public static List<Senha> consultarSenhas(String senha) {
		List<Senha> result = new ArrayList<>();
		if (senha.isEmpty())
			result = daoSenha.readAll();
		else
			result.add(daoSenha.read(senha));
		return result;
	}
	
	public static boolean senhaExisteNoEvento(String senha, String evento) {
		return daoSenha.PasswordExistInEvent(senha, evento);
	}
	
	public static List<Cliente> clientesEmEvento(String nome){
		return daoEvento.customersInEvent(nome);
	}
	
	/**********************************************************
	 * 
	 * CONSULTAS AUXILIARES
	 * 
	 **********************************************************/
	
	public static void acessarEvento(int senha) throws Exception{
		if (senhaUsada(senha)) {
			throw new Exception("Senha já utilizada!");
		}
		senhasUsadas.add(senha);
	}
	
	public static boolean senhaUsada(int senha) throws  Exception{
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
	  LocalDate dataComparar = LocalDate.parse(data,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	  boolean dataAnteriorOuNao = dataComparar.isBefore(hoje);
	  return dataAnteriorOuNao;
	   }
	 
	  

}
