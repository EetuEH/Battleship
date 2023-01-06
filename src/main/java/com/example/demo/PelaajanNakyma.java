package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;

import java.io.File;
import java.util.ArrayList;

public class PelaajanNakyma {
	ArrayList<Poyta> poydat=new ArrayList<Poyta>();
	Paaikkuna Ohjelma;
	Boolean voitettu;
	BorderPane bp;
	Boolean onkoLaivaValittu;
	CheckBox cb;
	//siirtetly tavarat

	Rectangle SiirLaiva;
	Laiva valittuLaiva;
	Rectangle lahde;
	Boolean voikoAmpua;
	Paint valintaL;

	Image vladivostok = new Image(getClass().getResourceAsStream("/assets/red_pennant_vladivostok.svg"));
	Image militaryboat = new Image(getClass().getResourceAsStream("/assets/Military_boat.svg"));
	Image submarine = new Image(getClass().getResourceAsStream("/assets/SubmarineSilhouette.svg"));
	Image jokulaiva = new Image(getClass().getResourceAsStream("/assets/1555188686.svg"));
	Image paatti = new Image(getClass().getResourceAsStream("/assets/1540235856.svg"));
	
	PelaajanNakyma(Paaikkuna paa,Poyta yksi, Poyta kaksi){
		Ohjelma=paa;
		yksi.setIsanta(this);
		kaksi.setIsanta(this);
		poydat.add(yksi);
		poydat.add(kaksi);
	}
	void AsetaNakyma(int i){
		bp = new BorderPane();

		Button Kysymys=new Button("[?]");
		Kysymys.setOnAction(event -> (new OhjeIkkuna(Ohjelma)).avaa());
		bp.setLeft(new VBox(Kysymys));
		Label nimi=new Label();
		nimi.setId("nimi");
		nimi.setFont(Font.font(22));
		HBox yl=new HBox(nimi);
		yl.setAlignment(Pos.CENTER);
		bp.setTop(yl);

		Button piilota=new Button("Piilota poyta");
		piilota.setMaxSize(100, 20);
		piilota.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				((Label)((HBox)bp.getTop()).getChildren().get(0)).setText("");
				bp.setCenter(new Pane());
			}
		});

		Button pelaa1=new Button("Pelaaja1, ammu");
		pelaa1.setId("1");
		Button pelaa2=new Button("Pelaaja2, ammu");
		pelaa2.setId("2");
		Button vaihda=new Button("vaihda, aseta laiva");
		vaihda.setId("3");


		Button Piilota=new Button("Piilota valitusta");
		Piilota.setId("4");
		Button PiirLaiv=new Button("Piirrä laivat");
		PiirLaiv.setId("5");
		Button Ammutut=new Button("Piirrä ammutut");
		Ammutut.setId("6");


		//Laitetttu kaikki nappi functio sisään koska turhauttavaa rakentaa jokaiselle oma event handleri
		EventHandler pelaajaNappi=new EventHandler() {
			@Override
			public void handle(Event event) {
				if(((Button)event.getSource()).getId()=="1"){
					lataaPelaaja(0);
					AmmuntaValmius();
				}
				if(((Button)event.getSource()).getId()=="2"){
					lataaPelaaja(1);
					AmmuntaValmius();
				}
				if(((Button)event.getSource()).getId()=="3"){
					if(((Poyta)bp.getCenter())==poydat.get(1)){
						LaivanSijoitus(0);
					}else {

						LaivanSijoitus(1);
					}
				}
				if(((Button)event.getSource()).getId()=="4"){
					((Poyta)bp.getCenter()).PiilotaSolut();
				}
				if(((Button)event.getSource()).getId()=="5"){
					((Poyta)bp.getCenter()).PiirLaivat();
				}
				if(((Button)event.getSource()).getId()=="6"){
					((Poyta)bp.getCenter()).NaytaAmmuttu();
				}
			}
		};

		//Kätevyys palasia
		pelaa1.setOnAction(pelaajaNappi);
		pelaa2.setOnAction(pelaajaNappi);
		vaihda.setOnAction(pelaajaNappi);
		Piilota.setOnAction(pelaajaNappi);
		PiirLaiv.setOnAction(pelaajaNappi);
		Ammutut.setOnAction(pelaajaNappi);

		Label PTek=new Label("Poyta manipulaatio");
		VBox Poyt=new VBox(PTek,piilota,pelaa1,pelaa2,vaihda);

		Label NTek=new Label("Poyta visuaali Napit");
		VBox Naytto=new VBox(NTek,Piilota,PiirLaiv,Ammutut);
		VBox oikea=new VBox(new Label("TESTAUSNAPPEJA:"),Poyt,Naytto);

		bp.setRight(oikea);

		//Heitetty kaikki alle
		LaivanSijoitus(i);

		//Seuranta Event
		bp.setOnMouseMoved(e->{
			if(!(SiirLaiva==null)){
				SiirLaiva.setX(e.getX());
				SiirLaiva.setY(e.getY());
			}
		});
		;
		//-> jopku koko standanri piäisi varmaan
		Scene Pelaajascene = new Scene(bp);



		Ohjelma.tila.setMinWidth(500);
		Ohjelma.tila.setMinHeight(500);
		Ohjelma.tila.setScene(Pelaajascene);
		LaivojenAsetus();


	}

	//Lähettää eventin että hiirtä siirretään tähdättyy X,Y locaationnn kyllä
	public void SiirHiir(){
		Node kohde = (Ohjelma.tila.getScene().focusOwnerProperty().get());


		Bounds vanheimainVanhempi=Ohjelma.tila.getScene().focusOwnerProperty().get().getParent().getParent().getParent().getBoundsInParent();

		Double VX=Ohjelma.tila.getScene().focusOwnerProperty().get().getBoundsInParent().getMinX()+vanheimainVanhempi.getMinX();
		Double HY=Ohjelma.tila.getScene().focusOwnerProperty().get().getParent().getBoundsInParent().getMinY()+vanheimainVanhempi.getMinY();

		Event.fireEvent(Ohjelma.tila.getScene().focusOwnerProperty().get(), new MouseEvent(MouseEvent.MOUSE_MOVED, VX,
				HY,0 , 0, MouseButton.NONE, 1, true, true, true, true,
				true, true, true, true, true, true, null));
	}

	//Lataa pelaajan näkymä ylä ja keski palasiin
	void lataaPelaaja(int i){
		//nimi


		//Pelipoytä
		this.poydat.get(i).PiilotaSolut();
		bp.setCenter(this.poydat.get(i));
	}
	void LaivanSijoitus(int i){
		//laivavalikko
		//Luodaan neliöitä, asetetaan ne HBoxiin ja HBoxi BorderPanessa alas
		((Label)((HBox)bp.getTop()).getChildren().get(0)).setText("Aseta laivat "+ poydat.get(i).annaNimi());
		lataaPelaaja(i);


		HBox hb = new HBox();
		hb.setAlignment(Pos.TOP_CENTER);
		for(int j=0;j<((Poyta)bp.getCenter()).HAV;j++){
			var nelio1 = new Rectangle(20, 40, Color.ORANGE);
			nelio1.setId("H");
			nelio1.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
			hb.getChildren().add(nelio1);
		}

		for(int j=0;j<((Poyta)bp.getCenter()).SUC;j++) {
			var nelio2 = new Rectangle(20, 40, Color.ORANGERED);
			nelio2.setId("S");
			nelio2.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
			hb.getChildren().add(nelio2);
		}
		for(int j=0;j<((Poyta)bp.getCenter()).RIS;j++) {
			var nelio3 = new Rectangle(20, 60, Color.RED);
			nelio3.setId("R");
			nelio3.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
			hb.getChildren().add(nelio3);
		}
		for(int j=0;j<((Poyta)bp.getCenter()).TAI;j++) {
			var nelio4 = new Rectangle(20, 80, Color.PALEVIOLETRED);
			nelio4.setId("T");
			nelio4.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
			hb.getChildren().add(nelio4);
		}
		for(int j=0;j<((Poyta)bp.getCenter()).LEN;j++) {
			var nelio5 = new Rectangle(20, 100, Color.VIOLET);
			nelio5.setId("L");
			nelio5.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");
			hb.getChildren().add(nelio5);
		}
		cb = new CheckBox("horizontal");



		EventHandler NelioLaiva=new EventHandler() {
			@Override
			public void handle(Event event) {
				//palautetaan valittulaivapikyllä

				if(lahde==event.getSource()){
					((Rectangle)event.getSource()).setFill(valintaL);
					lahde=null;
					bp.getChildren().remove(SiirLaiva);
					SiirLaiva=null;
					valittuLaiva=null;
				}
				//Seuranta laiva laatikko kyllä
				else if(lahde==null){
				lahde= (Rectangle) event.getSource();
				valintaL=lahde.getFill();
				((Rectangle)event.getSource()).setFill(Color.WHITE);
				lahde=(Rectangle) event.getSource();
				int koko=0;
				String tyyppi=lahde.getId();
				switch (tyyppi){
					case "H":
						koko=2;
						break;
					case "S":
						koko=3;
						break;
					case "R":
						koko=3;
						break;
					case "T":
						koko=4;
						break;
					case "L":
						koko=5;
						break;
				}

				if (cb.isSelected() == true) {
					valittuLaiva = new Laiva(koko, tyyppi, true);
					onkoLaivaValittu = true;
					if (SiirLaiva != null) {
						bp.getChildren().remove(SiirLaiva);
					}
					SiirLaiva= new Rectangle(40*koko,40,valintaL);
				}
				else if (cb.isSelected() == false) {
					valittuLaiva= new Laiva(koko, tyyppi, false);
					onkoLaivaValittu = true;
					if (SiirLaiva != null) {
						bp.getChildren().remove(SiirLaiva);
					}
					SiirLaiva= new Rectangle(40,40*koko,valintaL);
				}


				//Uusi siirrettävä hommeli
				bp.getChildren().add(SiirLaiva);
				SiirLaiva.setMouseTransparent(true);

			}}};

		for(Node lai:hb.getChildren()){
			lai.setFocusTraversable(true);
			lai.focusedProperty().addListener(((observable, oldValue, newValue) -> {
				if(newValue){
					((Rectangle)lai).setStroke(Color.CYAN);
				}
				else if(oldValue){
					((Rectangle)lai).setStroke(Color.BLACK);
				}
			}));
			lai.setOnMouseClicked(NelioLaiva);
		}

		hb.getChildren().add(cb);
		bp.setBottom(hb);
		LaivaLoppupaa();

	}

	//Asettaa valitulle Poydalle eventhandlerit olemaan sopivat loppupäiksi laivan sijoitukselle
	void LaivaLoppupaa(){
		EventHandler loppupaa=new EventHandler() {
			@Override
			public void handle(Event event) {
				if(SiirLaiva!=null) {


					Poyta.Solu source = (Poyta.Solu) event.getSource();
					Boolean test=source.poyta.AsetaLaiva(source.kX,source.kY, valittuLaiva);
					System.out.println(test);
					if(test){
						//venepalikka pois toiminnasta
						bp.getChildren().remove(SiirLaiva);
						SiirLaiva = null;
						lahde.setOnMouseClicked(null);
						lahde.setFill(Color.GRAY);
						lahde=null;
					}else{
						Duration aika=new Duration(500);

						FadeTransition valahdys=new FadeTransition(aika,SiirLaiva);
						valahdys.setFromValue(0.5);
						valahdys.setToValue(1.0);
						valahdys.play();
					}


				}
			}
		};
		((Poyta)bp.getCenter()).AsetaEvent(loppupaa );
	}


	//Asettaa valitulle Poydalle eventhandlerit olemaan ammunta hommelipi kyllä
	void AmmuntaValmius(){
		((Label)((HBox)bp.getTop()).getChildren().get(0)).setText("Ammu "+((Poyta)bp.getCenter()).annaNimi()+":n pöytää");
		voikoAmpua=true;
		bp.setBottom(null);
		((Poyta)bp.getCenter()).NaytaAmmuttu();
		EventHandler Ammutaan=new EventHandler() {
			@Override
			public void handle(Event event) {
				Media sound = new Media(new File("src/main/resources/assets/sound.mp3").toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				
				Poyta.Solu Kohde=((Poyta.Solu)event.getSource());
				if(voikoAmpua){
				if(!Kohde.ammuttu){
					Laiva KasitLaiva=Kohde.Ammu();
					mediaPlayer.play();
					System.out.println(Kohde);
					if(KasitLaiva!=null){
						KasitLaiva.osuma();
						if (KasitLaiva.onkoElossa() == false) {
							Kohde.poyta.laivamaara--;
							if(Kohde.poyta.laivamaara <= 0) {
								voitettu=true;
								
								final Stage dialog = new Stage();
				                dialog.initModality(Modality.APPLICATION_MODAL);
				                dialog.initOwner(Ohjelma.tila);
				                VBox dialogVbox = new VBox(20);
				                dialogVbox.getChildren().add(new Text(Kohde.poyta.annaNimi() + " voitti pelin!"));
								Button RESETOI=new Button("Aloita alusta");
								RESETOI.setId("RESET");
								RESETOI.setOnAction(event1 -> Ohjelma.alusta(Ohjelma.tila));
								dialogVbox.getChildren().add(RESETOI);
				                Scene dialogScene = new Scene(dialogVbox, 300, 200);
				                dialog.setScene(dialogScene);
				                dialog.show();

								
								System.out.println("PELI LOPPUI");
							}
						}
						voikoAmpua=true;
						Kohde.setFill(Color.INDIANRED);
						Kohde.poyta.PiirPallo(Kohde,Color.RED);
					}else {
						voikoAmpua=false;

						Kohde.setFill(Color.YELLOW);
					}
				}else{
					//ammuttu jo
				}
				}else {
					//ei voi ampua
				}
				Duration aika=new Duration(500);
				FadeTransition valahdys=new FadeTransition(aika,Kohde);
				valahdys.setFromValue(0.5);
				valahdys.setToValue(1.0);
				valahdys.play();

			}
		};
		((Poyta)bp.getCenter()).AsetaEvent(Ammutaan);
	}


	//Laivojen asetus
	void LaivojenAsetus(){

		//1 pelaaja asettaa laivat
		LaivanSijoitus(0);
		Button nappi=new Button("MENE TOISEEN PELAAJAAN");
		nappi.setId("Meno");
		Button VuoroNappi=new Button("Anna vuoro");


		EventHandler VaihdaVuoroa=new EventHandler() {
			@Override
			public void handle(Event event) {

				((Label)((HBox)bp.getTop()).getChildren().get(0)).setText("");
				nappi.disableProperty().setValue(true);
				Button nayta=new Button("Naytta pöytä");

				if(bp.getCenter()==poydat.get(0)){
					nappi.setText("Anna vuoro");
					nayta.setOnAction(event1 -> {
						nappi.setDisable(false);
						System.out.println("VAIHDETAAN PELAAJAAN2");
						System.out.println(bp.getCenter());
						lataaPelaaja(1);
						AmmuntaValmius();;
					});
				}else{
					nayta.setOnAction(event1 -> {
						nappi.setDisable(false);
						nappi.setText("Anna vuoro");
						System.out.println("VAIHDETAAN PELAAJAAN1");
						System.out.println(bp.getCenter());
						lataaPelaaja(0);
						AmmuntaValmius();;
					});
				}


				bp.setCenter(nayta);
			}
		};

		nappi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LaivanSijoitus(1);
				nappi.setText("Aloita peli");
				nappi.setOnAction(VaihdaVuoroa);
			}
		});


		((HBox)bp.getTop()).getChildren().addAll(nappi);


		//NÄppäimistökuuntentnententnentlua  tarpeen mukaisesti
		Scene Pelaajascene=Ohjelma.tila.getScene();
		Pelaajascene.setOnKeyPressed(event -> {
			System.out.println(event);

			//Pyörittäää laivaa kyllä
			//Spin me right round baby right round
			if(event.getCode()==KeyCode.R){
				if(SiirLaiva!=null){
					valittuLaiva.horizontal=(!valittuLaiva.horizontal);
					Double L=SiirLaiva.getWidth();
					SiirLaiva.setWidth(SiirLaiva.getHeight());
					SiirLaiva.setHeight(L);
				}
				cb.fire();
			}
			if(event.getCode()==KeyCode.SPACE){
				nappi.requestFocus();
			}
			//Näppäimillä pyöriä ympärriöääää ämpäri ja entteristä valehdellaan hiiren painallus
			if(event.getCode()== KeyCode.ENTER){
				System.out.println(Pelaajascene.focusOwnerProperty().get());
				Event.fireEvent(Pelaajascene.focusOwnerProperty().get(), new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
						0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
						true, true, true, true, true, true, null));
			}
		});

		//2 pelaaja asettaa laivat
		//asetetaan peli tila-> ladataan Tyhjä(?)//tai lauta jossa ei näy tavarat, eli vaikka värjätään kaikki solut sopivaksi
		// ->asetetaan uuden kuuntelijan painamiselle, ja kun painaa syö resurssin(vuoro liike) ja ampuu solua, jos osuu lisää takaisin resurssin, eli vain vaivaloisesti sanottuna uusi amunta
		//-> kun resurssit on käytetty voi painaa nappulaa mikä on tiputettu johonkin kohtaan näkyville kyllä->tyhjentää boardin näkyvistä, seuraava painallus avaa toisen näkymän
		//->Jatketaan kunnes tossen pelaajan kaikki laivat loppunut
		//->voitto scene(?)
	}


}
