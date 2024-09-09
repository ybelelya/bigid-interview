package com.bigid.interview.processor;

import com.bigid.interview.Config;
import com.bigid.interview.aggregator.Aggregator;
import com.bigid.interview.matcher.Matcher;
import com.bigid.interview.reader.WebReader;

import java.io.IOException;
import java.net.URISyntaxException;

public class ProcessorFactory {

    public static Processor getProcessor(ProcessorType processorType) throws URISyntaxException, IOException {
        return switch (processorType) {
            case SINGLE_THREAD ->
                    new SingleThreadProcessor(new WebReader(Config.SOURCE), new Matcher(), new Aggregator());
            case MULTI_THREAD ->
                    new MultiThreadProcessor(new WebReader(Config.SOURCE), new Matcher(), new Aggregator());
        };
    }
}
