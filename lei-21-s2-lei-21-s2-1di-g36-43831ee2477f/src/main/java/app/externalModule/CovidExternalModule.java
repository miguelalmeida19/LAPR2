package app.externalModule;

import app.domain.model.ReferenceValue;
import app.domain.model.TestParameter;
import com.example3.CovidReferenceValues1API;

public class CovidExternalModule implements ReferenceValuesExternalModule{
    public CovidExternalModule(){

    }

    public ReferenceValue getReferenceValues(TestParameter parameter) {
        CovidReferenceValues1API covidReferenceValues1API = new CovidReferenceValues1API();
        return new ReferenceValue(covidReferenceValues1API.getMaxReferenceValue(parameter.getCode(), 12345),covidReferenceValues1API.getMinReferenceValue(parameter.getCode(), 12345));
    }
}
