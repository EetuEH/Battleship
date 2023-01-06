package com.example.demo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.event.ChangeListener;

public class MainMenu extends GridPane{
	EventHandler hiiri;
	Paaikkuna paaohjelma;
	int PINTAALAmax;
	int PINTAALAnyT;
	//passataan Handleri tästä läpi kun se on vissiinkin asiallista

	// Tässä luodaan main menu, jossa kysytään nimet ja pelialustan koko.
	MainMenu(EventHandler handler,Paaikkuna mestari){
		super();
		this.hiiri=handler;
		this.paaohjelma=mestari;
		//Gridpane määritys heitetty tähän
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 40, 40, 40));
		this.setHgap(10);
		this.setVgap(10);

		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		this.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
		//???->

		// main menussa oleva iso otsikko (pelin nimi)
		Label headerLabel = new Label("Battleship");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		headerLabel.setTextFill(Color.web("#FFFFFF"));
		this.add(headerLabel, 0,0,2,1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

		// label joka osittaa paikan, johon pelaaja 1 asettaa nimensä
		Label nameLabel1 = new Label("Player 1: ");
		nameLabel1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		nameLabel1.setTextFill(Color.web("#FFFFFF"));
		this.add(nameLabel1, 0,1);

		// textfield, johon pelaaja 1 asettaa nimensä
		TextField nameField1 = new TextField();
		nameField1.setPrefHeight(40);
		nameField1.setText("teppo");
		this.add(nameField1, 1,1);

		// label joka soittaa paikan, johon pelaaja 2 asettaa nimensä
		Label nameLabel2 = new Label("Player 2: ");
		nameLabel2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		nameLabel2.setTextFill(Color.web("#FFFFFF"));
		this.add(nameLabel2, 0, 2);

		// textfield, johon pelaaja 2 asettaa nimensä
		TextField nameField2 = new TextField();
		nameField2.setText("matti");
		nameField2.setPrefHeight(40);
		this.add(nameField2, 1, 2);

		Label boardLabel = new Label("Boardsize: ");
		boardLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		boardLabel.setTextFill(Color.web("#FFFFFF"));
		this.add(boardLabel, 0, 3);

		// textfield, johon kirjoitetaan peliruudukon koko
		TextField boardField = new TextField();
		boardField.setText("10");
		boardField.setPrefHeight(40);
		this.add(boardField, 1, 3);







//VIIMEHETKEN LAIVAASETUS
		Label LaivMaart = new Label("LAIVAMÄÄRÄ: ");
		LaivMaart.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		LaivMaart.setTextFill(Color.web("#FFFFFF"));
		this.add(LaivMaart, 0, 5);

		// textfield, johon kirjoitetaan peliruudukon koko
		Label LaivMaar = new Label();
		LaivMaar.setText("10");
		LaivMaar.setPrefHeight(40);
		this.add(LaivMaar, 1, 5);


		Label shipSLabel = new Label("Ship surface: ");
		shipSLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		shipSLabel.setTextFill(Color.web("#FFFFFF"));
		this.add(shipSLabel, 0, 6);

		// textfield, johon kirjoitetaan peliruudukon koko
		Label shipSfield = new Label();
		//Kieltämättä selostusta parempaa laivojen määrän määrittämiseen kyllä
		shipSfield.setPrefHeight(40);
		this.add(shipSfield, 1, 6);
		HBox rivi=new HBox();
		rivi.minWidth(boardField.getMinWidth());
		for(int i=0;i<5;i++){
			VBox pallanen=new VBox();
			Label teksti=new Label(Integer.toString(i));
			teksti.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			TextField kent = new TextField();

			kent.setText("1");
			kent.textProperty().addListener((Ob,oldV,newV)->{
				try {


				int yht=0;
				System.out.println((pallanen.getParent()));
				for (Node noodi:((HBox)pallanen.getParent()).getChildren()
				) {

					yht+=Integer.valueOf(((TextField)((VBox) noodi).getChildren().get(1)).getText());
				}
				String LAIVOJENMAARA=Integer.toString(yht);
				LaivMaar.setText(LAIVOJENMAARA);
				PINTAALAmax= (int) Math.round(Integer.valueOf(boardField.getText())*Integer.valueOf(boardField.getText())*0.44);
				PINTAALAnyT=0;
				for (Node noodi:((HBox)pallanen.getParent()).getChildren()
				) {
					PINTAALAnyT+=Integer.valueOf(((TextField)((VBox) noodi).getChildren().get(1)).getText())*Integer.valueOf(((TextField)((VBox) noodi).getChildren().get(1)).getId());
				}
				shipSLabel.setText(Integer.toString(PINTAALAnyT)+"/"+Integer.toString(PINTAALAmax));

			}catch (Error e){
					System.out.println("WAU KENTSÄTÄ PUUTTUUUTUTUTU INTTITITITITITIT");
				}
			});
			pallanen.getChildren().addAll(teksti,kent);
			rivi.getChildren().addAll(pallanen);

		}
		VBox HAVI = (VBox) rivi.getChildren().get(0);
		VBox SUKE = (VBox) rivi.getChildren().get(1);
		VBox RIST = (VBox) rivi.getChildren().get(2);
		VBox TAIS = (VBox) rivi.getChildren().get(3);
		VBox LENT = (VBox) rivi.getChildren().get(4);
		((Label)HAVI.getChildren().get(0)).setText("HÄVITTÄJÄ");
		((TextField)HAVI.getChildren().get(1)).setId("2");
		((Label)SUKE.getChildren().get(0)).setText("SUKELLUSVENE");
		((TextField)SUKE.getChildren().get(1)).setId("3");
		((Label)RIST.getChildren().get(0)).setText("RISTEILIJÄ");
		((TextField)RIST.getChildren().get(1)).setId("3");
		((Label)TAIS.getChildren().get(0)).setText("TAISTELULAIVA");
		((TextField)TAIS.getChildren().get(1)).setId("4");
		((Label)LENT.getChildren().get(0)).setText("LENTOTUKIALUS");
		((TextField)LENT.getChildren().get(1)).setId("5");

		((TextField)HAVI.getChildren().get(1)).setText("5");
		((TextField)SUKE.getChildren().get(1)).setText("4");
		((TextField)RIST.getChildren().get(1)).setText("3");
		((TextField)TAIS.getChildren().get(1)).setText("2");
		((TextField)LENT.getChildren().get(1)).setText("1");
		this.add(rivi,1,4);

		boardField.textProperty().addListener((a,b,c)->{
			int yht=0;
			for (Node noodi : (rivi.getChildren())){

				yht += Integer.valueOf(((TextField) ((VBox) noodi).getChildren().get(1)).getText());
			}

			PINTAALAmax = (int) Math.round(Integer.valueOf(boardField.getText()) * Integer.valueOf(boardField.getText()) * 0.44);
			PINTAALAnyT = 0;
			for (Node noodi : (rivi.getChildren())){

				PINTAALAnyT += Integer.valueOf(((TextField) ((VBox) noodi).getChildren().get(1)).getText()) * Integer.valueOf(((TextField) ((VBox) noodi).getChildren().get(1)).getId());
			}
			shipSLabel.setText(Integer.toString(PINTAALAnyT) + "/" + Integer.toString(PINTAALAmax));

		});
//VIIMEHETKEN LAIVAASETUS



		// nappi, josta peli käynnistyy
		Button submitButton = new Button("Start");
		submitButton.setPrefHeight(40);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(100);
		this.add(submitButton, 0, 7, 2, 1);
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
		MainMenu self=this;


		// erilaisia määrityksiä esim. peliruudukon koko ja että on pakko asettaa pelaajille nimet
		EventHandler nappi=new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(PINTAALAnyT);
				System.out.println(PINTAALAmax);
				if((PINTAALAnyT>PINTAALAmax)) {
					showAlert(Alert.AlertType.ERROR, self.getScene().getWindow(), "Form Error!", "There are too many ships! Lower the amount of ships.");
					return;
				}
				if(Integer.valueOf(boardField.getText()) < 5){
					showAlert(Alert.AlertType.ERROR, self.getScene().getWindow(), "Form Error!", "Boardsize must be at least 5");
					return;
				}
				if(nameField1.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, self.getScene().getWindow(), "Form Error!", "Please enter the name of player 1");
					return;
				}
				if(nameField2.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, self.getScene().getWindow(), "Form Error!", "Please enter the name of player 2");
					return;
				}
				if(boardField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, self.getScene().getWindow(), "Form Error!", "Please enter boardsize");
					return;
				}
				if(nameField1.getText() == nameField2.getText() ) {
					showAlert(Alert.AlertType.ERROR, self.getScene().getWindow(), "Form Error!", "Both players cannot have the same name");
					return;
				}
				//nämä sittemmin jonnekkin?

				int HAV= Integer.parseInt(((TextField)HAVI.getChildren().get(1)).getText());
				int SUK= Integer.parseInt(((TextField)SUKE.getChildren().get(1)).getText());
				int RIS= Integer.parseInt(((TextField)RIST.getChildren().get(1)).getText());
				int TAI= Integer.parseInt(((TextField)TAIS.getChildren().get(1)).getText());
				int LEN= Integer.parseInt(((TextField)LENT.getChildren().get(1)).getText());
				int koko= Integer.parseInt(boardField.getText());
				Poyta yksi=new Poyta(nameField1.getText(),hiiri,HAV,SUK,RIS,TAI,LEN,koko);
				Poyta kaksi=new Poyta(nameField2.getText(),hiiri,HAV,SUK,RIS,TAI,LEN,koko);
				SyotaYloos(yksi,kaksi);
				return;//?

			}
		};
		submitButton.setOnAction(nappi);

	}



	private void SyotaYloos(Poyta yksi, Poyta kaksi){
		PelaajanNakyma peli=new PelaajanNakyma(this.paaohjelma,yksi,kaksi);
		this.paaohjelma.peli=peli;
		peli.AsetaNakyma(0);
	}
	// toteutetaan, jos jokin yllä olevista määrityksistä ei toteudu
	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	 // rakentaa scenen
	public Scene Rakenna(){
		GridPane gridPane = this;
		Scene scene = new Scene(gridPane, 800, 700);
		BackgroundSize backgroundSize = new BackgroundSize(800, 500, true, true, true, true);
		Image image = new Image("https://www.wargamer.com/wp-content/uploads/2021/03/world-of-warships-free-offer-battleship-doubloons-1-580x334.jpg");
		gridPane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				backgroundSize)));
		return scene;
	}
}
