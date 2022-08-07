package app.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class Report  implements Serializable {
    private final String diagnosis;
    private final String report;

    public Report(String diagnosis, String report){
        this.diagnosis = diagnosis;
        this.report = report;
    }

    public String getdiagnosis() {
        return diagnosis;
    }

    public String getReport() {
        return report;
    }

    @Override
    public String toString() {
        return "Report{" +
                "diagnosis='" + diagnosis + '\'' +
                ", report='" + report + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report1 = (Report) o;
        return diagnosis.equals(report1.diagnosis) && report.equals(report1.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diagnosis, report);
    }
}
