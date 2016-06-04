package ch.fhnw.project.io;

import java.util.*;

import ch.fhnw.project.datenmodell.Variable;
import javafx.scene.control.ProgressBar;

import java.io.*;

public interface FileParser {

	List<Variable> readData(File file) throws IOException;

}


