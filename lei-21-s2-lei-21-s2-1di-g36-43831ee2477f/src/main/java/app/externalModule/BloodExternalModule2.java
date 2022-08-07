package app.externalModule;

import app.domain.model.ReferenceValue;
import app.domain.model.TestParameter;
import com.example1.ExternalModule3API;

public class BloodExternalModule2 implements ReferenceValuesExternalModule {

    public BloodExternalModule2(){

    }

    public ReferenceValue getReferenceValues(TestParameter parameter) {
        ExternalModule3API externalModule3API = new ExternalModule3API();
        return new ReferenceValue(externalModule3API.getMinReferenceValue(parameter.getCode(), 12345), externalModule3API.getMaxReferenceValue(parameter.getCode(),12345));
    }
}
