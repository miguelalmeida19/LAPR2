package app.domain.model;

import app.externalModule.BarcodeAPIExternalModule;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class Sample  implements Serializable {
    private final String sampleCreationDate;
    private static double counter = 1.0;
    private final String code;

    /**
     * Constructor
     * @param date
     * @throws OutputException
     * @throws BarcodeException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Sample(String date) throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        code = generateCode();
        counter++;
        generateBarcode(code);
        sampleCreationDate = date;
    }

    /**
     * This method generates a Barcode
     * @param code
     * @throws BarcodeException
     * @throws OutputException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void generateBarcode(String code) throws BarcodeException, OutputException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("configuration.conf");
        properties.load(inputStream);
        Class<?> c= Class.forName(properties.getProperty("Company.API.barcode.Class"));
        BarcodeAPIExternalModule barcodeAPI = (BarcodeAPIExternalModule) c.newInstance();
        barcodeAPI.generateBarcode(code);
        inputStream.close();
    }

    /**
     * This method generates the code to a Sample
     * @return
     */
    private String generateCode(){
        String finalString = "";
        for(int n = String.valueOf(String.format("%.0f",counter)).length(); n < 12; n++){
            finalString = finalString + "0";
        }
        return finalString + String.format("%.0f",Double.valueOf(String.valueOf(counter)));

    }

    /**
     * This method returns the code of a Sample
     * @return
     */
    public String getCode() {
        return code;
    }
}
