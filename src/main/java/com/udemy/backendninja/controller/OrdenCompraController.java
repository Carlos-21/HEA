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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Detordencompra;
import com.udemy.backendninja.model.MateriaPrimaModel;
import com.udemy.backendninja.model.OrdenCompra;
import com.udemy.backendninja.model.ProveedoresModel;
import com.udemy.backendninja.servicios.CabOrdenCompraServicio;
import com.udemy.backendninja.servicios.DetOrdenCompraServicio;
import com.udemy.backendninja.servicios.MateriaPrimaServicio;
import com.udemy.backendninja.servicios.ProveedoresServicio;

@Controller
public class OrdenCompraController {

	@Autowired
	MateriaPrimaServicio matPrimServicio;
	@Autowired
	ProveedoresServicio provServicio;
	@Autowired
	CabOrdenCompraServicio cabOrdComServicio;
	@Autowired
	DetOrdenCompraServicio detOrdComServicio;
	@Autowired
	

	private static final String LSITAMODELPRODUCTO = "PROD" + (UUID.randomUUID());
	private static final String LSITAMODELPRODUCTO2 = "PROD" + (UUID.randomUUID());

	@RequestMapping("/ordenCompra")
	public String ordenCompra(Model model, HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		OrdenCompra oc = new OrdenCompra();
		
		model.addAttribute("nombreUsuario", authentication.getName());
		model.addAttribute("ordenCompra", oc);
		HttpSession session = request.getSession();
		session.setAttribute(LSITAMODELPRODUCTO, matPrimServicio.listAllMateriaPrima());
		session.setAttribute(LSITAMODELPRODUCTO2, new ArrayList<MateriaPrimaModel>());
		return "view/ordenCompra";
	}

