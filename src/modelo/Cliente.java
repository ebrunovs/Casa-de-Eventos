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
public class Cliente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String CPF;
	private String nome;
	
	@OneToMany(mappedBy="cliente", cascade= {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
	private List<Senha> senhas = new ArrayList<>();
	
	
	public Cliente() {
		
	}
	
	public Cliente(String cpf, String nome) {
		super();
		this.CPF = cpf;
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
	
	public List<Senha> getSenhas() {
		return senhas;
	}

	public void adicionar(Senha s){
		senhas.add(s);
		s.setCliente(this);
	}
	public void remover(Senha s){
		senhas.remove(s);
		s.setCliente(null);
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
