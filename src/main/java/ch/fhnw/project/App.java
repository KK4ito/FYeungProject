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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.*;




public class App extends Application {

	List<Variable> lstData;
	Button btnLoadFile = new Button("Import data");
	StackPane pane;
	ColorPicker colorPicker = new ColorPicker();
	Scene scene;
	Stage activeStage;
	Slider slider = new Slider(0,10,5);

	 NumberAxis scxAxis = new NumberAxis();
	 NumberAxis scyAxis = new NumberAxis();

	 NumberAxis linexAxis = new NumberAxis();
	 NumberAxis lineyAxis = new NumberAxis();

	 StackPane root = new StackPane();
	 LineChart<Number, Number> lineChart ;
	ScatterChart<Number, Number> sc;

	ComboBox cb = new ComboBox();
	ComboBox cb2 = new ComboBox();

	File file;
	final FileChooser fileChooser = new FileChooser();


	private void refreshData(String t){
		scxAxis.setLabel(t);
		linexAxis.setLabel(t);

		sc.getData().removeAll();
		lineChart.getData().removeAll();
		List<Variable> newList = new ArrayList<Variable>();
		newList.add(lstData.get(cb.getSelectionModel().getSelectedIndex()));
		newList.add(lstData.get(cb2.getSelectionModel().getSelectedIndex()));
		sc.getData().addAll(createChartData(newList));
		lineChart.getData().add(createChartData(newList));
	}
	public void loadDataFromFile(File file, Stage stage){

		cb.getItems().removeAll(cb.getItems());
		cb2.getItems().removeAll(cb2.getItems());
		try {
			FileParser fp;
			fp = makeObject(file);
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

			cb.getSelectionModel().select(0);
			cb2.getSelectionModel().select(1);
			cb.setDisable(true);
			cb2.setDisable(true);

			cb.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue observableValue, String t, String t2) {
					refreshData(t2);

				}
			});

			cb2.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue observableValue, String t, String t2) {
					refreshData(t2);
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
			lineChart.setVisible(checkbox.isSelected());
			lineChart.getData().addAll(createChartData(fp.readData(file)));

			sc = getScatterChart(file,fp);



			HistogramChart hi1 = new HistogramChart(fp.readData(file),0);
			HistogramChart hi2 = new HistogramChart(fp.readData(file),1);


			colorPicker.setOnAction(new EventHandler() {
				public void handle(Event t) {

					lineChart.getData().addAll(createChartData(fp.readData(file)));

				}
			});






			HBox firstLine = new HBox();
			firstLine.getChildren().addAll(cb,cb2, label,checkbox, colorPicker,slider, btnLoadFile);
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

			pane = new StackPane();
			pane.getChildren().add(vBox);

			scene = new Scene(pane, 800, 800);

			stage.setScene(scene);
			stage.setTitle("Project");
			stage.show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void start(Stage stage) {
		this.activeStage = stage;
		btnLoadFile.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						file = fileChooser.showOpenDialog(activeStage);
						if (file != null) {
							activeStage.close();
							Stage newStage = new Stage();
							newStage.setTitle("New Stage");
							activeStage = newStage;
							loadDataFromFile(file, newStage);
						}
					}
				});


		file = fileChooser.showOpenDialog(stage);
		loadDataFromFile(file, stage);
	//	File file = new File("bin/helvetia.txt");
	//	File file = new File("bin/temperatur-basel-all.txt");
	//	File file = new File("bin/survey-results.txt");
	//	File file = new File("bin/test.txt");



	}

    private ScatterChart<Number, Number> getScatterChart(File file,FileParser fp) {
        ScatterChart<Number,Number> sc = new ScatterChart<>(scxAxis,scyAxis);

        sc.setTitle(file.getName());
        lineChart.setTitle(file.getName());
        sc.setPrefSize(1000,600);
        sc.setLegendVisible(false);
        sc.getData().addAll(createChartData(fp.readData(file)));
        return sc;
    }


	public FileParser makeObject(File file) throws FileNotFoundException {
		String fileNameFormat = file.getAbsolutePath();
		return (FileParser)(fileNameFormat.endsWith(".txt")?new TabDelimited():(fileNameFormat.endsWith(".lin")?new LineOriented():null));
	}

	private Series<Number, Number> createChartData(List<Variable> lst) {
		if(lst.size() > 2 ) {
			cb.setDisable(false);
			cb2.setDisable(false);
			this.lstData = lst;
		}
		if(lst.size() >= 2){
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

				return getXYChartSerie(dm1, dm2);
			}

		}
		else {


		}
		System.out.println("ERROR");
		return null;
	}

	private XYChart.Series<Number, Number> getXYChartSerie(Variable dm1, Variable dm2){
		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();

		for (int i = 0 ; i < dm1.getValues().size(); i++){
			XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(dm1.getValue(i), dm2.getValue(i));
			series1.getData().add(dataPoint);
			Circle circle = new Circle();
			circle.setRadius(slider.getValue());		//TODO SCALE
			slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {

				circle.setRadius(slider.getValue());

			});
			dataPoint.setNode(circle);
		}
		return series1;
	}


	public static void main(String[] args){
		launch(args);
	}


}
