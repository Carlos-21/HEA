package com.udemy.backendninja.util;

public class Util {

	public static String nullCad(Object obj) {
		String resultado="";
		if(obj!=null) {
			resultado = obj.toString();
		}
		return resultado;
	}
}
