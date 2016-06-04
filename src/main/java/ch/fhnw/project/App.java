package ch.fhnw.project;

import ch.fhnw.project.datenmodell.Variable;
import ch.fhnw.project.gui.HistogramChart;
import ch.fhnw.project.io.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.*;


public class App extends Application {

    public ProgressBar pb = new ProgressBar();

	List<Variable> lstData = new ArrayList<>();
    List<Variable> lstInput = new ArrayList<>();

    Button btnLoadFile = new Button("Import data");
	StackPane pane;
	ColorPicker colorPicker = new ColorPicker(Color.BLUE);

	Label xAxis = new Label("xAxis: ");
	Label yAxis = new Label("yAxis: ");
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



	Alert alert = new Alert(Alert.AlertType.WARNING);




	File file;
	final FileChooser fileChooser = new FileChooser();



	private void refreshData(String t){
		scxAxis.setLabel(t);
		linexAxis.setLabel(t);

		sc.getData().removeAll();
		lineChart.getData().removeAll();
		sc.getData().clear();
		lineChart.getData().clear();
		List<Variable> newList = new ArrayList<Variable>();
        if(lstData.size() > 0) {
            newList.add(lstData.get(cb.getSelectionModel().getSelectedIndex()));
            newList.add(lstData.get(cb2.getSelectionModel().getSelectedIndex()));

            sc.getData().addAll(createChartData(newList));
            lineChart.getData().add(createChartData(newList));
        }
	}

	public void loadDataFromFile(File file, Stage stage) throws IOException {

		cb.getItems().removeAll(cb.getItems());
		cb2.getItems().removeAll(cb2.getItems());

		try {
			FileParser fp;
			fp = makeObject(file);


			lstInput = fp.readData(file);

            Label label;


			HistogramChart hi1 = new HistogramChart(lstInput);
			HistogramChart hi2 = new HistogramChart(lstInput);


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


			for (Variable model : lstInput) {

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
					if(t2 != null){
						refreshData(t2);
						hi1.collectionAll(cb.getSelectionModel().getSelectedIndex());
					}

				}
			});

			cb2.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue observableValue, String t, String t2) {
					if(t2 != null){
						refreshData(t2);
						hi2.collectionAll(cb2.getSelectionModel().getSelectedIndex());
					}

				}
			});


			lineChartSetting(file, fp, checkbox);
			sc = getScatterChart(file,fp);




			HBox firstLine = new HBox();
			firstLine.getChildren().addAll(xAxis,cb,yAxis,cb2, label,checkbox, colorPicker,slider, btnLoadFile);
			firstLine.setStyle("-fx-background-color: lightblue");
			firstLine.setAlignment(Pos.CENTER);
			firstLine.setSpacing(10);
			firstLine.setPadding(new Insets(5, 5, 5, 5));


			HBox histogram = new HBox();
			histogram.getChildren().addAll(hi1.collectionAll(0),hi2.collectionAll(1));
			histogram.setAlignment(Pos.CENTER);
			histogram.setSpacing(10);
			histogram.setPadding(new Insets(5, 5, 5, 5));


			root = new StackPane();

			root.getChildren().addAll(sc, lineChart);


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

	private void lineChartSetting(File file, FileParser fp, CheckBox checkbox) throws IOException {
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
		lineChart.getData().addAll(createChartData(lstInput));
	}


	@Override
	public void start(Stage stage) throws IOException {
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
							try {
								loadDataFromFile(file, newStage);
							} catch (IOException e1) {
								e1.printStackTrace();
								alertWarning();
							}
						}
					}
				});


		file = fileChooser.showOpenDialog(stage);
		loadDataFromFile(file, stage);

	}

    private ScatterChart<Number, Number> getScatterChart(File file,FileParser fp) throws IOException {
        ScatterChart<Number,Number> sc = new ScatterChart<>(scxAxis,scyAxis);

        sc.setTitle(file.getName());
        lineChart.setTitle(file.getName());

        sc.setLegendVisible(false);
        sc.getData().addAll(createChartData(lstInput));
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
			Variable var1 = lst.get(0);
			Variable var2 = lst.get(1);
			if(!(var1.getValues().size() == var2.getValues().size())){

				alertWarning();
			}
			else{
				scxAxis.setLabel(var1.getName());
				scyAxis.setLabel(var2.getName());
                linexAxis.setLabel(var1.getName());
                lineyAxis.setLabel(var2.getName());

				return getXYChartSerie(var1, var2);
			}

		}
		alertWarning();



		return null;
	}

	private void alertWarning() {
		alert.setTitle("Warning Dialog");
		alert.setHeaderText("File-Problem");
		alert.setContentText("Cannot open");

		alert.showAndWait();
	}

	private XYChart.Series<Number, Number> getXYChartSerie(Variable var1, Variable var2){
		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();

		for (int i = 0 ; i < var1.getValues().size(); i++){
			XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(var1.getValue(i), var2.getValue(i));
			series1.getData().add(dataPoint);
			Circle circle = new Circle();


			circle.setRadius(slider.getValue());
			circle.setFill(colorPicker.getValue());
			circle.radiusProperty().bind(slider.valueProperty());
			circle.fillProperty().bind(colorPicker.valueProperty());

			dataPoint.setNode(circle);
		}
		return series1;
	}


	public static void main(String[] args){
		launch(args);
	}


}
