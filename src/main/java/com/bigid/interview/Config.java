package com.bigid.interview;

import com.bigid.interview.processor.ProcessorType;

public interface Config {
    String SOURCE = "https://norvig.com/big.txt";
    int BATCH_SIZE = 1000;
    int TIMEOUT_IN_SECONDS = 20;
    int THREADS_COUNT = Runtime.getRuntime().availableProcessors();
    ProcessorType PROCESSOR_TYPE = ProcessorType.MULTI_THREAD;
}
