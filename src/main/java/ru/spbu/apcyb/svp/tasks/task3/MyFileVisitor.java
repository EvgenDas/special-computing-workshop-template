package ru.spbu.apcyb.svp.tasks.task3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

class MyFileVisitor implements FileVisitor<Path> {

  private final BufferedWriter bufferedWriter;

  public MyFileVisitor(BufferedWriter bufferedWriter) {
    this.bufferedWriter = bufferedWriter;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes)
      throws IOException {
    if (bufferedWriter != null) {
      bufferedWriter.write("Директория: " + path.toString() + "\n");
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes)
      throws IOException {
    if (bufferedWriter != null) {
      bufferedWriter.write("Файл: " + path.toString() + "\n");
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
    return FileVisitResult.TERMINATE;
  }

  @Override
  public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
    return FileVisitResult.CONTINUE;
  }
}