package com.alura.infra.errores;

public class IdNegativo {
	public static void verificar(Long id) {
		if(id <= 0) {
			throw new ValidacionDeIntegridad("El id no puede ser menor o igual que 0");
		}
	}
}	
