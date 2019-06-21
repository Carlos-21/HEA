var fecha = new Date();
var dia = fecha.getDate()
var mes = fecha.getMonth() + 1;
var anio = fecha.getFullYear();

var fechaTexto = '';
fechaTexto = ((dia < 10) ? '0' : '') + dia + '/' + ((mes < 10) ? '0' : '')+ mes + '/' + anio;

$(document).ready(function() {
	listarMateriaPrima();
	$("#tipoOperacion").val('add');
	$("#Eliminar").hide();
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

	$("#seccionBusqueda").hide();
	$("#codigoOrdenCompra").hide();
	// Poner día al día actual
	$("#fechaOrden").val(fechaTexto);
});

$("#Agregar").click(function() {
	$("#Eliminar").hide();
	$("#seccionBusqueda").hide();
	$("#codigoOrdenCompra").hide();
	$("#proveedorSeccion").show();
	$("#codOrdenCompra").val('');
	listarMateriaPrima();
	$("#c_tablaMateriaPrimaSeleccionadas").html('');
	$("#totalPagar").html('S./0')
	// $("#codProv").prop('readonly',false);
	// $("#RazSocial").prop('readonly',false);
	$("#contactoProveedor").prop('readonly', false);
	$("#tipoPago").prop('readonly', false);
	$("#fechaOrden").prop('readonly', false);

	$("#codProv").val('');
	$("#RazSocial").val('');
	$("#contactoProveedor").val('');
	$("#tipoPago").val('');
	$("#fechaOrden").val(fechaTexto);
	
	$("#tipoOperacion").val('add');
});
$("#Eliminar").click(function() {
	$("#tipoOperacion").val('del');
});
$("#ModifiEli").click(function() {
	$("#Eliminar").show();
	$("#seccionBusqueda").show();
	$("#codigoOrdenCompra").show();
	$("#proveedorSeccion").hide();
	$("#codOrdenCompra").val('');
	$("#totalPagar").html('S./0')
	$("#codProv").prop('readonly', true);
	$("#RazSocial").prop('readonly', true);
	$("#contactoProveedor").prop('readonly', true);
	$("#tipoPago").prop('readonly', true);
	$("#fechaOrden").prop('readonly', true);

	$("#Proveedores").val('Elija su proveedor');
	$("#codProv").val(''); 
	$("#RazSocial").val('');
	$("#contactoProveedor").val('');
	$("#tipoPago").val('');
	$("#fechaOrden").val('');
	
	$("#tipoOperacion").val('upd');
});

$("#Proveedores").change(function() {
	var ID = $("#Proveedores option:selected").val();
	var RazonSocial = $("#Proveedores option:selected").text();
	$("#codProv").val(ID);
	//$("#codProv").prop("name",ID);
	$("#RazSocial").val(RazonSocial);
	//$("#RazSocial").prop("name",RazonSocial);
});
function openModalMateriaPrima(codigoMateriaPrima) {
	$("#modalCantidadMateriaPrima").modal('show');
	$("#cantMatPrim").val('');
	$("#codMatPrimAux").val(codigoMateriaPrima);
}

function closeModalMateriaPrima() {
	$("#modalCantidadMateriaPrima").modal('hide');
	$("#cantMatPrim").val('');
	$("#codMatPrimAux").val('');
}

function buscarOrdenCompra() {
	var codigoOC = $("#inputCodigoBusqueda").val();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/buscarOrdenCompra",
		data : {
			codigoOC : codigoOC
		},
		success : function(data) {
			if (data != null) {
				var secciones = data.split("$$");
				
				var datos = secciones[0].split("@|");
				$("#codOrdenCompra").val(datos[0]);
				$("#codProv").val(datos[1]); 			
				$("#RazSocial").val(datos[2]);			
				$("#contactoProveedor").val(datos[3]);	
				$("#tipoPago").val(datos[4]);			
				$("#fechaOrden").val(datos[5]);			
				$("#totalPagar").val(datos[6]);			
				$("#totalPagar").html("S/." + datos[6])

				$("#c_tablaMateriaPrimas").html(secciones[1]);
				$("#c_tablaMateriaPrimaSeleccionadas").html(secciones[2]);
			} else {
				alert("No se encontró la orden de compra");
			}
		},
		error : function(e) {
			closeModalMateriaPrima();
			alert("ERROR...");
		}
	});
}
function agregarOrdenCompra(){
	var tipoOperacion=$("#tipoOperacion").val();
	var codOrdenCompra=$("#codOrdenCompra").val();
	var Proveedores=$("#Proveedores option:selected").val();
	var contactoProveedor=$("#contactoProveedor").val();
	var fechaOrden=$("#fechaOrden").val();
	var tipoPago=$("#tipoPago").val();
	
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/agregarOrdenCompra",
		data : {
			tipoOperacion : tipoOperacion,
			codOrdenCompra : codOrdenCompra,
			Proveedores : Proveedores,
			contactoProveedor : contactoProveedor,
			fechaOrden : fechaOrden,
			tipoPago : tipoPago
		},
		success : function(data) {
			console.log(data);
			$("#HTMLTotal").html(data);
		},
		error : function(xhr, status, error) {
			var err= JSON.parse(xhr.responseText);
			console.log("ERROR: "+err.Message);
		}
	});
}
function agregarCantidadAProducto() {

	var codMatPrim = $("#codMatPrimAux").val();
	var cantMatPrim = $("#cantMatPrim").val();
	$("#c_tablaMateriaPrimas").html('');
	$("#c_tablaMateriaPrimaSeleccionadas").html('');
	$("#totalPagar").html('');

	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/agregarCantAMateriaPrima",
		data : {
			codMatPrim : codMatPrim,
			cantMatPrim : cantMatPrim
		},
		success : function(data) {
			var datos = data.split("@|");
			var tablaTodos = datos[0];
			var tablaSeleccionados = datos[1];
			var totales = datos[2];
			$("#c_tablaMateriaPrimas").html(tablaTodos);
			$("#c_tablaMateriaPrimaSeleccionadas").html(tablaSeleccionados);
			$("#totalPagar").html("S/." + totales);
			closeModalMateriaPrima();
		},
		error : function(e) {
			closeModalMateriaPrima();
			alert("ERROR...");
		}
	});
}

function listarMateriaPrima(){
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/listarMateriaPrima",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaMateriaPrima").html(tabla);
		},
		error : function(err) {
			alert(err);
		}
	});
}
function eliminarMateriaPrima(codMatPrim) {
	$("#c_tablaMateriaPrimas").html('');
	$("#c_tablaMateriaPrimaSeleccionadas").html('');
	$("#totalPagar").html('');
	
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/eliminarCantAMateriaPrima",
		data : {
			codMatPrim : codMatPrim
		},
		success : function(data) {
			var datos = data.split("@|");
			var tablaTodos = datos[0];
			var tablaSeleccionados = datos[1];
			var totales = datos[2];
			$("#c_tablaMateriaPrimas").html(tablaTodos);
			$("#c_tablaMateriaPrimaSeleccionadas").html(tablaSeleccionados);
			$("#totalPagar").html("S/."+totales);
			closeModalMateriaPrima();
		},
		error : function(e) {
			closeModalMateriaPrima();
			alert("ERROR...");
		}
	});
}