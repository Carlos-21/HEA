package com.udemy.backendninja.conexion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.udemy.backendninja.model.MateriaPrimaModel;
import com.udemy.backendninja.model.User;
import com.udemy.backendninja.repository.RepositoryUsuario;
import com.udemy.backendninja.servicios.MateriaPrimaServicio;
import com.udemy.backendninja.serviciosImpl.MateriaPrimaServicioImpl;

public class Test {



	public static void main(String[] args) {
		
		/*	MateriaPrimaServicioImpl aa = new MateriaPrimaServicioImpl();
			aa.addMateriaPrima(new MateriaPrima(1,"P100","A",2,2,"a","a"));*/
		
		 BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		 System.out.println("ADADSADS");
		 System.out.println(pe.encode("venta"));
		 

	}

}
