package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @FXML
    TextField competence1;
    @FXML
    TextField competence2;
    @FXML
    TextField competence3;
    @FXML
    TextField competence4;
    @FXML
    TextField competence5;
    @FXML
    TextField competence6;
    @FXML
    Pane pane;
    @FXML
    Label erreur;
    @FXML
    Label erreurName;
    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void addPoint(ActionEvent event) {
        TextField field = (TextField)event.getSource();
        if (!isNumeric(field.getText()) || Double.parseDouble(field.getText())>20 || Double.parseDouble(field.getText())<0){
            erreur.setText("Erreur de saisie :");
            erreur.setTextFill(Color.RED);
            erreurName.setTextFill(Color.RED);
            if(!isNumeric(field.getText()))
                erreurName.setText("les valeurs doivent etre decimales");
            else
                erreurName.setText("les valeurs doivent etre entre 0 et 20");
            return;
        }

        Double note = Double.parseDouble(field.getText());
        System.out.println(field.getText() + field.getId());
        int axe = 0;
        switch (field.getId()){
            case "competence1":
                axe = 1;
                break;
            case "competence2":
                axe = 2;
                break;
            case "competence3":
                axe = 3;
                break;
            case "competence4":
                axe = 4;
                break;
            case "competence5":
                axe = 5;
                break;
            case "competence6":
                axe = 6;
                break;
        }
        Circle circle = new Circle();
        circle.setId("Point");
        circle.setCenterX(getXRadarChart(note,axe));
        circle.setCenterY(getYRadarChart(note,axe));
        circle.setRadius(5);
        circle.setStyle("-fx-fill-color: Black");
        pane.getChildren().add(circle);
    }

    @FXML
    private void clear(){
        competence1.setText("");
        competence2.setText("");
        competence3.setText("");
        competence4.setText("");
        competence5.setText("");
        competence6.setText("");
        erreur.setText("");
        erreurName.setText("");

        for(Object o : pane.getChildren().toArray())
            if(o instanceof Circle)
                if(((Circle) o).getId()=="Point")
                    pane.getChildren().remove(o);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

}
