package com.udemy.backendninja.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.backendninja.entity.Clientes;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.model.ClientesModel;
import com.udemy.backendninja.model.ProductosModel;
import com.udemy.backendninja.servicios.ClientesServicio;
import com.udemy.backendninja.servicios.DetFacturaServicio;
import com.udemy.backendninja.servicios.FacturaServicio;
import com.udemy.backendninja.servicios.ProductosServicio;

@Controller
public class FacturaController {

	@Autowired
	ProductosServicio prodServicio;
	@Autowired
	ClientesServicio cliServicio;
	@Autowired
	FacturaServicio facturaServicio;
	@Autowired
	DetFacturaServicio detFacturaServicio;

	private static final String LSITAMODELPRODUCTO = "PROD" + (UUID.randomUUID());
	private static final String LSITAMODELPRODUCTO2 = "PROD" + (UUID.randomUUID());

	@RequestMapping("/facturacion")
	public String factura(Model model, HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		HttpSession session = request.getSession();
		session.setAttribute(LSITAMODELPRODUCTO, prodServicio.listAllProductos());
		session.setAttribute(LSITAMODELPRODUCTO2, new ArrayList<ProductosModel>());
		return "view/facturacion";
	}

	@PostMapping("/agregarFactura") // Debería recibir todos los atributos de los input
	public void agregarOrdenCompra(Model model, HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		HttpSession session = request.getSession();
		String codigoFactura = String.valueOf((int) (100000 * Math.random()));
		Clientes cliente = new Clientes();
		String direccionOrig = request.getParameter("dirOrigen");
		String direccionDest = request.getParameter("dirDest");
		ClientesModel cliAux = cliServicio.findClienteByCod(request.getParameter("cotmatprima"));
		cliente.setApmatcliente(request.getParameter("apMaterno"));
		cliente.setAppatcliente(request.getParameter("apPaterno"));
		cliente.setCodcliente(request.getParameter("cotmatprima"));
		cliente.setNombrecliente(request.getParameter("nombreCliente"));
		cliente.setRfccliente(UUID.randomUUID().toString());
		cliente.setTelefonocliente(request.getParameter("telfCliente"));
		cliente.setCorreocliente(request.getParameter("correoCliente"));
		cliente.setRazonsocialcliente(cliAux.getRazonsocialcliente());
		cliente.setUbigeocliente("150101");
		@SuppressWarnings("unchecked")
		List<ProductosModel> listaSeleccionados = (List<ProductosModel>) session.getAttribute(LSITAMODELPRODUCTO2);

		Factura factura = facturaServicio.ingresarFactura(codigoFactura, cliente, direccionDest, direccionOrig,
				calcularSumaTotales(listaSeleccionados));

		for (ProductosModel mpm : listaSeleccionados) {
			detFacturaServicio.insertarFactura(factura, mpm.getCodprod(), mpm.getCantidad(),
					mpm.getPrecio().doubleValue());
		}
		String mensajeSalida = "";

		try {
			PrintWriter out = response.getWriter();
			mensajeSalida = "Se creo la factura Nro" + codigoFactura + "  ";
			mensajeSalida += "<a href=\"/imprimeFactura?codigoDocumento=" + codigoFactura
					+ "\"><strong>(Imprimir)</strong></a>";
			out = response.getWriter();
			out.println(mensajeSalida.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * @RequestMapping("/obtenerProveedores") public void
	 * obtenerProveedores(HttpServletRequest request, HttpServletResponse response)
	 * { try { PrintWriter out = response.getWriter(); List<ProveedoresModel> list =
	 * provServicio.enlistarProveedores();
	 * 
	 * StringBuilder sb=new StringBuilder(); sb.
	 * append("<select id=\"Proveedores\" class=\"form-control\" name=\"Proveedores\">"
	 * ); sb.append("<option selected disable>Elija su proveedor</option>"); for
	 * (ProveedoresModel pm : list) {
	 * sb.append("<option value=\""+pm.getCodProv()+"\">");
	 * sb.append(pm.getRazonSocial()); sb.append("</option>"); }
	 * sb.append("</select>");
	 * 
	 * 
	 * out.print(sb.toString()); } catch (IOException e) { e.printStackTrace(); } }
	 */

	@PostMapping("/agregarCantProd")
	public void agregarCantAProducto(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<ProductosModel> listaTodos = (List<ProductosModel>) session.getAttribute(LSITAMODELPRODUCTO);
		List<ProductosModel> listaSeleccionados = (List<ProductosModel>) session.getAttribute(LSITAMODELPRODUCTO2);
		String codProducto = request.getParameter("codProducto");
		int cantMatPrim = Integer.parseInt(request.getParameter("cantProducto"));
		ProductosModel pm = prodServicio.findByCodprod(codProducto);

		int aux = 0;
		for (ProductosModel producto : listaTodos) {
			if (producto.getCodprod().equals(codProducto)) {
				listaTodos.remove(aux);
				break;
			}
			aux++;
		}
		pm.setCantidad(cantMatPrim);
		listaSeleccionados.add(pm);

		session.removeAttribute(LSITAMODELPRODUCTO);
		session.removeAttribute(LSITAMODELPRODUCTO2);
		session.setAttribute(LSITAMODELPRODUCTO, listaTodos);
		session.setAttribute(LSITAMODELPRODUCTO2, listaSeleccionados);

		StringBuilder sbTodos = entablarTodos(listaTodos, "Todo");
		StringBuilder sbSeleccionados = entablarTodos(listaSeleccionados, "No Todo");
		System.out.println(sbTodos.toString());
		PrintWriter out;
		try {
			out = response.getWriter();
			String mensajeSalida = sbTodos.toString() + "@|" + sbSeleccionados.toString() + "@|"
					+ calcularSumaTotales(listaSeleccionados);
			out.write(mensajeSalida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/listarProductosFact")
	public void listProductos(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			List<ProductosModel> list = prodServicio.listAllProductos();
			StringBuilder sb = entablarTodos(list, "Todo");
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public StringBuilder entablarTodos(List<ProductosModel> list, String tipo) {
		String ID;
		boolean todo = tipo.equals("Todo");
		ID = (todo) ? "c_tablaMateriaPrimas" : "c_tablaMateriaPrimaSeleccionadas";

		StringBuilder sb = new StringBuilder();// tablaMateriaPrima
		sb.append("<table id=\"" + ID + "\" class=\"table table-hover table-striped bdt\" >");
		sb.append("<tr><th>C&oacute;digo</th>");
		sb.append("<th>Nombre</th>");
		sb.append("<th>Precio</th>");
		if (todo)
			sb.append("<th>Agregar</th></tr>");
		else
			sb.append("<th>Cantidad</th></tr>");
		for (ProductosModel materiaPrima : list) {
			sb.append("<tr>");
			sb.append("<td>" + materiaPrima.getCodprod() + "</td>");
			sb.append("<td>" + materiaPrima.getNombreprod() + "</td>");
			sb.append("<td>" + materiaPrima.getPrecio() + "</td>");
			if (todo)
				sb.append("<td><button type=\"button\" onclick=\"openModalMateriaPrima('" + materiaPrima.getCodprod()
						+ "')\" style=\"border-style:solid; border-width:1px; border-color:#2C1CD9; text-align:center;\"  class=\"btn btn-default btn-sm\"><i  class=\"fas fa-plus\"></i></i></button></td>");
			else
				sb.append("<td>" + materiaPrima.getCantidad() + "</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}

	@RequestMapping("/obtenerClientes")
	public void listaClienteTabla(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<ClientesModel> list = cliServicio.findAllClientes();
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();// tablaMateriaPrima
			sb.append("<table id=\"c_tablaCliente\" class=\"table table-default\" >");
			sb.append("<tr><th>C&oacute;digo</th>");
			sb.append("<th>Nombres y apellidos</th>");

			sb.append("<th>Acci&oacute;n</th></tr>");

			for (ClientesModel cm : list) {
				sb.append("<tr>");
				sb.append("<td>" + cm.getCodcliente() + "</td>");
				sb.append("<td>" + cm.toString() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"escogerCliente('" + cm.getCodcliente() + "', '"
						+ cm.toString() + "')\" class=\"btn btn-primary btn-block btn-sm\">Elegir</button></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/obtenerInfoCliente")
	public void obtenerDatosCliente(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String codigoC = request.getParameter("codigoCliente");
			ClientesModel cliente = cliServicio.findClienteByCod(codigoC);
			/*
			 * JSONObject jObj = new JSONObject();
			 * jObj.put("codigo",materiaprima.getCodmatprima());
			 */
			out.print(cliente.getCodcliente() + "@|" + cliente.getNombrecliente() + "@|" + cliente.getAppatcliente()
					+ "@|" + cliente.getApmatcliente() + "@|" + cliente.getTelefonocliente() + "@|"
					+ cliente.getCorreocliente());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long calcularSumaTotales(List<ProductosModel> listaSeleccionados) {
		long suma = 0;
		for (ProductosModel mpm : listaSeleccionados) {
			suma += mpm.getPrecio().doubleValue() * (int) mpm.getCantidad();
		}
		return suma;
	}
}
