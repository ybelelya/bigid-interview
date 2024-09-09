package com.bigid.interview.aggregator;

import com.bigid.interview.model.Occurrence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Aggregator {

    private static final Logger logger = LoggerFactory.getLogger(Aggregator.class);

    private final Map<String, List<Occurrence>> aggregatedOccurrences;

    public Aggregator() {
        this.aggregatedOccurrences = new ConcurrentHashMap<>();
    }

    public void aggregate(Map<String, List<Occurrence>> occurrencesChunk) {
        for (Map.Entry<String, List<Occurrence>> entry : occurrencesChunk.entrySet()) {
            aggregatedOccurrences.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).addAll(entry.getValue());
        }
    }

    public void logAggregatedOccurrences() {
        for (Map.Entry<String, List<Occurrence>> entry : aggregatedOccurrences.entrySet()) {
            logger.info("{} --> {}", entry.getKey(), entry.getValue().toString());
        }
    }
}
