package com.udemy.backendninja.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class conexion {

	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static Connection cn;
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static String user = "Empresa";
	static String password = "proy_taller";
	static String driver = "oracle.jdbc.driver.OracleDriver";

	public static Connection getConexion() {
		if (cn == null) {
			try {
				Class.forName(driver);
				cn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException | SQLException e) {
				LOGGER.info("ERROR{" + e + "}");
			}
		}
		return cn;
	}
	public static void cerrar() {
		if (cn != null) {
			try {
				cn.close();
			} catch (SQLException e) {
				LOGGER.info("ERROR{" + e + "}");
				e.printStackTrace();
			}
		}
	}
}
