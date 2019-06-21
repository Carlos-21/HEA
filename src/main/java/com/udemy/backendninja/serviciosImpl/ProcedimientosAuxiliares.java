package com.udemy.backendninja.serviciosImpl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
import com.udemy.backendninja.conexion.conexion;

import oracle.jdbc.OracleTypes;

public class ProcedimientosAuxiliares {

	
	public int contarCotizaciones() {
		int cantidad=-999999;
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		try {
			cs = cn.prepareCall("{CALL SP_OBTENERSECUENCIA(?)}");
			cs.registerOutParameter(1, OracleTypes.NUMBER);
			cs.execute();
			cantidad = (int) cs.getObject(1);
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return cantidad;
	}
	
	public String insertarCotizacionTemp(String coddocumeto, String doccliente,double totalpagar) {
		String mensajeSalida = "";
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		try {
			cs = cn.prepareCall("{CALL sp_insertaTempCoti(?,?,?,?)}");
			cs.setString(1, coddocumeto);
			cs.setString(2, doccliente);
			cs.setDouble(3,totalpagar);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.execute();
			mensajeSalida = (String) cs.getObject(4);
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return mensajeSalida;
	}
	
	public String insertarCotizacionTempDet(String coddocumeto, String codprod,int cantidad,double precioprod) {
		String mensajeSalida = "";
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		try {
			cs = cn.prepareCall("{CALL sp_insertaTempCotiDet(?,?,?,?,?)}");
			cs.setString(1, coddocumeto);
			cs.setString(2, codprod);
			cs.setInt(3,cantidad);
			cs.setDouble(4, precioprod);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.execute();
			mensajeSalida = (String) cs.getObject(5);
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return mensajeSalida;
	}
	
	public HashMap<Integer, String> obtenerDatosCoti(String coddocumeto) {
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		ResultSet rs;
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		try {
			cs = cn.prepareCall("{CALL SP_OBTENER_DATOS_COTI(?,?)}");
			cs.setString(1, coddocumeto);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			
			while(rs.next()) {
				
				map.put(1, rs.getString("CODDOCUMENTO"));
				map.put(2, rs.getString("NOMBRECLIENTE"));
				map.put(3,rs.getString("TOTALPAGAR"));
				map.put(4, rs.getString("ESTADO"));
				map.put(5, rs.getString("APPATCLIENTE"));
				map.put(6, rs.getString("APMATCLIENTE"));
				map.put(7, rs.getString("CORREOCLIENTE"));
				map.put(8, rs.getString("TELEFONOCLIENTE"));
			}
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return map;
	}
	
	public List<HashMap<Integer, String>> obtenerTodDatosCoti() {
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		ResultSet rs;
		List<HashMap<Integer, String>> list = new  ArrayList<HashMap<Integer,String>>();
		try {
			cs = cn.prepareCall("{CALL SP_OBTENER_TODDATOS_COTI(?)}");
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(1);
			
			while(rs.next()) {
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, rs.getString("CODDOCUMENTO"));
				map.put(2, rs.getString("NOMBRECLIENTE"));
				map.put(3,rs.getString("TOTALPAGAR"));
				map.put(4, rs.getString("ESTADO"));
				list.add((HashMap<Integer, String>) map);
			}
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return list;
	}

	
	public List<HashMap<Integer, String>> obtenerDetDatosCoti(String numDocu) {
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		ResultSet rs;
		List<HashMap<Integer, String>> list = new  ArrayList<HashMap<Integer,String>>();
		try {
			cs = cn.prepareCall("{CALL SP_OBTENER_DET_COTI(?,?)}");
			cs.setString(1, numDocu);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			
			while(rs.next()) {
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, rs.getString("CODDOCUMENTO"));
				map.put(2, rs.getString("codprod"));
				map.put(3,rs.getString("nombreprod"));
				map.put(4, rs.getString("precioprod"));
				list.add((HashMap<Integer, String>) map);
			}
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return list;
	}
	
	public HashMap<Integer, String> obtenerDatosCli(String codCli) {
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		ResultSet rs;
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		try {
			cs = cn.prepareCall("{CALL SP_OBTENER_DATOS_CLI(?,?)}");
			cs.setString(1, codCli);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			
			if(rs.next()) {
				
				map.put(1, rs.getString("codcliente"));
				map.put(2, rs.getString("nombrecliente"));
				map.put(3,rs.getString("appatcliente"));
				map.put(4, rs.getString("apmatcliente"));
				map.put(5, rs.getString("telefonocliente"));
				map.put(6, rs.getString("correocliente"));
				
			}
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return map;
	}
	
	public String aprobarCoti(String coddocumeto) {
		String mensajeSalida = "";
		Connection cn = conexion.getConexion();
		CallableStatement cs ;
		try {
			cs = cn.prepareCall("{CALL sp_aprobarcot(?)}");
			cs.setString(1, coddocumeto);
			cs.executeUpdate();
		}catch(Exception ex) {
			System.out.println("ERROR"+ex);
		}
		return mensajeSalida;
	}
	

}
