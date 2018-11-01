package org.sendoh;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileSource {
    private final String path;

    FileSource(String[] args) throws URISyntaxException {
        this.path = getPathOrSampleDataPath(args);
    }

    public List<String> getPaths() throws IOException {
        final List<String> allFilePaths = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(it -> it.getFileName().toString().contains("json"))
                    .forEach(it -> allFilePaths.add(it.toString()));
        }
        return allFilePaths;
    }

    private String getPathOrSampleDataPath(String[] args) throws URISyntaxException {
        if (args.length != 0) {
            return args[0];
        } else {
            return Paths
                    .get(Objects.requireNonNull(FileSource.class.getClassLoader()
                            .getResource("sample")).toURI()).toString();
        }
    }
}
