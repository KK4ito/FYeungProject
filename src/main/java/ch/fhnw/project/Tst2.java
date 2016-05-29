package ch.fhnw.project;/**
 * Created by FelixYeung on 26.05.16.
 */

import ch.fhnw.project.datenmodell.Variable;
import ch.fhnw.project.gui.HistogramChart;
import ch.fhnw.project.io.FileParser;
import ch.fhnw.project.io.LineOriented;
import ch.fhnw.project.io.TabDelimited;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class Tst2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        final NumberAxis scxAxis = new NumberAxis();
        scxAxis.setForceZeroInRange(false);
        final NumberAxis scyAxis = new NumberAxis();

        final NumberAxis slxAxis = new NumberAxis();
        slxAxis.setForceZeroInRange(false);
        final NumberAxis slyAxis = new NumberAxis();
        final ScatterChart sc = new ScatterChart(scxAxis,scyAxis);
        final LineChart lc = new LineChart(slxAxis,slyAxis);

        final ComboBox cb1 = new ComboBox();
        final ComboBox cb2 = new ComboBox();

        final Label label1 = new Label("Axis1");
        final Label label2 = new Label("Axis2");



        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(primaryStage);



       // File file = new File("bin/helvetia.txt");
        StackPane pane = new StackPane();


        FileParser fp ;

        try {
            fp= makeObject(file);

        fp.readData(file);

        Variable va1 = fp.readData(file).get(0);
        Variable va2 = fp.readData(file).get(1);

        final XYChart.Series serie = new XYChart.Series();
        final XYChart.Series serie1 = new XYChart.Series();
            scxAxis.setLabel(va1.getName());
            scyAxis.setLabel(va2.getName());
            slxAxis.setLabel(va1.getName());
            slyAxis.setLabel(va2.getName());



        CheckBox checkbox = new CheckBox("Line");
        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                lc.setVisible(newValue);

            }
        });

       final Slider slider = new Slider(0,20,5);
       final  ColorPicker colorPicker = new ColorPicker();
         colorPicker.setValue(Color.BLUE);



        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                sc.getData().addAll( createData(va1, va2,slider,colorPicker));

            }

        });


            createDataLine(va1, va2, serie1);


            lc.setLegendVisible(false);
        lc.setAnimated(false);
        lc.setCreateSymbols(true);
        lc.setAlternativeRowFillVisible(false);
        lc.setAlternativeColumnFillVisible(false);
        lc.setHorizontalGridLinesVisible(true);
        lc.setVerticalGridLinesVisible(true);
        lc.getXAxis().setVisible(false);
        lc.getYAxis().setVisible(false);
        lc.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lc.getStylesheets().addAll(getClass().getResource("chart.css").toExternalForm());
        lc.setTitle(file.getName());
        lc.setVisible(checkbox.isSelected());
        lc.getData().add(serie1);

        sc.setLegendVisible(false);
        sc.setTitle(file.getName());
        sc.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        sc.getData().addAll(createData(va1, va2,slider,colorPicker));



        HistogramChart hi1 = new HistogramChart(fp.readData(file),0);
        HistogramChart hi2 = new HistogramChart(fp.readData(file),1);




        StackPane stackpane = new StackPane();
        stackpane.getChildren().addAll(sc,lc);

            FlowPane histo = new FlowPane();
            histo.getChildren().addAll(hi1.collectionAll(),hi2.collectionAll());






        HBox firstLine = new HBox();
        firstLine.getChildren().addAll(label1,cb1,label2,cb2,checkbox,slider,colorPicker);
        firstLine.setAlignment(Pos.CENTER);
        firstLine.setSpacing(15);
        firstLine.setPadding(new Insets(10, 5, 10, 5));
            firstLine.setStyle("-fx-background-color: lightblue");

        /*HBox plotAndLine = new HBox();
        plotAndLine.getChildren().addAll(stackpane);
        plotAndLine.setSpacing(5);
        plotAndLine.setPadding(new Insets(5, 5, 5, 5));*/

       HBox histogram = new HBox();
        histogram.getChildren().addAll(hi1.collectionAll(),hi2.collectionAll());
        histogram.setSpacing(10);
        histogram.setPadding(new Insets(5, 5, 5, 5));
            histogram.setStyle("-fx-background-color: lightyellow");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(firstLine,stackpane,hi1.collectionAll(),hi2.collectionAll());
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(5, 5, 5, 5));


        pane.getChildren().addAll(vBox);

        Scene scene = new Scene(pane, 700, 800);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello JavaFX!");
        primaryStage.show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void createDataLine(Variable va1, Variable va2, XYChart.Series serie1) {
        for(int i =0; i<va1.getValues().size();i++) {

            XYChart.Data dataPoint = new XYChart.Data(va1.getValue(i), va2.getValue(i));
            serie1.getData().add(dataPoint);


            Circle circle = new Circle();
            circle.setRadius(1);
            dataPoint.setNode(circle);
        }
    }

    private XYChart.Series<Number, Number> createData(Variable va1, Variable va2, Slider slider, ColorPicker colorPicker) {

        XYChart.Series<Number, Number> serie = new XYChart.Series();
        for(int i =0; i<va1.getValues().size();i++) {

            XYChart.Data dataPoint = new XYChart.Data(va1.getValue(i), va2.getValue(i));
            serie.getData().add(dataPoint);


            Circle circle = new Circle();

            circle.setRadius(slider.getValue());
            circle.setFill(colorPicker.getValue());

            slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {

                circle.setRadius(slider.getValue());

            });
            dataPoint.setNode(circle);
        }

        return serie;
    }

    public FileParser makeObject(File file) throws FileNotFoundException {
        String fileNameFormat = file.getAbsolutePath();
        return (FileParser)(fileNameFormat.endsWith(".txt")?new TabDelimited():(fileNameFormat.endsWith(".lin")?new LineOriented():null));
    }


}
