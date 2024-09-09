package com.bigid.interview.model;

public record Occurrence(long lineOffset, long charOffset) {

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + " , charOffset=" + charOffset + "]";
    }
}
