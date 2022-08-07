package app.mappers.dto;

import app.domain.model.Report;
import app.mappers.ReportMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ReportDTOTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Report report;
    public ReportDTO repDto;
    public ReportMapper reportMapper = new ReportMapper();

    @Before
    public void setUp() throws Exception {
        report = new Report("diagnosis", "report");
        repDto = reportMapper.toDTO(report);
    }

    @Test
    public void getDignosis() {
        Assert.assertTrue(report.getdiagnosis().equals("diagnosis"));
    }

    @Test
    public void getReport() {
        Assert.assertTrue(report.getReport().equals("report"));

    }
}