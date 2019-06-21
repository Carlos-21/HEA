package com.udemy.backendninja.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.backendninja.entity.Detordencompra;

public interface RepositoryDetOrdenCompra extends JpaRepository<Detordencompra,Serializable> {
	@Query (value="SELECT doc from Detordencompra as doc "
			+ "WHERE doc.id.docCodordencompra= :codOrdenCompra "
			+ "AND doc.materiaprima.codmatprima= :codMateriaPrima")
	public Detordencompra findBycodOrdenCompraYcodMateriaPrima(@Param("codOrdenCompra") String codOrdenCompra, @Param("codMateriaPrima") String codMateriaPrima);
	
	@Query(value="SELECT s_detOrdenCompra.nextval from dual", nativeQuery=true)
	public Long getNextDetOrdenCompraId();
	
	@Modifying
    @Transactional
	@Query (value="DELETE from Detordencompra as doc "
			+ "WHERE doc.id.docCodordencompra= :codOrdenCompra "
			+ "AND doc.materiaprima.codmatprima= :codMateriaPrima")
	public void deleteBycodOrdenCompraYcodMateriaPrima(@Param("codOrdenCompra") String codOrdenCompra, @Param("codMateriaPrima") String codMateriaPrima);

	
}
