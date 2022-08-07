package app.domain.model;


import java.io.Serializable;

public class TestParameterResult  implements Serializable {

    private final String result;
    private final String metric;
    private final ReferenceValue refValue;

    public TestParameterResult(String result, String metric, ReferenceValue refValue){
        this.result = result;
        this.metric = metric;
        this.refValue = refValue;
    }

    public String getMetric() {
        return metric;
    }

    public ReferenceValue getRefValue() {
        return refValue;
    }

    public String getResult() {
        return result;
    }
}
