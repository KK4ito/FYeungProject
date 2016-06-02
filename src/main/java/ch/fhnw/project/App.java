package ch.fhnw.project;

import ch.fhnw.project.datenmodell.Variable;
import ch.fhnw.project.gui.HistogramChart;
import ch.fhnw.project.io.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;




public class App extends Application {

	NumberAxis scxAxis = new NumberAxis();
	 NumberAxis scyAxis = new NumberAxis();

	 NumberAxis linexAxis = new NumberAxis();
	 NumberAxis lineyAxis = new NumberAxis();

	 StackPane root = new StackPane();
	 LineChart<Number, Number> lineChart ;

	ComboBox cb = new ComboBox();
	ComboBox cb2 = new ComboBox();





	@Override
	public void start(Stage stage) {

		StackPane pane = new StackPane();

		FileChooser filechooser = new FileChooser();
		File file = filechooser.showOpenDialog(stage);
	//	File file = new File("bin/helvetia.txt");
	//	File file = new File("bin/temperatur-basel-all.txt");
	//	File file = new File("bin/survey-results.txt");
	//	File file = new File("bin/test.txt");

		final ColorPicker colorPicker = new ColorPicker();
		//colorPicker.setValue(Color.CORAL);

		Slider slider = new Slider(0,100,20);

		FileParser fp;
		try {

			fp= makeObject(file);
			fp.readData(file);
			Label label;

			if(file.exists()){
				label = new Label("Found");
			}
			else {
				label = new Label("NA");
			}



			CheckBox checkbox = new CheckBox("Line");
			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					lineChart.setVisible(newValue);
                    root.requestLayout();
				}
			});



			for (Variable model : fp.readData(file)) {

				cb.getItems().add(model.getName());
				cb2.getItems().add(model.getName());
			}


			cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
					System.out.println(cb.getItems().get((Integer) number2));

				}
			});

			cb2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
					System.out.println(cb2.getItems().get((Integer) number2));
				}
			});




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
		//	lineChart.setTitle(file.getName());
			lineChart.setVisible(checkbox.isSelected());


			ScatterChart<Number, Number> sc = getScatterChart(file,fp);

            lineChart.getData().addAll(createChartData(fp.readData(file),0,0));

			HistogramChart hi1 = new HistogramChart(fp.readData(file),0);
			HistogramChart hi2 = new HistogramChart(fp.readData(file),1);


			colorPicker.setOnAction(new EventHandler() {
				public void handle(Event t) {

					lineChart.getData().addAll(createChartData(fp.readData(file),0,0));

				}
			});






			HBox firstLine = new HBox();
			firstLine.getChildren().addAll(cb,cb2, label,checkbox, colorPicker,slider);
			firstLine.setAlignment(Pos.CENTER);
			firstLine.setSpacing(10);
			firstLine.setPadding(new Insets(5, 5, 5, 5));


			HBox histogram = new HBox();
			histogram.getChildren().addAll(hi1.collectionAll(), hi2.collectionAll());
			histogram.setSpacing(1);
			histogram.setPadding(new Insets(5, 5, 5, 5));


			root = new StackPane();


			root.getChildren().addAll(sc, lineChart);



			System.out.println(sc.getData().get(0).getData());
			System.out.println(lineChart.getData().get(0).getData());
			VBox vBox = new VBox();
			vBox.getChildren().addAll(firstLine,root,histogram);
			vBox.setAlignment(Pos.CENTER);
			vBox.setSpacing(10);
			vBox.setPadding(new Insets(5, 5, 5, 5));



			pane.getChildren().add(vBox);

			Scene scene = new Scene(pane, 800, 800);

			stage.setScene(scene);
			stage.setTitle("Hello JavaFX!");
			stage.show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

    private ScatterChart<Number, Number> getScatterChart(File file,FileParser fp) {
        ScatterChart<Number,Number> sc = new ScatterChart<>(scxAxis,scyAxis);

        sc.setTitle(file.getName());
        lineChart.setTitle(file.getName());
        sc.setPrefSize(1000,600);
        sc.setLegendVisible(false);
        sc.getData().addAll(createChartData(fp.readData(file),0,0));
        return sc;
    }


	public FileParser makeObject(File file) throws FileNotFoundException {
		String fileNameFormat = file.getAbsolutePath();
		return (FileParser)(fileNameFormat.endsWith(".txt")?new TabDelimited():(fileNameFormat.endsWith(".lin")?new LineOriented():null));
	}

	private Series<Number, Number> createChartData(List<Variable> lst, int var1, int var2) {
		XYChart.Series<Number, Number> series1;
		series1 = new XYChart.Series<>();

		if(lst.size() == 2){
			Variable dm1 = lst.get(0);
			Variable dm2 = lst.get(1);
			if(!(dm1.getValues().size() == dm2.getValues().size())){
				System.out.println("ERROR");
			}
			else{
				System.out.println("OK");
				scxAxis.setLabel(dm1.getName());
				scyAxis.setLabel(dm2.getName());
                linexAxis.setLabel(dm1.getName());
                lineyAxis.setLabel(dm2.getName());

				for (int i = 0 ; i < dm1.getValues().size(); i++){
					XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(dm1.getValue(i), dm2.getValue(i));
					series1.getData().add(dataPoint);
					Circle circle = new Circle();
					circle.setRadius(3);		//TODO SCALE
					dataPoint.setNode(circle);

				}

				return series1;
			}

		}
		else {
			//TODO for more Var.
		}
		System.out.println("ERROR");
		return null;
	}




	public static void main(String[] args){
		launch(args);
	}


}
