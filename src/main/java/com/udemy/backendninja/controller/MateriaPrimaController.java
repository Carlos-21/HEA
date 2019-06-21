package com.udemy.backendninja.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.udemy.backendninja.model.MateriaPrimaModel;
import com.udemy.backendninja.servicios.MateriaPrimaServicio;

@Controller
public class MateriaPrimaController {

	@Autowired
	@Qualifier("materiaPrimaServiceImpl")
	private MateriaPrimaServicio materiaPrimaServicio;

	@GetMapping("/mantMateriaPrima")
	public String showMantProducto(Model model, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		model.addAttribute("MateriaPrima", new MateriaPrimaModel());
		return "view/mantMateriaPrima";
	}

	@GetMapping("/addMateriaPrima")
	public String addMateriaPrima(Model model, @RequestParam(name = "msj", required = false) String msj,
			@ModelAttribute("MateriaPrima") MateriaPrimaModel materiaprima, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("operacion") String op, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		String redirect = "";
		if ("create".equals(op)) {
			int id = (int) (10000 * Math.random());
			materiaprima.setIdmatprim(id);
			MateriaPrimaModel mp = materiaPrimaServicio.addMateriaPrima(materiaprima);
			if (mp == null) {
				model.addAttribute("msj", "Error");
			} else {
				model.addAttribute("nombreInsumo", materiaprima.getNombrematprima());
				model.addAttribute("msj", "Agregar");
			}
			redirect = "/view/mantMateriaPrima";
		} else if ("update".equals(op)) {
			MateriaPrimaModel mp = materiaPrimaServicio.findByCodmatprima(materiaprima.getCodmatprima());
			materiaprima.setIdmatprim(mp.getIdmatprim());
			materiaPrimaServicio.addMateriaPrima(materiaprima);
			model.addAttribute("nombreInsumo", materiaprima.getNombrematprima());
			model.addAttribute("msj", "Modificar");
			redirect = "/view/mantMateriaPrima";
		} else {
		}
		return redirect;
	}

	@PostMapping("/deleteMateriaPrima")
	public String deleteMateriaPrima(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		model.addAttribute("nombreUsuario", authentication.getName());
		String codigoMP = request.getParameter("codigoMP");
		MateriaPrimaModel materiaprima = materiaPrimaServicio.findByCodmatprima(codigoMP);
		materiaPrimaServicio.removeMateriaPrima(materiaprima);
		return "redirect:/mantMateriaPrima";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/obtenerDatosMateriaPrima")
	public void obtenerDatosMateriaPrima(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String codigoMP = request.getParameter("codigoMP");
			MateriaPrimaModel materiaprima = materiaPrimaServicio.findByCodmatprima(codigoMP);
			/*
			 * JSONObject jObj = new JSONObject();
			 * jObj.put("codigo",materiaprima.getCodmatprima());
			 */
			out.print(materiaprima.getCodmatprima() + "@|" + materiaprima.getNombrematprima() + "@|"
					+ materiaprima.getPreciomatprima() + "@|" + materiaprima.getCantidadmatprima() + "@|"
					+ materiaprima.getIdcategory() + "@|" + materiaprima.getDescmatprima());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/obtenerListaMateriaPrima")
	public void listaMateriaPrimaTabla(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<MateriaPrimaModel> list = materiaPrimaServicio.listAllMateriaPrima();
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();// tablaMateriaPrima
			sb.append("<table id=\"tablaMateriaPrima\" class=\"table table-default\" >");
			sb.append("<tr><th>C&oacute;digo</th>");
			sb.append("<th>Nombre</th>");
			sb.append("<th>Descripci&oacute;n</th>");
			sb.append("<th>Precio</th>");
			sb.append("<th>Acci&oacute;n</th></tr>");

			for (MateriaPrimaModel mp : list) {
				sb.append("<tr>");
				sb.append("<td>" + mp.getCodmatprima() + "</td>");
				sb.append("<td>" + mp.getNombrematprima() + "</td>");
				sb.append("<td>" + mp.getDescmatprima() + "</td>");
				sb.append("<td>" + mp.getPreciomatprima() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarMateriaPrima('" + mp.getCodmatprima()
						+ "')\" class=\"btn btn-primary btn-block btn-sm\">Eliminar</button></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/obtenerMateriaPrimaEnTabla")
	public void obtenerProductoEnTabla(HttpServletRequest request, HttpServletResponse response) {
		String codigoMateriaPrima = request.getParameter("codigo");
		MateriaPrimaModel mp = materiaPrimaServicio.findByCodmatprima(codigoMateriaPrima);
		try {

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();// tablaMateriaPrima
			if (mp != null) {
				sb.append("<table id=\"tablaMateriaPrima\" class=\"table table-default\" >");
				sb.append("<tr><th>C&oacute;digo</th>");
				sb.append("<th>Nombre</th>");
				sb.append("<th>Descripci&oacute;n</th>");
				sb.append("<th>Precio</th>");
				sb.append("<th>Acci&oacute;n</th></tr>");
				sb.append("<tr>");
				sb.append("<td>" + mp.getCodmatprima() + "</td>");
				sb.append("<td>" + mp.getNombrematprima() + "</td>");
				sb.append("<td>" + mp.getDescmatprima() + "</td>");
				sb.append("<td>" + mp.getPreciomatprima() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarMateriaPrima('" + mp.getCodmatprima()
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
