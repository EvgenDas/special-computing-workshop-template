package ru.spbu.apcyb.svp.tasks.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.spbu.apcyb.svp.tasks.task3.DriveQuickstart.getCredentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class testing communication with the Google Drive API
 *
 * @author Evgeny
 */
class DriveQuickstartTest {

  private DriveQuickstart driveQuickstart;
  private Drive service;


  @BeforeEach
  void setUp() throws GeneralSecurityException, IOException {
    driveQuickstart = new DriveQuickstart();
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    service = new Drive.Builder(httpTransport, GsonFactory.getDefaultInstance(),
        getCredentials(httpTransport))
        .setApplicationName("Google Drive API Java Quickstart")
        .build();
  }

  @Test
  void getTenFilesAndPrintTest() throws IOException {
    FileList fileList = driveQuickstart.getAndPrintFiles(service, 10);
    assertEquals(10, fileList.getFiles().size());
  }

  @Test
  void getFileByIdTest() throws IOException {
    File file = driveQuickstart.getFileById(service,
        "1Tnn93SGaKBLbEQrnnCgIB4F8Eg49tFzQ9etHkFPZrIw");
    assertEquals("test1-score", file.getName());
  }

  @Test
  void uploadFileTest() throws IOException {
    String id = driveQuickstart.
        uploadFile(service, "file_scan.txt", "src/test/resources/file_scan.txt", "file/txt");
    assertEquals("file_scan.txt", driveQuickstart.getFileById(service, id).getName());
  }

  @Test
  void deleteFileTest() throws IOException {
    String id = driveQuickstart.
        uploadFile(service, "file_scan.txt", "src/test/resources/file_scan.txt", "file/txt");
    driveQuickstart.deleteFile(service, id);
    assertThrows(IOException.class, () -> driveQuickstart.getFileById(service, id));
  }

  @Test
  void getAndPrintFilesThrownIOException() {
    assertThrows(IOException.class, () -> driveQuickstart.getAndPrintFiles(service, -1));
  }

  @Test
  void getFileByIdThrownIOException() {
    assertThrows(IOException.class, () -> driveQuickstart.getFileById(service, "id"));
  }

  @Test
  void uploadFileThrownIOException() {
    assertThrows(IOException.class, () -> driveQuickstart.uploadFile(service, "file_scan.txt",
        "src/test/resource/file_scan.txt", "file/txt"));
  }

  @Test
  void deleteFileThrownIOException() {
    assertThrows(IOException.class, () -> driveQuickstart.deleteFile(service, "id"));
  }

  @Test
  void uploadFileThrownIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class,
        () -> driveQuickstart.uploadFile(service, "file_scan.txt",
            "src/test/resource/file_scan.txt", "fil"));
  }

}
