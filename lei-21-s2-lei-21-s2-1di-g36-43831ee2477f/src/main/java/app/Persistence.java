package app;

import app.controller.App;

import java.io.*;
import java.util.ArrayList;

public class Persistence {
    /**
     * Reads the object from file
     *
     * @param ficheiro file path / name
     * @return object loaded
     */
    public static Object readObjectFromFile(String ficheiro) throws IOException, ClassNotFoundException {
        ficheiro = "./storage/" + ficheiro;
        FileInputStream fileIn = new FileInputStream(ficheiro);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object a = in.readObject();
        in.close();
        fileIn.close();
        return a;

    }

    public static void saveObject(Object objectToSave, String fileName) throws IOException {

        fileName = "./storage/" + fileName;
        new File(fileName).delete();
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        System.out.println(fileName);
        out.writeObject(objectToSave);
        out.close();
        fileOut.close();
    }

    public static void saveAllData() throws IOException {
        saveObject(App.getInstance().getCompany().getTestStore().getTestList(), "tests.bin");
        saveObject(App.getInstance().getCompany().getClientStore().getClients(), "clients.bin");
        saveObject(App.getInstance().getCompany().getEmployeeStore().getEmployees(), "employees.bin");
        saveObject(App.getInstance().getCompany().getLaboratoryStore().getLaboratories(), "labs.bin");
        saveObject(App.getInstance().getCompany().getParameterCategoryStore().getParameterCategoryList(), "parameterCategories.bin");
        saveObject(App.getInstance().getCompany().getParameterStore().getListOfParameters(), "parameters.bin");
        saveObject(App.getInstance().getCompany().getTestTypeStore().getListOfTestType(), "testTypes.bin");
        saveObject(App.getInstance().getCompany().getAuthFacade().getUsers(), "users.bin" );
        saveObject(App.getInstance().getCompany().getAuthFacade().getRoles(), "userRoles.bin" );
        saveObject(App.getInstance().getCompany().getAuthFacade().getUserSession(), "sessions.bin");
    }
}
