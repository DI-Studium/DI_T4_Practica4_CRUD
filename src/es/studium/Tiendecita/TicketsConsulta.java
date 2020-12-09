package es.studium.Tiendecita;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.Desktop;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class TicketsConsulta extends JFrame {
	BaseDatos bd = new BaseDatos();
	Connection conexion = null;
	String[] cadena;
	Choice choiceSelecTicket = new Choice();
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
	public TicketsConsulta() {
		setTitle("Consulta Ticket");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 690, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		conexion = bd.conectar();
		String[] data1 = bd.consultarTicketsTabla(conexion).split("#");
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

		JLabel lblSelecTicket = new JLabel("Selecciona el ticket a consultar:");
		lblSelecTicket.setBounds(12, 383, 200, 16);
		contentPane.add(lblSelecTicket);


		choiceSelecTicket.setBounds(105, 405, 446, 22);
		contentPane.add(choiceSelecTicket);

		// Rellenar
		choiceSelecTicket.add("Seleccionar un ticket, para detalles");
		conexion = bd.conectar();
		cadena = (bd.consultarTicketsChoice(conexion)).split("#");
		for(int i = 0; i < cadena.length; i++)
		{
			choiceSelecTicket.add(cadena[i]);
		}
		add(choiceSelecTicket);
		bd.desconectar(conexion);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choiceSelecTicket.getSelectedItem().equals("Seleccionar un ticket, para detalles"))
				{
					// No hacemos nada
				}
				else
				{
					// Coger el elemento seleccionado
					String[] tabla = choiceSelecTicket.getSelectedItem().split(" - ");
					// El idCliente que quiero editar está en tabla[0]
					idTicketConsultar = Integer.parseInt(tabla[0]);
					String fecha=tabla[1];
					String[] fechaCambio=fecha.split("-");
					String fecha2 = fechaCambio[2]+"/"+fechaCambio[1]+"/"+fechaCambio[0];
					String idTicket=tabla[0];

					cadena = (bd.consultarArticulo(conexion, idTicketConsultar)).split("#");
					// cadena[0] = idTicket
					// cadena[1] = fechaTicket
					// cadena[2] = totalTicket

					new TicketsConsulta2(idTicket, fecha2);

					bd.desconectar(conexion);
				}
			}
		});
		btnConsultar.setBounds(563, 402, 97, 25);
		contentPane.add(btnConsultar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnAceptar.setBounds(12, 469, 97, 25);
		contentPane.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(563, 469, 97, 25);
		contentPane.add(btnCancelar);
		setVisible(true);
	}
	public void imprimirPDF(){
		// Se crea el documento 
		Document documento = new Document();
		try 
		{ 
			// Se crea el OutputStream para el fichero donde queremos dejar el pdf. 
			FileOutputStream ficheroPdf = new FileOutputStream("ConsultaTicketGeneral.pdf");
			PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(22);
			// Se abre el documento. 
			documento.open();
			Paragraph titulo = new Paragraph("Listado de Tickets", 
					FontFactory.getFont("arial", // fuente 
							22, // tamaño 
							Font.ITALIC, // estilo 
							BaseColor.BLUE)); // color
			titulo.setAlignment(Element.ALIGN_CENTER);
			documento.add(titulo);
			// Sacar los datos
			conexion = bd.conectar();
			String[] cadena = bd.consultarTicketsTablaPDF1(conexion).split("\n");
			bd.desconectar(conexion);
			PdfPTable tabla = new PdfPTable(3); // Se indica el número de columnas
			tabla.setSpacingBefore(5); // Espaciado ANTES de la tabla
			tabla.addCell("id Tickets");
			tabla.addCell("Fecha Tickets");
			tabla.addCell("Total Tickets");

			// En cada posición de cadena tenemos un registro completo

			String[] subCadena;
			// En subCadena, separamos cada campo por -
			// subCadena[0] = id
			// subCadena[1] = Fecha
			// subCadena[2] = Total
			for (int i = 0; i < cadena.length; i++) 
			{
				subCadena = cadena[i].split(" - ");
				for(int j = 0; j < 3;j++)
				{
					tabla.addCell(subCadena[j]);
				}
			}
			documento.add(tabla); 
			documento.close(); 
			//Abrimos el archivo PDF recién creado 
			try 
			{
				File path = new File ("ConsultaTicketGeneral.pdf"); 
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
