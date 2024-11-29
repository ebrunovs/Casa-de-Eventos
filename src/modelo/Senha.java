package modelo;

public class Senha {
	private int id;
	private int codigo;
	private Evento evento;
	private Cliente cliente;
	
	public Senha(int codigo, Evento evento, Cliente cliente) {
		super();
		this.codigo = codigo;
		this.evento = evento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Evento getEvento() {
		return evento;
	}
	
}
