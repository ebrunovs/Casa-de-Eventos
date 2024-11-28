package appswing;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import regras_negocio.Fachada;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnVaga;
	private JLabel label;
	private JMenu mnCriar;
	private JMenu mnApagar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Viagem");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 444, 249);
		label.setText("Inicializando...");
		label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/viagem.jpg"));
		imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(imagem);
		frame.getContentPane().add(label);

		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnVaga = new JMenu("Vaga");
		mnVaga.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaVaga();
			}
		});
		menuBar.add(mnVaga);

		mnCriar = new JMenu("Criar vagas");
		mnCriar .addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Fachada.inicializar();
					Fachada.criarVagas(10);
					Fachada.finalizar();
					JOptionPane.showMessageDialog(frame, "vagas criadas", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame, ex.getMessage());
				}
			}
		});
		menuBar.add(mnCriar);
		
		mnApagar = new JMenu("Apagar vagas");
		mnApagar .addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Fachada.inicializar();
					Fachada.apagarVagas();
					Fachada.finalizar();
					JOptionPane.showMessageDialog(frame, "vagas apagadas", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame, ex.getMessage());
				}
			}
		});
		menuBar.add(mnApagar);

	}
}
