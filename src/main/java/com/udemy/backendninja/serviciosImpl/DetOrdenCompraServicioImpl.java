package com.udemy.backendninja.serviciosImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Detordencompra;
import com.udemy.backendninja.entity.DetordencompraId;
import com.udemy.backendninja.entity.Materiaprima;
import com.udemy.backendninja.repository.RepositoryDetOrdenCompra;
import com.udemy.backendninja.repository.RepositoryMateriaPrima;
import com.udemy.backendninja.servicios.DetOrdenCompraServicio;

@Service("DetOrdenCompraServicio")
public class DetOrdenCompraServicioImpl implements DetOrdenCompraServicio{

	@Autowired RepositoryDetOrdenCompra repoDetOrdCom;
	@Autowired RepositoryMateriaPrima repoMatPrim;
	@Override
	public boolean ingresarDetOrdenCompra(Cabordencompra cabOrdCom, String codMatPrim, String cantidad) {
		Detordencompra detOrdCom= new Detordencompra();
		long id=repoDetOrdCom.count();
		Materiaprima matPrim=repoMatPrim.findByCodmatprima(codMatPrim);
		DetordencompraId ID=ingresarDetOrdenCompraId(cantidad,codMatPrim,cabOrdCom.getOpcCodordencompra());
		
		detOrdCom.setCabordencompra(cabOrdCom);
		detOrdCom.setMateriaprima(matPrim);		
		detOrdCom.setId(ID);
		
		return (repoDetOrdCom.save(detOrdCom)==null)?false:true;
	}
	
	public boolean existeDetOrdenCompraxCodCabordcompraxcodMP(String codCabOrdCom, String codMatPrim) {
		Detordencompra detOrdCom=null;
		detOrdCom= repoDetOrdCom.findBycodOrdenCompraYcodMateriaPrima(codCabOrdCom, codMatPrim);
		return (detOrdCom==null)?false:true;
	}

	private DetordencompraId ingresarDetOrdenCompraId(String cantidad, String codMatPrima, String CodCabMatPri) {
		DetordencompraId ID= new DetordencompraId();
		long cant= Long.parseLong(cantidad);
		long idDetOrdenCompra=repoDetOrdCom.getNextDetOrdenCompraId();
		ID.setDocIdcompra(BigDecimal.valueOf(idDetOrdenCompra));
		ID.setCantidad(new BigDecimal(cant));
		ID.setCodmateria(codMatPrima);
		ID.setDocCodordencompra(CodCabMatPri);
		return ID;
	}

	@Override
	public boolean borrarDetOrdenCompraxCodCabordcompraxcodMP(String codCabOrdCom, String codMatPrim) {
		try{
			repoDetOrdCom.deleteBycodOrdenCompraYcodMateriaPrima(codCabOrdCom,codMatPrim);
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	
}

