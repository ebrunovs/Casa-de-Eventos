/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appconsole;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Evento;
import modelo.Cliente;
import modelo.Senha;

public class Consultar {
	protected EntityManager manager;

	public Consultar() {
		try {
			manager = Util.conectarBanco();
			
			TypedQuery<Evento> query1 ;
			TypedQuery<Cliente> query2 ;
			TypedQuery<Senha> query3 ;
			List<Evento> eventos;
			List<Cliente> clientes;
			List<Senha> senhas;
			String jpql;

			System.out.println("\n---quais os eventos do cliente Bruno Eneas?");
			jpql = "select e from Evento e "
					+ "join e.senhas s  "
					+ "join s.cliente c "
					+ "where c.nome like 'Bruno Eneas'  ";
			query1 = manager.createQuery(jpql, Evento.class);
			eventos = query1.getResultList();
			for (Evento e : eventos)
				System.out.println(e);

			
			System.out.println("\n---quais as senhas do evento na data x ?");
			jpql = "select s from Senha s join s.evento e where e.data = '11/12/2025' ";
			query3 = manager.createQuery(jpql, Senha.class);
			senhas = query3.getResultList();
			for (Senha s : senhas)
				System.out.println(s);
			
			System.out.println("\n---quais os eventos que tem mais de n senhas ?");
			jpql = "select e from Evento e where size(e.senhas) > 4";
			query1 = manager.createQuery(jpql, Evento.class);
			eventos = query1.getResultList();
			for (Evento e : eventos)
				System.out.println(e);
//
//			System.out.println("\n---qual a turma do aluno 20211370002 (jose)?");
//			jpql = "select t from Turma t join t.alunos a where a.matricula = '20211370002' ";
//			//jpql = "select a.turma from Aluno a where a.matricula = '20211370002' ";
//			query2 = manager.createQuery(jpql, Turma.class);
//			turmas = query2.getResultList();
//			for (Turma t : turmas)
//				System.out.println(t);
//			
//			
//			System.out.println("\n---quais as turmas que tem dois alunos");
//			jpql = "select t from Turma t where size(t.alunos) = 2";
//			query2 = manager.createQuery(jpql, Turma.class);
//			turmas = query2.getResultList();
//			for (Turma t : turmas)
//				System.out.println(t);
//			
//			System.out.println("\n---quais as turmas que nao tem alunos");
//			jpql = "select t from Turma t where size(t.alunos) = 0";
//			//jpql = "select t from Turma t where t.alunos is Empty";
//			query2 = manager.createQuery(jpql, Turma.class);
//			turmas = query2.getResultList();
//			for (Turma t : turmas)
//				System.out.println(t);
			
		} catch (Exception e) {
			System.out.println("excecao=" + e.getMessage());
		}
		Util.fecharBanco();
		System.out.println("\nfim da aplica��o");
	}

	// =================================================
	public static void main(String[] args) {
		new Consultar();
	}
}
