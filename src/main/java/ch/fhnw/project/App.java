package ch.fhnw.project;

import ch.fhnw.project.datenmodell.DataModel;
import ch.fhnw.project.io.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLClientInfoException;
import java.util.*;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(stage);

        FileParser fp;
        try {
            fp = getData(file);
            fp.readData(file);



        Button button = new Button("Click me...");


        Label label = new Label("My first button:");
        Label label2 = new Label("The second button:");

        final CheckBox checkbox = new CheckBox("checkBox");



        ChoiceBox cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList(
                "1", "2 ", "3", "4")
        );

        ChoiceBox cb2 = new ChoiceBox();
        cb2.setItems(FXCollections.observableArrayList(
                "1", "2 ", "3", "4")
        );

         NumberAxis xAxis = new NumberAxis();
         NumberAxis yAxis = new NumberAxis();
         ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);


        xAxis.setLabel("Axis1");
        yAxis.setLabel("Axis2");
        sc.setTitle("...");

        sc.setPrefSize(800,400);
            sc.setLegendVisible(false);

        XYChart.Series series1 = new XYChart.Series();


            List<XYChart.Series<Double, Double>> answer = FXCollections.observableArrayList();
            XYChart.Series<Double, Double> aSeries = new XYChart.Series<Double, Double>();
            List<List> liste;
            List<String> list;




            for(DataModel m : fp.getList()){
                String name = m.getName();
                List<Double> lstValue = m.getValues();


                for (int i =0;i<lstValue.size();i++){
                   // series1.getData().add(new XYChart.Data(i, lstValue.get(i)));
                    System.out.print(lstValue.get(i));
                }
                System.out.println();
            }

            /*for(DataModel m : fp.getList()){

                for(double d : m.getValues()){
                    System.out.print(d + "/" );
                }
                System.out.println();
            }
*/

        sc.getData().addAll(series1);







        HBox firstLine = new HBox();
        firstLine.getChildren().addAll(cb,cb2, button,checkbox);
        firstLine.setAlignment(Pos.CENTER);
        firstLine.setSpacing(10);
        firstLine.setPadding(new Insets(5, 5, 5, 5));


        HBox scatterChartLine = new HBox();
        scatterChartLine.getChildren().addAll(sc);

        scatterChartLine.setSpacing(10);
        scatterChartLine.setPadding(new Insets(5, 5, 5, 5));


        HBox histogramLine = new HBox();
        histogramLine.getChildren().addAll();
        histogramLine.setAlignment(Pos.CENTER);
        histogramLine.setSpacing(10);
        histogramLine.setPadding(new Insets(5, 5, 5, 5));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(firstLine,scatterChartLine,histogramLine);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(5, 5, 5, 5));


        StackPane pane = new StackPane();
        pane.getChildren().add(vBox);

        Scene scene = new Scene(pane, 800, 800);

        stage.setScene(scene);
        stage.setTitle("Hello JavaFX!");
        stage.show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public FileParser getData(File file) throws FileNotFoundException {
        String fileNameFormat = file.getAbsolutePath();
        FileParser fp;
        if (fileNameFormat.endsWith(".txt")) {

            return new TabDelimited();

        } else if (fileNameFormat.endsWith(".lin")) {

            return new LineOriented();
        }

        return null;
    }

    private List<XYChart.Series<String, Double>> getChartData() {
        double aValue = 17.56;
        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        XYChart.Series<String, Double> aSeries = new XYChart.Series<String, Double>();

        aSeries.setName("here name of Axis");


        for (int i = 0; i < 10; i++) {
            aSeries.getData().add(new XYChart.Data(Integer.toString(i), aValue));
            aValue = aValue + Math.random() - .5;
        }
        answer.addAll(aSeries);
        return answer;
    }


}
