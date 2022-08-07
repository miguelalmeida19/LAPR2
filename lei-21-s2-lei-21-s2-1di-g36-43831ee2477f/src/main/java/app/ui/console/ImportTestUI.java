package app.ui.console;

import app.controller.ImportTestController;

import app.mappers.dto.ClientDTO;
import app.ui.console.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportTestUI implements Runnable {
    static Scanner scanner = new Scanner(System.in);
    private final ImportTestController controller = new ImportTestController();
    private ClientDTO clientDTO;
    private int index;
    private String fileName;
    private List<String> options;

    public ImportTestUI() {
        options = new ArrayList<>();
    }

    public void run() {
        try {
            selectOption();
            controller.getTestList(fileName);
            controller.getTestDTOList();
            Utils.print("\n******************************\nSuccess!");
        } catch (Exception e) {
            Utils.print(e.getMessage());
        }
    }

    public void selectOption() {
        getFiles();
        index = Utils.showAndSelectIndex(options, "Select one of these files containing tests:");
        fileName = options.get(index);
    }

    public void getFiles(){
        for (File f : controller.getFiles()){
            if (!options.contains(f.getName())) {
                options.add(f.getName());
            }
        }
    }
}