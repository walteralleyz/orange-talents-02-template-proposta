package br.com.zup.Credicard.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Component
public class MetricsProposal {
    private final MeterRegistry meterRegistry;
    private final Collection<String> strings = new ArrayList<>();

    @Autowired
    public MetricsProposal(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        createGauge();
    }

    public void createGauge() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        meterRegistry.gauge("propostas", tags, strings, Collection::size);
    }

    public void addString(String date) {
        strings.add(date);
    }
}
