package fr.amu.iut.cc3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.*;

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

    private Map<String,Circle> points = new HashMap<>();
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

        for(Object o : pane.getChildren().toArray()) {
            if (o instanceof Circle) {
                if (((Circle) o).getId() == field.getId()) {
                    ((Circle) o).setCenterX(getXRadarChart(note, axe));
                    ((Circle) o).setCenterY(getYRadarChart(note, axe));
                }
            }
        }
        Circle circle = new Circle();
        circle.setId(field.getId());
        circle.setCenterX(getXRadarChart(note,axe));
        circle.setCenterY(getYRadarChart(note,axe));
        circle.setRadius(5);
        circle.setStyle("-fx-fill-color: Black");
        points.put(field.getId(), circle);
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
                if (((Circle) o).getId() != null) {
                    pane.getChildren().remove(o);

                }
            }
            if (o instanceof Line)
                if (((Line) o).getId() != null)
                    pane.getChildren().remove(o);
        }
        points.clear();
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


        Line line1 = new Line();
        line1.startXProperty().bind(points.get("competence1").centerXProperty());
        line1.startYProperty().bind(points.get("competence1").centerYProperty());
        line1.endXProperty().bind(points.get("competence2").centerXProperty());
        line1.endYProperty().bind(points.get("competence2").centerYProperty());
        line1.setId("line1");

        Line line2 = new Line();
        line2.startXProperty().bind(points.get("competence2").centerXProperty());
        line2.startYProperty().bind(points.get("competence2").centerYProperty());
        line2.endXProperty().bind(points.get("competence3").centerXProperty());
        line2.endYProperty().bind(points.get("competence3").centerYProperty());
        line2.setId("line2");

        Line line3 = new Line();
        line3.startXProperty().bind(points.get("competence3").centerXProperty());
        line3.startYProperty().bind(points.get("competence3").centerYProperty());
        line3.endXProperty().bind(points.get("competence4").centerXProperty());
        line3.endYProperty().bind(points.get("competence4").centerYProperty());
        line3.setId("line3");

        Line line4 = new Line();
        line4.startXProperty().bind(points.get("competence4").centerXProperty());
        line4.startYProperty().bind(points.get("competence4").centerYProperty());
        line4.endXProperty().bind(points.get("competence5").centerXProperty());
        line4.endYProperty().bind(points.get("competence5").centerYProperty());
        line4.setId("line4");

        Line line5 = new Line();
        line5.startXProperty().bind(points.get("competence5").centerXProperty());
        line5.startYProperty().bind(points.get("competence5").centerYProperty());
        line5.endXProperty().bind(points.get("competence6").centerXProperty());
        line5.endYProperty().bind(points.get("competence6").centerYProperty());
        line5.setId("line5");

        Line line6 = new Line();
        line6.startXProperty().bind(points.get("competence6").centerXProperty());
        line6.startYProperty().bind(points.get("competence6").centerYProperty());
        line6.endXProperty().bind(points.get("competence1").centerXProperty());
        line6.endYProperty().bind(points.get("competence1").centerYProperty());
        line6.setId("line6");

        pane.getChildren().addAll(line1,line2, line3, line4, line5, line6 );
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
