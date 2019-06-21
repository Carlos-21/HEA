package com.udemy.backendninja.serviciosImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Proveedores;
import com.udemy.backendninja.model.ProveedoresModel;
import com.udemy.backendninja.repository.RepositoryProveedor;
import com.udemy.backendninja.servicios.ProveedoresServicio;

@Service("ProveedoresServicioImpl")
public class ProveedoresServicioImpl implements ProveedoresServicio{

	@Autowired RepositoryProveedor repoProv;
	@Override
	public List<ProveedoresModel> enlistarProveedores() {
		List<ProveedoresModel> pm=new ArrayList<ProveedoresModel>();
		List<Proveedores> list=repoProv.findAll();
		
		for(Proveedores p : list)
			pm.add(convertirDeEntityAModel(p));
		
		return pm;
	}

	private ProveedoresModel convertirDeEntityAModel(Proveedores p) {
		ProveedoresModel pm= new ProveedoresModel();
		pm.setCodProv(p.getCodprov());
		pm.setRazonSocial(p.getRazonSocialProv());
		pm.setUbigeo(p.getUbigeo());
		return pm;
	}
}
