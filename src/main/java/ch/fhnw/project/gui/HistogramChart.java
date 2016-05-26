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
    int select;
    List <Variable> lst;

    public HistogramChart(List<Variable> lst, int select){
        this.lst = lst;
        this.select = select;
    }


    //barChart.setLegendVisible(true);




    private ObservableList<XYChart.Series<String, Double>> createBarChartData(List<Variable> lst, int select) {

        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        XYChart.Series<String, Double> aSeries = new XYChart.Series<String, Double>();


        Variable dm1 = lst.get(select);
        aSeries.setName(dm1.getName());
        Double max = Collections.max(dm1.getValues());
        Double min = Collections.min((dm1.getValues()));

        double range = ceil(Math.sqrt(dm1.getValues().size()));
        double width = Math.abs((max-min)/range);


        int count = 0;
        int testCount =0;

        for (int i = 0; i < range; i++) {
            for (int m = 0 ; m < dm1.getValues().size(); m++){

                if(min+width*i<=dm1.getValue(m) && min+width*(i+1)>=dm1.getValue(m)){
                    count++;
                }
            }
            System.out.println(count);
            aSeries.getData().add(new XYChart.Data(Integer.toString(i), count));
            testCount+=count;
            count=0;
        }
        System.out.println(dm1.getValues().size());
        System.out.println("range:" +range);
        System.out.println("count:" +testCount);
        answer.addAll(aSeries);

        return answer;
    }

    private BarChart settings(){
        barChart.setBarGap(0);
        barChart.setCategoryGap(0);
        barChart.setCategoryGap(0);
        return this.barChart;
    }

    public BarChart collectionAll(){
        barChart.setData(createBarChartData(lst,select));
        xAxis.setTickLabelsVisible(false);
        settings();
        return this.barChart;
    }
}
