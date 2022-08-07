package app.mappers;

import app.domain.model.Report;
import app.mappers.dto.ReportDTO;

public class ReportMapper {

    public ReportDTO toDTO(Report report){
        return new ReportDTO(report.getdiagnosis(), report.getReport());
    }
}
