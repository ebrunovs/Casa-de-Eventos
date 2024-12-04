package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Evento;
import modelo.Senha;
import regras_negocio.Fachada;

public class TelaConsultas {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnBuscarClienteEvento;
	private JLabel cliente_label;
	private JTextField cliente_nome_textField;
	private JTextField data_textField;
	private JTextField senhas_qtde_textField;

	/**
	 * Create the application.
	 */
	public TelaConsultas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true); // janela modal

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Fachada.inicializar();
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});
		frame.setTitle("Consultas");
		frame.setBounds(100, 100, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 130, 685, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			// proibir alteracao de celulas
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		// evento de selecao de uma linha da tabela
		

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JLabel errormsgmLabel = new JLabel("");
		errormsgmLabel.setForeground(Color.RED);
		errormsgmLabel.setBounds(21, 311, 416, 23);
		frame.getContentPane().add(errormsgmLabel);

		btnBuscarClienteEvento = new JButton("Buscar eventos do cliente");
		btnBuscarClienteEvento.setBackground(Color.LIGHT_GRAY);
		btnBuscarClienteEvento.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String nome = cliente_nome_textField.getText();
		            if(nome.equals(""))
		            	throw new Exception("Campo cliente vazio.");
		            List<Evento> eventos = Fachada.eventosCliente(nome);
		            if(eventos.isEmpty())
		            	throw new Exception("O cliente não possui eventos.");
		            
		            DefaultTableModel model = new DefaultTableModel();
		            table.setModel(model);

		            model.addColumn("Id Evento");
		            model.addColumn("Nome Evento");
		            model.addColumn("Data Evento");

		            for (Evento evento : eventos) {
		                model.addRow(new Object[] { 
		                    evento.getId(), 
		                    evento.getNome(), 
		                    evento.getData() 
		                });
		            }

		        } catch (Exception ex) {
		        	errormsgmLabel.setText(ex.getMessage());
		        }
		    }
		});

		btnBuscarClienteEvento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarClienteEvento.setBounds(209, 27, 250, 23);
		frame.getContentPane().add(btnBuscarClienteEvento);

		cliente_nome_textField = new JTextField();
		cliente_nome_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cliente_nome_textField.setColumns(10);
		cliente_nome_textField.setBounds(93, 28, 106, 20);
		frame.getContentPane().add(cliente_nome_textField);

		cliente_label = new JLabel("Cliente:");
		cliente_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cliente_label.setBounds(37, 29, 50, 16);
		frame.getContentPane().add(cliente_label);
		
		JLabel data_label = new JLabel("Data:");
		data_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		data_label.setBounds(37, 60, 50, 16);
		frame.getContentPane().add(data_label);
		
		data_textField = new JTextField();
		data_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		data_textField.setColumns(10);
		data_textField.setBounds(93, 59, 106, 20);
		frame.getContentPane().add(data_textField);
		
		JButton btnBuscarDataSenhas = new JButton("Buscar senhas do evento na data");
		btnBuscarDataSenhas.setBackground(Color.LIGHT_GRAY);
		btnBuscarDataSenhas.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String data = data_textField.getText();
		            if(data.equals(""))
		            	throw new Exception("Campo data vazio.");
		            List<Senha> senhas = Fachada.senhasPorData(data);
		            if(senhas == null)
		            	throw new Exception("Não existe senhas para esta data.");
		            DefaultTableModel model = new DefaultTableModel();
		            table.setModel(model);

		            model.addColumn("Id Senha");
		            model.addColumn("Código Senha");

		            for (Senha senha : senhas) {
		                model.addRow(new Object[] { 
		                    senha.getId(), 
		                    senha.getCodigo(), 
		                    
		                });
		            }

		        } catch (Exception ex) {
		        	errormsgmLabel.setText(ex.getMessage());
		        }
		    }
		});

		btnBuscarDataSenhas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarDataSenhas.setBounds(209, 58, 250, 23);
		frame.getContentPane().add(btnBuscarDataSenhas);
		
		JLabel senhas_qtde_label = new JLabel("Senha:");
		senhas_qtde_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		senhas_qtde_label.setBounds(37, 88, 50, 16);
		frame.getContentPane().add(senhas_qtde_label);
		
		senhas_qtde_textField = new JTextField();
		senhas_qtde_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		senhas_qtde_textField.setColumns(10);
		senhas_qtde_textField.setBounds(93, 87, 106, 20);
		frame.getContentPane().add(senhas_qtde_textField);
		
		JButton btnEventosSenhas = new JButton("Buscar quais eventos tem mais senhas ");
		btnEventosSenhas.setBackground(Color.LIGHT_GRAY);
		btnEventosSenhas.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (senhas_qtde_textField.getText().trim().isEmpty()) {
		                throw new Exception("Campo quantidade de Senhas vazio.");
		            }
		        	
		            int qtdeSenhas = Integer.parseInt(senhas_qtde_textField.getText());
		            
		            List<Evento> eventos = Fachada.senhasPorEvento(qtdeSenhas);
		            if(eventos.isEmpty())
		            	throw new Exception("O evento não possui senhas.");

		            DefaultTableModel model = new DefaultTableModel();
		            table.setModel(model);

		            model.addColumn("Id Evento");
		            model.addColumn("Nome Evento");
		            model.addColumn("Qtd. Senhas");

		            for (Evento evento : eventos) {
		                model.addRow(new Object[] { 
		                    evento.getId(), 
		                    evento.getNome(), 
		                    evento.getSenhas().size() 
		                });
		            }

		        } catch (Exception ex) {
		        	errormsgmLabel.setText(ex.getMessage());
		        }
		    }
		});

		btnEventosSenhas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEventosSenhas.setBounds(209, 86, 250, 23);
		frame.getContentPane().add(btnEventosSenhas);

		frame.setVisible(true);
	}
}
