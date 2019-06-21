$(function() {
	$("#result").hide();
	$('#tablaProductos').bdt();
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/obtenerListaProductos",
		data : {},
		success : function(data) {
			var tabla = data;
			$("#c_tablaProductos").html(tabla);

		},
		error : function(e) {
			alert("ERROR...");
		}
	});
})
function openModalProducto(codigoProducto) {
	$("#modalCantidadProd").modal('show');
	$("#cantProd").val('');
	$("#codProdAux").val(codigoProducto);
}

function closeModalProducto() {
	$("#modalCantidadProd").modal('hide');
	$("#cantProd").val('');
	$("#codProdAux").val('');
	
}

function agregarCantidadAProducto() {
	
	var codProd = $("#codProdAux").val();
	var cantProd = $("#cantProd").val();
	
	$("#c_tablaProductos").html('');
	$("#c_tablaProdSeleccionados").html('');
	$("#totalPagar").html('');
	$.ajax({
		type : "POST",
		datatype : "JSON",
		url : "/agregarCantAProducto",
		data : {
			codProd : codProd,
			cantProd : cantProd
		},
		success : function(data) {
			var datos = data.split("@|");
			var tablaTodos = datos[0];
			var tablaSeleccionados = datos[1];
			var totales = datos[2];
			$("#c_tablaProductos").html(tablaTodos);
			$("#c_tablaProdSeleccionados").html(tablaSeleccionados);
			$("#totalPagar").html("S/."+totales);
			closeModalProducto();
		},
		error : function(e) {
			closeModalProducto();
			alert("ERROR...");
		}
	});
}

function enviarformulario(){
	 $.ajax({
         type: "POST",
         url: "/agregarCotizacion",
         data: $("#formGenerico").serialize(), // Adjuntar los campos del formulario enviado.
         success: function(data)
         {
        	 $("#result").show();
        	 $("#panel-login").hide();
        	 $("#resultadoTexto").html(data);
        	 
         }
       });
}


