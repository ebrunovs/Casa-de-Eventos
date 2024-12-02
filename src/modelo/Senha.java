package modelo;

public class Senha {
	private int id;
	private String codigo;
	private Evento evento;
	private Cliente cliente;
	
	public Senha(String codigo) {
		super();
		this.codigo = codigo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "Senha: Codigo=" + codigo + ", evento=" + evento.getNome() + ", cliente=" + cliente.getNome();
	}
//	--------------------RELACIONAMENTO--------------------------------
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
}
