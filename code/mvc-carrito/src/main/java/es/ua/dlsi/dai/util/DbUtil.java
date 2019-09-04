package es.ua.dlsi.dai.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

	private static Connection connection = null;

	public static Connection getConnection() {
		if (connection != null)
			return connection;
		else {
			try {
				Properties prop = new Properties();
				InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
				prop.load(inputStream);
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				Class.forName(driver);
				connection = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ResultSet resultSet = null;
			DatabaseMetaData metadata;
			try {
				metadata = connection.getMetaData();

				resultSet = metadata.getTables(null, null, "PRODUCTOS", null);
				// Si la tabla 'productos' no existe, la creamos
				if (!resultSet.next()) {
					connection.createStatement().executeUpdate(
							"create table productos (productoid int identity,name varchar(100),quantity int)");
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return connection;
		}

	}
}
