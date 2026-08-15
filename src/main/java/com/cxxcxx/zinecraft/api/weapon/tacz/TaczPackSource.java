package com.cxxcxx.zinecraft.api.weapon.tacz;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

sealed interface TaczPackSource permits TaczPackSource.Directory, TaczPackSource.Zip {
  static TaczPackSource of(Path path) {
    return Files.isDirectory(path) ? new Directory(path) : new Zip(path);
  }

  String name();

  InputStream open(String path);

  Stream<String> paths();

  record Directory(Path root) implements TaczPackSource {
    @Override
    public String name() {
      return root.getFileName().toString();
    }

    @Override
    public InputStream open(String path) {
      String normalized = TaczGunPackKt.normalize(path);
      if (normalized == null) return null;
      Path normalizedRoot = root.normalize();
      Path target = normalizedRoot.resolve(normalized).normalize();
      if (!target.startsWith(normalizedRoot) || !Files.isRegularFile(target)) return null;
      try {
        return Files.newInputStream(target);
      } catch (IOException ignored) {
        return null;
      }
    }

    @Override
    public Stream<String> paths() {
      try (Stream<Path> stream = Files.walk(root)) {
        List<String> result = stream.filter(Files::isRegularFile)
            .map(path -> root.relativize(path).toString().replace('\\', '/')).toList();
        return result.stream();
      } catch (IOException ignored) {
        return Stream.empty();
      }
    }
  }

  record Zip(Path file) implements TaczPackSource {
    @Override
    public String name() {
      return file.getFileName().toString();
    }

    @Override
    public InputStream open(String path) {
      String normalized = TaczGunPackKt.normalize(path);
      if (normalized == null) return null;
      try {
        ZipFile zip = new ZipFile(file.toFile());
        ZipEntry entry = zip.getEntry(normalized);
        if (entry == null || entry.isDirectory()) {
          zip.close();
          return null;
        }
        return new FilterInputStream(zip.getInputStream(entry)) {
          @Override
          public void close() throws IOException {
            try {
              super.close();
            } finally {
              zip.close();
            }
          }
        };
      } catch (IOException ignored) {
        return null;
      }
    }

    @Override
    public Stream<String> paths() {
      try (ZipFile zip = new ZipFile(file.toFile())) {
        List<String> result = zip.stream().filter(entry -> !entry.isDirectory()).map(ZipEntry::getName).toList();
        return result.stream();
      } catch (IOException ignored) {
        return Stream.empty();
      }
    }
  }
}
