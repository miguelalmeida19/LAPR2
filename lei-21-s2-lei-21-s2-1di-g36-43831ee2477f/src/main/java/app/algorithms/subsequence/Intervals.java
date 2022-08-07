package app.algorithms.subsequence;

import app.controller.App;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.TestMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Intervals {
    private Company company;
    private TestStore testStore;
    private ClientStore clientStore;
    private TestMapper testMapper;
    private List<Test> testList;
    private SimpleDateFormat df;
    private String[][] intervals;
    private final int intervalsArraySize = 24;
    private int[] sequence;
    private int[] testsRegArray;
    private int[] testsValiArray;
    String[] allDatesInInterval;
    private LocalDate currentDate;
    private WeekFields weekFields = WeekFields.of(Locale.getDefault());

    /**
     * Constructor
     * @throws ParseException
     */
    public Intervals() throws ParseException {
        company = App.getInstance().getCompany();
        testStore = company.getTestStore();
        clientStore = company.getClientStore();
        testMapper = new TestMapper();
        testList = testStore.getTestList();
        df = new SimpleDateFormat("HH:mm");
        initializeIntervals();
    }

    /**
     * This method initializes the intervals with the hours and minutes
     * @throws ParseException
     */
    public void initializeIntervals() throws ParseException {
        String[] halfHours = {"00", "30"};
        List<String> times = new ArrayList<>();

        for (int i = 8; i < 21; i++) {
            for (int j = 0; j < 2; j++) {
                String time = i + ":" + halfHours[j];
                if (i < 10) {
                    time = "0" + time;
                }
                if (i == 20 && j == 1) {
                    break;
                }
                times.add(time);
            }
        }
        //System.out.println(times);
        intervals = new String[intervalsArraySize][2];
        int counter = 0;
        for (int i = 0; i < intervals.length; i++) {
            for (int f = 0; f < intervals[0].length; f++) {
                intervals[i][f] = times.get(counter);
                if (f == 0) {
                    counter++;
                }
            }
        }
    }

    /**
     * This method returns the full sequence
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public int[] getSequence(Calendar start, Calendar end) throws ParseException {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        List<Test> testList = testStore.getTestList();
        long endDate = end.getTimeInMillis();
        long startDate = start.getTimeInMillis();
        final long days = TimeUnit.MILLISECONDS.toDays(Math.abs(endDate - startDate));


        List<LocalDate> daysList = getDays(start, end, days);

        allDatesInInterval = new String[Math.toIntExact(days) - getSundays(daysList) + 1];

        getAllDatesInInterval(allDatesInInterval, daysList, defaultZoneId);

        //System.out.println(Arrays.deepToString(allDatesInInterval));

        sequence = new int[intervalsArraySize];
        testsRegArray = new int[intervalsArraySize];
        testsValiArray = new int[intervalsArraySize];

        int daysWithoutSundays = Math.toIntExact(days) - getSundays(daysList);

        int[][] intervalsRegDate = new int[daysWithoutSundays + 1][intervalsArraySize];
        int[][] intervalsValiDate = new int[daysWithoutSundays + 1][intervalsArraySize];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");

        getTestsRegInterval(allDatesInInterval, intervalsRegDate);
        //System.out.println("\n\n######################\n\n");
        getTestsValiInterval(allDatesInInterval, intervalsValiDate);

        int[] fullSequence = toFullSequence(intervalsRegDate, intervalsValiDate);
        //System.out.println(Arrays.deepToString(intervalsRegDate));
        //System.out.println("\n\n\n\n\n");
        //System.out.println(Arrays.deepToString(intervalsValiDate));
        //System.out.println("\n\n\n\n\n");
        //System.out.println(Arrays.toString(fullSequence));

        return fullSequence;
    }

    /**
     * This method returns the days on a interval
     * @param start
     * @param end
     * @param numberOfDays
     * @return
     */
    public List<LocalDate> getDays(Calendar start, Calendar end, long numberOfDays) {
        Calendar[] days = new Calendar[Math.toIntExact(numberOfDays)];
        LocalDate startDate = LocalDate.from(LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault()));
        LocalDate endDate = LocalDate.from(LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault()));

        return IntStream.iterate(0, i -> i + 1)
                .limit(Math.toIntExact(numberOfDays) + 1)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    /**
     * This method returns the number of sundays on a list
     * @param dayslist
     * @return
     */
    public int getSundays(List<LocalDate> dayslist) {
        int counter = 0;
        for (LocalDate date : dayslist) {
            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * This method gets all dates in a interval
     * @param allDatesInInterval
     * @param daysList
     * @param defaultZoneId
     */
    public void getAllDatesInInterval(String[] allDatesInInterval, List<LocalDate> daysList, ZoneId defaultZoneId) {
        int c = 0;
        for (LocalDate localDate : daysList) {
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            String month = String.format("%02d", localDate.getMonthValue());
            String day = String.format("%02d", date.getDate());
            //System.out.println(day + "-" + month + "-" + localDate.getYear());
            if (!localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                allDatesInInterval[c] = day + "-" + month + "-" + localDate.getYear();
                c++;
            }
        }
    }

    /**
     * This method gets the number of tests registered in a interval
     * @param allDatesInInterval
     * @param intervalsRegDate
     */
    public void getTestsRegInterval(String[] allDatesInInterval, int[][] intervalsRegDate) {
        for (int h = 0; h < allDatesInInterval.length; h++) {
            //currentDate = LocalDate.parse(allDatesInInterval[h], formatter);
            String currentDateString = allDatesInInterval[h];
            for (Test test : testList) {
                //System.out.println("Test Creation Date: " + test.getCreationDate().split(" ")[0].replace("/","-"));
                //System.out.println("CurrentDateString: " + currentDateString);
                if (test.getCreationDate().split(" ")[0].replace("/", "-").equals(currentDateString)) {
                    String hour = test.getCreationDate().split(" ")[1];
                    //LocalDate currentDateHour = LocalDate.parse(hour, formatterHour);
                    LocalTime currentDateHour = LocalTime.of(Integer.parseInt(hour.split(":")[0]), Integer.parseInt(hour.split(":")[1]));
                    for (int i = 0; i < intervals.length; i++) {
                        LocalTime firstInterval = LocalTime.of(Integer.parseInt(intervals[i][0].split(":")[0]), Integer.parseInt(intervals[i][0].split(":")[1]));
                        LocalTime secondInterval = LocalTime.of(Integer.parseInt(intervals[i][1].split(":")[0]), Integer.parseInt(intervals[i][1].split(":")[1]));

                        //LocalDateTime firstInterval = LocalDate.parse(intervals[i][0], formatterHour).atStartOfDay();
                        //LocalDateTime secondInterval = LocalDate.parse(intervals[i][1], formatterHour).atStartOfDay();

                        //System.out.println("CurrentDateHour = " + hour);
                        //System.out.println("First Interval = " + intervals[i][0]);
                        //System.out.println("Second Interval = " + intervals[i][1]);

                        if ((currentDateHour.isBefore(secondInterval) && currentDateHour.isAfter(firstInterval)) || currentDateHour.equals(firstInterval)) {
                            intervalsRegDate[h][i]++;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method gets the number of tests validated in a interval
     * @param allDatesInInterval
     * @param intervalsValiDate
     */
    public void getTestsValiInterval(String[] allDatesInInterval, int[][] intervalsValiDate) {
        for (int h = 0; h < allDatesInInterval.length; h++) {
            //currentDate = LocalDate.parse(allDatesInInterval[h], formatter);
            String currentDateString = allDatesInInterval[h];
            for (Test test : testList) {
                //System.out.println("Test Validation Date: " + test.getValidationDate().split(" ")[0].replace("/","-"));
                //System.out.println("CurrentDateString: " + currentDateString);
                if (test.getValidationDate().split(" ")[0].replace("/", "-").equals(currentDateString)) {
                    String hour = test.getValidationDate().split(" ")[1];
                    //LocalDate currentDateHour = LocalDate.parse(hour, formatterHour);
                    LocalTime currentDateHour = LocalTime.of(Integer.parseInt(hour.split(":")[0]), Integer.parseInt(hour.split(":")[1]));
                    for (int i = 0; i < intervals.length; i++) {
                        LocalTime firstInterval = LocalTime.of(Integer.parseInt(intervals[i][0].split(":")[0]), Integer.parseInt(intervals[i][0].split(":")[1]));
                        LocalTime secondInterval = LocalTime.of(Integer.parseInt(intervals[i][1].split(":")[0]), Integer.parseInt(intervals[i][1].split(":")[1]));

                        //LocalDateTime firstInterval = LocalDate.parse(intervals[i][0], formatterHour).atStartOfDay();
                        //LocalDateTime secondInterval = LocalDate.parse(intervals[i][1], formatterHour).atStartOfDay();

                        //System.out.println("CurrentDateHour = " + hour);
                        //System.out.println("First Interval = " + intervals[i][0]);
                        //System.out.println("Second Interval = " + intervals[i][1]);

                        if ((currentDateHour.isBefore(secondInterval) && currentDateHour.isAfter(firstInterval)) || currentDateHour.equals(firstInterval)) {
                            intervalsValiDate[h][i]++;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method returns an array with the tests registered minuts the tests validated
     * @param intervalsRegDate
     * @param intervalsValiDate
     * @return
     */
    public int[] toFullSequence(int[][] intervalsRegDate, int[][] intervalsValiDate) {
        int[] fullSequence = new int[intervalsRegDate.length * intervalsRegDate[0].length];
        int counter = 0;
        for (int i = 0; i < intervalsRegDate.length; i++) {
            for (int f = 0; f < intervalsRegDate[0].length; f++) {
                fullSequence[counter] = intervalsRegDate[i][f] - intervalsValiDate[i][f];
                counter++;
            }
        }
        return fullSequence;
    }

    /**
     * This method gets the interval of days
     * @param start
     * @param end
     * @return
     */
    public String getIntervalOfDays(int start, int end) {
        final int initialDateIndex = start / intervalsArraySize;
        final int finalDateIndex = end / intervalsArraySize;

        final int initialDateIndexMod = start % intervalsArraySize;
        final int endDateIndexMod = end % intervalsArraySize;

        String initialDate = allDatesInInterval[initialDateIndex];
        String finalDate = allDatesInInterval[finalDateIndex];

        String initialHour = intervals[initialDateIndexMod][0] + "-" + intervals[initialDateIndexMod][1];
        String endHour = intervals[endDateIndexMod][0] + "-" + intervals[endDateIndexMod][1];

        return initialDate + " [" + initialHour + "]" + " to " + finalDate + " [" + endHour + "]";
    }

    /**
     * This method returns an array with the tests performed by day
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByDay(LocalDate start, LocalDate end) {
        int position = 0;
        List<LocalDate> listOfDatesBetween = getListOfDatesBetween(start, end);
        removeSundays(listOfDatesBetween);
        int[] allTestsPerformedByDay = new int[listOfDatesBetween.size()];
        for (LocalDate date : listOfDatesBetween) {
            //LocalDate currentDate = LocalDate.of(Integer.parseInt(date.split("-")[2]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[0]));
            for (Test test : testList) {
                String dateString = date.toString();
                String year = dateString.split("-")[0];
                String month = String.format("%02d", Integer.parseInt(dateString.split("-")[1]));
                String day = String.format("%02d", Integer.parseInt(dateString.split("-")[2]));
                String formattedDate = day + "-" + month + "-" + year;
                if (test.getValidationDate().replace("/", "-").split(" ")[0].equals(formattedDate) && test.isValidated()) {
                    allTestsPerformedByDay[position]++;
                }
            }
            position++;
        }
        return allTestsPerformedByDay;
    }

    /**
     * This method returns an array with the tests performed by week
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByWeek(LocalDate start, LocalDate end) {

        List<LocalDate> listOfDatesBetween = getListOfDatesBetween(start, end);
        removeSundays(listOfDatesBetween);

        final long weeksBetween = ChronoUnit.WEEKS.between(start, end);
        //System.out.println("Weeks Between: " + weeksBetween);
        int[] allTestsPerformedByWeek = new int[Math.toIntExact(weeksBetween)+1];

        int[] testsPerformedByDay = getTestsPerformedByDay(start, end);

        int counter = 0;
        int index = 0;
        for (LocalDate localDate: listOfDatesBetween){
            if (localDate.getDayOfWeek().equals(DayOfWeek.MONDAY)  && index!=0){
                counter++;
            }
            allTestsPerformedByWeek[counter]+= testsPerformedByDay[index];
            index++;
        }
        return allTestsPerformedByWeek;
    }

    /**
     * This method returns an array with the tests performed by month
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByMonth(LocalDate start, LocalDate end){
        List<LocalDate> listDatesBetween = getListOfDatesBetween(start, end);
        removeSundays(listDatesBetween);
        int[] testsPerformedByDay = getTestsPerformedByDay(start, end);
        final int monthsBetween = getNumberOfMonthsBetween(start, end);
        int[] testsPerformedByMonth = new int[monthsBetween];
        LocalDate currentMonth = start;
        LocalDate nextMonth = LocalDate.of(currentMonth.getYear(), currentMonth.plusMonths(1).getMonth(), 1);
        int position = 0;
        int index = 0;
        for (LocalDate localDate:listDatesBetween){
            if ((localDate.isAfter(currentMonth) || localDate.equals(currentMonth))  && localDate.isBefore(nextMonth) && !localDate.equals(nextMonth)){
                testsPerformedByMonth[position]+=testsPerformedByDay[index];
            }
            else {
                position++;
                currentMonth = localDate;
                nextMonth = currentMonth.plusMonths(1);
            }
            index++;
        }
        return testsPerformedByMonth;
    }

    /**
     * This method returns an array with the tests performed by year
     * @param start
     * @param end
     * @return
     */
    public int[] getTestsPerformedByYear(LocalDate start, LocalDate end){
        List<LocalDate> listDatesBetween = getListOfDatesBetween(start, end);
        removeSundays(listDatesBetween);
        int[] testsPerformedByDay = getTestsPerformedByDay(start, end);
        final int yearsBetween = getNumberOfYearsBetween(start, end);
        int[] testsPerformedByYear = new int[yearsBetween];
        LocalDate currentYear = start;
        LocalDate nextYear = LocalDate.of(currentYear.plusYears(1).getYear(), 1, 1);
        int position = 0;
        int index = 0;
        for (LocalDate localDate:listDatesBetween){
            if ((localDate.isAfter(currentYear) || localDate.equals(currentYear))  && localDate.isBefore(nextYear) && !localDate.equals(nextYear)){
                testsPerformedByYear[position]+=testsPerformedByDay[index];
            }
            else {
                position++;
                currentYear = localDate;
                nextYear = currentYear.plusYears(1);
            }
            index++;
        }
        return testsPerformedByYear;
    }

    /**
     * This method returns a list with the number of days between
     * @param start
     * @param end
     * @return
     */
    private List<LocalDate> getListOfDatesBetween(LocalDate start, LocalDate end) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(start, end);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween+1)
                .mapToObj(i -> start.plusDays(i))
                .collect(Collectors.toList());
    }

    /**
     * This method removes the sundays from a list
     * @param list
     */
    private void removeSundays(List<LocalDate> list){
        Iterator<LocalDate> it = list.iterator();

        while (it.hasNext()) {
            LocalDate localDate = it.next();
            if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY){
                it.remove();
            }
        }
    }

    /**
     * This method returns the number of months between a date
     * @param start
     * @param end
     * @return
     */
    private int getNumberOfMonthsBetween(LocalDate start, LocalDate end){
        int counter = 0;
        while(start.isBefore(end)) {
            start = start.plusMonths(1);
            counter++;
        }
        return counter;
    }

    /**
     * This method returns the number of years between a date
     * @param start
     * @param end
     * @return
     */
    private int getNumberOfYearsBetween(LocalDate start, LocalDate end){
        int counter = 0;
        while(start.isBefore(end)) {
            start = start.plusYears(1);
            counter++;
        }
        return counter;
    }
}