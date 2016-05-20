package ch.fhnw.project.io;

import java.util.*;

import ch.fhnw.project.datenmodell.DataModel;

import java.io.*;

public interface FileParser {

	public void readData(File file);
	public List<DataModel> getList();
}
