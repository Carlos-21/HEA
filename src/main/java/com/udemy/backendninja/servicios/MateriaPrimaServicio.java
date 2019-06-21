package com.udemy.backendninja.servicios;

import java.util.List;

import com.udemy.backendninja.model.MateriaPrimaModel;

public interface MateriaPrimaServicio {
	public abstract List<MateriaPrimaModel> listAllMateriaPrima();
	public abstract MateriaPrimaModel addMateriaPrima(MateriaPrimaModel materiaprima);
	public abstract int removeMateriaPrima(int id);
	public abstract void removeMateriaPrima(MateriaPrimaModel mp);
	public abstract MateriaPrimaModel updateMateriaPrima(MateriaPrimaModel materiaprima);
	public abstract long  countMateriaPrima();
	public abstract MateriaPrimaModel findByCodmatprima(String codigo);
}
