package modelo;

import java.util.ArrayList;

public class Cliente {
	private int id;
	private int CPF;
	private String nome;
	private ArrayList<Senha> senhas = new ArrayList<>();
	
	
	public Cliente(int id, int cPF, String nome) {
		super();
		this.id = id;
		CPF = cPF;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCPF() {
		return CPF;
	}
	
	public void setCPF(int cPF) {
		CPF = cPF;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ArrayList<Senha> getListaSenhas() {
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
