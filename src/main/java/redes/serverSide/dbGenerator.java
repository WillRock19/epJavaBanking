package redes.serverSide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import redes.PasswordManager;

public class dbGenerator 
{
	private PasswordManager passwordManager;
	
	public dbGenerator()
	{
		passwordManager = new PasswordManager();
	}
	
	public void CreateTablesIfNotExists(Connection connection) 
	{
		String createAccountTable = createAccountTableStatement();
		String createUserTable = createUserTableStatement();
		
		try
		{
			System.out.println("Criando tabelas...");
			
			if(connection == null) 
			{
				System.err.println("Impossivel de criar as tabelas. Nao ha conexao com o banco.");
				return;
			}
			
			Statement statement = connection.createStatement();
            
			statement.execute(createAccountTable);
			statement.execute(createUserTable);
			
			System.out.println("Tabelas criadas com sucesso!");
        } 
		catch (SQLException e) 
		{
			System.err.println("Nao foi possivel criar as tabelas do banco de dados.");
            System.err.println("O seguinte erro ocorreu: " + e.getMessage());
            System.exit(0);
        }
	}
	
	public void PopulateTableIfEmpty(Connection connection)
	{
		try
		{
			System.out.println("Populando Banco de dados...");
			
			if(connection == null) 
			{
				System.err.println("Impossivel de criar as tabelas. Nao ha conexao com o banco.");
				return;
			}
			
			Statement statement = connection.createStatement();
            
			if(!accountTableHasValues(statement)) 
				createTwoDefaultAccounts(connection);
			
			if(!userTableHasValues(statement)) 
				createTwoDefaultUsers(connection);
			
			System.out.println("Banco populado com sucesso!");
        } 
		catch (SQLException e) 
		{
			System.err.println("Nao foi possivel popular o Banco de Dados.");
            System.err.println("O seguinte erro ocorreu: " + e.getMessage());
            System.exit(0);
        }
	}
	
	private String createUserTableStatement() 
	{
		return "CREATE TABLE IF NOT EXISTS User (\n"
			     + "Id integer PRIMARY KEY, \n"
			     + "UserName text NOT NULL, \n"
			     + "Password text NOT NULL, \n"
			     + "AccountId integer NOT NULL, \n"
			     + "Adress text NOT NULL,       \n"
			     + "Birth_Date DATE NOT NULL,   \n"
			     + "Age tinyint NOT NULL,  		\n "
			     + "FOREIGN KEY (AccountId) REFERENCES Account(Id)"
			     + ");";
	}
	
	private String createAccountTableStatement() 
	{
		return "CREATE TABLE IF NOT EXISTS Account (\n"
					+ "Id integer PRIMARY KEY ,  \n"
					+ "Open_Date DATE NOT NULL, \n"
					+ "Ammount_Money double NOT NULL \n"
				    + ");";
	}

	private String createUserInsertStatement() 
	{
		return "INSERT INTO User(Id, UserName, Password, AccountId, Adress, Birth_Date, Age) VALUES(?,?,?,?,?,?,?)";	
	}
	
	private String createAccountInsertStatement() 
	{
		return "INSERT INTO Account(Id, Open_Date, Ammount_Money) VALUES(?,?,?)";	
	}
	
	private boolean userTableHasValues(Statement statement) throws SQLException
	{
		return tableHasRows(statement,  "User");
	}
	
	private boolean accountTableHasValues(Statement statement) throws SQLException
	{
		return tableHasRows(statement, "Account");
	}
	
	private boolean tableHasRows(Statement statement, String tableName) throws SQLException
	{
		String selectAllUser = "SELECT * FROM " + tableName;
		ResultSet result = statement.executeQuery(selectAllUser);
		
		return result.next();
	}
	
	private void createTwoDefaultAccounts(Connection connection) throws SQLException
	{	
		String accountDefaultData[][] = new String[][] 
		{ 
			{ "22-04-2015 08:40:00", "4025.56" }, 
			{ "19-01-2016 12:30:56", "800.00", } 
		};
		
		for(int index = 0; index < 2; index++) 
		{
			String sql = createAccountInsertStatement();
			PreparedStatement prepared = connection.prepareStatement(sql);
			
			String dateInString = accountDefaultData[index][0];
			
			prepared.setInt(1, index+1);
			prepared.setDate(2, DateToInsert(dateInString));
			prepared.setDouble(3, Double.parseDouble(accountDefaultData[index][1]));
			
			prepared.executeUpdate();
		}
	}

	private void createTwoDefaultUsers(Connection connection) throws SQLException
	{
		String userDefaultData[][] = new String[][] 
		{ 
			{ "Paulo Roberto Silva Freitas", "19-01-1999 00:00:00"}, 
			{ "Marcos da Silva Teixeira", "19-06-2000 00:00:00"} 
		};
		
		for(int index = 0; index < 2; index++) 
		{
			String sql = createUserInsertStatement();
			PreparedStatement prepared = connection.prepareStatement(sql);
			
			prepared.setInt(1, index + 1);
			prepared.setString(2, userDefaultData[index][0]);
			
			String senha = "senha" + (index + 1);
			prepared.setString(3, passwordManager.EncodePassword(senha));
		
			prepared.setInt(4, index + 1);
			prepared.setString(5, "Rua Long Beach 34" + index);
			
			String dateInString = userDefaultData[index][1];
			
			prepared.setDate(6, DateToInsert(dateInString));
			prepared.setInt(7, 18 + index);
			
			prepared.executeUpdate();
		}
	}
	
	private java.sql.Date DateToInsert(String dateInString)
	{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			Date birth_date = sdf.parse(dateInString);
			
			return new java.sql.Date(birth_date.getTime());
		}
		catch(ParseException e) 
		{
			System.err.println("Nao foi possivel criar uma data para inserir no Banco de Dados.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
			
			return null;
		}
	}
}
