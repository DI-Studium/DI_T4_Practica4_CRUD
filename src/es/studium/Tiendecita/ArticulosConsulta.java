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
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
		// Se crea el documento 
				Document documento = new Document();
				try 
				{ 
					// Se crea el OutputStream para el fichero donde queremos dejar el pdf. 
					FileOutputStream ficheroPdf = new FileOutputStream("ConsultaArticulos.pdf");
					PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(22);
					// Se abre el documento. 
					documento.open();
					Paragraph titulo = new Paragraph("Listado de Articulos", 
							FontFactory.getFont("arial", // fuente 
									22, // tamaño 
									Font.ITALIC, // estilo 
									BaseColor.BLUE)); // color
					titulo.setAlignment(Element.ALIGN_CENTER);
					documento.add(titulo);
					// Sacar los datos
					conexion = bd.conectar();
					String[] cadena = bd.consultarArticulos(conexion).split("\n");
					bd.desconectar(conexion);
					PdfPTable tabla = new PdfPTable(4); // Se indica el número de columnas
					tabla.setSpacingBefore(5); // Espaciado ANTES de la tabla
					tabla.addCell("Id Articulo");
					tabla.addCell("Descripcion Articulo");
					tabla.addCell("Unidades");
					tabla.addCell("Precio");
					
					// En cada posición de cadena tenemos un registro completo
					
					String[] subCadena;
					// En subCadena, separamos cada campo por -
					// subCadena[0] = id
					// subCadena[1] = descripcion
					// subCadena[2] = cantidad
					// subCadena[3] = precio
					for (int i = 0; i < cadena.length; i++) 
					{
						subCadena = cadena[i].split("-");
						for(int j = 0; j < 4;j++)
						{
							tabla.addCell(subCadena[j]);
						}
					}
					documento.add(tabla); 
					documento.close(); 
					//Abrimos el archivo PDF recién creado 
					try 
					{
						File path = new File ("ConsultaArticulos.pdf"); 
						Desktop.getDesktop().open(path); 
					}
					catch (IOException ex) 
					{
						//System.out.println("Se ha producido un error al abrir el archivo PDF"); 
						System.err.println("Error: "+ex);
					}
				}
				catch ( Exception e ) 
				{ 
					//System.out.println("Se ha producido un error al generar el archivo PDF"); 
					System.err.println("Error: "+e);
				}
	}
}
