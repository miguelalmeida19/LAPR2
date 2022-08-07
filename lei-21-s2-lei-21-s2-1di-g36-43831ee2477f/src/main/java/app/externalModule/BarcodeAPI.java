package app.externalModule;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import java.io.File;
import java.io.IOException;

public class BarcodeAPI implements BarcodeAPIExternalModule {
    public BarcodeAPI(){

    }
    public void generateBarcode(String code) throws BarcodeException, OutputException, IOException {
        Barcode bc = BarcodeFactory.createCode128(code);
        bc.setBarWidth(2);
        bc.setPreferredBarHeight(100);
        File imgFile = new File("barcodes/" + code + ".jpeg");
        BarcodeImageHandler.saveJPEG(bc,imgFile);
    }
}
