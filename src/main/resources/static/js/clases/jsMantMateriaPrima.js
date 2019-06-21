$(function() {
	$("#operacion").val('create');
	$("#btnBuscaMP").hide();
	$("#btnBuscaLista").hide();
	$('#tablaMateriaPrima').DataTable();
	$("#c_tablaMateriaPrima").html('');
	limpiarCampos();
});

function limpiarCampos(){
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
	$("#c_tablaMateriaPrima").html('');
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
	$("#c_tablaMateriaPrima").html('');
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
	$("#auxBotonesFooter").hide();
	limpiarCampos();
});
function buscarMateriaPrimaBtn() {
	var op = $("#operacion").val();
	if (op === 'update') {
		obtenerDatosMateriaPrima();
	} else {
		var codigoBusquedaEliminar = $("#cotmatprima").val();
		obtenerMateriaPrimaPorCodigo(codigoBusquedaEliminar);
	}

}
function obtenerListaMateriaPrima() {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerListaMateriaPrima",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaMateriaPrima").html(tabla);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function obtenerDatosMateriaPrima() {
	var codigoMP = $("#cotmatprima").val();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerDatosMateriaPrima",
		data : {
			codigoMP : codigoMP
		},
		success : function(data) {
			var datos = data.split("@|");
			$("#nombrematprima").prop('disabled', false);
			$("#nombrematprima").val(datos[1]);
			$("#preciomatprima").prop('disabled', false);
			$("#preciomatprima").val(datos[2]);
			$("#idcategory").prop('disabled', false);
			$("#idcategory").val(datos[4]);
			$("#descmatprima").prop('disabled', false);
			$("#descmatprima").val(datos[5]);
			$("#cantidadmatprima").prop('disabled', true);
			$("#cantidadmatprima").val(datos[3]);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function eliminarMateriaPrima(codigo) {
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
		url : "/deleteMateriaPrima",
		data : {
			codigoMP : codigoMP
		},
		success : function(data) {
			$("#c_tablaMateriaPrima").html('');
			obtenerListaMateriaPrima();
			$("#myModal").modal('hide');
		},
		error : function(e) {
			alert("ERROR...");
			$("#myModal").modal('hide');
		}
	});
}

function obtenerMateriaPrimaPorCodigo(codigo) {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerMateriaPrimaEnTabla",
		data : {codigo:codigo},
		success : function(data) {
			var tabla = data;
			$("#c_tablaMateriaPrima").html(tabla);
		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

