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
import java.util.Collections;
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
        circle.setId(field.getId());
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

        for(Object o : pane.getChildren().toArray()) {
            if (o instanceof Circle) {
                if (((Circle) o).getId() != null)
                    pane.getChildren().remove(o);
            }
            if (o instanceof Line)
                if (((Line) o).getId() != null)
                    pane.getChildren().remove(o);
        }
    }
    @FXML
    private void tracer(){
        ArrayList<Circle> tab = new ArrayList<Circle>();
        for(Object o : pane.getChildren().toArray())
            if(o instanceof Circle)
                if(((Circle) o).getId()!=null) {
                    tab.add(((Circle) o));
                }
        if(tab.size()<6)
            return;

        Circle c1 = new Circle();
        Circle c2 = new Circle();;
        Circle c3 = new Circle();;
        Circle c4 = new Circle();;
        Circle c5 = new Circle();;
        Circle c6 = new Circle();;

        for (Circle c : tab)
            switch (c.getId()){
                case "competence1":
                     c1 = c;
                case "competence2":
                     c2 = c;
                case "competence3":
                     c3 = c;
                case "competence4":
                     c4 = c;
                case "competence5":
                     c5 = c;
                case "competence6":
                     c6 = c;
            }
        Line line1 = new Line(c1.getCenterX(),c1.getCenterY(),c2.getCenterX(),c2.getCenterY());
        line1.setId("line1");
        Line line2 = new Line(c2.getCenterX(),c2.getCenterY(),c3.getCenterX(),c3.getCenterY());
        line2.setId("line2");
        Line line3 = new Line(c3.getCenterX(),c3.getCenterY(),c4.getCenterX(),c4.getCenterY());
        line3.setId("line3");
        Line line4 = new Line(c4.getCenterX(),c4.getCenterY(),c5.getCenterX(),c5.getCenterY());
        line4.setId("line4");
        Line line5 = new Line(c5.getCenterX(),c5.getCenterY(),c6.getCenterX(),c6.getCenterY());
        line5.setId("line5");
        Line line6 = new Line(c6.getCenterX(),c6.getCenterY(),c1.getCenterX(),c1.getCenterY());
        line6.setId("line6");

        pane.getChildren().addAll(line1, line2, line3, line4, line5, line6);
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
