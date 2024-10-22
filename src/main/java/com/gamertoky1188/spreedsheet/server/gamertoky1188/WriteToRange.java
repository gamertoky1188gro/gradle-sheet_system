//package com.gamertoky1188.spreedsheet.server.gamertoky1188;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.model.ValueRange;
//import com.google.api.services.sheets.v4.SheetsScopes;
//
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class WriteToRange {
//
//    private static final String APPLICATION_NAME = "Sheets samples";
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final String CREDENTIALS_FILE_PATH = "G:\\java\\gradle\\project2\\gamertoky1188\\gamertoky1188\\src\\main\\resources\\credentials.json";
//
//    public static Credential getCredentials() throws IOException, GeneralSecurityException {
//        InputStream credentialsStream = new FileInputStream(CREDENTIALS_FILE_PATH);
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(credentialsStream));
//
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                clientSecrets,
//                Collections.singletonList(SheetsScopes.SPREADSHEETS))
//                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public static void writeToRange(String spreadsheetId, String range, Object[][] values) throws IOException, GeneralSecurityException {
//        Credential credentials = getCredentials();
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                credentials)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        List<List<Object>> data = new ArrayList<>();
//        for (Object[] row : values) {
//            data.add(Arrays.asList(row)); // Convert each row to a list
//        }
//
//        ValueRange body = new ValueRange()
//                .setValues(data); // Set the values as a list of lists
//
//        service.spreadsheets().values().update(spreadsheetId, range, body)
//                .setValueInputOption("RAW")
//                .execute();
//
//        System.out.println("Data written to range: " + range);
//    }
//
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        String spreadsheetId = "1tVdSzXsM0ddWvOHozWFmm5kqAbPeC_XCriqdTTi2N4E"; // Replace with your spreadsheet ID
//        String range = "Sheet1!A1"; // Specify the range to write data to
//
//        Object[][] values = {
//                {"Name", "Age", "City"},
//                {"Alice", 30, "New York"},
//                {"Bob", 25, "Los Angeles"}
//        };
//
//        writeToRange(spreadsheetId, range, values);
//    }
//}
