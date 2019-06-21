$(function(){
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
	
});