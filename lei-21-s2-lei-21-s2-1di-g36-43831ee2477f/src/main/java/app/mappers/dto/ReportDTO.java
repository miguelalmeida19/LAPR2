package app.mappers.dto;

public class ReportDTO {
    private String dignosis;
    private String report;

    /**
     * Constructor
     * @param dignosis
     * @param report
     */
    public ReportDTO(String dignosis, String report) {
        this.dignosis = dignosis;
        this.report = report;
    }

    /**
     * This method returns the diagnosis
     * @return diagnosis
     */
    public String getDignosis() {
        return dignosis;
    }

    /**
     * This method retu
     * @return
     */
    public String getReport() {
        return report;
    }
}
