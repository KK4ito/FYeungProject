package ch.fhnw.project;/**
 * Created by FelixYeung on 26.05.16.
 */

import ch.fhnw.project.datenmodell.Variable;
import ch.fhnw.project.gui.HistogramChart;
import ch.fhnw.project.io.FileParser;
import ch.fhnw.project.io.TabDelimited;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Tst2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        File file = new File("bin/helvetia.txt");
        StackPane pane = new StackPane();

        FileParser fp = new TabDelimited();

        HistogramChart hi1 = new HistogramChart(fp.readData(file),0);
        HistogramChart hi2 = new HistogramChart(fp.readData(file),1);



        pane.getChildren().addAll(hi1.collectionAll(), hi2.collectionAll());

        Scene scene = new Scene(pane, 800, 800);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello JavaFX!");
        primaryStage.show();

    }
}
