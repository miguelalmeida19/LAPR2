package app.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class TestParameter  implements Serializable {
    private Parameter parameter;

    private TestParameterResult testParameterResult;

    public TestParameter(Parameter parameter){
        this.parameter = parameter;
        testParameterResult = null;
    }

    public void addResult(String result, String metric, ReferenceValue refValue){
        testParameterResult = new TestParameterResult(result,metric,refValue);
    }

    public Parameter getParameter() {
        return parameter;
    }

    public String getCode(){
        return parameter.getCode();
    }

    public TestParameterResult getTestParameterResul() {
        return testParameterResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestParameter that = (TestParameter) o;
        return Objects.equals(parameter, that.parameter) && Objects.equals(testParameterResult, that.testParameterResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, testParameterResult);
    }
}

