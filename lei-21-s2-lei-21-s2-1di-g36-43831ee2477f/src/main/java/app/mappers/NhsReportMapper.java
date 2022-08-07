package app.mappers;

import app.mappers.dto.NhsReportDTO;

import java.util.Date;

public class NhsReportMapper {
    public NhsReportMapper(){
        //removed
    }
    public NhsReportDTO toDto(Date today, Date[] intervalOfDaysToFitTheModel, String daysOrWeeks, int numberOfHistoricalPoints, String modelToUse, String[] independentVars, double significanceLevelForHipT, double significanceLevelForIC){
        return new NhsReportDTO(today, intervalOfDaysToFitTheModel,daysOrWeeks,numberOfHistoricalPoints,modelToUse,independentVars,significanceLevelForHipT,significanceLevelForIC);
    }
}
