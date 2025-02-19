package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Evento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int id;
	private String nome;
	private String data;
	private double preco;
	
	@OneToMany(mappedBy="evento", cascade= {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
	private List<Senha> senhas = new ArrayList<>();
	
	
	public Evento() {
		
	}
	
	public Evento(String nome, String data, double preco) {
		super();
		this.nome = nome;
		this.data = data;
		this.preco = preco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public List<Senha> getSenhas() {
		return senhas;
	}

	public void adicionar(Senha s){
		senhas.add(s);
		s.setEvento(this);
	}
	
	public void remover(Senha s){
		senhas.remove(s);
		s.setEvento(null);
	}
	
	public Senha localizar(int id){
		for(Senha s : senhas){
			if(s.getId() == id)
				return s;
		}
		return null;
	}
	
	
	
	@Override
	public String toString() {
		String texto = "Evento: Nome=" + nome + ", data=" + data + ", preco=" + preco;
		
		texto += ", senhas: ";
		for(Senha s : senhas)
			if(s != null)
				texto += s.getCodigo() + ", ";
			else
				texto += "";
		return texto;
	}
	
}
