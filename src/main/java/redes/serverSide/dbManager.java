package redes.serverSide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import redes.User;

public class dbManager {
	private dbGenerator dbGenerator;
	private Connection connection;
	private String dbPath;

	public dbManager(String dbName) {
		createDBPath(dbName);

		dbGenerator = new dbGenerator();
		connection = null;
	}

	public void CreateAndPopulateDataBase() {
		OpenDataBaseConnection();

		dbGenerator.CreateTablesIfNotExists(connection);
		dbGenerator.PopulateTableIfEmpty(connection);
	}

	public boolean UserDataExistsInDataBase(User user) {
		try {
			boolean accountExists = itemExistInTable("Account", "Id", user.getAccount());
			boolean passwordExists = itemExistInTable("User", "Password", "'" + user.getPassword() + "'");

			return accountExists && passwordExists;
		} catch (SQLException e) {
			System.err.println("Nao foi possivel buscar os dados do usuario no Banco de Dados.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());

			return false;
		}
	}

	private void OpenDataBaseConnection() {
		try {
			System.out.println("Iniciando conexao com banco de dados...");
			loadJDBCDrive();

			// If database does not exist, it will be created automatically
			connection = DriverManager.getConnection(dbPath);

			System.out.println("O banco de dados foi conectado com sucesso!");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Não foi possivel carregar a classe do JDBC. O seguinte erro ocorreu: " + e.getMessage());
			System.exit(0);
		} catch (Exception e) {
			System.err.println("Um erro ocorreu ao abrir a conexão com o banco de dados.");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private void loadJDBCDrive() throws Exception {
		Class.forName("org.sqlite.JDBC");
	}

	private void createDBPath(String dbName) {
		dbPath = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + dbName;
	}

	private boolean itemExistInTable(String tableName, String criteriaFieldName, String valueToSearch)
			throws SQLException {
		String selectItemFromTable = String.format("SELECT * FROM %s WHERE %s = %s", tableName, criteriaFieldName,
				valueToSearch);

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(selectItemFromTable);

		return result.next();
	}
}
