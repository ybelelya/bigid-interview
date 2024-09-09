package com.bigid.interview.matcher;

import com.bigid.interview.model.Occurrence;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MatcherTest {

    private final Matcher matcher = new Matcher();


    @Test
    void testMatchWithSingleName() {
        try (MockedStatic<Names> namesMock = Mockito.mockStatic(Names.class)) {
            namesMock.when(Names::content).thenReturn(Set.of("John", "Dave"));
            Map<String, List<Occurrence>> result = matcher.match("John likes apples", 0);

            assertTrue(result.containsKey("John"));
            assertEquals(1, result.get("John").size());

            Occurrence occurrence = result.get("John").getFirst();
            assertEquals(0, occurrence.charOffset());
            assertEquals(0, occurrence.lineOffset());
        }
    }

    @Test
    void testMatchWithMultipleNames() {
        try (MockedStatic<Names> namesMock = Mockito.mockStatic(Names.class)) {
            namesMock.when(Names::content).thenReturn(Set.of("John", "Dave"));
            Map<String, List<Occurrence>> result = matcher
                    .match("""
                            John and Dave are brothers.
                            John younger.
                            """, 0);

            assertTrue(result.containsKey("John"));
            assertTrue(result.containsKey("Dave"));
            assertEquals(2, result.get("John").size());
            assertEquals(1, result.get("Dave").size());

            Occurrence johnOccurrence1 = result.get("John").get(0);
            Occurrence johnOccurrence2 = result.get("John").get(1);
            Occurrence daveOccurrence = result.get("Dave").getFirst();

            assertEquals(0, johnOccurrence1.lineOffset());
            assertEquals(0, johnOccurrence1.charOffset());

            assertEquals(1, johnOccurrence2.lineOffset());
            assertTrue(johnOccurrence2.charOffset() > 0);

            assertEquals(0, daveOccurrence.lineOffset());
            assertTrue(daveOccurrence.charOffset() > 0);
        }
    }

    @Test
    void testMatchWithNoNames() {
        try (MockedStatic<Names> namesMock = Mockito.mockStatic(Names.class)) {
            namesMock.when(Names::content).thenReturn(Set.of("John", "Dave"));
            Map<String, List<Occurrence>> result = matcher.match("There are nothing here.", 0);

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void testMatchWithEmptyText() {
        try (MockedStatic<Names> namesMock = Mockito.mockStatic(Names.class)) {
            namesMock.when(Names::content).thenReturn(Set.of("John", "Dave"));
            Map<String, List<Occurrence>> result = matcher.match("", 0);

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void testMatchWithLineOffset() {
        try (MockedStatic<Names> namesMock = Mockito.mockStatic(Names.class)) {
            namesMock.when(Names::content).thenReturn(Set.of("John", "Dave"));
            String text = """
                    John is here.
                    Dave is there.
                    """;

            Map<String, List<Occurrence>> result = matcher.match(text, 5);
            Occurrence johnOccurrence = result.get("John").getFirst();
            Occurrence daveOccurrence = result.get("Dave").getFirst();

            assertEquals(5, johnOccurrence.lineOffset());
            assertEquals(6, daveOccurrence.lineOffset());
        }
    }
}

