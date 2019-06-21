package com.udemy.backendninja.serviciosImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Detfactura;
import com.udemy.backendninja.entity.Detordencompra;
import com.udemy.backendninja.entity.DetordencompraId;
import com.udemy.backendninja.entity.Factura;
import com.udemy.backendninja.entity.Materiaprima;
import com.udemy.backendninja.entity.Productos;
import com.udemy.backendninja.repository.RepositoryCabOrdenCompra;
import com.udemy.backendninja.repository.RepositoryDetOrdenCompra;
import com.udemy.backendninja.repository.RepositoryDetfactura;
import com.udemy.backendninja.repository.RepositoryFactura;
import com.udemy.backendninja.repository.RepositoryMateriaPrima;
import com.udemy.backendninja.repository.RepositoryProductos;
import com.udemy.backendninja.servicios.DetFacturaServicio;
import com.udemy.backendninja.servicios.DetOrdenCompraServicio;

@Service("DetFacturaServicioImpl")
public class DetFacturaServicioImpl implements DetFacturaServicio{

	@Autowired RepositoryDetfactura repoDetfactura;
	@Autowired RepositoryProductos repoProducto;
	@Override
	public boolean insertarFactura(Factura factura, String codProducto, double cantidad, double precio) {
		Detfactura detFactura= new Detfactura();
		Productos producto=repoProducto.findByCodprod(codProducto);
		detFactura.setIdfactura(new BigDecimal((int)(Math.random()*100)));
		detFactura.setFactura(factura);
		detFactura.setCantidadproducto(new BigDecimal(cantidad));
		detFactura.setPrecioproducto(new BigDecimal(precio));
		detFactura.setProductos(producto);
		
		return (repoDetfactura.save(detFactura)==null)?false:true;
	}
	@Override
	public List<Detfactura> findByFactura(Factura factura) {
		List<Detfactura> list= repoDetfactura.findByFactura(factura);
		return list;
	}
}

