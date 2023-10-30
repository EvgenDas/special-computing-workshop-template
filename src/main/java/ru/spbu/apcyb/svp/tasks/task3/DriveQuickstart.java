package ru.spbu.apcyb.svp.tasks.task3;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class for communicating with the Google Drive api.
 *
 * @author Evgeny
 */
public class DriveQuickstart {

  public static final Logger logger = Logger.getLogger(DriveQuickstart.class.getName());

  /**
   * Application name.
   */
  private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
  /**
   * Global instance of the JSON factory.
   */
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  /**
   * Directory to store authorization tokens for this application.
   */
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /**
   * Global instance of the scopes required by this quickstart. If modifying these scopes, delete
   * your previously saved tokens/ folder.
   */
  private static final List<String> SCOPES =
      Collections.singletonList(DriveScopes.DRIVE);

  /**
   * Creates an authorized Credential object.
   *
   * @param httpTransport The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */

  public static Credential getCredentials(final NetHttpTransport httpTransport)
      throws IOException {
    InputStream in = DriveQuickstart.class.getResourceAsStream("/credentials.json");
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + "/credentials.json");
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  /**
   * The method launches 4 different actions, interacting with Google Drive.
   *
   * @param args String args
   * @throws IOException              reading error
   * @throws GeneralSecurityException authentication error
   */
  public static void main(String... args) throws IOException, GeneralSecurityException {
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
        .setApplicationName(APPLICATION_NAME)
        .build();

    DriveQuickstart driveQuickstart = new DriveQuickstart();

    driveQuickstart.getAndPrintFiles(service, 5);
    driveQuickstart.getFileById(service, "1p_pUBEQKOXMujuY6EVuRyqSp7TN93wT7");
    driveQuickstart.uploadFile(service, "file_tree.txt", "src/main/resources/file_tree.txt",
        "file/txt");
    driveQuickstart.deleteFile(service, "1aWgQRVIQb6QQU-7Nz6k9gm6lp54_9G2Q");
  }

  /**
   * The method accesses the disk and outputs the first 10 files.
   *
   * @param service       connection to the disk
   * @param numberOfFiles 1 <= number <= 1000
   * @return FileList
   * @throws IOException reading error
   */

  public FileList getAndPrintFiles(Drive service, int numberOfFiles) throws IOException {
    FileList result = service.files().list()
        .setPageSize(numberOfFiles)
        .setFields("nextPageToken, files(id, name)")
        .execute();
    List<File> files = result.getFiles();
    if (files == null || files.isEmpty()) {
      logger.log(Level.INFO, "No files found.");
    } else {
      logger.log(Level.INFO, "Files:");
      for (File file : files) {
        logger.log(Level.INFO, "File {0}\n", file.getId() + ":" + file.getName());
      }
    }
    return result;
  }

  /**
   * The method accesses the disk and outputs file.
   *
   * @param service connection to the disk
   * @param id      file id
   * @return File
   * @throws IOException reading error
   */

  public File getFileById(Drive service, String id) throws IOException {
    File file = service.files().get(id).execute();
    if (file == null) {
      logger.log(Level.INFO, "No file found.");
    } else {
      logger.log(Level.INFO, "File {0}\n", file.getId() + ":" + file.getName());
    }
    return file;
  }

  /**
   * The method accesses the disk and upload file.
   *
   * @param service     connection to the disk
   * @param name        file name
   * @param path        file path
   * @param fileContent file Type
   * @return String file ID
   * @throws IOException reading error
   */

  public String uploadFile(Drive service, String name, String path, String fileContent)
      throws IOException {
    File fileMetadata = new File();
    fileMetadata.setName(name);
    java.io.File filePath = new java.io.File(path);
    FileContent mediaContent = new FileContent(fileContent, filePath);
    try {
      File file = service.files().create(fileMetadata, mediaContent)
          .setFields("id")
          .execute();
      logger.log(Level.INFO, "File ID: {0}", file.getId());
      return file.getId();
    } catch (GoogleJsonResponseException e) {
      throw new IllegalArgumentException("Unable to upload file: " + e.getDetails());
    }
  }

  /**
   * The method accesses the disk and delete file.
   *
   * @param service connection to the disk
   * @param fileId  file ID
   * @throws IOException reading error
   */
  public void deleteFile(Drive service, String fileId) throws IOException {
    try {
      service.files().delete(fileId).execute();
    } catch (IOException e) {
      throw new IOException("Не удаётся удалить файл");
    }
    logger.log(Level.INFO, "Файл успешно удалён {0}", fileId);
  }
}