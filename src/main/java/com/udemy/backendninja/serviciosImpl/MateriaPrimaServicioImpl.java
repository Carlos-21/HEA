package com.udemy.backendninja.serviciosImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Materiaprima;
import com.udemy.backendninja.model.MateriaPrimaModel;
import com.udemy.backendninja.repository.RepositoryMateriaPrima;
import com.udemy.backendninja.servicios.MateriaPrimaServicio;

@Service("materiaPrimaServiceImpl")
public class MateriaPrimaServicioImpl implements MateriaPrimaServicio {
	@Autowired
	@Qualifier("RepositoryMateriaPrima")
	private RepositoryMateriaPrima materiaPrimaRepository;

	@Override
	public List<MateriaPrimaModel> listAllMateriaPrima() {
		List<Materiaprima> listMatPrim=materiaPrimaRepository.findAll();
		List<MateriaPrimaModel> listMatPrimModel=new ArrayList<MateriaPrimaModel>();
		for (Materiaprima materiaPrima : listMatPrim) {
			listMatPrimModel.add(convertirDeEntityAModel(materiaPrima));
		}
		
		return listMatPrimModel;
	}

	@Override
	public MateriaPrimaModel addMateriaPrima(MateriaPrimaModel materiaprima) {
		Materiaprima mp = materiaPrimaRepository.save(convertirDeModelAEntity(materiaprima));
		
		return convertirDeEntityAModel(mp);
	}

	@Override
	public int removeMateriaPrima(int id) {
		materiaPrimaRepository.deleteById(id);
		return 0;
	}

	@Override
	public MateriaPrimaModel updateMateriaPrima(MateriaPrimaModel materiaprima) {
		Materiaprima mp = materiaPrimaRepository.save(convertirDeModelAEntity(materiaprima));
		
		return convertirDeEntityAModel(mp);
	}
	
	@Override
	public long  countMateriaPrima() {
		return materiaPrimaRepository.count();
	}

	@Override
	public MateriaPrimaModel findByCodmatprima(String codigo) {
		Materiaprima mp=materiaPrimaRepository.findByCodmatprima(codigo);
		MateriaPrimaModel mpm=null;
		
		if(mp!=null)
			mpm = convertirDeEntityAModel(mp);
			
		return mpm;
	}

	@Override
	public void removeMateriaPrima(MateriaPrimaModel mp) {
		materiaPrimaRepository.delete(convertirDeModelAEntity(mp));
	}
	
	
	
	private Materiaprima convertirDeModelAEntity(MateriaPrimaModel mpm){
		Materiaprima mp = new Materiaprima();
		String codMatPrima=mpm.getCodmatprima();
		
		mp.setCantidadmatprima(BigDecimal.valueOf(mpm.getCantidadmatprima()));
		mp.setCodmatprima(codMatPrima);
		mp.setDescripcion(mpm.getDescmatprima());
		mp.setFechaprodMatprima("");	//No está en el formulario
		mp.setGastomatprima(BigDecimal.valueOf(0));
		mp.setIdmatprima(BigDecimal.valueOf(mpm.getIdmatprim()));
		mp.setNombrematprima(mpm.getNombrematprima());
		mp.setPreciomatprima(BigDecimal.valueOf(mpm.getPreciomatprima()));
		
		return mp;
	}

	private MateriaPrimaModel convertirDeEntityAModel(Materiaprima mp){
		MateriaPrimaModel mpm = new MateriaPrimaModel();
		mpm.setCantidadmatprima(mp.getCantidadmatprima().intValue());
		mpm.setCodmatprima(mp.getCodmatprima());
		mpm.setDescmatprima(mp.getDescripcion());
		mpm.setIdcategory("");	//No se usa
		mpm.setIdmatprim(mp.getIdmatprima().longValue());
		mpm.setNombrematprima(mp.getNombrematprima());
		mpm.setPreciomatprima(mp.getPreciomatprima().longValue());
		return mpm;
	}
	
}
