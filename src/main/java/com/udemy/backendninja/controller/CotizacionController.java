package com.udemy.backendninja.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.backendninja.model.ClientesModel;
import com.udemy.backendninja.model.ProductosModel;
import com.udemy.backendninja.servicios.ClientesServicio;
import com.udemy.backendninja.servicios.ProductosServicio;
import com.udemy.backendninja.serviciosImpl.ProcedimientosAuxiliares;

@Controller
public class CotizacionController {
	private static final String LSITAMODELPRODUCTO = "PROD" + (UUID.randomUUID());
	private static final String LSITAMODELPRODUCTO2 = "PROD" + (UUID.randomUUID());
	@Autowired
	@Qualifier("productosServicioImpl")
	private ProductosServicio productosServicio;

	@Autowired
	@Qualifier("clientesServicioImpl")
	private ClientesServicio clientesServicio;

	@GetMapping("/cotizacionCliente")
	public String showCoti(HttpServletRequest request, HttpServletResponse response, Model model,
			Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		HttpSession session = request.getSession();
		session.setAttribute(LSITAMODELPRODUCTO, productosServicio.listAllProductos());
		session.setAttribute(LSITAMODELPRODUCTO2, new ArrayList<ProductosModel>());
		return "/view/cotizacionCliente";
	}

	@RequestMapping("/obtenerListaProductos")
	public void listProductos(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			List<ProductosModel> list = productosServicio.listAllProductos();
			StringBuilder sb = tablaListaTodos(list);
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public StringBuilder tablaListaTodos(List<ProductosModel> list) {
		StringBuilder sb = new StringBuilder();// tablaMateriaPrima
		sb.append("<table id=\"tablaProductos\" class=\"table table-hover table-striped bdt\" >");
		sb.append("<tr><th>C&oacute;digo</th>");
		sb.append("<th>Nombre</th>");
		sb.append("<th>Precio</th>");
		sb.append("<th>Acci&oacute;n</th></tr>");
		for (ProductosModel producto : list) {
			sb.append("<tr>");
			sb.append("<td>" + producto.getCodprod() + "</td>");
			sb.append("<td>" + producto.getNombreprod() + "</td>");
			sb.append("<td>" + producto.getPrecio() + "</td>");
			sb.append("<td><button type=\"button\" onclick=\"openModalProducto('" + producto.getCodprod()
					+ "')\" style=\"border-style:solid; border-width:1px; border-color:#2C1CD9; text-align:center;\"  class=\"btn btn-default btn-sm\"><i  class=\"fas fa-plus\"></i></i></button></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}

	public StringBuilder tablaListaSeleccionados(List<ProductosModel> list) {
		StringBuilder sb = new StringBuilder();// tablaMateriaPrima
		sb.append("<table id=\"tablaProductos\" class=\"table table-hover table-striped bdt\" >");
		sb.append("<tr><th>C&oacute;digo</th>");
		sb.append("<th>Nombre</th>");
		sb.append("<th>Precio</th>");
		sb.append("<th>Cantidad</th>");
		for (ProductosModel producto : list) {
			sb.append("<tr>");
			sb.append("<td>" + producto.getCodprod() + "</td>");
			sb.append("<td>" + producto.getNombreprod() + "</td>");
			sb.append("<td>" + producto.getPrecio() + "</td>");
			sb.append("<td>" + producto.getCantidad() + "</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}

	public double calcularSumaTotales(List<ProductosModel> list) {
		double totalAPagar = 0;
		for (ProductosModel mp : list) {
			totalAPagar += (mp.getCantidad() * mp.getPrecio().doubleValue());
		}
		return totalAPagar;

	}

	@RequestMapping("/agregarCantAProducto")
	public void agregarCantAProducto(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<ProductosModel> listaTodos = (List<ProductosModel>) session.getAttribute(LSITAMODELPRODUCTO);
		List<ProductosModel> listaSeleccionados = (List<ProductosModel>) session.getAttribute(LSITAMODELPRODUCTO2);
		String codProducto = request.getParameter("codProd");
		int cantProd = Integer.parseInt(request.getParameter("cantProd"));
		ProductosModel pm = productosServicio.findByCodprod(codProducto);
		pm.setCantidad(cantProd);
		
		for (ProductosModel prod : listaSeleccionados) {
			if (prod.getCodprod().equals(pm.getCodprod())) {
				listaTodos.remove(pm);
			}
			
		}
		listaSeleccionados.add(pm);
		/**/
		session.removeAttribute(LSITAMODELPRODUCTO);
		session.removeAttribute(LSITAMODELPRODUCTO2);
		/* Re-enviamos las listas a la sesion */
		session.setAttribute(LSITAMODELPRODUCTO, listaTodos);
		session.setAttribute(LSITAMODELPRODUCTO2, listaSeleccionados);
		StringBuilder sbTodos = tablaListaTodos(listaTodos);
		StringBuilder sbSeleccionados = tablaListaSeleccionados(listaSeleccionados);
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

	@RequestMapping("/agregarCotizacion")
	public void agregarCotizacion(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String nombre = request.getParameter("nombreCliente");
		String appat = request.getParameter("apPaterno");
		String apmat = request.getParameter("apMaterno");
		String correo = request.getParameter("correoCliente");
		String telefono = request.getParameter("telfCliente");
		String codigo = request.getParameter("codCliente");
		PrintWriter out;
		String mensajeSalida = "";
		HttpSession session = request.getSession();
		List<ProductosModel> listaSeleccionados = (List<ProductosModel>) session.getAttribute(LSITAMODELPRODUCTO2);
		ProcedimientosAuxiliares pa = new ProcedimientosAuxiliares();
		int valorSecuencia = (int) (100000 * Math.random());
		ClientesModel cm = new ClientesModel();
		cm.setApmatcliente(apmat);
		cm.setAppatcliente(appat);
		cm.setCodcliente(codigo);
		cm.setCorreocliente(correo);
		cm.setRazonsocialcliente(nombre + " " + appat + " " + apmat);
		cm.setRfccliente(UUID.randomUUID().toString().substring(1, 10));
		cm.setTelefonocliente(telefono);
		cm.setUbigeocliente("010101");
		cm.setNombrecliente(nombre);
		clientesServicio.addClientes(cm);
		session.setAttribute("msj", "1");
		String resultado = pa.insertarCotizacionTemp(String.valueOf(valorSecuencia), codigo,
				calcularSumaTotales(listaSeleccionados));
		if (resultado == null) {
			for (ProductosModel pm : listaSeleccionados) {
				pa.insertarCotizacionTempDet(String.valueOf(valorSecuencia), pm.getCodprod(), pm.getCantidad(),
						pm.getPrecio().doubleValue());
			}
			try {
				mensajeSalida = "Se creo la cotización N°" + valorSecuencia + "  ";
				mensajeSalida += "<a href=\"/imprimeReporte?codigoDocumento=" + valorSecuencia
						+ "\"><strong>(Imprimir)</strong></a>";
				out = response.getWriter();
				out.println(mensajeSalida.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
