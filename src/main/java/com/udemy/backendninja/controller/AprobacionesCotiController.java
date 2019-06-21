package com.udemy.backendninja.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.backendninja.serviciosImpl.ProcedimientosAuxiliares;

@Controller
public class AprobacionesCotiController {

	public StringBuilder tablaListaSeleccionados(List<HashMap<Integer, String>> list) {
		StringBuilder sb = new StringBuilder();// tablaMateriaPrima
		sb.append("<table id=\"tablaProductos\" class=\"table table-hover table-striped bdt\" >");
		sb.append("<tr><th>C&oacute;digo</th>");
		sb.append("<th>Nombre</th>");
		sb.append("<th>Total</th>");
		sb.append("<td>Acciones</th>");
		for (HashMap<Integer, String> hm : list) {
			sb.append("<tr>");
			sb.append("<td>" + hm.get(1) + "</td>");
			sb.append("<td>" + hm.get(2) + "</td>");
			sb.append("<td>" + hm.get(3) + "</td>");
			sb.append("<td><button type=\"button\" onclick=\"openModalCotizacion('" + hm.get(1)
			+ "')\" style=\"border-style:solid; border-width:1px; border-color:#2C1CD9; text-align:center;\"  class=\"btn btn-default btn-sm\"><i  class=\"fas fa-plus\"></i></i></button></td>");
	
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}
	
	public StringBuilder tablaListaSeleccionadosDet(List<HashMap<Integer, String>> list) {
		StringBuilder sb = new StringBuilder();// tablaMateriaPrima
		sb.append("<table id=\"tablaProductos\" class=\"table table-hover table-striped bdt\" >");
		sb.append("<tr><th>C&oacute;digo</th>");
		sb.append("<th>Producto</th>");
		sb.append("<th>Nombre&nbsp;Producto</th>");
		sb.append("<th>Precio</th>");
		for (HashMap<Integer, String> hm : list) {
			sb.append("<tr>");
			sb.append("<td>" + hm.get(1) + "</td>");
			sb.append("<td>" + hm.get(2) + "</td>");
			sb.append("<td>" + hm.get(3) + "</td>");
			sb.append("<td>" + hm.get(4) + "</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}
	
	@GetMapping("/aprobacionCoti")
	public String aprobacionCoti(Model model, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		return "view/aprobacionCoti";
	}

	@RequestMapping("/gralCotizacion")
	public void obtenerGralCoti(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		ProcedimientosAuxiliares pa = new ProcedimientosAuxiliares();
		String codigo = request.getParameter("codigo");
		List<HashMap<Integer,String>> list = pa.obtenerDetDatosCoti(codigo);
		StringBuilder sb  = tablaListaSeleccionadosDet(list);
		HashMap<Integer,String> is2 = pa.obtenerDatosCoti(codigo);
		try {
			PrintWriter out = response.getWriter();
			out.print(sb.toString()+is2.get(0)+"@|"+is2.get(1)+"@|"+is2.get(2)+"@|"+is2.get(3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/obtenerListaCoti")
	public void obtenerListaCoti(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		List<HashMap<Integer,String>> list = new ProcedimientosAuxiliares().obtenerTodDatosCoti();
		StringBuilder datos = tablaListaSeleccionados(list);
		try {
			PrintWriter out = response.getWriter();
			out.print(datos.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/aprobarCoti")
	public void aprobarCoti(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		String codigo = request.getParameter("codigo");
		ProcedimientosAuxiliares pa = new ProcedimientosAuxiliares();
		pa.aprobarCoti(codigo);
	}

}
