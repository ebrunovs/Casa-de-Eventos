package modelo;

public class Senha {
	private int id;
	private int codigo;
	private Evento evento;
	private String cliente;
	
	public Senha(int id, int codigo, Evento evento) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.evento = evento;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
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
