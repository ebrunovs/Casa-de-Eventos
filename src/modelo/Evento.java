package modelo;

import java.util.ArrayList;

public class Evento {
	private int id;
	private String nome;
	private String data;
	private double preco;
	private ArrayList<Senha> senhas = new ArrayList<>();
	
	public Evento(String nome) {
		super();
		this.nome = nome;
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

	public ArrayList<Senha> getSenhas() {
		return senhas;
	}

	public void adicionar(Senha s){
		senhas.add(s);
	}
	
	public void remover(Senha s){
		senhas.remove(s);
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
