package com.udemy.backendninja.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.backendninja.entity.Clientes;
import com.udemy.backendninja.model.ClientesModel;
import com.udemy.backendninja.model.MateriaPrimaModel;
import com.udemy.backendninja.servicios.ClientesServicio;

@Controller
public class ClienteController {
	@Autowired ClientesServicio clientesServicio;
	
	@GetMapping("/mantCliente")
	public String mantenimientoCliente(Model model, Authentication authentication) {
		model.addAttribute("nombreUsuario",authentication.getName());
		model.addAttribute("cliente", new ClientesModel());
		return "view/mantCliente";
	}
	
	
	@GetMapping("/addCliente")
	public String addCliente(Model model, @RequestParam(name = "msj", required = false) String msj,
			@ModelAttribute("cliente") ClientesModel cliente, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("operacion") String op, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		
		cliente.setRazonsocialcliente(cliente.getNombrecliente() + " " + cliente.getAppatcliente() + " " + cliente.getApmatcliente());
		cliente.setRfccliente(UUID.randomUUID().toString().substring(1, 10));
		cliente.setUbigeocliente("010101");
		String redirect = "";
		if ("create".equals(op)) {
			int id = (int) (10000 * Math.random());
			cliente.setIdCliente(id);
			Clientes cl=clientesServicio.addClientes(cliente);
			if (cl == null) {
				model.addAttribute("msj", "Error");
			} else {
				model.addAttribute("nombreCliente", cliente.toString());
				model.addAttribute("msj", "Agregar");
			}
			redirect = "/view/mantCliente";
		} else if ("update".equals(op)) {
			ClientesModel cm = clientesServicio.findClienteByCod(cliente.getCodcliente());
			cliente.setIdCliente(cm.getIdCliente());
			clientesServicio.addClientes(cliente);
			model.addAttribute("nombreCliente", cliente.toString());
			model.addAttribute("msj", "Modificar");
			redirect = "/view/mantCliente";
		} else {
		}
		return redirect;
	}

	@PostMapping("/deleteCliente")
	public String deleteCliente(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		String codigoMP = request.getParameter("codigoC");
		ClientesModel cliente = clientesServicio.findClienteByCod(codigoMP);
		clientesServicio.removeCliente(cliente.getCodcliente());
		return "redirect:/mantCliente";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/obtenerDatosCliente")
	public void obtenerDatosCliente(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String codigoC = request.getParameter("codigoC");
			ClientesModel cliente = clientesServicio.findClienteByCod(codigoC);
			/*
			 * JSONObject jObj = new JSONObject();
			 * jObj.put("codigo",materiaprima.getCodmatprima());
			 */
			out.print(cliente.getCodcliente() + "@|" + cliente.getNombrecliente() + "@|"
					+ cliente.getAppatcliente()+ "@|" + cliente.getApmatcliente() + "@|"
					+ cliente.getTelefonocliente() + "@|" + cliente.getCorreocliente());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/obtenerListaCliente")
	public void listaClienteTabla(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<ClientesModel> list = clientesServicio.findAllClientes();
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();// tablaMateriaPrima
			sb.append("<table id=\"c_tablaCliente\" class=\"table table-default\" >");
			sb.append("<tr><th>C&oacute;digo</th>");
			sb.append("<th>Nombres y apellidos</th>");
			sb.append("<th>Telefono</th>");
			sb.append("<th>Correo</th>");
			sb.append("<th>Acci&oacute;n</th></tr>");

			for (ClientesModel cm : list) {
				sb.append("<tr>");
				sb.append("<td>" + cm.getCodcliente() + "</td>");
				sb.append("<td>" + cm.toString() + "</td>");
				sb.append("<td>" + cm.getTelefonocliente() + "</td>");
				sb.append("<td>" + cm.getCorreocliente() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarCliente('" + cm.getCodcliente()
						+ "', '"+cm.toString()+"')\" class=\"btn btn-primary btn-block btn-sm\">Eliminar</button></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	@RequestMapping("/obtenerClienteEnTabla")
	public void obtenerClienteEnTabla(HttpServletRequest request, HttpServletResponse response) {
		String codigoCliente = request.getParameter("codigo");
		ClientesModel cliente = clientesServicio.findClienteByCod(codigoCliente);
		try {

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();// tablaMateriaPrima
			if (cliente != null) {
				sb.append("<table id=\"c_tablaCliente\" class=\"table table-default\" >");
				sb.append("<tr><th>C&oacute;digo</th>");
				sb.append("<th>Nombres y apellidos</th>");
				sb.append("<th>Telefono</th>");
				sb.append("<th>Correo</th>");
				sb.append("<th>Acci&oacute;n</th></tr>");
				sb.append("<tr>");
				sb.append("<td>" + cliente.getCodcliente() + "</td>");
				sb.append("<td>" + cliente.toString() + "</td>");
				sb.append("<td>" + cliente.getTelefonocliente() + "</td>");
				sb.append("<td>" + cliente.getCorreocliente() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarCliente('" + cliente.getCodcliente()
				sb.append("<td><button type=\"button\" onclick=\"eliminarMateriaPrima('" + cliente.getCodmatprima()
						+ "')\" class=\"btn btn-primary btn-block btn-sm\">Eliminar</button></td>");
				sb.append("</tr>");

				sb.append("</table>");
			} else {
				sb.append(
						"<div class=\"alert alert-info\"><strong>Lo sentimos...!</strong> No se encontraron resultados.</div>");
			}
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
