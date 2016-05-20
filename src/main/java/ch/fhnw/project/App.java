package ch.fhnw.project;

import ch.fhnw.project.datenmodell.DataModel;
import ch.fhnw.project.io.*;
import javafx.application.Application;
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

        FileParser fp ;

        try {
            fp = getData(file);

            fp.readData(file);
            for(DataModel m : fp.getList()){
                System.out.print(m.getName() + " Values: ");

                for(double d : m.getValues()){
                    System.out.print(d + "/" );
                }
                System.out.println();
            }
            System.out.println("______________________________");

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
