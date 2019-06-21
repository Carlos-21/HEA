package com.udemy.backendninja.serviciosImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Productos;
import com.udemy.backendninja.model.ProductosModel;
import com.udemy.backendninja.repository.RepositoryProductos;
import com.udemy.backendninja.servicios.ProductosServicio;

@Service("productosServicioImpl")
public class ProductosServicioImpl implements ProductosServicio {
	@Autowired
	@Qualifier("RepositoryProductos")
	private RepositoryProductos productosRepository;

	@Override
	public List<ProductosModel> listAllProductos() {
		List<Productos> listProd = productosRepository.findAll();
		List<ProductosModel> listProdAux = new ArrayList<ProductosModel>();
		for (Productos prod : listProd) {
			ProductosModel pm = new ProductosModel();
			pm = convertirProdEntidadAModel(prod);
			listProdAux.add(pm);
		}
		return listProdAux;
	}

	@Override
	public ProductosModel findByCodprod(String codigoProducto) {
		ProductosModel pm = new ProductosModel();
		Productos producto = productosRepository.findByCodprod(codigoProducto);
		pm = convertirProdEntidadAModel(producto);
		return pm;
	}

	
	public ProductosModel convertirProdEntidadAModel(Productos producto) {
		ProductosModel prodModel = new ProductosModel();
		if(producto!=null) {
		
		prodModel.setIdprod(producto.getIdprod());
		prodModel.setCodprod(producto.getCodprod());
		prodModel.setDescripcionprod(producto.getDescripcionprod());
		prodModel.setDetordenventas(producto.getDetordenventas());
		prodModel.setInventario(producto.getInventario());
		prodModel.setNombreprod(producto.getNombreprod());
		prodModel.setPrecio(producto.getPrecio());
		}
		return prodModel;

	}

	public Productos convertirProdModelAEntidad(ProductosModel productoModel) {
		Productos prod = new Productos();
		prod.setIdprod(productoModel.getIdprod());
		prod.setCodprod(productoModel.getCodprod());
		prod.setDescripcionprod(productoModel.getDescripcionprod());
		prod.setDetordenventas(productoModel.getDetordenventas());
		prod.setInventario(productoModel.getInventario());
		prod.setNombreprod(productoModel.getNombreprod());
		prod.setPrecio(productoModel.getPrecio());
		return prod;

	}

	@Override
	public ProductosModel addProductos(ProductosModel productoM) {
		Productos producto = productosRepository.save(convertirProdModelAEntidad(productoM));
		return convertirProdEntidadAModel(producto);
	}

	@Override
	public ProductosModel updateProductos(ProductosModel productoM) {
		Productos producto = productosRepository.save(convertirProdModelAEntidad(productoM));
		return convertirProdEntidadAModel(producto);
	}

	@Override
	public int removeProductos(String id) {
		productosRepository.deleteById(id);
		return 0;
	}

}
