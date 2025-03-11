package modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Senha20231370009")
public class Senha {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int id;
	private String codigo;
	
	@ManyToOne(cascade= {CascadeType.MERGE})
	private Evento evento;
	@ManyToOne(cascade= {CascadeType.MERGE})
	private Cliente cliente;
	
	public Senha() {
		
	}
	
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
