package com.bigid.interview.processor;

import com.bigid.interview.reader.WebReader;
import com.bigid.interview.aggregator.Aggregator;
import com.bigid.interview.matcher.Matcher;
import com.bigid.interview.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadProcessor.class);

    private final WebReader webReader;
    private final Matcher matcher;
    private final Aggregator aggregator;

    public MultiThreadProcessor(WebReader webReader, Matcher matcher, Aggregator aggregator) {
        this.webReader = webReader;
        this.matcher = matcher;
        this.aggregator = aggregator;
    }

    public void process() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(Config.THREADS_COUNT);
             webReader) {
            List<String> batch = new ArrayList<>();
            String line;
            int lineOffset = 0;

            while ((line = webReader.readLine()) != null) {
                batch.add(line);
                if (batch.size() == Config.BATCH_SIZE) {
                    String batchText = String.join(System.lineSeparator(), batch);
                    final int offset = lineOffset;
                    CompletableFuture
                            .supplyAsync(() -> matcher.match(batchText, offset), executorService)
                            .thenAcceptAsync(aggregator::aggregate);
                    lineOffset += batch.size();
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                String batchText = String.join(System.lineSeparator(), batch);
                final int offset = lineOffset;
                CompletableFuture
                        .supplyAsync(() -> matcher.match(batchText, offset), executorService)
                        .thenAcceptAsync(aggregator::aggregate);
            }
            executorService.shutdown();
            boolean success = executorService.awaitTermination(Config.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            if (success) {
                logger.info("App successfully finished.");
            } else {
                logger.error("App timed out");
            }
            aggregator.logAggregatedOccurrences();
        } catch (IOException | InterruptedException e) {
            logger.error("Error during processing", e);
            throw new RuntimeException(e);
        }
    }
}

