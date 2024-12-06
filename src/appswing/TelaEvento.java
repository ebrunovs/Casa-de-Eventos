package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Cliente;
import modelo.Evento;
import modelo.Senha;
import regras_negocio.Fachada;

public class TelaEvento{
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton criar_button;
	private JButton limpar_button;
	private JButton apagar_button;
	private JButton atualizar_button;
	private JLabel label;
	private JLabel tip_label;
	private JLabel nome_label;
	private JLabel preco_label;
	private JLabel busca_label;
	private JTextField buscaNome_textField;
	private JTextField nome_textField;
	private JTextField preco_textField;
	private JTextField data_textField;

	/**
	 * Create the application.
	 */
	public TelaEvento() {
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
				listagem();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});
		frame.setTitle("Eventos");
		frame.setBounds(100, 100, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 63, 685, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			// proibir alteracao de celulas
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		// evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// pegar o nome, data nascimento e apelidos da pessoa selecionada
						String nome = (String) table.getValueAt(table.getSelectedRow(), 1);
						Evento ev = Fachada.localizarEvento(nome);
						String data= ev.getData();
						Double preco = ev.getPreco();
						nome_textField.setText(nome);
						preco_textField.setText(preco.toString());
						data_textField.setText(data);
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}

			}
		});

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

		apagar_button = new JButton("Apagar");
		apagar_button.setToolTipText("apagar evento e seus dados");
		apagar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (nome_textField.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					// pegar o nome na linha selecionada
					String nome = nome_textField.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operacao apaga o evento: " + nome, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						Fachada.apagarEvento(nome);
						label.setText("Evento excluido");
						listagem(); // listagem
					} else
						label.setText("exclus�o cancelada");

				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		apagar_button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		apagar_button.setBounds(169, 340, 74, 23);
		frame.getContentPane().add(apagar_button);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 372, 607, 14);
		frame.getContentPane().add(label);

		button = new JButton("Buscar por nome");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(175, 27, 149, 23);
		frame.getContentPane().add(button);

		buscaNome_textField = new JTextField();
		buscaNome_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buscaNome_textField.setColumns(10);
		buscaNome_textField.setBounds(62, 28, 106, 20);
		frame.getContentPane().add(buscaNome_textField);

		tip_label = new JLabel("selecione um evento para editar");
		tip_label.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(tip_label);

		nome_label = new JLabel("Nome:");
		nome_label.setHorizontalAlignment(SwingConstants.LEFT);
		nome_label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		nome_label.setBounds(31, 239, 62, 14);
		frame.getContentPane().add(nome_label);

		nome_textField = new JTextField();
		nome_textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		nome_textField.setColumns(10);
		nome_textField.setBackground(Color.WHITE);
		nome_textField.setBounds(101, 235, 165, 20);
		frame.getContentPane().add(nome_textField);

		preco_label = new JLabel("Preço:");
		preco_label.setHorizontalAlignment(SwingConstants.LEFT);
		preco_label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		preco_label.setBounds(31, 264, 62, 14);
		frame.getContentPane().add(preco_label);

		preco_textField = new JTextField();
		preco_textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		preco_textField.setColumns(10);
		preco_textField.setBounds(101, 260, 86, 20);
		frame.getContentPane().add(preco_textField);
		

		criar_button = new JButton("Criar");
		criar_button.setToolTipText("cadastrar novo evento");
		criar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (nome_textField.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = nome_textField.getText().trim();
					Double preco =Double.parseDouble(preco_textField.getText().trim());
					String data = data_textField.getText().trim();
					Fachada.criarEvento(nome, data, preco);
			
					label.setText("evento cadastrado");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		criar_button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		criar_button.setBounds(21, 340, 62, 23);
		frame.getContentPane().add(criar_button);

		atualizar_button = new JButton("Atualizar");
		atualizar_button.setToolTipText("atualizar evento ");
		atualizar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (nome_textField.getText().trim().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = nome_textField.getText().trim();
					String data = data_textField.getText().trim();
					Fachada.alterarDataEvento(nome,data);
					label.setText("evento atualizado");
					listagem();
				} catch (Exception ex2) {
					label.setText(ex2.getMessage());
				}
			}
		});
		atualizar_button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		atualizar_button.setBounds(82, 340, 87, 23);
		frame.getContentPane().add(atualizar_button);

		busca_label = new JLabel("Texto:");
		busca_label.setBounds(21, 32, 46, 14);
		frame.getContentPane().add(busca_label);

		limpar_button = new JButton("Limpar");
		limpar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nome_textField.setText("");
				preco_textField.setText("");
				
			}
		});
		limpar_button.setBounds(252, 340, 89, 23);
		frame.getContentPane().add(limpar_button);
		
		data_textField = new JTextField();
		data_textField.setBounds(101, 289, 96, 19);
		frame.getContentPane().add(data_textField);
		data_textField.setColumns(10);
		
		JLabel data_label = new JLabel("Data:");
		data_label.setHorizontalAlignment(SwingConstants.LEFT);
		data_label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		data_label.setBounds(31, 291, 62, 14);
		frame.getContentPane().add(data_label);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Evento> lista = Fachada.consultarEventos(buscaNome_textField.getText());

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Id");
			model.addColumn("Nome");
			model.addColumn("Data");
			model.addColumn("Preço");
			model.addColumn("Lista de senhas");
			

			// criar as linhas da tabela
			
			for (Evento ev : lista) {
				String senhas ="";
				for (Senha s : ev.getSenhas()) {
					senhas += s.getCodigo()+" ";
				}

				model.addRow(new Object[] { ev.getId(), ev.getNome(), ev.getData(),ev.getPreco(), senhas});
			}
			tip_label.setText("resultados: " + lista.size() + " eventos   - selecione uma linha para editar");

			// redimensionar a coluna 0,3 e 4
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(40); // coluna id
			table.getColumnModel().getColumn(3).setMinWidth(200); // coluna dos apelidos
			table.getColumnModel().getColumn(4).setMinWidth(200); // coluna dos telefones
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
