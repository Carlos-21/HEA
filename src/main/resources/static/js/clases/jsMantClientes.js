$(function() {
	$("#cantidadmatprima").prop("disabled", true);
	$("#operacion").val('create');
	$("#btnBuscaC").hide();
	$("#btnBuscaLista").hide();
	$('#tablaCliente').DataTable();
	$("#c_tablaCliente").html('');
});

function limpiarCampos() {
	$("#nombrecliente").val('');
	$("#appatcliente").val('');
	$("#apmatcliente").val('');
	$("#telefonocliente").val('');
	$("#correocliente").val('');
}

$("#showIngresar").click(function() {
	$("#operacion").val('create');
	$("#botonBuscar").hide();
	$("#areaCodigo").show();
	$("#lblNombre").show();
	$("#lblCodigo").show();
	$("#lblNombre").show();
	$("#nombrecliente").show();
	$("#grupo1").show();
	$("#grupo2").show();
	$("#grupo3").show();

	$("#btnBuscaLista").hide();
	$("#btnBuscaMP").hide();

	$("#nombrecliente").prop('disabled', false);
	$("#appatcliente").prop('disabled', false);
	$("#apmatcliente").prop('disabled', false);
	$("#telefonocliente").prop('disabled', false);
	$("#correocliente").prop('disabled', false);

	$("#c_tablaCliente").html('');
	limpiarCampos();
	$("#auxBotonesFooter").show();
});
$("#showModificar").click(function() {
	$("#operacion").val('update');
	$("#botonBuscar").show();
	$("#areaCodigo").show();
	$("#lblCodigo").show();
	$("#lblNombre").show();
	$("#nombrecliente").show();
	$("#grupo1").show();
	$("#grupo2").show();
	$("#grupo3").show();

	$("#btnBuscaLista").hide();
	$("#btnBuscaC").show();

	$("#nombrecliente").prop('disabled', true);
	$("#appatcliente").prop('disabled', true);
	$("#apmatcliente").prop('disabled', true);
	$("#telefonocliente").prop('disabled', true);
	$("#correocliente").prop('disabled', true);

	$("#c_tablaCliente").html('');
	limpiarCampos();
	$("#auxBotonesFooter").show();
});
$("#showEliminar").click(function() {
	$("#operacion").val('delete');

	$("#lblCodigo").hide();
	$("#areaCodigo").hide();
	$("#lblNombre").hide();
	$("#nombrecliente").hide();

	$("#btnBuscaLista").show();
	$("#btnBuscaC").show();
	$("#grupo1").hide();
	$("#grupo2").hide();
	$("#grupo3").hide();
	limpiarCampos();
	$("#auxBotonesFooter").hide();
});
/*function obtenerDatosClienteBtn() {
	var op = $("#operacion").val();

	if (op === 'update') {
		obtenerDatosClientes();
	} else {
		var codigoBusquedaEliminar = $("#codcliente").val();
		obtenerClientePorCodigo(codigoBusquedaEliminar);
	}

}*/
function buscarClienteEnter() {
	obtenerDatosClientes();
}
function obtenerListaCliente() {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerListaCliente",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaCliente").html(tabla);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function obtenerDatosClientes() {
	var codigoC = $("#codcliente").val();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerDatosCliente",
		data : {
			codigoC : codigoC
		},
		success : function(data) {
			var datos = data.split("@|");
			// $("#codcliente").prop('disabled', false);
			// $("#codcliente").val(datos[0]);
			$("#nombrecliente").prop('disabled', false);
			$("#nombrecliente").val(datos[1]);
			$("#appatcliente").prop('disabled', false);
			$("#appatcliente").val(datos[2]);
			$("#apmatcliente").prop('disabled', false);
			$("#apmatcliente").val(datos[3]);
			$("#telefonocliente").prop('disabled', false);
			$("#telefonocliente").val(datos[4]);
			$("#correocliente").prop('disabled', false);
			$("#correocliente").val(datos[5]);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function eliminarCliente(codigo, nombre) {
	abrirModalEliminacion(codigo, nombre);
}

function abrirModalEliminacion(codigo, nombre) {
	$("#myModal").modal('show');
	$("#auxCodCliente").val(codigo);
	$("#parrafoConfirmacion").html(
			'¿Seguro de eliminar el cliente ' + nombre + '?');
}

function eliminarClienteConf() {
	var codigoC = $("#auxCodCliente").val();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/deleteCliente",
		data : {
			codigoC : codigoC
		},
		success : function(data) {
			$("#c_tablaCliente").html('');
			obtenerListaCliente();
			$("#myModal").modal('hide');
		},
		error : function(e) {
			alert("ERROR...");
			$("#myModal").modal('hide');
		}
	});
}

function obtenerClientePorCodigo(codigo) {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerClienteEnTabla",
		data : {
			codigo : codigo
		},
		success : function(data) {
			var tabla = data;
			$("#c_tablaCliente").html(tabla);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}
