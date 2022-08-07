package app.ui.console;

import app.controller.OverviewController;
import app.mappers.dto.ClientDTO;
import app.ui.console.utils.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class OverviewUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private OverviewController overviewController;
    private ClientDTO clientDTO;
    private int index;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private String fileName;

    public OverviewUI() {
        try {
            overviewController = new OverviewController();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void run() {
        try {
            setInterval();
            int[] sequence = getSequence(startDate, endDate);
            int[] subSequence = getSubSequence(sequence);
            Utils.print(Arrays.toString(subSequence));
            int start = getStart(sequence, subSequence);
            int end = start + subSequence.length-1;
            Utils.print(getIntervalOfDays(start, end));
            Utils.print("\nTests Performed By Day\n");
            System.out.print("\nEnter the start date of interval: [dd-MM-YYYY] ");
            String startDate = read.nextLine();
            LocalDate startDateLocalDate = LocalDate.of(Integer.parseInt(startDate.split("-")[2]), Integer.parseInt(startDate.split("-")[1]), Integer.parseInt(startDate.split("-")[0]));
            System.out.print("Enter the end date of interval: [dd-MM-YYYY] ");
            String endDate = read.nextLine();
            LocalDate endDateLocalDate = LocalDate.of(Integer.parseInt(endDate.split("-")[2]), Integer.parseInt(endDate.split("-")[1]), Integer.parseInt(endDate.split("-")[0]));
            int[] testsPerformedByDay = getTestsPerformedByDay(startDateLocalDate, endDateLocalDate);
            Utils.print(Arrays.toString(testsPerformedByDay));
            Utils.print("\nTests Performed By Year\n");
            Utils.print(Arrays.toString(getTestsPerformedByYear(startDateLocalDate, endDateLocalDate)));

        } catch (Exception e) {
            Utils.print(e.getMessage());
        }
    }

    private void setInterval() throws ParseException {
        System.out.print("\nEnter the start date of interval: [dd-MM-YYYY] ");
        String start = read.nextLine();
        startDate.set(Integer.parseInt(start.split("-")[2]), Integer.parseInt(start.split("-")[1])-1, Integer.parseInt(start.split("-")[0]));
        System.out.print("Enter the end date of interval: [dd-MM-YYYY] ");
        String end = read.nextLine();
        endDate.set(Integer.parseInt(end.split("-")[2]), Integer.parseInt(end.split("-")[1])-1, Integer.parseInt(end.split("-")[0]));
    }

    private int getTestsWaitingResults(){
        return overviewController.getTestWaitingResults();
    }

    private int getTestWaitingDiagnosis(){
        return overviewController.getTestWaitingDiagnosis();
    }

    private int[] getSequence(Calendar startDate, Calendar endDate) throws ParseException {
        return overviewController.getSequence(startDate, endDate);
    }

    private int[] getSubSequence(int[] sequence) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return overviewController.getSubSequence(sequence);
    }

    private int getStart(int[] parent, int[] children){
        return overviewController.findArray(parent, children);
    }

    private String getIntervalOfDays(int start, int end){
        return overviewController.getIntervalOfDays(start, end);
    }

    private int[] getTestsPerformedByDay(LocalDate start, LocalDate end){
        return overviewController.getTestsPerformedByDay(start, end);
    }

    private int[] getTestsPerformedByWeek(LocalDate start, LocalDate end){
        return overviewController.getTestsPerformedByWeek(start, end);
    }

    private int[] getTestsPerformedByMonth(LocalDate start, LocalDate end){
        return overviewController.getTestsPerformedByMonth(start, end);
    }

    private int[] getTestsPerformedByYear(LocalDate start, LocalDate end){
        return overviewController.getTestsPerformedByYear(start, end);
    }
 }
