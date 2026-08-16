package com.cxxcxx.zinecraft.integration.tacz;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TaczArchitectureTest {
  private static final Path MAIN_JAVA = Path.of("src", "main", "java");

  private static boolean importsTacz(Path path) {
    try {
      return Files.readString(path).contains("import com.tacz.");
    } catch (IOException exception) {
      throw new IllegalStateException(exception);
    }
  }

  @Test
  void taczTypesStayInsideIntegrationModule() throws IOException {
    List<Path> leaks;
    try (var files = Files.walk(MAIN_JAVA)) {
      leaks = files.filter(path -> path.toString().endsWith(".java"))
          .filter(TaczArchitectureTest::importsTacz)
          .filter(path -> !path.normalize().startsWith(
              MAIN_JAVA.resolve(Path.of("com", "cxxcxx", "zinecraft", "integration", "tacz"))))
          .toList();
    }
    assertTrue(leaks.isEmpty(), () -> "TaCZ types leaked outside integration/tacz: " + leaks);
  }
}
