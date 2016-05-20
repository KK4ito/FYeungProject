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
        
        FileParser fp = new TabDelimited();
        
        fp.readData(file);
        fp.getList();

//test


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
