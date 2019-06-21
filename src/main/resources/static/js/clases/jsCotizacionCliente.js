$(function() {
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerListaCoti",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaCotizacion").html(tabla);

		},
		error : function(e) {
			alert("ERROR...");
		}
	});
})

function openModalCotizacion(codigo) {
	$("#modalCotizacion").modal('show');
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/gralCotizacion",
		data : {
			codigo : codigo
		},
		success : function(data) {

			var tabla = data.split("@|");
			$("#codigo").val(tabla[1]);
			$("#nombreCliente").val(tabla[2]);
			$("#total").val(tabla[3]);
			$("#estado").val(tabla[4]);
			$("#c_detCotizacion").html(tabla[0]);

		},
		error : function(e) {
			alert("ERROR...");
		}
	});
}

function aprobarCoti() {
	var codigo = $("#codigo").val();

	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/aprobarCoti",
		data : {
			codigo : codigo
		},
		success : function(data) {

			$("#modalCotizacion").modal('hide');
			alert("Se aprobo la cotizacion.");
			windows.location="aprobacionCoti";
		},
		error : function(e) {
			alert("ERROR...");
			$("#modalCotizacion").modal('hide');
		}
	});
}
