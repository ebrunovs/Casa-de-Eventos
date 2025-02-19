package appconsole;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;

public class Cadastrar {

	private EntityManager manager;
	
	public Cadastrar(){
		
		try {
			manager = Util.conectarBanco();
			System.out.println("cadastrando eventos...");
			
			
			
			
			Evento e1, e2, e3;
			e1 = new Evento("ENEX 7", "01/01/2025",100.0);
			e2 = new Evento("Casamento", "01/02/2025",0);
			e3 = new Evento("SECT","11/12/2025",50.0);
			
			Cliente c1,c2,c3,c4,c5;
			c1 = new Cliente("12345678911", "Bruno Eneas");
			c2 = new Cliente("98765432199", "Mariana Pereira");
			c3 = new Cliente("12312312333", "Kaue Henrique");
			c4 = new Cliente("32132132111", "Eduardo Jorge");
			c5 = new Cliente("45665445644", "Roberta Emilia");	
			
	
			Senha s1,s2,s3,s4,s5,s6,s7,s8;
			s1 = new Senha("123445");
			s2 = new Senha("543211");	
			s3 = new Senha("135689");
			s4 = new Senha("245654");
			s5 = new Senha("914624");
			s6 = new Senha("863423");
			s7 = new Senha("653611");
			s8 = new Senha("567423");

			
			e1.adicionar(s1);
			c1.adicionar(s1);
			e2.adicionar(s2);
			c1.adicionar(s2);
			e2.adicionar(s3);
			c2.adicionar(s3);
			e2.adicionar(s4);
			c3.adicionar(s4);
			e2.adicionar(s5);
			c4.adicionar(s5);
			e2.adicionar(s6);
			c5.adicionar(s6);
			e3.adicionar(s7);
			c1.adicionar(s7);
			e3.adicionar(s8);
			c3.adicionar(s8);
			
			
			Senha[] senhas = {
					s1,s2,s3,s4,s5,s6,s7,s8
			};
			
			Evento[] eventos = {
				e1,e2,e3	
			};
			
			Cliente[] clientes = {
				c1,c2,c3,c4,c5	
			};
			
			
			manager.getTransaction().begin();
			for(int i = 0; i < senhas.length; i++ ) {
				manager.persist(senhas[i]);
			}
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			for(int i = 0; i < eventos.length; i++ ) {
				manager.persist(eventos[i]);
			}
			manager.getTransaction().commit();
			
			
			manager.getTransaction().begin();
			for(int i = 0; i < clientes.length; i++ ) {
				manager.persist(clientes[i]);
			}
			manager.getTransaction().commit();
			
	
			
		} catch (Exception e) 	{
			System.out.println("Exceção: " + e.getMessage());
		}
		
		Util.fecharBanco();
		System.out.println("fim do programa");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}
