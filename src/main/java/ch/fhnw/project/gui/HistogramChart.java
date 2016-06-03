package ch.fhnw.project.gui;

import ch.fhnw.project.datenmodell.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Collections;
import java.util.List;

import static java.lang.StrictMath.ceil;

/**
 * Created by FelixYeung on 25.05.16.
 */
public class HistogramChart {


    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart barChart = new BarChart(xAxis, yAxis);
    //int select;
    List <Variable> lst;

    public HistogramChart(List<Variable> lst){
        this.lst = lst;

    }

    private ObservableList<XYChart.Series<String, Double>> createBarChartData(List<Variable> lst, int select) {

        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        XYChart.Series<String, Double> aSeries = new XYChart.Series<String, Double>();


        Variable var1 = lst.get(select);
        aSeries.setName(var1.getName());
        Double max = Collections.max(var1.getValues());
        Double min = Collections.min((var1.getValues()));

        double range = ceil(Math.sqrt(var1.getValues().size()));
        double width = Math.abs((max-min)/range);


        int count = 0;
        int testCount =0;

        for (int i = 0; i < range; i++) {
            for (int m = 0 ; m < var1.getValues().size(); m++){

                if(min+width*i<=var1.getValue(m) && min+width*(i+1)>=var1.getValue(m)){
                    count++;
                }
            }
            aSeries.getData().add(new XYChart.Data(Integer.toString(i), count));
            testCount+=count;
            count=0;
        }
        answer.addAll(aSeries);

        return answer;
    }

    private BarChart settings(){
        barChart.setBarGap(0);
        barChart.setCategoryGap(0);
        barChart.setCategoryGap(0);
        barChart.setPrefWidth(1000);
        return this.barChart;
    }

    public BarChart collectionAll(int select){
        barChart.setData(createBarChartData(lst,select));
        xAxis.setTickLabelsVisible(false);
        settings();
        return this.barChart;
    }
}