	@PostMapping("/agregarOrdenCompra") 
	public String agregarOrdenCompra(/*@RequestParam("tipoOperacion")String tipoOperacion, 
			@RequestParam("codOrdenCompra")String codCabordencompra,
			@RequestParam("Proveedores")String codProv,
			@RequestParam("contactoProveedor")String contactoProveedor,
			@RequestParam("fechaOrden")String fechaOrden,
			@RequestParam("tipoPago")String tipoPago,*/
			@ModelAttribute("ordenCompra") OrdenCompra ordencompra,
			Model model, HttpServletRequest request, HttpServletResponse response,Authentication authentication) {
		//ModelAndView vista=new ModelAndView("view/ordenCompra");
		//vista.addObject("nombreUsuario", authentication.getName());
		model.addAttribute("nombreUsuario", authentication.getName());
		System.out.println(ordencompra);
		System.out.println("La operacion es "+ordencompra.getTipoOperacion());
		
		HttpSession session = request.getSession();
		if (ordencompra.getTipoOperacion().equals("add")) {
			List<MateriaPrimaModel> listaSeleccionados = (List<MateriaPrimaModel>) session.getAttribute(LSITAMODELPRODUCTO2);
			long costo = calcularSumaTotales(listaSeleccionados);

			Cabordencompra cabOrdCom = cabOrdComServicio.ingresarCabOrdenCompra(ordencompra.getCodProv(), ordencompra.getContactoProveedor(), ordencompra.getFechaOrden(),
					ordencompra.getTipoPago(), new BigDecimal(costo), authentication.getName());

			for (MateriaPrimaModel mpm : listaSeleccionados) {
				detOrdComServicio.ingresarDetOrdenCompra(cabOrdCom, mpm.getCodmatprima(),
						Integer.toString(mpm.getCantidadmatprima()));
			}
			//vista.addObject("msj", "AgregarExito");
			//vista.addObject("codigoOC", cabOrdCom.getOpcCodordencompra());
			model.addAttribute("msj", "AgregarExito");
			model.addAttribute("codigoOC", cabOrdCom.getOpcCodordencompra());
			
			System.out.println("Se agregó el Orden de Compra satisfactoriamente");
		} else if (ordencompra.getTipoOperacion().equals("upd")) {
			Cabordencompra coc = cabOrdComServicio.encontrarCabOrdenCompra(ordencompra.getCodOrdenCompra());
			List<MateriaPrimaModel> listaNueva = (List<MateriaPrimaModel>) session.getAttribute(LSITAMODELPRODUCTO2);
			List<MateriaPrimaModel> listaAntigua = convertirListaDetOrdenCompraAListMateriaPrima(new ArrayList(coc.getDetordencompras()));
			
			long costo = calcularSumaTotales(listaNueva);

			coc.setOpcGastocompra(new BigDecimal(costo));
			cabOrdComServicio.actualizarDetOrdenComprax(coc);
			
			for (MateriaPrimaModel mpmAnt : listaAntigua) {	//Elimino la materia prima que ya no se quiere
				boolean existe = false;
				for (MateriaPrimaModel mpmNue : listaNueva) {	
					if (mpmAnt.getCodmatprima().equals(mpmNue.getCodmatprima()) 
							&& mpmAnt.getCantidadmatprima()==mpmNue.getCantidadmatprima()) {
						
						existe = true;
						break;
					}
				}
				if (!existe) {
					detOrdComServicio.borrarDetOrdenCompraxCodCabordcompraxcodMP(coc.getOpcCodordencompra(),
							mpmAnt.getCodmatprima());
				}
			}
			
			for (MateriaPrimaModel mpmNue : listaNueva) {
				if (!detOrdComServicio.existeDetOrdenCompraxCodCabordcompraxcodMP(coc.getOpcCodordencompra(),
						mpmNue.getCodmatprima())) {
					detOrdComServicio.ingresarDetOrdenCompra(coc, mpmNue.getCodmatprima(),
							Integer.toString(mpmNue.getCantidadmatprima()));
				}
			}
			//vista.addObject("msj", "ModificarExito");
			//vista.addObject("codigoOC", coc.getOpcCodordencompra());
			model.addAttribute("msj", "ModificarExito");
			model.addAttribute("codigoOC", coc.getOpcCodordencompra());
		}
		else if (ordencompra.getTipoOperacion().equals("del")) {
			Cabordencompra coc = cabOrdComServicio.encontrarCabOrdenCompra(ordencompra.getCodOrdenCompra());
			List<MateriaPrimaModel> listaAntigua = convertirListaDetOrdenCompraAListMateriaPrima(new ArrayList(coc.getDetordencompras()));
			
			for (MateriaPrimaModel mpmAnt : listaAntigua) {	//Elimino todos los Detordencompra
				detOrdComServicio.borrarDetOrdenCompraxCodCabordcompraxcodMP(coc.getOpcCodordencompra(), mpmAnt.getCodmatprima());
			}
			String codigoOC=coc.getOpcCodordencompra();
			cabOrdComServicio.borrarCabOrdenCompra(coc);
			
			model.addAttribute("msj", "EliminarExito");
			model.addAttribute("codigoOC", codigoOC);
		}

		return "view/ordenCompra";
	}

