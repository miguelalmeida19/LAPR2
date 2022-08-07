package app.domain.model;

import java.io.Serializable;

public class ReferenceValue  implements Serializable {
    private final double refValueMax;
    private final double refValueMin;
    private String metric;

    public ReferenceValue(double refValueMax, double refValueMin, String metric){
        this.refValueMax = refValueMax;
        this.refValueMin = refValueMin;
        this.metric = metric;
    }
    public ReferenceValue(double refValueMax, double refValueMin){
        this.refValueMax = refValueMax;
        this.refValueMin = refValueMin;
    }

    public String getMetric() {
        return metric;
    }

    public double getRefValueMax() {
        return refValueMax;
    }

    public double getRefValueMin() {
        return refValueMin;
    }

    @Override
    public String toString() {
        return "ReferenceValue{" +
                "refValueMax=" + refValueMax +
                ", refValueMin=" + refValueMin +
                ", metric='" + metric + '\'' +
                '}';
    }
}
