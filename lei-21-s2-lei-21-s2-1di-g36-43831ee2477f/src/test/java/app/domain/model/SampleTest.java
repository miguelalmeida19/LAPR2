package app.domain.model;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SampleTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Sample sample;

    @Before
    public void setUp() throws Exception {
        sample = new Sample("30-05-2021");
    }
}