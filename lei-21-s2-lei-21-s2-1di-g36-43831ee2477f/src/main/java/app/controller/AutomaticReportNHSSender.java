package app.controller;

import app.mappers.NhsReportMapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

public class AutomaticReportNHSSender extends TimerTask {
    @Override
    public void run() {

        try {
            Properties properties = new Properties();
            InputStream in = new FileInputStream("configuration.conf");
            properties.load(in);
            Date today = parseDateDays(properties.getProperty("Company.NhsReport.setToday"));
            String daysOrWeeks = properties.getProperty("Company.NhsReport.daysOrWeek");
            checkEmpty(daysOrWeeks);
            int numberOfHistoricalPoints = Integer.parseInt(properties.getProperty("Company.NhsReport.numberHistoricalPointsToSend"));
            String modelToUse = properties.getProperty("Company.NhsReport.modelToUse");
            checkEmpty(modelToUse);
            String[] independentVars = new String[2];
            if(modelToUse.equals("multiple")){
                independentVars [0] = "meanAge";
                independentVars [1] = "tests/day";
            }else{
                independentVars[0] = properties.getProperty("Company.NhsReport.independentVar");
                checkEmpty (independentVars[0]);
            }
            Date lowerLimit;
            Date upperLimit;
            if(daysOrWeeks.equals("weeks")){
                lowerLimit = parseDatesWeek(properties.getProperty("Company.NhsReport.lowerDateIntervalToFitRegresion"));
                upperLimit = parseDatesWeek(properties.getProperty("Company.NhsReport.upperDateIntervalToFitRegresion"));
            }else{
                lowerLimit = parseDateDays(properties.getProperty("Company.NhsReport.lowerDateIntervalToFitRegresion"));
                upperLimit = parseDateDays(properties.getProperty("Company.NhsReport.upperDateIntervalToFitRegresion"));
            }
            double significanceLevelForHypothesisTests = Double.parseDouble(properties.getProperty("Company.NhsReport.SignificanceLevelForHypothesisTests"));
            double significanceLevelForCI = Double.parseDouble(properties.getProperty("Company.NhsReport.SignificanceLevelForConfidenceIntervals"));
            Date[] dates = new Date[2];
            dates[0] = lowerLimit;
            dates[1] = upperLimit;
            SendReportNhsController controller = new SendReportNhsController();
            NhsReportMapper mapper = new NhsReportMapper();
            controller.generateReport(mapper.toDto(today, dates,daysOrWeeks, numberOfHistoricalPoints,modelToUse,independentVars, significanceLevelForHypothesisTests,significanceLevelForCI));

        }catch (Exception e){
            System.out.println("[!] Error: "+e.getMessage());
        }
    }
    private Date parseDatesWeek(String date) throws ParseException {
        Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(date);
        if(date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek() != DayOfWeek.SUNDAY) throw new IllegalArgumentException("The date provided must be a sunday when the program is set to send week data");
        return  date1;
    }
    private Date parseDateDays(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }
    private void checkEmpty(String text){
        if(text.isEmpty()){
            throw new IllegalArgumentException("Some property is blank. Pleas check the configuration file.");
        }
    }
}
