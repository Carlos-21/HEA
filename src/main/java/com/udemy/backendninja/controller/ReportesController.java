package com.udemy.backendninja.controller;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.backendninja.conexion.conexion;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.servicios.FacturaServicio;
import com.udemy.backendninja.serviciosImpl.ProcedimientosAuxiliares;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ReportesController {

	
	@Autowired
	FacturaServicio facturaServicio;
	
	@RequestMapping("imprimeReporte")
	public void imprimirReporte(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ByteArrayOutputStream baos = null;
		ServletOutputStream out = null;
		Connection cn = null;
		try {
			//Parametros
			String codigoDocumento = request.getParameter("codigoDocumento");
			HashMap<Integer,String> coti = new ProcedimientosAuxiliares().obtenerDatosCoti(codigoDocumento);
			//
			
			HashMap<String,Object> params = new HashMap<String,Object>();
			
			params.put("P_CODDOCU", coti.get(1));
			params.put("P_NOMBRECLIENTE", coti.get(2));
			params.put("P_TOTALPAGAR", coti.get(3));
			params.put("P_APPATCLIENTEU", coti.get(5));
			params.put("P_APMATCLIENTE", coti.get(6));
			params.put("P_CORREOCLIENTE", coti.get(7));
			params.put("P_TELEFONOCLIENTE", coti.get(8));
			cn = conexion.getConexion();
			String jasper ="C:\\Users\\pc\\Documents\\workspace-sts-3.9.5.RELEASE\\SISHEA V5\\SISHEA\\Reportes/" + "OrdenCompra.jasper";
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, params, cn);
			String filename;
			baos = new ByteArrayOutputStream();
			response.setContentType("application/pdf");
			filename = "Cotizacion"+coti.get(1)+".pdf";
			response.setHeader("content-disposition", "inline; filename=\"" + filename + "\"");
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			response.setContentLength(baos.size());
			out = response.getOutputStream();
			baos.writeTo(out);
		} catch (Exception e) {
			//LOGGER.error("GP.ERROR: {}", e);
			throw e;
		}finally {
			baos.close();
			out.close();
		}
	}
	
	@RequestMapping("imprimeFactura")
	public void imprimeFactura(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ByteArrayOutputStream baos = null;
		ServletOutputStream out = null;
		Connection cn = null;
		try {
			Factura factura = facturaServicio.findByCodfactura(request.getParameter("codigoDocumento"));
			//Parametros
			HashMap<String,Object> params = new HashMap<String,Object>();
			String fecha ="20/11/2018";
			params.put("P_CODDOCU", factura.getCodfactura());
			params.put("P_NOMBRECLIENTE", factura.getClientes().getNombrecliente());
			params.put("P_TOTALPAGAR", factura.getCosto());
			params.put("P_APPATCLIENTEU", factura.getClientes().getAppatcliente());
			params.put("P_APMATCLIENTE", factura.getClientes().getApmatcliente());
			params.put("P_CORREOCLIENTE", factura.getClientes().getCorreocliente());
			params.put("P_TELEFONOCLIENTE", factura.getClientes().getTelefonocliente());
			params.put("p_origen", factura.getDireccionemision());
			params.put("p_dest", factura.getDirecciondestino());
			params.put("P_FECHA", fecha);
			params.put("P_COSTO", factura.getCosto());
			params.put("LOGO","C:\\Users\\pc\\Documents\\workspace-sts-3.9.5.RELEASE\\SISHEA V5\\SISHEA\\src\\main\\resources\\static\\images\\logo.png");//Varia la vaina esta
			cn = conexion.getConexion();
			String jasper ="C:\\Users\\pc\\Documents\\workspace-sts-3.9.5.RELEASE\\SISHEA V5\\SISHEA\\Reportes/" + "Factura.jasper";
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, params, cn);
			String filename;
			baos = new ByteArrayOutputStream();
			response.setContentType("application/pdf");
			filename = "Factura"+factura.getCodfactura()+".pdf";
			response.setHeader("content-disposition", "inline; filename=\"" + filename + "\"");
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			response.setContentLength(baos.size());
			out = response.getOutputStream();
			baos.writeTo(out);
		} catch (Exception e) {
			//LOGGER.error("GP.ERROR: {}", e);
			throw e;
		}
	}

}
