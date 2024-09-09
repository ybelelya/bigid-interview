package com.bigid.interview.processor;

import com.bigid.interview.Config;
import com.bigid.interview.model.Occurrence;
import com.bigid.interview.reader.WebReader;
import com.bigid.interview.aggregator.Aggregator;
import com.bigid.interview.matcher.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SingleThreadProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(SingleThreadProcessor.class);

    private final WebReader webReader;
    private final Matcher matcher;
    private final Aggregator aggregator;

    public SingleThreadProcessor(WebReader webReader, Matcher matcher, Aggregator aggregator) {
        this.webReader = webReader;
        this.matcher = matcher;
        this.aggregator = aggregator;
    }


    public void process() {
        try (webReader) {
            List<String> batch = new ArrayList<>();
            String line;
            int lineOffset = 0;

            while ((line = webReader.readLine()) != null) {
                batch.add(line);
                if (batch.size() == Config.BATCH_SIZE) {
                    String batchText = String.join(System.lineSeparator(), batch);
                    aggregator.aggregate(matcher.match(batchText, lineOffset));
                    lineOffset += batch.size();
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                String batchText = String.join(System.lineSeparator(), batch);
                Map<String, List<Occurrence>> matched = matcher.match(batchText, lineOffset);
                aggregator.aggregate(matched);
            }
            logger.info("App successfully finished.");
            aggregator.logAggregatedOccurrences();
        } catch (IOException e) {
            logger.error("Error during processing", e);
            throw new RuntimeException(e);
        }
    }
}

