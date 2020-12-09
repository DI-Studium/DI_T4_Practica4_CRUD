package es.studium.Tiendecita;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class ArticuloBaja extends JFrame {
	int idArticuloBorrar = 0;
	BaseDatos bd = new BaseDatos();
	Connection conexion = null;
	String[] cadena;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public ArticuloBaja() {
		setTitle("Baja de art\u00EDculo");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 396, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Choice choiceSelecArt = new Choice();
		choiceSelecArt.setBounds(122, 57, 235, 20);
		contentPane.add(choiceSelecArt);
		
		JLabel lblSelecArt = new JLabel("Selecciona el art\u00EDculo a dar de baja:");
		lblSelecArt.setBounds(10, 37, 209, 14);
		contentPane.add(lblSelecArt);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choiceSelecArt.getSelectedItem().equals("Seleccionar un articulo..."))
				{
					// No hacemos nada
				}
				else
				{
					// Coger el elemento seleccionado
					String[] tabla = choiceSelecArt.getSelectedItem().split("-");
					// El idCliente que quiero borrar está en tabla[0]
					idArticuloBorrar = Integer.parseInt(tabla[0]);
					if((bd.borrarArticulos(conexion, idArticuloBorrar))==0)
					{
						// Todo bien
						System.out.println("Baja de articulo correcta");
						setVisible(false);
					}
					else
					{
						// Error
						System.out.println("Error al borrar el articulo");
					}
					// Desconectar BD
					bd.desconectar(conexion);
				}
			}
		});
		btnAceptar.setBounds(10, 120, 89, 23);
		contentPane.add(btnAceptar);
		
		choiceSelecArt.add("Seleccionar un articulo...");
		// Conectar BD
		conexion = bd.conectar();
		cadena = (bd.consultarArticulosChoice(conexion)).split("#");
		for(int i = 0; i < cadena.length; i++)
		{
			choiceSelecArt.add(cadena[i]);
		}
		add(choiceSelecArt);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(268, 120, 89, 23);
		contentPane.add(btnCancelar);
		setVisible(true);
	}
}
