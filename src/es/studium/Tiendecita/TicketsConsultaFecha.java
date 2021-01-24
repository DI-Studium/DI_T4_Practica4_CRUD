package es.studium.Tiendecita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JButton;

public class TicketsConsultaFecha extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDateChooser fechaDesde;
	int anio;
	int mes;
	int dia;
	private JDateChooser fechaHasta;
	int anio2;
	int mes2;
	int dia2;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public TicketsConsultaFecha() {
		setTitle("Consulta Tickets");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 278, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fechaDesde = new JDateChooser();
		fechaDesde.setDateFormatString("dd-MM-yyyy");
		Date newDate = new Date();
		fechaDesde.setDate(newDate);
		anio = fechaDesde.getCalendar().get(Calendar.YEAR);
		mes = fechaDesde.getCalendar().get(Calendar.MONTH) + 1;
		dia = fechaDesde.getCalendar().get(Calendar.DAY_OF_MONTH);
		fechaDesde.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anio = fechaDesde.getCalendar().get(Calendar.YEAR);
				mes = fechaDesde.getCalendar().get(Calendar.MONTH) + 1;
				dia = fechaDesde.getCalendar().get(Calendar.DAY_OF_MONTH);
			}
		});
		fechaDesde.setBounds(108, 21, 110, 22);
		contentPane.add(fechaDesde);
		
		
		
		fechaHasta = new JDateChooser();
		fechaHasta.setDateFormatString("dd-MM-yyyy");
		Date newDate2 = new Date();
		fechaHasta.setDate(newDate2);
		anio2 = fechaHasta.getCalendar().get(Calendar.YEAR);
		mes2 = fechaHasta.getCalendar().get(Calendar.MONTH) + 1;
		dia2 = fechaHasta.getCalendar().get(Calendar.DAY_OF_MONTH);
		fechaHasta.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anio2 = fechaHasta.getCalendar().get(Calendar.YEAR);
				mes2 = fechaHasta.getCalendar().get(Calendar.MONTH) + 1;
				dia2 = fechaHasta.getCalendar().get(Calendar.DAY_OF_MONTH);
			}
		});
		fechaHasta.setBounds(108, 54, 110, 22);
		contentPane.add(fechaHasta);
		
		JLabel lblFDesde = new JLabel("Fecha Desde:");
		lblFDesde.setBounds(20, 21, 88, 14);
		contentPane.add(lblFDesde);
		
		JLabel lblFHasta = new JLabel("Fecha Hasta:");
		lblFHasta.setBounds(20, 54, 88, 14);
		contentPane.add(lblFHasta);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(90, 96, 89, 23);
		contentPane.add(btnAceptar);
		setVisible(true);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anio = fechaDesde.getCalendar().get(Calendar.YEAR);
				mes = fechaDesde.getCalendar().get(Calendar.MONTH) + 1;
				dia = fechaDesde.getCalendar().get(Calendar.DAY_OF_MONTH);
				String fecha = ""+anio+"/"+mes+"/"+dia;
				anio2 = fechaHasta.getCalendar().get(Calendar.YEAR);
				mes2 = fechaHasta.getCalendar().get(Calendar.MONTH) + 1;
				dia2 = fechaHasta.getCalendar().get(Calendar.DAY_OF_MONTH);
				String fecha2 = ""+anio2+"/"+mes2+"/"+dia2;
				
				setVisible(false);
				//Cierra la aplicacion
				new TicketsConsultaFecha2(fecha, fecha2);
			}
		});
	}
}