	@RequestMapping("/obtenerProveedores")
	public void obtenerProveedores(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			List<ProveedoresModel> list = provServicio.enlistarProveedores();

			StringBuilder sb = new StringBuilder();
			sb.append("<select id=\"Proveedores\" class=\"form-control\" name=\"Proveedores\">");
			sb.append("<option selected disable hidden>Elija su proveedor</option>");
			for (ProveedoresModel pm : list) {
				sb.append("<option value=\"" + pm.getCodProv() + "\">");
				sb.append(pm.getRazonSocial());
				sb.append("</option>");
			}
			sb.append("</select>");

			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/agregarCantAMateriaPrima")
	public void agregarCantAProducto(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<MateriaPrimaModel> listaTodos = (List<MateriaPrimaModel>) session.getAttribute(LSITAMODELPRODUCTO);
		List<MateriaPrimaModel> listaSeleccionados = (List<MateriaPrimaModel>) session
				.getAttribute(LSITAMODELPRODUCTO2);
		String codMatPrim = request.getParameter("codMatPrim");
		int cantMatPrim = Integer.parseInt(request.getParameter("cantMatPrim"));
		MateriaPrimaModel pm = matPrimServicio.findByCodmatprima(codMatPrim);
		pm.setCantidadmatprima(pm.getCantidadmatprima() + cantMatPrim);

		int aux = 0;
		for (MateriaPrimaModel matprim : listaTodos) {
			if (matprim.getCodmatprima().equals(codMatPrim)) {
				listaTodos.remove(aux);
				break;
			}
			aux++;
		}
		pm.setCantidadmatprima(cantMatPrim);
		listaSeleccionados.add(pm);
		/**/
		session.removeAttribute(LSITAMODELPRODUCTO);
		session.removeAttribute(LSITAMODELPRODUCTO2);
		/* Re-enviamos las listas a la sesion */
		session.setAttribute(LSITAMODELPRODUCTO, listaTodos);
		session.setAttribute(LSITAMODELPRODUCTO2, listaSeleccionados);

		StringBuilder sbTodos = entablarTodos(listaTodos, "Todo");
		StringBuilder sbSeleccionados = entablarTodos(listaSeleccionados, "No Todo");
		// System.out.println(sbTodos.toString());
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

	@PostMapping("/eliminarCantAMateriaPrima")
	public void eliminarCantAMateriaPrima(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<MateriaPrimaModel> listaTodos = (List<MateriaPrimaModel>) session.getAttribute(LSITAMODELPRODUCTO);
		List<MateriaPrimaModel> listaSeleccionados = (List<MateriaPrimaModel>) session
				.getAttribute(LSITAMODELPRODUCTO2);

		String codMatPrim = request.getParameter("codMatPrim");
		MateriaPrimaModel pm = matPrimServicio.findByCodmatprima(codMatPrim);

		// pm.setCantidadmatprima(pm.getCantidadmatprima() + cantMatPrim);

		int aux = 0;
		for (MateriaPrimaModel matprim : listaSeleccionados) {
			if (matprim.getCodmatprima().equals(codMatPrim)) {
				listaSeleccionados.remove(aux);
				listaTodos.add(matprim);
				break;
			}
			aux++;
		}
		/**/
		session.removeAttribute(LSITAMODELPRODUCTO);
		session.removeAttribute(LSITAMODELPRODUCTO2);
		/* Re-enviamos las listas a la sesion */
		session.setAttribute(LSITAMODELPRODUCTO, listaTodos);
		session.setAttribute(LSITAMODELPRODUCTO2, listaSeleccionados);

		StringBuilder sbTodos = entablarTodos(listaTodos, "Todo");
		StringBuilder sbSeleccionados = entablarTodos(listaSeleccionados, "No Todo");
		// System.out.println(sbTodos.toString());
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

	@PostMapping("/buscarOrdenCompra")
	public void buscarOrdenCompra(@RequestParam("codigoOC") String codigoOC, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cabordencompra coc = cabOrdComServicio.encontrarCabOrdenCompra(codigoOC);
		StringBuilder sb = new StringBuilder();
		sb.append(coc.getOpcCodordencompra() + "@|");
		sb.append(coc.getProveedores().getCodprov() + "@|");
		sb.append(coc.getProveedores().getRazonSocialProv() + "@|");
		sb.append(coc.getOpcContactoprov() + "@|");
		sb.append(coc.getOpcTipopago() + "@|");
		sb.append(coc.getOpcFechaorden() + "@|");
		sb.append(coc.getOpcGastocompra());

		List<Detordencompra> listaCompras = new ArrayList<Detordencompra>(coc.getDetordencompras());
		if (listaCompras == null)
			System.out.println("No hay detalle de orden de compra");
		else {
			List<MateriaPrimaModel> listaSeleccionados = convertirListaDetOrdenCompraAListMateriaPrima(listaCompras);
			List<MateriaPrimaModel> listaTodosAux = matPrimServicio.listAllMateriaPrima();
			List<MateriaPrimaModel> listaTodosFinal = eliminarActualesATodo(listaSeleccionados, listaTodosAux);
			/* Alguna forma de cargar las listas */
			/*
			 * var tablaTodos = datos[6]; var tablaSeleccionados = datos[7];
			 */

			StringBuilder listaTodoSB = entablarTodos(listaTodosFinal, "Todo");
			StringBuilder listaActualSB = entablarTodos(listaSeleccionados, "Actual");
			sb.append("$$" + listaTodoSB + "$$");
			sb.append(listaActualSB);
			/**/
			session.removeAttribute(LSITAMODELPRODUCTO);
			session.removeAttribute(LSITAMODELPRODUCTO2);
			/* Re-enviamos las listas a la sesion */
			session.setAttribute(LSITAMODELPRODUCTO, listaTodosFinal);
			session.setAttribute(LSITAMODELPRODUCTO2, listaSeleccionados);

			try {
				PrintWriter out = response.getWriter();
				out.println(sb);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/listarMateriaPrima")
	public void listProductos(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			List<MateriaPrimaModel> list = matPrimServicio.listAllMateriaPrima();
			StringBuilder sb = entablarTodos(list, "Todo");
			
			HttpSession session=request.getSession();
			session.removeAttribute(LSITAMODELPRODUCTO);
			session.removeAttribute(LSITAMODELPRODUCTO2);
			session.setAttribute(LSITAMODELPRODUCTO, matPrimServicio.listAllMateriaPrima());
			session.setAttribute(LSITAMODELPRODUCTO2, new ArrayList<MateriaPrimaModel>());
			out.print(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public StringBuilder entablarTodos(List<MateriaPrimaModel> list, String tipo) {
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
		else {
			sb.append("<th>Cantidad</th>");
			sb.append("<th>Eliminar</th></tr>");
		}

		for (MateriaPrimaModel materiaPrima : list) {
			sb.append("<tr>");
			sb.append("<td>" + materiaPrima.getCodmatprima() + "</td>");
			sb.append("<td>" + materiaPrima.getNombrematprima() + "</td>");
			sb.append("<td>" + materiaPrima.getPreciomatprima() + "</td>");
			if (todo)
				sb.append("<td><button type=\"button\" onclick=\"openModalMateriaPrima('"
						+ materiaPrima.getCodmatprima()
						+ "')\" style=\"border-style:solid; border-width:1px; border-color:#2C1CD9; text-align:center;\"  class=\"btn btn-default btn-sm\"><i  class=\"fas fa-plus\"></i></i></button></td>");
			else {
				sb.append("<td>" + materiaPrima.getCantidadmatprima() + "</td>");
				sb.append("<td><button type=\"button\" onclick=\"eliminarMateriaPrima('" + materiaPrima.getCodmatprima()
						+ "')\" style=\"border-style:solid; border-width:1px; border-color:#2C1CD9; text-align:center;\"  "
						+ "class=\"btn btn-default btn-sm\"><i  class=\"fas fa-minus-circle\"></i></i></button></td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb;
	}

	public long calcularSumaTotales(List<MateriaPrimaModel> listaSeleccionados) {
		long suma = 0;
		for (MateriaPrimaModel mpm : listaSeleccionados) {
			suma += mpm.getPreciomatprima() * mpm.getCantidadmatprima();
		}
		return suma;
	}

	public List<MateriaPrimaModel> convertirListaDetOrdenCompraAListMateriaPrima(List<Detordencompra> listDOC) {
		List<MateriaPrimaModel> listMP = new ArrayList<>();
		for (Detordencompra doc : listDOC) {
			MateriaPrimaModel mp = new MateriaPrimaModel();
			mp.setCodmatprima((doc.getMateriaprima().getCodmatprima()));
			mp.setNombrematprima(doc.getMateriaprima().getNombrematprima());
			mp.setPreciomatprima(doc.getMateriaprima().getPreciomatprima().doubleValue());
			mp.setCantidadmatprima(doc.getMateriaprima().getCantidadmatprima().intValue());
			listMP.add(mp);
		}
		return listMP;
	}

	public List<MateriaPrimaModel> eliminarActualesATodo(List<MateriaPrimaModel> listaSeleccionados,
			List<MateriaPrimaModel> listaTodos) {
		for (MateriaPrimaModel seleccionado : listaSeleccionados) {
			for (int i = 0; i < listaSeleccionados.size(); i++) {
				for (int j = 0; j < listaTodos.size(); j++) {
					if (listaTodos.get(j).getCodmatprima().equals(listaSeleccionados.get(i).getCodmatprima())) {
						listaTodos.remove(j);
						break;
					}
				}
			}
		}
		return listaTodos;
	}
}
