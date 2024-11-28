package modelo;

import java.util.ArrayList;

public class Evento {
	private int id;
	private String nomeevento;
	private String data;
	private double preco;
	private ArrayList<Senha> senhas = new ArrayList<>();
	
	public Evento(int id, String nomeevento, String data, double preco) {
		super();
		this.id = id;
		this.nomeevento = nomeevento;
		this.data = data;
		this.preco = preco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getnomeevento() {
		return nomeevento;
	}

	@Override
	public String toString() {
		return "Evento [nomeevento=" + nomeevento + ", data=" + data + ", preco=" + preco + "]";
	}

	public void setnomeevento(String nomeevento) {
		this.nomeevento = nomeevento;
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
	
	
}
