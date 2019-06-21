package com.udemy.backendninja.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.backendninja.entity.Inventario;
import com.udemy.backendninja.entity.Productos;
import com.udemy.backendninja.model.ProductosModel;
import com.udemy.backendninja.servicios.ProductosServicio;

@Controller
public class ProductosController {

	@Autowired
	@Qualifier("productosServicioImpl")
	private ProductosServicio productosServicio;

	@GetMapping("/mantProductos")
	public String showMantProductos(Model model, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		model.addAttribute("Productos", new Productos());
		return "view/mantProductos";
	}

	@PostMapping("/addProductos")
	public String addProductos(Model model, @RequestParam(name = "msj", required = false) String msj,
			@ModelAttribute("Productos") Productos productos, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("operacion") String op, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		Inventario inventario = new Inventario();
		inventario.setCodinventario("I100");
		inventario.setIdinventario(new BigDecimal(1));
		inventario.setCantidadproductoinicial(new BigDecimal(2));
		inventario.setCantidadproductofinal(new BigDecimal(3));
		inventario.setPrecioventaprod(new BigDecimal(4));
		inventario.setCantvendprod(new BigDecimal(5));
		inventario.setGananciaProducto(new BigDecimal(6));
		productos.setInventario(inventario);
		String redirect = "";
		if ("create".equals(op)) {
			int id = (int) (10000 * Math.random());
			BigDecimal bd = new BigDecimal(id);
			productos.setIdprod(bd);
			ProductosModel pm = productosServicio.addProductos(productosServicio.convertirProdEntidadAModel(productos));
			if (pm == null) {
				model.addAttribute("msj", "Error");
			} else {
				model.addAttribute("nombreProducto", productos.getNombreprod());
				model.addAttribute("msj", "Agregar");
			}
			redirect = "view/mantProductos";

		} else if ("update".equals(op)) {
			ProductosModel mp = productosServicio.findByCodprod(productos.getCodprod());
			productos.setIdprod(mp.getIdprod());
			productosServicio.addProductos(productosServicio.convertirProdEntidadAModel(productos));
			model.addAttribute("nombreProducto", productos.getNombreprod());
			model.addAttribute("msj", "Modificar");
			redirect = "view/mantProductos";
		} else {
		}
		return redirect;
	}

	@PostMapping("/deleteProducto")
	public String deleteProducto(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		String codigoMP = request.getParameter("codigoMP");
		// ProductosModel productos = productosServicio.findByCodprod(codigoMP);
		productosServicio.removeProductos(codigoMP);
		try {
			PrintWriter out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/mantProductos";
	}

	@RequestMapping("/obtenerListaProducto")
	public void obtenerListaProducto(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<ProductosModel> list = productosServicio.listAllProductos();
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();// tablaMateriaPrima
			sb.append("<table id=\"tablaProducto\" class=\"table table-default\" >");
			sb.append("<tr><th>C&oacute;digo</th>");
			sb.append("<th>Nombre</th>");
			sb.append("<th>Descripci&oacute;n</th>");
			sb.append("<th>Precio</th>");
			sb.append("<th>Acci&oacute;n</th></tr>");

			for (ProductosModel mp : list) {
				sb.append("<tr>");
				sb.append("<td>" + mp.getCodprod() + "</td>");
				sb.append("<td>" + mp.getNombreprod() + "</td>");
				sb.append("<td>" + mp.getDescripcionprod() + "</td>");
				sb.append("<td>" + mp.getPrecio() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarProducto('" + mp.getCodprod()
						+ "')\" class=\"btn btn-primary btn-block btn-sm\">Eliminar</button></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/obtenerDatosProductos")
	public void obtenerDatosMateriaPrima(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String codigoMP = request.getParameter("codigoMP");
			ProductosModel productos = productosServicio.findByCodprod(codigoMP);
			/*
			 * JSONObject jObj = new JSONObject();
			 * jObj.put("codigo",materiaprima.getCodmatprima());
			 */
			if(productos.getCodprod() != null) {
				out.print(productos.getCodprod() + "@|" + productos.getNombreprod() + "@|" + productos.getPrecio() + "@|"
						+ productos.getDescripcionprod());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/obtenerProductoEnTabla")
	public void obtenerProductoEnTabla(HttpServletRequest request, HttpServletResponse response) {
		String codigoProd = request.getParameter("codigo");
		ProductosModel mp = productosServicio.findByCodprod(codigoProd);
		try {

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			if (mp.getCodprod() != null) {
				sb.append("<table id=\"tablaProducto\" class=\"table table-default\" >");
				sb.append("<tr><th>C&oacute;digo</th>");
				sb.append("<th>Nombre</th>");
				sb.append("<th>Descripci&oacute;n</th>");
				sb.append("<th>Precio</th>");
				sb.append("<th>Acci&oacute;n</th></tr>");
				sb.append("<tr>");
				sb.append("<td>" + mp.getCodprod() + "</td>");
				sb.append("<td>" + mp.getNombreprod() + "</td>");
				sb.append("<td>" + mp.getDescripcionprod() + "</td>");
				sb.append("<td>" + mp.getPrecio() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarProducto('" + mp.getCodprod()
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
	}

}
