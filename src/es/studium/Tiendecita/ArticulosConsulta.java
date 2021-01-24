package es.studium.Tiendecita;

import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.awt.event.ActionEvent;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
public class ArticulosConsulta extends JFrame {
	BaseDatos bd = new BaseDatos();
	Connection conexion = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableArt;

	/**
	 * Create the frame.
	 */
	public ArticulosConsulta() {
		setTitle("Consulta Art\u00EDculos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 645, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		conexion = bd.conectar();
		String[] data1 = bd.consultarArticulosTabla(conexion).split("#");
		bd.desconectar(conexion);
		//creamos el arreglo de objetos que contendra el
		//contenido de las columnas
		Object[] data = new Object[4];
		// creamos el modelo de Tabla
		DefaultTableModel dtm= new DefaultTableModel();
		// se crea la Tabla con el modelo DefaultTableModel
		tableArt = new JTable(dtm);
		// insertamos las columnas
		dtm.addColumn("id");
		dtm.addColumn("Nombre Articulo");
		dtm.addColumn("Precio Articulo");
		dtm.addColumn("Cantidad Articulo");
		// insertamos el contenido de las columnas
		for(int row = 0; row < data1.length;) {
			data[0] = data1[row];
			data[1] = data1[row+1];
			data[2] = data1[row+2];
			data[3] = data1[row+3];
			dtm.addRow(data);
			row=row+4;
		}
		
		tableArt.setBounds(23, 44, 579, 255);
		contentPane.add(tableArt);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(602, 44, 17, 255);
		contentPane.add(scrollBar);
		
		JLabel lblTitulo = new JLabel("Consulta Art\u00EDculos");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(23, 11, 570, 22);
		contentPane.add(lblTitulo);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnAceptar.setBounds(25, 326, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnPdf = new JButton("PDF");
		btnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirPDF();
			}
		});
		btnPdf.setBounds(504, 326, 89, 23);
		contentPane.add(btnPdf);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirPDF();
			}
		});
		btnImprimir.setBounds(405, 326, 89, 23);
		contentPane.add(btnImprimir);
		setVisible(true);
	}
	public void imprimirPDF(){
		try 
		{ 
			// Compilar el informe generando fichero jasper
			JasperCompileManager.compileReportToFile("ArticulosTiendecita.jrxml");
			System.out.println("Fichero ArticulosTiendecita.jasper generado CORRECTAMENTE!"); 
			// Objeto para guardar parámetros necesarios para el informe 
			HashMap<String,Object> parametros = new HashMap<String,Object>();
			//Guardamos los parametros del informe
			parametros.put("titulo", "Listado de  artículos");
			// Cargar el informe compilado 
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("ArticulosTiendecita.jasper"); 
			// Conectar a la base de datos para sacar la información 
			Connection conexion2 = bd.conectar();
			// Completar el informe con los datos de la base de datos 
			JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion2);
			//Desconexion de la BD
			bd.desconectar(conexion2);
			// Mostrar el informe en JasperViewer 
			JasperViewer.viewReport(print, false); 
			// Para exportarlo a pdf 
			JasperExportManager.exportReportToPdfFile(print, "ArticulosTiendecita.pdf");
			// Abrir el fichero PDF generado 
			File path = new File ("ArticulosTiendecita.pdf");
			Desktop.getDesktop().open(path);
		} 
		catch (Exception e) 
		{ 
			System.out.println("Error: " + e.toString()); 
		}
	}
}
