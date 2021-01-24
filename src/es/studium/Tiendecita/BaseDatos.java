package es.studium.Tiendecita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos
{
	String fecha;
	String fecha2;
	String BaseDatos="tiendecita";
	String login="root";
	String password="";
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/"+BaseDatos+"?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useSSL=false";

	Connection connection = null;
	Statement statement = null;
	Statement statement1 = null;
	Statement statement2 = null;
	ResultSet rs = null;

	public Connection conectar()
	{
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return connection;
	}
	public void desconectar(Connection c)
	{
		try
		{
			if(c!=null)
			{
				c.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 3-"+e.getMessage());
		}
	}
	public int altaArticulo(Connection c, String sentencia)
	{
		int resultado = 1;
		try
		{
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				resultado = 0;
			}
			else
			{
				resultado = 1;
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error altaArticulo-"+sqle.getMessage());
		}
		return (resultado);
	}
	public void AltaTicket(Connection c, String sentencia)
	{
		try
		{
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			System.out.println("Error AltaTicket-"+sqle.getMessage());
		}
	}
	public int ultimoIdTicket(Connection c, String sentencia)
	{
		int resultado = 0; // INSERT incorrecto
		try
		{
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				String sentenciaConsulta = "SELECT idTicket FROM tickets ORDER BY 1 DESC LIMIT 1";
				ResultSet rs = statement.executeQuery(sentenciaConsulta);
				if(rs.next())
				{
					resultado = rs.getInt("idTicket");
				}
			}
			else
			{
				resultado = 0;
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error ultimoIdTicket-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarArticulosChoice(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM articulos";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idArticulo") + "-" +
						rs.getString("descripcionArticulo") + "-" +
						rs.getString("cantidadArticulo")+ "-" +
						rs.getString("precioArticulo")+"#";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarArticulosChoice-"+sqle.getMessage());
		}
		return (resultado);
	}
	public int borrarArticulos(Connection c, int idArticulo)
	{
		int resultado = 1;
		try
		{
			String sentencia1 = "DELETE FROM articulostickets WHERE idArticuloFK1 = "+ idArticulo;
			String sentencia2 = "DELETE FROM articulos WHERE idArticulo = "+ idArticulo;
			//Crear una sentencia
			statement1 = c.createStatement();
			statement2 = c.createStatement();
			// Ejecutar la sentencia SQL
			if((statement1.executeUpdate(sentencia1))==1)
			{
				if((statement2.executeUpdate(sentencia2))==1) 
				{
					resultado = 0;
				}
			}
			else
			{
				if((statement2.executeUpdate(sentencia2))==1) 
				{
					resultado = 0;
				}
				else
				{
					resultado = 1;
				}
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error borrarArticulos-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarArticulo(Connection c, int idArticulo)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM articulos WHERE idArticulo="+idArticulo;
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			rs.next();
			resultado = resultado + rs.getInt("idArticulo") + "#" +
					rs.getString("descripcionArticulo") + "#" +
					rs.getString("cantidadArticulo")+ "#" +
					rs.getString("precioArticulo")+ "#";

		}
		catch (SQLException sqle)
		{
			
		}
		return (resultado);
	}
	public int modificarArticulos(Connection c, String sentencia)
	{
		int resultado = 1;
		try
		{
			//Crear una sentencia
			statement = c.createStatement();
			// Ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				resultado = 0;
			}
			else
			{
				resultado = 1;
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error modificarArticulos-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarArticulosTabla(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM articulos";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idArticulo") + "#" +
						rs.getString("descripcionArticulo") + "#" +
						rs.getString("cantidadArticulo")+ "#" +
						rs.getString("precioArticulo")+"#";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarArticulosTabla-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarArticulos(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM articulos";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idArticulo") + "-" +
						rs.getString("descripcionArticulo") + "-" +
						rs.getString("cantidadArticulo")+ "-" +
						rs.getString("precioArticulo")+"\n";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarArticulos-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarTicketsTabla(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM tickets";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idTicket") + "#" +
						rs.getString("fechaTicket") + "#" +
						rs.getString("totalTicket")+ "#";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarTicketsTabla-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarTicketsTablaFecha(Connection c, String fecha, String fecha2)
	{
		String resultado = "";
		this.fecha=fecha;
		this.fecha2=fecha2;
		try
		{
			String sentencia = "SELECT * FROM tickets where fechaTicket between \""+fecha+"\" and \""+fecha2+"\";";
			System.out.println(sentencia);
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idTicket") + "#" +
						rs.getString("fechaTicket") + "#" +
						rs.getString("totalTicket")+ "#";
			}
			System.out.println(resultado);
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarTicketsTabla-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarTicketsChoice(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM tickets";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idTicket") + " - " +
						rs.getString("fechaTicket") + " - " +
						rs.getString("totalTicket")+"#";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarTicketsChoice-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarTicketsTabla2(Connection c, String idTicketConsultar)
	{
		String resultado = "";
		try
		{
			
			String sentencia = "SELECT descripcionArticulo, articulostickets.cantidadArticulo, precioArticulo FROM tiendecita.articulos, tiendecita.articulostickets, tiendecita.tickets where idTicket="+idTicketConsultar+" AND idArticulo=idArticuloFK1 AND idTicket=idTicketFK2";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getString("descripcionArticulo") + "#" +
						rs.getString("articulostickets.cantidadArticulo") + "#" +
						rs.getString("precioArticulo")+"#";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarTicketsTabla2-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarTicketsTablaPDF1(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM tickets";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getInt("idTicket") + " - " +
						rs.getString("fechaTicket") + " - " +
						rs.getString("totalTicket")+ "\n";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarTicketsTabla-"+sqle.getMessage());
		}
		return (resultado);
	}
	public String consultarTicketsTablaPDF2(Connection c, String idTicketConsultar)
	{
		String resultado = "";
		try
		{
			
			String sentencia = "SELECT descripcionArticulo, articulostickets.cantidadArticulo, precioArticulo FROM tiendecita.articulos, tiendecita.articulostickets, tiendecita.tickets where idTicket="+idTicketConsultar+" AND idArticulo=idArticuloFK1 AND idTicket=idTicketFK2";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getString("descripcionArticulo") + " - " +
						rs.getString("articulostickets.cantidadArticulo") + " - " +
						rs.getString("precioArticulo")+"\n";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error consultarTicketsTabla2-"+sqle.getMessage());
		}
		return (resultado);
	}
}