package ch.fhnw.project;

import ch.fhnw.project.datenmodell.DataModel;
import ch.fhnw.project.io.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

            for (DataModel model : fp.getList()) {
                for (double d : model.getValues()) {
                    //System.out.print(d);

                }
                System.out.println();

            }
           // series2.getData().add(new XYChart.Data(4.2, 193.2));


        sc.getData().addAll(series1);


//---------------------------histogram----------------------------------------------------------------------------
        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        final BarChart<String,Number> barChart = new BarChart<>(xAxis1,yAxis1);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Axis1");
        yAxis.setLabel("Axis2");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Histogram");
        series2.getData().add(new XYChart.Data("0-10", 2));
        series2.getData().add(new XYChart.Data("10-20", 3));
        series2.getData().add(new XYChart.Data("20-30", 5));
        series2.getData().add(new XYChart.Data("30-40", 3));
        series2.getData().add(new XYChart.Data("40-50", 4));

           // barChart.getData().addAll(serie2);


        final CategoryAxis xAxis2 = new CategoryAxis();
            xAxis2.setTickLabelsVisible(false);

            final NumberAxis yAxis2 = new NumberAxis();
            yAxis2.setTickLabelsVisible(false);
        final BarChart<String,Number> barChart2 = new BarChart<>(xAxis1,yAxis1);
        barChart2.setCategoryGap(0);
        barChart2.setBarGap(0);



//-------------------------------------------------------------------------------------------------------




        HBox firstLine = new HBox();
        firstLine.getChildren().addAll(cb,cb2, button,checkbox);
        firstLine.setAlignment(Pos.CENTER);
        firstLine.setSpacing(10);
        firstLine.setPadding(new Insets(5, 5, 5, 5));


        HBox scatterChartLine = new HBox();
        scatterChartLine.getChildren().addAll(sc);

        scatterChartLine.setSpacing(10);
        scatterChartLine.setPadding(new Insets(5, 5, 5, 5));


        HBox histogramLine = new HBox(barChart,barChart2);
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


}
