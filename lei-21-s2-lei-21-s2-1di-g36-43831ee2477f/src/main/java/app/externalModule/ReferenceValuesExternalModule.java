package app.externalModule;

import app.domain.model.Parameter;
import app.domain.model.ReferenceValue;
import app.domain.model.TestParameter;

public interface ReferenceValuesExternalModule {
    public ReferenceValue getReferenceValues(TestParameter parameter);
}
