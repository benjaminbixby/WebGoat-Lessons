package org.owasp.webgoat.converter;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class JavaSourceTest {

    private Path tempDirectory;

    @Before
    public void init() throws IOException {
        tempDirectory = Files.createTempDirectory("test");
        tempDirectory.toFile().deleteOnExit();
    }

    @Test
    public void regularExpressionShouldPickUpProperty() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("aaaWebGoatI18N.get(\"test\")");
        Path tempFile = tempDirectory.resolve("test");
        Files.write(tempFile, lines, StandardCharsets.UTF_8);
        List<String> properties = new JavaSource(tempFile, "Test").referencedProperties();
        assertThat(properties, IsCollectionContaining.hasItem("test"));
    }
}