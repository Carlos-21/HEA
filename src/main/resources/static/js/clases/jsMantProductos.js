$(function() {
	$("#cantidadmatprima").prop("disabled", true);
	$("#cantidadmatprima").val('I100');
	$("#operacion").val('create');
	$("#btnBuscaMP").hide();
	$("#btnBuscaLista").hide();
	$('#tablaMateriaPrima').DataTable();
	$("#c_tablaProducto").html('');
});

function limpiarCampos() {
	$("#cotmatprima").val('');
	$("#nombrematprima").val('');
	$("#preciomatprima").val(0);
	$("#cantidadmatprima").val(0);
	$("#descmatprima").val('');
}

$("#showIngresar").click(function() {
	$("#operacion").val('create');
	$("#lblNombre").show();
	$("#nombrematprima").show();
	$("#btnBuscaLista").hide();
	$("#btnBuscaMP").hide();
	$("#nombrematprima").prop('disabled', false);
	$("#nombrematprima").prop('disabled', false);
	$("#preciomatprima").prop('disabled', false);
	$("#idcategory").prop('disabled', false);
	$("#descmatprima").prop('disabled', false);
	$("#cantidadmatprima").prop('disabled', false);
	$("#grupo1").show();
	$("#grupo2").show();
	$("#grupo3").show();
	$("#c_tablaProducto").html('');
	limpiarCampos();
	$("#auxBotonesFooter").show();
});
$("#showModificar").click(function() {
	$("#operacion").val('update');
	$("#lblNombre").show();
	$("#nombrematprima").show();
	$("#btnBuscaLista").hide();
	$("#btnBuscaMP").show();
	$("#nombrematprima").prop('disabled', true);
	$("#nombrematprima").prop('disabled', true);
	$("#preciomatprima").prop('disabled', true);
	$("#idcategory").prop('disabled', true);
	$("#descmatprima").prop('disabled', true);
	$("#cantidadmatprima").prop('disabled', true);
	$("#grupo1").show();
	$("#grupo2").show();
	$("#grupo3").show();
	$("#c_tablaProducto").html('');
	limpiarCampos();
	$("#auxBotonesFooter").show();
});
$("#showEliminar").click(function() {
	$("#operacion").val('delete');
	$("#lblNombre").hide();
	$("#nombrematprima").hide();
	$("#btnBuscaLista").show();
	$("#btnBuscaMP").show();
	$("#grupo1").hide();
	$("#grupo2").hide();
	$("#grupo3").hide();
	limpiarCampos();
	$("#auxBotonesFooter").hide();
});
function obtenerDatosProductosBtn() {
	var op = $("#operacion").val();

	if (op === 'update') {
		obtenerDatosProductos();
	} else {
		var codigoBusquedaEliminar = $("#cotmatprima").val();
		obtenerProductoPorCodigo(codigoBusquedaEliminar);
	}

}
function obtenerListaProducto() {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerListaProducto",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaProducto").html(tabla);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function obtenerDatosProductos() {
	var codigoMP = $("#cotmatprima").val();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerDatosProductos",
		data : {
			codigoMP : codigoMP
		},
		success : function(data) {
			var datos = data.split("@|");
			$("#nombrematprima").prop('disabled', false);
			$("#nombrematprima").val(datos[1]);
			$("#preciomatprima").prop('disabled', false);
			$("#preciomatprima").val(datos[2]);

			$("#descmatprima").prop('disabled', false);
			$("#descmatprima").val(datos[3]);

		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function eliminarProducto(codigo) {
	abrirModalEliminacion(codigo);
}

function abrirModalEliminacion(codigo) {
	$("#myModal").modal('show');
	$("#auxCodInsumo").val(codigo);
	$("#parrafoConfirmacion").html(
			'¿Seguro de eliminar el insumo ' + codigo + '?');
}

function eliminarInsumoConf() {
	var codigoMP = $("#auxCodInsumo").val();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/deleteProducto",
		data : {
			codigoMP : codigoMP
		},
		success : function(data) {
			$("#c_tablaProducto").html('');
			obtenerListaProducto();
			$("#myModal").modal('hide');
		},
		error : function(e) {
			alert("ERROR...");
			$("#myModal").modal('hide');
		}
	});
}

function obtenerProductoPorCodigo(codigo) {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerProductoEnTabla",
		data : {
			codigo : codigo
		},
		success : function(data) {
			var tabla = data;
			$("#c_tablaProducto").html(tabla);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}
