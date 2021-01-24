package es.studium.Tiendecita;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Desktop;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class TicketsConsultaFecha2 extends JFrame {
	String fecha;
	String fecha2;
	BaseDatos bd = new BaseDatos();
	Connection conexion = null;
	int idTicketConsultar;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableTicket;
	/**
	 * Create the frame.
	 */
	public TicketsConsultaFecha2(String fecha, String fecha2 ) {
		this.fecha=fecha;
		this.fecha2=fecha2;
		setTitle("Consulta Ticket");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		conexion = bd.conectar();
		String[] data1 = bd.consultarTicketsTablaFecha(conexion, fecha, fecha2).split("#");
		//Comprobamos si la busqueda genera datos
		if (data1.length==0) {
		//creamos el arreglo de objetos que contendra el
		//contenido de las columnas
		Object[] data = new Object[3];
		// creamos el modelo de Tabla
		DefaultTableModel dtm= new DefaultTableModel();
		// se crea la Tabla con el modelo DefaultTableModel
		tableTicket = new JTable(dtm);
		// insertamos las columnas
		dtm.addColumn("id Tickets");
		dtm.addColumn("Fecha Tickets");
		dtm.addColumn("Total Tickets");
		// insertamos el contenido de las columnas
		for(int row = 0; row < data1.length;) {
			data[0] = data1[row];
			data[1] = data1[row+1];
			data[2] = data1[row+2];
			dtm.addRow(data);
			row=row+3;
		}
		tableTicket.setBounds(12, 52, 648, 269);
		contentPane.add(tableTicket);

		JLabel lblTitulo = new JLabel("Consultar Tickets");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(12, 13, 648, 16);
		contentPane.add(lblTitulo);

		JButton btnPDF = new JButton("PDF");
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirPDF();
			}
		});
		btnPDF.setBounds(563, 331, 97, 25);
		contentPane.add(btnPDF);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirPDF();
			}
		});

		btnImprimir.setBounds(454, 331, 97, 25);
		contentPane.add(btnImprimir);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnAceptar.setBounds(12, 367, 97, 25);
		contentPane.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(563, 367, 97, 25);
		contentPane.add(btnCancelar);
		setVisible(true);
		}
		else if (data1.length>0) {
			System.out.println("No hay tickets a mostrar");
		}
	}
	public void imprimirPDF(){
		try 
		{ 
			// Compilar el informe generando fichero jasper
			JasperCompileManager.compileReportToFile("ListadoTicketsTiendecita.jrxml");
			System.out.println("Fichero ListadoTicketsTiendecita.jasper generado CORRECTAMENTE!"); 
			// Objeto para guardar parámetros necesarios para el informe 
			HashMap<String,Object> parametros = new HashMap<String,Object>();
			//Guardamos los parametros del informe
			
			String[] fechaCambio=fecha.split("/");
			String fechaesp1 = fechaCambio[2]+"/"+fechaCambio[1]+"/"+fechaCambio[0];
			String[] fechaCambio2=fecha2.split("/");
			String fechaesp2 = fechaCambio2[2]+"/"+fechaCambio2[1]+"/"+fechaCambio2[0];
			
			parametros.put("titulo", "Listado de  Tickets desde "+fechaesp1+" hasta "+fechaesp2+"");
			// Cargar el informe compilado 
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("ListadoTicketsTiendecita.jasper"); 
			// Conectar a la base de datos para sacar la información 
			Connection conexion2 = bd.conectar();
			// Completar el informe con los datos de la base de datos 
			JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion2);
			//Desconexion de la BD
			bd.desconectar(conexion2);
			// Mostrar el informe en JasperViewer 
			JasperViewer.viewReport(print, false); 
			// Para exportarlo a pdf 
			JasperExportManager.exportReportToPdfFile(print, "ListadoTicketsTiendecita.pdf");
			// Abrir el fichero PDF generado 
			File path = new File ("ListadoTicketsTiendecita.pdf");
			Desktop.getDesktop().open(path);
		} 
		catch (Exception e) 
		{ 
			System.out.println("Error: " + e.toString()); 
		}
	}
}
