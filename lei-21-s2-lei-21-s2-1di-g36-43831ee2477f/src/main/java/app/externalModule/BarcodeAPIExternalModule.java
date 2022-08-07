package app.externalModule;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.IOException;

public interface BarcodeAPIExternalModule {
    public void generateBarcode(String code) throws BarcodeException, OutputException, IOException;
}
