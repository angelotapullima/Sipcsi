package com.bufeotec.sipcsi.Models;

import java.util.ArrayList;

public class NumEmergencia {

	//declare private data instead of public to ensure the privacy of data field of each class
	private String entidad;
	private String numero;

	public NumEmergencia(String entidad, String numero) {
		this.entidad = entidad;
		this.numero = numero;
	}

	//retrieve user's name
	public String getEntidad(){
		return entidad;
	}

	//retrieve users' hometown
	public String getNumero(){
		return numero;
	}

	public static ArrayList<NumEmergencia> getUsers() {
		ArrayList<NumEmergencia> numEmergencias = new ArrayList<NumEmergencia>();
		numEmergencias.add(new NumEmergencia("Bomberos", "116"));
		numEmergencias.add(new NumEmergencia("Policia", "105"));
		numEmergencias.add(new NumEmergencia("Policia de Carretera", "110"));
		numEmergencias.add(new NumEmergencia("Comisaria PNP Iquitos", "(065)231131"));
		numEmergencias.add(new NumEmergencia("Comisaria PNP Moronacocha", "(065)234971"));
		numEmergencias.add(new NumEmergencia("Comisaria PNP Punchana", "(065)251970"));
		numEmergencias.add(new NumEmergencia("Comisaria PNP San Juan", "(065)257203"));
		numEmergencias.add(new NumEmergencia("Policía de turismo", "(065) 242081"));
		numEmergencias.add(new NumEmergencia("Defensa Civil", "113"));
		numEmergencias.add(new NumEmergencia("Radio Patrulla", "105"));
		numEmergencias.add(new NumEmergencia("Electro Oriente", "(065)233192"));
		numEmergencias.add(new NumEmergencia("Ambulancia", "115"));
		numEmergencias.add(new NumEmergencia("Hospital Iquitos", "(065)231721"));
		numEmergencias.add(new NumEmergencia("Es Salud", "(065)235101"));
		numEmergencias.add(new NumEmergencia("Hospital Regional de Loreto", "(065)252004"));
		numEmergencias.add(new NumEmergencia("Clinica Ana Stahl", "(065)250025"));
		numEmergencias.add(new NumEmergencia("Clinica Santa Anita", "(065)266003"));
		numEmergencias.add(new NumEmergencia("SedaLoreto", "(065)264835"));
		numEmergencias.add(new NumEmergencia("Aeropuerto ", "(065)260147"));
		return numEmergencias;
	}
}
