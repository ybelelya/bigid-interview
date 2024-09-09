
package com.bigid.interview.processor;

import com.bigid.interview.aggregator.Aggregator;
import com.bigid.interview.matcher.Matcher;
import com.bigid.interview.reader.WebReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MultiThreadProcessorTest {

    @InjectMocks
    private MultiThreadProcessor processor;

    @Mock
    private WebReader webReaderMock;

    @Mock
    private Aggregator aggregatorMock;

    @Mock
    private Matcher matcherMock;

    @Test
    void testProcessWithBatchProcessing() throws IOException {
        List<String> lines = List.of("line 1", "line 2", "line 3", "line 4", "line 5");
        when(webReaderMock.readLine())
                .thenReturn(lines.get(0))
                .thenReturn(lines.get(1))
                .thenReturn(lines.get(2))
                .thenReturn(lines.get(3))
                .thenReturn(null);

        when(matcherMock.match(anyString(), anyInt())).thenReturn(Map.of());

        processor.process();

        verify(matcherMock, times(1)).match(anyString(), eq(0));
        verify(aggregatorMock, times(1)).aggregate(any());
    }
}