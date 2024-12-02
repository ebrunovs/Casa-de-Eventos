package regras_negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	
	public static void criarEvento(String nome, String data, double preco) throws  Exception{
		DAO.begin();
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
		for(Evento ev : listarEventos()) {
			if(ev.getData().equals(data)) {
				DAO.rollback();
				throw new Exception("Evento não pode ocorrer no mesmo dia que outro.");
			}
		}
			//Um evento não poderá ocorrer em uma data que já passou.
		if(compararData(data)) {
				DAO.rollback();
				throw new Exception("O evento não pode ser criado em uma data que já passou.");
			}


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
		if(dataOculpada(data)) {
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
		if(dataOculpada(data)) {
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

		for (Senha s : e.getSenhas()) {
			daoSenha.delete(s); // deletar o telefone orfao
		}

		 daoEvento.delete(e);
		 DAO.commit();
		
	}

	public static void criarCliente(String cpf, String nome ) throws Exception{
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

		for (Senha s : c.getSenhas()) {
			daoSenha.delete(s); // deletar o telefone orfao
		}

		 daoCliente.delete(c);
		 DAO.commit();
		
	}
	
	public static void criarSenha(String codigo, String evento, String cliente) throws Exception{
		DAO.begin();
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
		if (s != null) {
			DAO.rollback();
			throw new Exception("Criar Senha - Senha já cadastrada:" + codigo);
		}
		if (codigo.isEmpty()) {
			DAO.rollback();
			throw new Exception("criar telefone - senha vazia:" + codigo);
		}
		for(Senha senha : listarSenhas()) {
			// Senha so pode ser criada se não houver uma mesma no cliente X
			if(senha.getCodigo() == codigo && senha.getCliente().getCPF().equals(c.getCPF()) && senha.getEvento().getId() == e.getId()) {
				DAO.rollback();
				throw new Exception("Criar Senha - senha já cadastrada em um cliente deste evento");
			}
			// Senha so pode ser criada se não houver uma mesma no evento X
			if(senha.getCodigo() == codigo && senha.getEvento().getId() == e.getId()) {
				DAO.rollback();
				throw new Exception("Criar Senha - senha já cadastrada nesse evento");
			}	
		}

		s = new Senha(codigo);
		c.adicionar(s);
		e.adicionar(s);
		s.setCliente(c);
		s.setEvento(e);
		daoSenha.create(s);
		DAO.commit();
	}
	
	public static void apagarSenha(String codSenha) throws Exception{
		DAO.begin();
		Senha s = daoSenha.read(codSenha);
		if(s == null) {
			DAO.rollback();
			throw new Exception("Apagar senha - senha inexistente:" + codSenha);
		}
		Cliente c = s.getCliente();
		Evento e = s.getEvento();
		c.remover(s);
		e.remover(s);
		s.setCliente(null);
		s.setEvento(null);
		DAO.commit();
	}
	
	public static List<Cliente> listarClientes() {
		List<Cliente> result = daoCliente.readAll();
		return result;
	}

	public static List<Evento> listarEventos() {
		List<Evento> result = daoEvento.readAll();
		return result;
	}

	public static List<Senha> listarSenhas() {
		List<Senha> result = daoSenha.readAll();
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
		resultado = daoEvento.passwordsByDate(data);
		return resultado;
	}
	
	public static List<Evento> senhasPorEvento(int n){
		List<Evento> resultado;
		resultado = daoEvento.passwordsByEvent(n);
		return resultado;
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
	
	
	public static boolean dataOculpada(String data) throws  Exception{
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
