package app.mappers.dto;

import app.domain.model.ReferenceValue;
import app.domain.model.TestParameterResult;

public class TestParameterResultDTO {
    private final String result;
    private final String metric;
    private final ReferenceValue refValue;

    /**
     * Constructor
     * @param testParameterResult
     */
    public TestParameterResultDTO(TestParameterResult testParameterResult){
        this.result = testParameterResult.getResult();
        this.metric = testParameterResult.getMetric();
        this.refValue = testParameterResult.getRefValue();
    }

    /**
     * This method returns the metric of a TestParameterResult
     * @return
     */
    public String getMetric() {
        return metric;
    }

    /**
     * This method returns the result of a TestParameterResult
     * @return
     */
    public String getResult() {
        return result;
    }

    /**
     * This method returns the RefValue of a TestParameterResult
     * @return
     */
    public String getRefValueString() {
        return refValue.toString();
    }

    /**
     * This method returns a ref Value object of a TestParameterResult
     * @return
     */
    public ReferenceValue getRefValue() {
        return refValue;
    }



}
