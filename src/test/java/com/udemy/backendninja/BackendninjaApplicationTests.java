package com.udemy.backendninja;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.backendninja.entity.Cabordencompra;
import com.udemy.backendninja.entity.Detordencompra;
import com.udemy.backendninja.servicios.CabOrdenCompraServicio;
import com.udemy.backendninja.servicios.DetOrdenCompraServicio;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendninjaApplicationTests {
	@Autowired
	CabOrdenCompraServicio cabOrdComServicio;
	@Autowired
	DetOrdenCompraServicio detOrdComServicio;
	
	@Test
	@Ignore
	public void contextLoads() {
	}

	@Test
	@Ignore
	@Transactional
	public void probrarEncontrarCabordenCompra() {
		Cabordencompra coc = cabOrdComServicio.encontrarCabOrdenCompra("COC12");
		if (coc != null) {
			List<Detordencompra> listDoc;
			Set<Detordencompra> setDetOrdCompra = coc.getDetordencompras();
			if (setDetOrdCompra != null) {
				if (setDetOrdCompra.isEmpty())
					System.out.println("Está vacio");
				else {
					System.out.println("Tiene elementos");
					listDoc = new ArrayList<Detordencompra>(setDetOrdCompra);
					for (Detordencompra d : listDoc) {
						System.out.println(d.getMateriaprima().getDescripcion());
					}
				}
			}

		} else {
			System.out.println("No se encontró la cabecera de orden compra");
		}

	}
	
	@Test
	@Transactional
	public void probarBorrar() {
		Cabordencompra coc = cabOrdComServicio.encontrarCabOrdenCompra("COC1");
		detOrdComServicio.borrarDetOrdenCompraxCodCabordcompraxcodMP(coc.getOpcCodordencompra(),
				"MP100");
		System.out.println("Se borró el DetOrden de COC1 y MP100");
	}

}
