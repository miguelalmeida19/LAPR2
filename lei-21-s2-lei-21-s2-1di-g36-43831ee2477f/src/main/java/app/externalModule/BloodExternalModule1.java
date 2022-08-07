package app.externalModule;

import app.domain.model.ReferenceValue;
import app.domain.model.TestParameter;
import com.example2.ExternalModule2API;

public class BloodExternalModule1 implements ReferenceValuesExternalModule{
    public BloodExternalModule1(){

    }
    @Override
    public ReferenceValue getReferenceValues(TestParameter parameter) {
        ExternalModule2API externalModule2API = new ExternalModule2API();
        return new ReferenceValue(externalModule2API.getReferenceFor(parameter.getCode()).getMaxValue(), externalModule2API.getReferenceFor(parameter.getCode()).getMinValue(),externalModule2API.getReferenceFor(parameter.getCode()).getMetric());

    }

}
