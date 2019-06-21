$(document).ready(function() {
	$("#result").hide();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/listarProductosFact",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaProductos").html(tabla);
		},
		error : function(err) {
			alert(err);
		}
	});
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerProveedores",
		data : {},
		success : function(data) {
			var prov = data;
			$("#Proveedores").html(prov);
		},
		error : function(err) {
			alert(err);
		}
	});
});

$("#Proveedores").change(function() {
	var ID = $("#Proveedores option:selected").val();
	var RazonSocial = $("#Proveedores option:selected").text();
	$("#codProv").val(ID);
	$("#RazSocial").val(RazonSocial);
});
function openModalMateriaPrima(codigoMateriaPrima) {
	$("#modalCantidadProd").modal('show');
	$("#cantMatPrim").val('');
	$("#codMatPrimAux").val(codigoMateriaPrima);
}

function closeModalMateriaPrima() {
	$("#modalCantidadProd").modal('hide');
	$("#cantMatPrim").val('');
	$("#codMatPrimAux").val('');

}

function abrirModalCliente() {

	$("#modalCliente").modal('show');
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerClientes",
		data : {},
		success : function(data) {
			var prov = data;
			$("#c_tablaCliente").html(prov);
		},
		error : function(err) {
			alert(err);
		}
	});
}

function escogerCliente(codCliente) {
	var cod = codCliente;
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerInfoCliente",
		data : {
			codigoCliente : cod
		},
		success : function(data) {
		
			var prov = data.split("@|");
			$("#cotmatprima").val(prov[0]);
			$("#nombreCliente").val(prov[1]);
			$("#apPaterno").val(prov[2]);
			$("#apMaterno").val(prov[3]);
			$("#telfCliente").val(prov[4]);
			$("#correoCliente").val(prov[5]);
			$("#modalCliente").modal('hide');
		},
		error : function(err) {
			alert(err);
			$("#modalCliente").modal('hide');
		}
	});

}

function agregarCantidadAProducto() {

	var codMatPrim = $("#codMatPrimAux").val();
	var cantMatPrim = $("#cantProd").val();
	$("#c_tablaProductos").html('');
	$("#c_tablaProdSeleccionados").html('');
	$("#totalPagar").html('');

	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/agregarCantProd",
		data : {
			codProducto : codMatPrim,
			cantProducto : cantMatPrim
		},
		success : function(data) {
			var datos = data.split("@|");
			var tablaTodos = datos[0];
			var tablaSeleccionados = datos[1];
			var totales = datos[2];
			$("#c_tablaProductos").html(tablaTodos);
			$("#c_tablaProdSeleccionados").html(tablaSeleccionados);
			$("#totalPagar").html("S/." + totales);
			
			closeModalMateriaPrima();
		},
		error : function(e) {
			closeModalMateriaPrima();
			alert(e);
		}
	});
}

function enviarformulario() {
	$.ajax({
		type : "POST",
		url : "/agregarFactura",
		data : $("#formGenerico").serialize(), // Adjuntar los campos del
												// formulario enviado.
		success : function(data) {
			$("#result").show();
			$("#panel-login").hide();
			$("#resultadoTexto").html(data);

		}
	});
}
