package com.udemy.backendninja.servicios;

import java.util.List;
import com.udemy.backendninja.entity.Productos;
import com.udemy.backendninja.model.ProductosModel;

public interface ProductosServicio {
	public abstract List<ProductosModel> listAllProductos();
	public ProductosModel convertirProdEntidadAModel(Productos producto);
	public abstract ProductosModel  findByCodprod(String codigoProducto);
	public abstract ProductosModel addProductos(ProductosModel producto);
	public abstract ProductosModel updateProductos(ProductosModel producto);
	public abstract int removeProductos(String id);
}
