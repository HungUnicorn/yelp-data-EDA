package org.sendoh;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.junit.Assert.*;

public class FileSourceTest {

    @Test
    public void getPaths_givenEmptyArgs_returnSomeFromDefault() throws URISyntaxException, IOException {
        assertFalse(new FileSource(new String[0]).getPaths().isEmpty());
    }
}