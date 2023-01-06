package com.example.demo;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Paaikkuna extends Application {

	public Stage tila;
	public MainMenu menu;
	public PelaajanNakyma peli;

	public void start(Stage stage) {
		alusta(stage);
	}
	public void alusta(Stage stage){
		tila=stage;
		tila.setTitle("Battleship");
		EventHandler hiiriHandleri=new EventHandler<MouseEvent>() {
			//En sittemmin tiedä olenko väärin käsittänyt handlerin määrityksen että kaikki pitää Applikaatio tasolla
			@Override
			public void handle(MouseEvent event) {
				test((Poyta.Solu) event.getSource());
			}
			void test(Poyta.Solu a){
				System.out.println("painetaan");
				//Nappula laogiikat tänne, eli jotemmin laitetaan laivoja jokin flagi tai jottain. Sitttemin ammuntaan siirrytään erikseen kai?
				System.out.println(a);
				System.out.println(a.Ammu());
				System.out.println(a);
			}
		};
		this.menu=new MainMenu(hiiriHandleri,this);


		stage.setScene(menu.Rakenna());
		stage.show();
	}
}




