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

public class TelaSenha {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnBuscarPorCodigo;
	private JButton criar_button;
	private JButton limpar_button;
	private JButton apagar_button;
	private JLabel label;
	private JLabel tip_label;
	private JLabel evento_label;
	private JLabel codigo_label;
	private JLabel busca_label;
	private JTextField buscaCodigo_textField;
	private JTextField evento_textField;
	private JTextField codigo_textField;
	private JLabel cliente_label;
	private JTextField cliente_textField;

	/**
	 * Create the application.
	 */
	public TelaSenha() {
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
		frame.setTitle("Clientes");
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
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// pegar o nome, data nascimento e apelidos da pessoa selecionada
						String codigo = (String) table.getValueAt(table.getSelectedRow(), 1);
						Senha sen = Fachada.localizarSenha(codigo);
						String ev = sen.getEvento().getNome();
						String cli = sen.getCliente().getNome();
						evento_textField.setText(ev);
						codigo_textField.setText(codigo);
						cliente_textField.setText(cli);
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
		apagar_button.setToolTipText("apagar cliente e seus dados");
		apagar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// pegar o nome na linha selecionada
					String cod = codigo_textField.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operacao apaga a senha: " + cod, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						Fachada.apagarSenha(cod);
						label.setText("senha excluida");
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

		btnBuscarPorCodigo = new JButton("Buscar por codigo");
		btnBuscarPorCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnBuscarPorCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarPorCodigo.setBounds(175, 27, 149, 23);
		frame.getContentPane().add(btnBuscarPorCodigo);

		buscaCodigo_textField = new JTextField();
		buscaCodigo_textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buscaCodigo_textField.setColumns(10);
		buscaCodigo_textField.setBounds(62, 28, 106, 20);
		frame.getContentPane().add(buscaCodigo_textField);

		tip_label = new JLabel("selecione um cliente para editar");
		tip_label.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(tip_label);

		evento_label = new JLabel("Evento:");
		evento_label.setHorizontalAlignment(SwingConstants.LEFT);
		evento_label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		evento_label.setBounds(41, 264, 62, 14);
		frame.getContentPane().add(evento_label);

		evento_textField = new JTextField();
		evento_textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		evento_textField.setColumns(10);
		evento_textField.setBackground(Color.WHITE);
		evento_textField.setBounds(101, 260, 165, 20);
		frame.getContentPane().add(evento_textField);

		codigo_label = new JLabel("Codigo:");
		codigo_label.setHorizontalAlignment(SwingConstants.LEFT);
		codigo_label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		codigo_label.setBounds(31, 239, 62, 14);
		frame.getContentPane().add(codigo_label);

		codigo_textField = new JTextField();
		codigo_textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		codigo_textField.setColumns(10);
		codigo_textField.setBounds(101, 234, 165, 20);
		frame.getContentPane().add(codigo_textField);
		

		criar_button = new JButton("Criar");
		criar_button.setToolTipText("cadastrar nova senha");
		criar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (evento_textField.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String evento = evento_textField.getText().trim();
					String codigo = codigo_textField.getText().trim();
					String cliente = cliente_textField.getText().trim();

					Fachada.criarSenha(codigo,evento,cliente);
			
					label.setText("Senha cadastrada");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		criar_button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		criar_button.setBounds(21, 340, 62, 23);
		frame.getContentPane().add(criar_button);


		busca_label = new JLabel("Texto:");
		busca_label.setBounds(21, 32, 46, 14);
		frame.getContentPane().add(busca_label);

		limpar_button = new JButton("Limpar");
		limpar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evento_textField.setText("");
				codigo_textField.setText("");
				
			}
		});
		limpar_button.setBounds(276, 234, 89, 23);
		frame.getContentPane().add(limpar_button);
		
		cliente_label = new JLabel("Cliente: ");
		cliente_label.setBounds(41, 293, 45, 13);
		frame.getContentPane().add(cliente_label);
		
		cliente_textField = new JTextField();
		cliente_textField.setBounds(101, 290, 165, 19);
		frame.getContentPane().add(cliente_textField);
		cliente_textField.setColumns(10);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Senha> lista = Fachada.consultarSenhas(buscaCodigo_textField.getText());

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Id");
			model.addColumn("Código");
			model.addColumn("Evento");
			model.addColumn("Cliente");

			

			// criar as linhas da tabela
			
			for (Senha s : lista) {				
				model.addRow(new Object[] { s.getId(), s.getCodigo(), s.getEvento().getNome(), s.getCliente().getNome()});
			}
			tip_label.setText("resultados: " + lista.size() + " pessoas   - selecione uma linha para editar");

			// redimensionar a coluna 0,3 e 4
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(40); // coluna id
			table.getColumnModel().getColumn(2).setMinWidth(200); // coluna dos apelidos
			table.getColumnModel().getColumn(3).setMinWidth(200); // coluna dos telefones
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
