package ru.spbu.apcyb.svp.tasks.task3;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Directory tree scan class.
 *
 * @author Evgeny
 */

public class FileTreeScan {

  private Path pathOfDirectory;
  private final Path pathOfTargetFile;

  public FileTreeScan(Path pathOfDirectory, Path pathOfTargetFile) {
    this.pathOfDirectory = pathOfDirectory;
    this.pathOfTargetFile = pathOfTargetFile;
  }

  /**
   * Start scanning the directory tree and writing directories and files to the target file.
   *
   * @param args The desired directory and target file
   * @throws IOException File not found or read error
   */

  public static void main(String... args) throws IOException {
    if (args.length != 2) {
      throw new IllegalArgumentException(
          "The number of arguments does not match the required one, it should be 2, but in fact "
              + args.length);
    }

    FileTreeScan fileTreeScan = new FileTreeScan(Path.of(args[0]), Path.of(args[1]));
    fileTreeScan.scan();
  }

  /**
   * The method of scanning the desired directory and writing its file tree.
   *
   * @throws IOException The occurrence of a problem with recursive file traversal
   */

  public void scan() throws IOException {
    pathsToAbsolute();
    try (BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(pathOfTargetFile.toFile()))) {
      Files.walkFileTree(pathOfDirectory, new MyFileVisitor(bufferedWriter));
    } catch (IOException exception) {
      throw new IOException(
          "При рекурсивном обходе файлов возникла проблема:" + exception.getMessage());
    }
  }

  private void pathsToAbsolute() throws FileNotFoundException {
    if (!checkExists()) {
      throw new FileNotFoundException("Одного из файлов не существует");
    }
    if (!pathOfDirectory.isAbsolute()) {
      pathOfDirectory = pathOfDirectory.toAbsolutePath();
    }
  }

  private boolean checkExists() {
    return Files.exists(pathOfDirectory) && Files.exists(pathOfTargetFile);
  }

  public void setPathOfDirectory(Path pathOfDirectory) {
    this.pathOfDirectory = pathOfDirectory;
  }

  public Path getPathOfTargetFile() {
    return pathOfTargetFile;
  }
}

