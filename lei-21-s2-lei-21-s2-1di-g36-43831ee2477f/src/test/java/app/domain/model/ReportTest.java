package app.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Report report;

    @Before
    public void setUp() throws Exception {
        report = new Report("diagnosis", "report");
    }

    @Test
    public void testGetdiagnosis() {
        Assert.assertEquals(true, this.report.getdiagnosis().equals("diagnosis"));
    }

    @Test
    public void testGetReport() {
        Assert.assertEquals(true, this.report.getReport().equals("report"));

    }

    @Test
    public void testTestToString() {
        Assert.assertEquals(true, this.report.toString().equals("Report{" +
                "diagnosis='" + report.getdiagnosis() + '\'' +
                ", report='" + report.getReport() + '\'' +
                '}'));
    }

    @Test
    public void checkEquals(){
        Report e = new Report("diagnosis","report");
        Report d = new Report("diagnosis","report");
        Assert.assertEquals(true, e.equals(d));
    }

    @Test
    public void checkEquals2(){
        Report e = new Report("diagnosis","report");
        Report d = new Report("diagnosis","reports");

        Assert.assertEquals(false, e.equals(d));
    }

    @Test
    public void testTestHashCode() {
        Assert.assertEquals(true, this.report.hashCode() == 1812532260);
    }
}