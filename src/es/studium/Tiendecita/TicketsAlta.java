package es.studium.Tiendecita;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class TicketsAlta extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser fechaChooser;
	private JTextField textCantidad;
	DefaultTableModel dtm= new DefaultTableModel();
	private JTable tableTicket= new JTable(dtm);
	private JTextField textTotal;
	double total=0.0;
	double subtotal=0.0;
	int anio;
	int mes;
	int dia;
	String[] ArticuloT=null;
	ArrayList<String> al = new ArrayList<String>();//Crea un array list
	 //creamos el arreglo de objetos que contendra el contenido de las columnas
	 Object[] data = new Object[4];
	BaseDatos bd = new BaseDatos();
	Connection conexion = null;
	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public TicketsAlta() throws ParseException {
		setTitle("Alta Ticket");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Nuevo Ticket");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(12, 13, 658, 22);
		contentPane.add(lblTitulo);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(12, 67, 56, 16);
		contentPane.add(lblFecha);
		
		JLabel lblArticulo = new JLabel("Articulo:");
		lblArticulo.setBounds(12, 96, 56, 16);
		contentPane.add(lblArticulo);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(499, 96, 56, 16);
		contentPane.add(lblCantidad);
		
		fechaChooser = new JDateChooser();
		fechaChooser.setDateFormatString("dd-MM-yyyy");
		Date newDate = new Date();
		fechaChooser.setDate(newDate);
		anio = fechaChooser.getCalendar().get(Calendar.YEAR);
		mes = fechaChooser.getCalendar().get(Calendar.MONTH) + 1;
		dia = fechaChooser.getCalendar().get(Calendar.DAY_OF_MONTH);
		fechaChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anio = fechaChooser.getCalendar().get(Calendar.YEAR);
				mes = fechaChooser.getCalendar().get(Calendar.MONTH) + 1;
				dia = fechaChooser.getCalendar().get(Calendar.DAY_OF_MONTH);
			}
		});
	
		
		fechaChooser.setBounds(68, 64, 177, 22);
		contentPane.add(fechaChooser);

		Choice choiceArticulo = new Choice();
		choiceArticulo.setBounds(68, 96, 372, 22);
		contentPane.add(choiceArticulo);
		
		
		choiceArticulo.add("Seleccionar uno...");// Montar el Choice
		
		// Conectar a la base de datos
				// Rellenar el Choice
				String sqlSelectArticulo = "SELECT * FROM articulos";
				BaseDatos bd = new BaseDatos();
				conexion = bd.conectar();
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmtArticulo = conexion.createStatement();
					ResultSet rsArticulo = stmtArticulo.executeQuery(sqlSelectArticulo);
					while (rsArticulo.next()) 
					{
						choiceArticulo.add(rsArticulo.getString("idArticulo")+
								" - "+rsArticulo.getString("descripcionArticulo")+
								" - "+rsArticulo.getString("precioArticulo"));
					}
					rsArticulo.close();
					stmtArticulo.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar"+"\n"+ex);
					ex.printStackTrace();
				}
				bd.desconectar(conexion);
				// Desconectar
				
		
		
		textCantidad = new JTextField();
		textCantidad.setColumns(10);
		textCantidad.setBounds(563, 93, 107, 22);
		contentPane.add(textCantidad);
		
		JButton btnAnadir = new JButton("A\u00F1adir");
		btnAnadir.setBounds(12, 136, 97, 25);
		contentPane.add(btnAnadir);
		 // insertamos las columnas
		 dtm.addColumn("Id Articulo");
		 dtm.addColumn("Nombre Articulo");
		 dtm.addColumn("Precio Articulo");
		 dtm.addColumn("Cantidad Articulo");
		 //se define el tamaño
		 tableTicket.setPreferredScrollableViewportSize(new Dimension(400, 70));
		 //Creamos un JscrollPane y le agregamos la JTable
		 JScrollPane scrollPane = new JScrollPane(tableTicket);
		 
		 //Agregamos el JScrollPane al contenedor
		 getContentPane().add(scrollPane, BorderLayout.CENTER);
		 //manejamos la salida
		addWindowListener(new WindowAdapter() {});
		//Hasta aqui sera la tabla
		
		tableTicket.setBounds(12, 174, 658, 177);
		contentPane.add(tableTicket);
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArticuloT=choiceArticulo.getSelectedItem().split("-");//guardamos en una tabla cada dato que esta separado por un guion del choise.
				al.add(ArticuloT[0]);
				al.add(ArticuloT[1]);
				al.add(ArticuloT[2]);
				al.add(textCantidad.getText());
				 // insertamos las columnas
				 //dtm.addColumn("id");
				 //dtm.addColumn("Nombre Articulo");
				 //dtm.addColumn("Precio Articulo");
				 // insertamos el contenido de las columnas
				 for(int row = 0; row < al.size();row=row+4) {
				 data[0] = al.get(row);
				 data[1] = al.get(row+1);
				 data[2] = al.get(row+2);
				 data[3] = al.get(row+3);
				 subtotal= (Double.parseDouble((String) data[2])*Double.parseDouble((String)data[3]));
				 }
				 total=total+subtotal;
				 dtm.addRow(data);
				 textTotal.setText(String.valueOf(total));
			}});

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(480, 375, 42, 16);
		contentPane.add(lblTotal);
		
		textTotal = new JTextField();
		textTotal.setEditable(false);
		textTotal.setBounds(533, 372, 137, 22);
		contentPane.add(textTotal);
		textTotal.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anio = fechaChooser.getCalendar().get(Calendar.YEAR);
				mes = fechaChooser.getCalendar().get(Calendar.MONTH) + 1;
				dia = fechaChooser.getCalendar().get(Calendar.DAY_OF_MONTH);
				String fecha = ""+anio+"/"+mes+"/"+dia;
				System.out.println(fecha);
				setVisible(false);
				//Cierra la aplicacion
				//Conectarse a la BD y hacer las consustas
				// Conectar BD
				conexion = bd.conectar();
				// Hacer INSERT
				String sentencia = "INSERT INTO tickets VALUES(null,'"+fecha+"','"+textTotal.getText()+"')";
				int idTicket = bd.ultimoIdTicket(conexion, sentencia);
				
				// Feedback
				
				if(idTicket!=0)
				{
					for(int i = 0; i < al.size();i=i+4)
					{
						String sentencia2 = "INSERT INTO articulostickets VALUES (null,'"+al.get(i)+"','"+idTicket+"','"+al.get(i+3)+"')";
						bd.AltaTicket(conexion, sentencia2);
					}	
					System.out.println("Alta de ticket realizada");
				}
				else
				{
					// Error
					System.out.println("Error en Alta de ticket");
				}
	bd.desconectar(conexion);
	// Desconectar
			}
		});
		btnAceptar.setBounds(12, 431, 97, 25);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(573, 431, 97, 25);
		contentPane.add(btnCancelar);
		setVisible(true);
	}
}
