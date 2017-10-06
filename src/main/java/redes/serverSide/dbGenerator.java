package redes.serverSide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

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
	    	 System.out.println("Iniciando conexao com banco de dados...");
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
		String createUserTable = createUserTableStatement();
		String createAccountTable = createAccountTableStatement();
		
		try
		{
			if(connection == null)
				OpenDataBaseConnection();
			
			Statement statement = connection.createStatement();
            
			statement.execute(createUserTable);
			statement.execute(createAccountTable);
        } 
		catch (SQLException e) 
		{
			System.err.println("Nao foi possivel criar as tabelas do banco de dados.");
            System.err.println("O seguinte erro ocorreu: " + e.getMessage());
        }
	}
	
	public void PopulateTableIfEmpty()
	{
		try
		{
			if(connection == null)
				OpenDataBaseConnection();
			
			Statement statement = connection.createStatement();
            
			if(!UserTableHasValues(statement)) 
			{
				String usuario1 = "Paulo Roberto Silva Freitas";
				String usuario2 = "Marcos da Silva Teixeira";
				
				for(int index = 0; index < 2; index++) 
				{
					String sql = createUserInsertStatement();
					PreparedStatement prepared = connection.prepareStatement(sql);
					
					prepared.setString(1, usuario1);
					prepared.setString(2, "senha1");
				
					prepared.setInt(3, index + 1);
					prepared.setString(4, "Rua Long Beach 34" + index);
					//prepared.setDate(5, new Date());
				}
				
				
				
			}
			
			if(!AccountTableHasValues(statement)) 
			{
				String sql = createAccountInsertStatement();
			}
        } 
		catch (SQLException e) 
		{
			System.err.println("Nao foi possivel popular o Banco de Dados.");
            System.err.println("O seguinte erro ocorreu: " + e.getMessage());
        }
	}
	
	private void createDBPath(String dbName) 
	{
		dbPath = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + dbName;
	}
	
	private void loadJDBCDrive() throws Exception
	{
        Class.forName("org.sqlite.JDBC");
	}

	
	private String createUserTableStatement() 
	{
		return "CREATE TABLE IF NOT EXISTS User(\n"
			     + "Id integer NOT NULL AUTO_INCREMENT, \n"
			     + "UserName text NOT NULL, \n"
			     + "Password text NOT NULL, \n"
			     + "AccountId integer NOT NULL, \n"
			     + "Adress text NOT NULL,       \n"
			     + "Birth_Date DATE NOT NULL,   \n"
			     + "Age tinyint NOT NULL   		\n "
			     + "PRIMARY KEY (Id)"
			     + "FOREIGN KEY (AccountId) REFERENCES Account(Id)"
			     + ")";
	}
	
	private String createAccountTableStatement() 
	{
		return "CREATE TABLE IF NOT EXISTS Account(\n"
					+ "Id integer  NOT NULL AUTO_INCREMENT,  \n"
					+ "Open_Date DATE NOT NULL, \n"
					+ "Ammount_Money integer NOT NULL, \n"
					+ "PRIMARY KEY (Id)"
				    + ")";
	}

	private String createUserInsertStatement() 
	{
		return "INSERT INTO Usuario(UserName, Password, AccountId, Adress text, Birth_Date, Age) VALUES(?,?,?,?,?,?)";	
	}
	
	private String createAccountInsertStatement() 
	{
		return "INSERT INTO Account(Open_Date, Ammount_Money) VALUES(?,?)";	
	}
	
	private boolean UserTableHasValues(Statement statement) throws SQLException
	{
		return TableHasRows(statement,  "User");
	}
	
	private boolean AccountTableHasValues(Statement statement) throws SQLException
	{
		return TableHasRows(statement, "Account");
	}
	
	private boolean TableHasRows(Statement statement, String tableName) throws SQLException
	{
		String selectAllUser = "SELECT * FROM " + tableName;
		ResultSet result = statement.executeQuery(selectAllUser);
		
		return result.next();
	}


}
