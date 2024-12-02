package modelo;

import java.util.ArrayList;

public class Cliente {
	private int id;
	private String CPF;
	private String nome;
	private ArrayList<Senha> senhas = new ArrayList<>();
	
	public Cliente(String nome) {
		super();
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
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
		String texto = "Cliente: CPF=" + CPF + ", nome=" + nome;
		
		texto += ", senhas: ";
		for(Senha s : senhas)
			if(s != null)
				texto += s.getCodigo() + ", ";
			else
				texto += "";
		return texto;
	}
	
	
	
}
