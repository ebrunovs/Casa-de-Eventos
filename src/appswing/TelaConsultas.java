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

import modelo.Cliente;
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
	private JTextField senhas_textField;
	private JTextField evento_textField;
	private JLabel eventoSenhas_label_1;
	private JTextField senhaEvento_textField;

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
		scrollPane.setBounds(20, 176, 685, 155);
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
		scrollPane.setColumnHeaderView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JLabel errormsgmLabel = new JLabel("");
		errormsgmLabel.setForeground(Color.RED);
		errormsgmLabel.setBounds(20, 355, 416, 23);
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
		
		JLabel senhas_qtde_label = new JLabel("Qtde:");
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
		
		senhas_textField = new JTextField();
		senhas_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		senhas_textField.setColumns(10);
		senhas_textField.setBounds(93, 118, 106, 20);
		frame.getContentPane().add(senhas_textField);
		
		JLabel senhas_label = new JLabel("Senha:");
		senhas_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		senhas_label.setBounds(37, 119, 50, 16);
		frame.getContentPane().add(senhas_label);
		
		JButton btnBuscarSenhaExiste = new JButton("Buscar senha existe no evento");
		btnBuscarSenhaExiste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		            if (senhas_textField.getText().trim().isEmpty()) {
		                throw new Exception("Campo de Senha vazio.");
		            }
		            if ( senhaEvento_textField.getText().trim().isEmpty()) {
		                throw new Exception("Campo de evento vazio.");
		            }
		            String senha = senhas_textField.getText();
		            String evento = senhaEvento_textField.getText();
		            
		            Boolean existe = Fachada.senhaExisteNoEvento(senha,evento);
		            
		            DefaultTableModel model = new DefaultTableModel();
		            table.setModel(model);
		            model.addColumn("resultado");
		            if(!existe)
		            	model.addRow(new Object[]{"Senha não existe no evento."});
		            if(existe)
		            	model.addRow(new Object[]{"Senha  existe no evento."});
		            

		        } catch (Exception ex) {
		        	errormsgmLabel.setText(ex.getMessage());
		        }
		    }
		});
		btnBuscarSenhaExiste.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarSenhaExiste.setBackground(Color.LIGHT_GRAY);
		btnBuscarSenhaExiste.setBounds(418, 117, 250, 23);
		frame.getContentPane().add(btnBuscarSenhaExiste);
		
		JLabel evento_label = new JLabel("Evento:");
		evento_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		evento_label.setBounds(37, 149, 50, 16);
		frame.getContentPane().add(evento_label);
		
		evento_textField = new JTextField();
		evento_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		evento_textField.setColumns(10);
		evento_textField.setBounds(93, 149, 106, 20);
		frame.getContentPane().add(evento_textField);
		
		JButton btnConvidadosEvento = new JButton("Buscar convidados do evento");
		btnConvidadosEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		            if (evento_textField.getText().trim().isEmpty()) {
		                throw new Exception("Campo de Senhas vazio.");
		            }
		        	
		            String evento = evento_textField.getText();

		            List<Cliente> clientes = Fachada.clientesEmEvento(evento);
		            if(clientes.isEmpty())
		            	throw new Exception("O evento não possui senhas.");
		            
		            DefaultTableModel model = new DefaultTableModel();
		            table.setModel(model);

		            model.addColumn("Id");
		            model.addColumn("Nome");
		            model.addColumn("CPF");

		            for (Cliente cliente : clientes) {
		                model.addRow(new Object[] { 
		                    cliente.getId(), 
		                    cliente.getNome(), 
		                    cliente.getCPF(), 
		                });
		            }

		        } catch (Exception ex) {
		        	errormsgmLabel.setText(ex.getMessage());
		        }
			}
		});
		btnConvidadosEvento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnConvidadosEvento.setBackground(Color.LIGHT_GRAY);
		btnConvidadosEvento.setBounds(209, 148, 250, 23);
		frame.getContentPane().add(btnConvidadosEvento);
		
		eventoSenhas_label_1 = new JLabel("Evento:");
		eventoSenhas_label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eventoSenhas_label_1.setBounds(219, 120, 50, 16);
		frame.getContentPane().add(eventoSenhas_label_1);
		
		senhaEvento_textField = new JTextField();
		senhaEvento_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		senhaEvento_textField.setColumns(10);
		senhaEvento_textField.setBounds(279, 120, 106, 20);
		frame.getContentPane().add(senhaEvento_textField);

		frame.setVisible(true);
	}
}
