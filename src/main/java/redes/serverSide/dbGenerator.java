package redes.serverSide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbGenerator 
{
	private String dbPath;
	private Connection connection;
	
	public dbGenerator(String dbName)
	{
		createDBPath(dbName);
		connection = null;
	}
	
	public void OpenDataBaseConnection() 
	{
	      try 
	      {
	    	 loadJDBCDrive();
	    	 
	    	 //If database does not exist, it will be created automatically
	    	 connection = DriverManager.getConnection(dbPath);
	         
	         System.out.println("O banco de dados foi conectado com sucesso!");
	      } 
	      catch(ClassNotFoundException e) 
	      {
	    	  System.err.println("Não foi possivel carregar a classe do JDBC. O seguinte erro ocorreu: " + e.getMessage()); 
	          System.exit(0);
	      }
	      catch (Exception e ) 
	      {
	    	 System.err.println("Um erro ocorreu ao abrir a conexão com o banco de dados."); 
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
   }
	
	public void CreateTablesIfNotExists() 
	{
		String createUserTable = "CREATE IF NOT EXISTS User(\n"
							     + "Id integer PRIMARY KEY, \n"
							     + "UserName text NOT NULL, \n"
							     + "Password text NOT NULL  \n";
		
		try
		{
			if(connection == null)
				OpenDataBaseConnection();
			
			Statement statement = connection.createStatement();
            statement.execute(createUserTable);
        } 
		catch (SQLException e) 
		{
			System.err.println("Nao foi possivel criar as tabelas do banco de dados.");
            System.err.println("O seguinte erro ocorreu: " + e.getMessage());
        }
	}
	
	private void createDBPath(String dbName) 
	{
		dbPath = "jdbc:sqlite:" + System.getProperty("user.dir") + dbName;
	}
	
	private void loadJDBCDrive() throws Exception
	{
        Class.forName("org.sqlite.JDBC");
	}
	
}
