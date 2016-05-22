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
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLClientInfoException;
import java.util.*;

public class App extends Application {

	NumberAxis scxAxis = new NumberAxis();
	 NumberAxis scyAxis = new NumberAxis();

	 NumberAxis linexAxis = new NumberAxis();
	 NumberAxis lineyAxis = new NumberAxis();

	 StackPane root = new StackPane();
	 LineChart<Number, Number> lineChart ;
	@Override
	public void start(Stage stage) {

		//	FileChooser filechooser = new FileChooser();
		//	File file = filechooser.showOpenDialog(stage);
		File file = new File("bin/helvetia.txt");
		//	File file = new File("bin/temperatur-basel-all.txt");

		FileParser fp;
		try {

			fp = getData(file);
			fp.readData(file);
			Button button;
			if(file.exists()){
				button = new Button("Found");
			}
			else {
				button = new Button("NA");
			}



			CheckBox checkbox = new CheckBox("Line");
			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					lineChart.setVisible(newValue);
					root.requestLayout();
				}
			});


			ChoiceBox cb = new ChoiceBox();
			cb.setItems(FXCollections.observableArrayList(
					"1", "2 ", "3", "4")
			);

			ChoiceBox cb2 = new ChoiceBox();
			cb2.setItems(FXCollections.observableArrayList(
					"1", "2 ", "3", "4")
			);



			ScatterChart<Number,Number> sc = new ScatterChart<>(scxAxis,scyAxis);
			sc.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");


			lineChart = new LineChart<>(linexAxis, lineyAxis);
			lineChart.setLegendVisible(false);
			lineChart.setAnimated(false);
			lineChart.setCreateSymbols(true);
			lineChart.setAlternativeRowFillVisible(false);
			lineChart.setAlternativeColumnFillVisible(false);
			lineChart.setHorizontalGridLinesVisible(false);
			lineChart.setVerticalGridLinesVisible(false);
			lineChart.getXAxis().setVisible(false);
			lineChart.getYAxis().setVisible(false);
			lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
			lineChart.getStylesheets().addAll(getClass().getResource("chart.css").toExternalForm());
			lineChart.setVisible(checkbox.isSelected());



			sc.setTitle(file.getName());

			sc.setPrefSize(1000,600);
			sc.setLegendVisible(false);

			lineChart.getData().addAll(createChartData(fp.getList()));
			sc.getData().addAll(createChartData(fp.getList()));

			HBox firstLine = new HBox();
			firstLine.getChildren().addAll(cb,cb2, button,checkbox);
			firstLine.setAlignment(Pos.CENTER);
			firstLine.setSpacing(10);
			firstLine.setPadding(new Insets(5, 5, 5, 5));

			/*
			HBox scatterChartLine = new HBox();
			scatterChartLine.getChildren().addAll(sc);
			scatterChartLine.setSpacing(10);
			scatterChartLine.setPadding(new Insets(5, 5, 5, 5));


			HBox histogramLine = new HBox();
			histogramLine.getChildren().addAll();
			histogramLine.setAlignment(Pos.CENTER);
			histogramLine.setSpacing(10);
			histogramLine.setPadding(new Insets(5, 5, 5, 5));
*/

			root = new StackPane();

			root.getChildren().addAll(sc, lineChart);

			System.out.println(sc.getData().get(0).getData());
			System.out.println(lineChart.getData().get(0).getData());
			VBox vBox = new VBox();
			//vBox.getChildren().addAll(firstLine,scatterChartLine,histogramLine);
			vBox.getChildren().addAll(firstLine,root);
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

	private Series<Number, Number> createChartData(List<DataModel> lst) {
		XYChart.Series<Number, Number> series1 =  new XYChart.Series<>();
		series1 = new XYChart.Series<>();

		if(lst.size() == 2){
			DataModel dm1 = lst.get(0);
			DataModel dm2 = lst.get(1);
			if(!(dm1.getValues().size() == dm2.getValues().size())){
				System.out.println("ERROR");
			}
			else{
				System.out.println("OK");
				scxAxis.setLabel(dm1.getName());
				scyAxis.setLabel(dm2.getName());
				for (int i = 0 ; i < dm1.getValues().size(); i++){
					series1.getData().add(new XYChart.Data<Number, Number> (dm1.getValue(i), dm2.getValue(i)));

				}
				return series1;
			}

		}
		else {
			//TODO
		}
		System.out.println("ERROR");
		return null;
	}

	public static void main(String[] args){
		launch(args);
	}


}
