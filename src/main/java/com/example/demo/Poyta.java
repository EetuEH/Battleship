package com.example.demo;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Poyta extends Parent {
    private VBox vertikaali=new VBox();
    private String nimi="";
    public ArrayList<Laiva> laivat=new ArrayList<Laiva>();
    private PelaajanNakyma Isanta;
    int laivamaara;
    public int HAV;
    public int SUC;
    public int RIS;
    public int TAI;
    public int LEN;
    Poyta(String pelaaja, EventHandler handler,int HAV,int SUC,int RIS,int TAI,int LEN,int koko){
        this.nimi=pelaaja;
        this.HAV=HAV;
        this.SUC=SUC;
        this.RIS=RIS;
        this.TAI=TAI;
        this.LEN=LEN;
        for(int y=0;y<koko;y++){
            HBox horizontti=new HBox();
            for(int x=0;x<koko;x++){
                Solu solu=new Solu(x,y,this);
                solu.setOnMouseClicked(handler);
                horizontti.getChildren().add(solu);
            }
            vertikaali.getChildren().add(horizontti);

        }
        getChildren().add(vertikaali);

    }

    public String annaNimi(){
        return nimi;
    }
    public void setIsanta(PelaajanNakyma syot){
        this.Isanta=syot;
    }
    public PelaajanNakyma getIsanta(){
        return this.Isanta;
    }


    public Solu getSolu(int x,int y){
        if(x<((VBox)getChildren().get(0)).getChildren().size() && y<((HBox)((VBox)getChildren().get(0)).getChildren().get(0)).getChildren().size())
            return (Solu)((HBox)((VBox)getChildren().get(0)).getChildren().get(y)).getChildren().get(x);
        else{
            return null;

        }
    }

    public  void AsetaEvent(EventHandler tapahtuma){
        for(Node rivi : vertikaali.getChildren()){
            for(Node solu : ((HBox) rivi).getChildren()){
                solu.setOnMouseClicked(tapahtuma);
            }
        }
    }

    public boolean AsetaLaiva(int X, int Y, Laiva laiva){

        if(VoikoAsettaaLaiva(X,Y,laiva)){
            laivamaara+=1;
            laiva.EnsimSolu=getSolu(X,Y);
            for(int j = 0; j<laiva.koko; j++){
                if(!laiva.horizontal){
                    getSolu(X,Y+j).aseta(laiva);
                }else{
                    getSolu(X+j,Y).aseta(laiva);
                }
            }
            laivat.add(laiva);
            PiirLaivat();
            return true;
        }else{
            System.out.println("Ei voi");
            return false;
        }

    }

    public Boolean VoikoAsettaaLaiva(int X, int Y,Laiva laiva){

            for (int j = 0; j < laiva.koko; j++) {
                if (!laiva.horizontal) {
                    if (getSolu(X, Y + j) == null) return false;
                    else {
                        if (getSolu(X, Y + j).laiva != null) return false;
                    }
                } else {
                    if (getSolu(X + j, Y) == null) return false;
                    else {
                        if (getSolu(X + j, Y).laiva != null) return false;
                    }
                }
            }
            return true;

    }


    //piilottaa näkyvyydestä
    public void PiilotaSolut(){
        //Poistaa siirto elementtejä'
        ArrayList<Node> palaset=new ArrayList<Node>();
        for(Node palanen: this.getChildren()){
            if(palanen.getId()=="Piirto"){
                palaset.add(palanen);
            }
        }
        for (Node palanen:palaset
             ) {
            this.getChildren().remove(palanen);
        }

        //Soluvärejä
        for (Node rivi:vertikaali.getChildren()
             ) {
            for (Node solu:((HBox)rivi).getChildren()
                 ) {
                ((Solu)solu).setFill(Color.BLUE);
            }
        }
    }
    //piirtää laivat kaikki kyllä
    public void PiirLaivat(){
        System.out.println("Piirrä laivat");
        for (Laiva paatti:laivat
             ) {
            int X=paatti.EnsimSolu.kX;
            int Y=paatti.EnsimSolu.kY;
            Color vari;
            //Tällä voi piirtää muotoa laivalle
            switch (paatti.tyyppi){
                case "H":
                    vari=Color.ORANGE;
                    break;
                case "S":
                    vari=Color.ORANGERED;
                    break;
                case "R":
                    vari=Color.RED;
                    break;
                case "T":
                    vari=Color.PALEVIOLETRED;
                    break;
                case "L":
                    vari=Color.VIOLET;
                    break;
                default:
                    vari=Color.BLACK;
                    break;
            }
            for(int j=0;j< paatti.koko;j++){
                if(!paatti.horizontal){
                    Solu sol=getSolu(X,Y+j);
                    getSolu(X,Y+j).setFill(Color.DARKSEAGREEN);


                    PiirPallo(sol,vari);


                }else{
                    Solu sol=getSolu(X+j,Y);
                    sol.setFill(Color.DARKSEAGREEN);
                    PiirPallo(sol,vari);
                }
            }
        }
    }
    //näyttää ammutut ruudut
    public void NaytaAmmuttu(){
        System.out.println("Näytä ammutut");
        PiilotaSolut();
        Color vari=Color.BLACK;
        for (Node rivi:vertikaali.getChildren()
        ) {
            for (Node solu:((HBox)rivi).getChildren()
            ) {
                if(((Solu)solu).laiva!=null&&((Solu)solu).ammuttu){
                    switch (((Solu)solu).laiva.tyyppi){
                        case "H":
                            vari=Color.ORANGE;
                            break;
                        case "S":
                            vari=Color.ORANGERED;
                            break;
                        case "R":
                            vari=Color.RED;
                            break;
                        case "T":
                            vari=Color.PALEVIOLETRED;
                            break;
                        case "L":
                            vari=Color.VIOLET;
                            break;
                        default:
                            vari=Color.BLACK;
                            break;
                    }
                    ((Solu)solu).setFill(vari);
                    PiirPallo(solu,Color.RED);
                }else if(((Solu)solu).ammuttu){
                    ((Solu)solu).setFill(Color.YELLOW);
                }
            }
        }
    }
    //piirtää pallon tahdätyn solun päälle
    public void PiirPallo(Node kohde, Color vari){
        //Punainen pallo ammuttuun kohtaan
        Double VX=kohde.getBoundsInParent().getMinX();
        Double HY=kohde.getParent().getBoundsInParent().getMinY();
        Circle pallo=new Circle(10,vari);
        pallo.setMouseTransparent(true);
        pallo.setId("Piirto");
        pallo.setCenterX(VX+20);
        pallo.setCenterY(HY+20);
        this.getChildren().add(pallo);
    }


    @Override
    public String toString() {
        return "Poyta{" +
                ", nimi='" + nimi + '\'' +
                ", laivat=" + laivat +
                '}';
    }




    public class Solu extends Rectangle{
        public Laiva laiva=null;
        public boolean ammuttu=false;
        public Poyta poyta;
        public int kX;
        public int kY;


        Solu(int X,int Y,Poyta poyta){
            super(40,40);
            this.kX=(X);
            this.kY=(Y);
            this.poyta=poyta;
            setFill(Color.BLUE);
            setStroke(Color.BLACK);

            this.setFocusTraversable(true);
            this.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                if(newValue){
                    this.setStroke(Color.CYAN);
                    poyta.getIsanta().SiirHiir();
                }
                else if(oldValue){
                    this.setStroke(Color.BLACK);
                }
            }));


        }
        public Laiva Ammu(){
            this.ammuttu=true;
            if(this.laiva==null){
                return null;
            }else {
                return this.laiva;
            }
        }
        public void aseta(Laiva vene){
            this.laiva=vene;
            setFill(Color.GREEN);
        }
        @Override
        public String toString() {
            return "Solu{"
                    +"poyta=" + poyta.nimi +
                    ", X=" + this.kX +
                    " , Y=" + this.kY +
                    " , laiva=" + laiva +
                    ", ammuttu=" + ammuttu+
                    '}';
        }
    }
}
