package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OhjeIkkuna{
    Paaikkuna ohjelma;
    OhjeIkkuna(Paaikkuna Ohjelma){
        ohjelma=Ohjelma;
    }
    public void avaa() {
        Stage ikkuna=new Stage();
        ikkuna.setMinHeight(100);
        ikkuna.setMinWidth(600);
        ikkuna.setTitle("Ohjeita");
        Label teksti=new Label("Käyntännössä koko sovellusta pitäisi voida mennä läpi nuoli näppäimillä, täbillä ja Entterillä" );
        Label teksti2=new Label("Laivojen asetus vaiheessa voit ottaa laivan kantoon ja asettaa joko takaisin paikkaansa\n" +
                "Tai asettaa sen ruudukkoonv välilyönnistä keskität näppäin ohjaimen vuoro nappiin");
        VBox palaset=new VBox();
        palaset.getChildren().add(teksti);
        palaset.getChildren().add(teksti2);
        Button RESETOI=new Button("RESET");
        RESETOI.setId("RESET");
        RESETOI.setOnAction(event -> ohjelma.alusta(this.ohjelma.tila));
        palaset.getChildren().add(RESETOI);
        Scene Skene=new Scene(palaset,200 ,200);
        ikkuna.setScene(Skene);
        ikkuna.show();
    }
}




