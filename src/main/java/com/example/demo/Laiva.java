package com.example.demo;

import javafx.scene.shape.Rectangle;

public class Laiva{
	
	public boolean horizontal;
	//true = laiva on horisontaalisessa asennossa
	//false = laiva on vertikaalisessa asennossa
	
	public int hp;
	//laivan hp (health points). Tällä voidaan tarkistaa laivan "elossa oleminen"
	
	public String tyyppi;
	//laivan tyyppi eli ---koko---, Stringiksi?= tai jotain

	public int koko;
	//Laivan koko ja tyyppi pitää eritellä että ne voi piirtää eri tavalla

	public Poyta.Solu EnsimSolu;
	//Tämä tänne käperöity, avittamaan piirtämistä-> otetaan eka solu katotaan horizontti ja koko, laitetaan soveltuva mikälie paikalleen
	public Laiva(int koko, String tyyppi, boolean horizontal) {
		
		this.koko=koko;
		hp = koko;
		this.tyyppi = tyyppi;
		this.horizontal = horizontal;
	}


	public void osuma() {
		hp--;
	}
	
	public boolean onkoElossa() {
		return hp > 0;
	}
	
}
