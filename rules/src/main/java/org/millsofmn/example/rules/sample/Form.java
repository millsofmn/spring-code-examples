package org.millsofmn.example.rules.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Form {

    private List<Sample> samples = new ArrayList<>();

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public void addSample(Sample sample) {
        this.samples.add(sample);
    }

    public void addSamples(List<Sample> samples) {
        this.samples = samples;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Form.class.getSimpleName() + "[", "]")
                .add("samples=" + samples)
                .toString();
    }
}
