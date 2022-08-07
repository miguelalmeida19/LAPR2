package app.controller;

import app.algorithms.sorting.SortAlgorithm;
import app.algorithms.subsequence.Intervals;
import app.algorithms.subsequence.Subsequence;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class OverviewController {
    private Company company;
    private TestStore testStore;
    private ClientStore clientStore;
    private TestMapper testMapper;
    private List<Test> testList;
    private SimpleDateFormat df;
    private Intervals intervals;
    private ArrayUtils arrayUtils = new ArrayUtils();


    /**
     * Constructor
     * @throws ParseException
     */
    public OverviewController() throws ParseException {
        company = App.getInstance().getCompany();
        testStore = company.getTestStore();
        clientStore = company.getClientStore();
        testMapper = new TestMapper();
        testList = testStore.getTestList();
        intervals = new Intervals();
        df =  new SimpleDateFormat("dd-MM-yyyy");
    }

    /**
     * This method gets the number of clients
     * @return
     */
    public int getClientsNumber(){
        return clientStore.getClients().size();
    }

    /**
     * This method gets the number of tests
     * @return
     */
    public int getTestsNumber(){
        return testStore.getTestList().size();
    }

    /**
     * This method returns the tests registered
     * @return
     */
    public int getTestWaitingResults() {
        int counter = 0;
        for (Test test : testList){
            if (test.isRegistered()){
                counter++;
            }
        }
        return counter;
    }

    /**
     * This method returns the number of tests waiting for diagnosis
     * @return
     */
    public int getTestWaitingDiagnosis() {
        int counter = 0;
        for (Test test : testList){
            if (test.isSamplesAnalysed()){
                counter++;
            }
        }
        return counter;
    }

    /**
     * It creates a  instance of the algorithm to be used
     * @param subsequence
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public int[] getSubSequence(int[] subsequence) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream in = new FileInputStream("configuration.conf");
        properties.load(in);
        Class<?> subsequenceClass = Class.forName(properties.getProperty("Company.Algorithms.subsequence"));
        Subsequence subsequenceAlgorithm = (Subsequence) subsequenceClass.newInstance();
        in.close();
        return subsequenceAlgorithm.getSubsequence(subsequence);
    }

    /**
     * This method gets an array with a sequence
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public int[] getSequence(Calendar start, Calendar end) throws ParseException {
        return intervals.getSequence(start, end);
    }

    /**
     *  This method finds the array that we want
     * @param largeArray
     * @param subArray
     * @return
     */
    public int findArray(int[] largeArray, int[] subArray) {

        if (largeArray.length == 0 || subArray.length == 0) {
            return -1;
        }

        if (subArray.length > largeArray.length) {
            return -1;
        }

        for (int i = 0; i < largeArray.length; i++) {
            if (largeArray[i] == subArray[0]) {

                boolean subArrayFound = true;
                for (int j = 0; j < subArray.length; j++) {
                    if (largeArray.length <= i+j || subArray[j] != largeArray[i+j]) {
                        subArrayFound = false;
                        break;
                    }
                }

                if (subArrayFound) {
                    return i;
                }

            }
        }

        return -1;
    }

    /**
     * This method returns the number of days between the interval
     * @param start
     * @param end
     * @return
     */
    public String getIntervalOfDays(int start, int end){
        return intervals.getIntervalOfDays(start, end);
    }

    /**
     * This method gets the number of tests by day
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByDay(LocalDate start, LocalDate end){
        return intervals.getTestsPerformedByDay(start, end);
    }

    /**
     * This method gets the number of tests by week
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByWeek(LocalDate start, LocalDate end){
        return intervals.getTestsPerformedByWeek(start, end);
    }

    /**
     * This method gets the number of tests by month
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByMonth(LocalDate start, LocalDate end){
        return intervals.getTestsPerformedByMonth(start, end);
    }

    /**
     * This method gets the number of tests by year
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByYear(LocalDate start, LocalDate end){
        return intervals.getTestsPerformedByYear(start, end);
    }
}