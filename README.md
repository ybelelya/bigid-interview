## BigId Challenge

## Overview

Project fetches big file from web and processes it by chunks.

Project has 2 processors implementation:

- single thread - matches all inputs one by one
- multi thread - matches all inputs in several threads

Interface Config contains configuration constants

- `SOURCE` - website url
- `BATCH_SIZE` - lines count for processing at once by matcher
- `TIMEOUT_IN_SECONDS` - timeout for threads, available only for multi thread implementation
- `THREADS_COUNT` - threads count, available only for multi thread implementation
- `PROCESSOR_TYPE` = processor type (MULTI_THREAD or SINGLE_THREAD)

### Requirements

- Java21
- Maven 3.x

### How to run locally

```
    mvn clean install 
    mvn compile exec:java
```
