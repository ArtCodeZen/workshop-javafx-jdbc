package db;

import java.sql.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	private static Connection connection = null;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Properties propsProperties = loadProperties();
				String urlString = propsProperties.getProperty("dburl");
				connection = DriverManager.getConnection(urlString, propsProperties);

			} catch (SQLException e) {
				// TODO: handle exception
				throw new DbException(e.getMessage());
			}

		}
		return connection;
	}

	public static void closeConnetion(){
		if (connection != null) {
			try {
				connection.close();

			} catch (SQLException e) {
				// TODO: handle exception
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fStream = new FileInputStream("db.properties")) {
			Properties propsProperties = new Properties();
			propsProperties.load(fStream);
			return propsProperties;
		} catch (IOException e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		}

	}
	public static void closeStatements(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
