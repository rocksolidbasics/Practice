package ldap;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreToLdif {

	private static String m_CountQuery = "SELECT count(*) FROM ";
	private static String m_SearchQuery = "SELECT * FROM ";
	
	public static void main(String[] args) throws IOException {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + args[0] + ":5432/postgres", "postgres", "password")) {

			System.out.println("Java JDBC PostgreSQL Example");
			System.out.println("Connected to PostgreSQL database!");
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(m_CountQuery + args[1]);
			while (resultSet.next()) {
				System.out.println("Total records to export: " + resultSet.getString("count") + "\n");
			}
			
			ResultSet dataSet = statement.executeQuery(m_SearchQuery + args[1]);
			ResultSetMetaData metaData = dataSet.getMetaData();
			for(int i=1; i<=metaData.getColumnCount(); i++) {
				System.out.print(metaData.getColumnLabel(i) + " | ");
			}
			
			System.out.println("\n\nRunning query => " + m_SearchQuery + args[1]);

			long i = 1;
			int fileCount = 1;
			
			while (dataSet.next()) {
				_appendToFile(dataSet);
				
				if(i == 10000) {
					_resetFile(fileCount++);
					i = 1;
				}
				
				i++;
			}

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	private static File _resetFile(int fileCount) throws IOException {
		File expFile = new File (".");
		System.out.println(expFile.getAbsolutePath());
		
		File newExportFile = new File(expFile.getAbsolutePath() + File.separator + "Ldif.export." + fileCount);
		newExportFile.createNewFile();
		
		return newExportFile;
	}

	private static void _appendToFile(ResultSet dataSet) {
		
	}

}