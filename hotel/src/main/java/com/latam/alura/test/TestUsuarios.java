package com.latam.alura.test;

import com.latam.alura.controller.UserController;


public class TestUsuarios {

	public static void main(String[] args){
		
		UserController userController = new UserController();
		
		userController.guardar("admin", "admin123");

		
	}

}
