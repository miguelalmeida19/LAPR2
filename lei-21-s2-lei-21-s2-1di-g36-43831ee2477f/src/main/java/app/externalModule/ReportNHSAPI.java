package app.externalModule;


import com.nhs.report.Report2NHS;

public class ReportNHSAPI implements ReportNHS{

    public void SendReport(String report){
        Report2NHS.writeUsingFileWriter(report);
    }
}
