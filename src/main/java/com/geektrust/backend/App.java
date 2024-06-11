package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.geektrust.backend.appConfig.ApplicationConfig;
import com.geektrust.backend.commands.CommandInvoker;

public class App {
	public static void main(String[] args) {
        final int INPUT_FILE_INDEX = 0;
        final int COMMAND_NAME_INDEX = 0;
        final String DELIMITER = " ";

		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
		ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader reader;
        String inputFile = commandLineArgs.get(INPUT_FILE_INDEX);
        commandLineArgs.remove(INPUT_FILE_INDEX);
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(DELIMITER));
                commandInvoker.executeCommand(tokens.get(COMMAND_NAME_INDEX), tokens);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
