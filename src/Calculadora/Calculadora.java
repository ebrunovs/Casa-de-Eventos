package Calculadora;

import java.util.ArrayList;

public class Calculadora {
	public static ArrayList<String> historico = new ArrayList<>();
	
	public static double somar(double n1, double n2) {
		double r = n1 + n2;
		historico.add(n1 + " + " + n2 + "= " + r);
		return r;
	}
	public static double subtrair(double n1, double n2) {
		double r = n1 - n2;
		historico.add(n1 + " - " + n2 + "= " + r);
		return r;
	}
	public static double multiplicar(double n1, double n2) {
		double r = n1 * n2;
		historico.add(n1 + " * " + n2 + "= " + r);
		return r;
	}
	public static double dividir(double n1, double n2) {
		double r = n1 / n2;
		historico.add(n1 + " / " + n2 + "= " + r);
		return r;
	}
	public static ArrayList<String> historico() {
		return historico;
	}
}
