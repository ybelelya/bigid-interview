package com.bigid.interview.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WebReader implements AutoCloseable{

    private final BufferedReader reader;

    public WebReader(String url) throws URISyntaxException, IOException {
        URL webUrl = new URI(url).toURL();
        this.reader= new BufferedReader(
                new InputStreamReader(webUrl.openStream()));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        if(reader != null) {
            reader.close();
        }
    }
}
