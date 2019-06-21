package com.udemy.backendninja.serviciosImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Proveedores;
import com.udemy.backendninja.entity.Usuarios;
import com.udemy.backendninja.model.User;
import com.udemy.backendninja.repository.RepositoryCabOrdenCompra;
import com.udemy.backendninja.repository.RepositoryProveedor;
import com.udemy.backendninja.repository.RepositoryUsuario;
import com.udemy.backendninja.servicios.CabOrdenCompraServicio;

@Service("CabOrdenCompraServicio")
public class CabOrdenCompraServicioImpl implements CabOrdenCompraServicio {

	@Autowired
	RepositoryProveedor repoProv;
	@Autowired
	RepositoryCabOrdenCompra repoCabOrdCom;
	@Autowired
	RepositoryUsuario repoUsu;

	@Override
	public Cabordencompra ingresarCabOrdenCompra(String codProv, String contactoProveedor, String fechaOrden,
			String tipoPago, BigDecimal costo, String nombreUsuario) {
		Cabordencompra cabOrdCom = new Cabordencompra();
		Proveedores prov = repoProv.findByCodprov(codProv);
		long id = repoCabOrdCom.getNextCabOrdCompra();
		User user = repoUsu.findByUsername(nombreUsuario);

		String codOrdenCompra = "COC" + id;

		cabOrdCom.setOpcCodordencompra(codOrdenCompra);
		cabOrdCom.setOpcId(new BigDecimal(id));
		cabOrdCom.setUsuarios(convertirDeUserAUsuario(user));
		cabOrdCom.setProveedores(prov);
		cabOrdCom.setOpcRazonsocialprov(prov.getRazonSocialProv());
		cabOrdCom.setOpcContactoprov(contactoProveedor);
		cabOrdCom.setOpcFechaorden(fechaOrden);
		cabOrdCom.setOpcTipopago(tipoPago);
		cabOrdCom.setOpcGastocompra(costo);

		return repoCabOrdCom.save(cabOrdCom);
	}

	@Override
	public Cabordencompra encontrarCabOrdenCompra(String codCabOrdenCompra) {
		return repoCabOrdCom.findByOpcCodordencompra(codCabOrdenCompra);
	}

	private Usuarios convertirDeUserAUsuario(User u) {
		Usuarios usuario = new Usuarios();
		usuario.setApellidomaternousuario(u.getApellidomaternousuario());
		usuario.setApellidopaternousuario(u.getApellidopaternousuario());
		usuario.setContrasena(u.getContrasena());
		usuario.setEdadusuario(new Long(u.getEdadusuario()));
		usuario.setEmailusuario(u.getEmailusuario());
		usuario.setIdusuario(u.getIduser());
		usuario.setNombreusuario(u.getNombreusuario());
		usuario.setTelefono(u.getTelefono());
		usuario.setUsername(u.getUsername());
		return usuario;
	}

	@Override
	public boolean actualizarDetOrdenComprax(Cabordencompra coc) {
		repoCabOrdCom.save(coc);
		return (repoCabOrdCom.save(coc) != null) ? true : false;
	}

	@Override
	public boolean borrarCabOrdenCompra(Cabordencompra coc) {
		try {
			repoCabOrdCom.delete(coc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
