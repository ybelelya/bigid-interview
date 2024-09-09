package com.bigid.interview;

import com.bigid.interview.processor.Processor;
import com.bigid.interview.processor.ProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        Processor processor = ProcessorFactory.getProcessor(Config.PROCESSOR_TYPE);
        long start = System.currentTimeMillis();
        processor.process();
        long end = System.currentTimeMillis();
        logger.info("================================");
        logger.info("Processing time: {}ms", end - start);
    }
}
