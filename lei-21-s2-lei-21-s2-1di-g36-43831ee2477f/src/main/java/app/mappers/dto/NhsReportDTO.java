package app.mappers.dto;

import java.util.Date;

public class NhsReportDTO {
    private final Date today;
    private final Date[] intervalOfDaysToFitTheModel;
    private final  String daysOrWeeks;
    private final int numberOfHistoricalPoints;
    private final String modelToUse;
    private final String[] independentVars;
    private final double significanceLevelForHipT;
    private final double significanceLevelForIC;
    public NhsReportDTO(Date today, Date[] intervalOfDaysToFitTheModel, String daysOrWeeks, int numberOfHistoricalPoints, String modelToUse, String[] independentVars, double significanceLevelForHipT, double significanceLevelForIC){
        this.today = today;
        this.intervalOfDaysToFitTheModel = intervalOfDaysToFitTheModel;
        this.daysOrWeeks = daysOrWeeks;
        this.numberOfHistoricalPoints = numberOfHistoricalPoints;
        this.modelToUse = modelToUse;
        this.independentVars = independentVars;
        this.significanceLevelForHipT = significanceLevelForHipT;
        this.significanceLevelForIC = significanceLevelForIC;
    }

    public double getSignificanceLevelForHipT() {
        return significanceLevelForHipT;
    }

    public double getSignificanceLevelForIC() {
        return significanceLevelForIC;
    }

    public Date getToday() {
        return today;
    }

    public Date[] getIntervalOfDaysToFitTheModel() {
        return intervalOfDaysToFitTheModel;
    }

    public int getNumberOfHistoricalPoints() {
        return numberOfHistoricalPoints;
    }

    public String getDaysOrWeeks() {
        return daysOrWeeks;
    }

    public String getModelToUse() {
        return modelToUse;
    }

    public String[] getIndependentVars() {
        return independentVars;
    }
}
