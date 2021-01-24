package es.studium.Tiendecita;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	JMenuItem mntmArticulosAlta = new JMenuItem("Alta");
	JMenuItem mntmArticulosBaja = new JMenuItem("Baja");
	JMenuItem mntmArticulosModificacion = new JMenuItem("Modificacion");
	JMenuItem mntmArticulosConsulta = new JMenuItem("Consulta");
	JMenuItem mntmTicketsAlta = new JMenuItem("Alta");
	JMenuItem mntmTicketsConsulta = new JMenuItem("Consulta");
	JMenuItem mntmTicketsConsultaFecha = new JMenuItem("Consulta Fecha");
	public Menu() {
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArticulos = new JMenu("Articulos");
		menuBar.add(mnArticulos);


		mnArticulos.add(mntmArticulosAlta);
		mnArticulos.add(mntmArticulosBaja);
		mnArticulos.add(mntmArticulosModificacion);
		mnArticulos.add(mntmArticulosConsulta);
		
		JMenu mnTickets = new JMenu("Tickets");
		menuBar.add(mnTickets);


		mnTickets.add(mntmTicketsAlta);
		mnTickets.add(mntmTicketsConsulta);
		mnTickets.add(mntmTicketsConsultaFecha);
		
		mntmArticulosAlta.addActionListener(this);
		mntmArticulosBaja.addActionListener(this);
		mntmArticulosModificacion.addActionListener(this);
		mntmArticulosConsulta.addActionListener(this);
		
		mntmTicketsAlta.addActionListener(this);
		mntmTicketsConsulta.addActionListener(this);
		mntmTicketsConsultaFecha.addActionListener(this);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==mntmArticulosAlta) {
			new ArticulosAlta();
		}
		if (e.getSource()==mntmArticulosBaja) {
			new ArticuloBaja();
		}
		if (e.getSource()==mntmArticulosModificacion) {
			new ArticuloModificar();
		}  
		if (e.getSource()==mntmArticulosConsulta) {
			new ArticulosConsulta();
		}
		if (e.getSource()==mntmTicketsAlta) {
			try {
				new TicketsAlta();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==mntmTicketsConsulta) {
			new TicketsConsulta();
		}
		if (e.getSource()==mntmTicketsConsultaFecha) {
			new TicketsConsultaFecha();
		}
	}
}
