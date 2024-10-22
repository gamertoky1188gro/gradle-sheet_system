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
//import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
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
//public class WriteMultipleRanges {
//
//    private static final String APPLICATION_NAME = "Sheets samples";
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final String CREDENTIALS_FILE_PATH = "G:\\java\\gradle\\project2\\gamertoky1188\\gamertoky1188\\src\\main\\resources\\credentials.json";
//
//    // Nested class for handling range data
//    public static class RangeData {
//        private String range; // The range in the format "Sheet1!A1:C1"
//        private List<List<Object>> values; // 2D list of values for this range
//
//        public RangeData(String range, List<List<Object>> values) {
//            this.range = range;
//            this.values = values;
//        }
//
//        public String getRange() {
//            return range;
//        }
//
//        public List<List<Object>> getValues() {
//            return values;
//        }
//    }
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
//    public static void writeMultipleRanges(String spreadsheetId, List<RangeData> rangeDataList) throws IOException, GeneralSecurityException {
//        Credential credentials = getCredentials();
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                credentials)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        List<ValueRange> data = new ArrayList<>();
//        for (RangeData rangeData : rangeDataList) {
//            ValueRange valueRange = new ValueRange()
//                    .setRange(rangeData.getRange())
//                    .setValues(rangeData.getValues());
//            data.add(valueRange);
//        }
//
//        BatchUpdateValuesRequest body = new BatchUpdateValuesRequest().setValueInputOption("RAW").setData(data);
//
//        // Perform the batch update
//        service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
//
//        System.out.println("Data written to multiple ranges.");
//    }
//
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        String spreadsheetId = "1tVdSzXsM0ddWvOHozWFmm5kqAbPeC_XCriqdTTi2N4E"; // Replace with your spreadsheet ID
//
//        // Example data to write (2D array) for multiple ranges
//        List<RangeData> ranges = new ArrayList<>();
//        ranges.add(new RangeData("Sheet1!A1:C1", Arrays.asList(Arrays.asList("Name", "Age", "City"))));
//        ranges.add(new RangeData("Sheet1!A3:C3", Arrays.asList(Arrays.asList("Alice", 30, "New York"))));
//        ranges.add(new RangeData("Sheet1!A5:C5", Arrays.asList(Arrays.asList("Bob", 25, "Los Angeles"))));
//
//        // Call the writeMultipleRanges method
//        writeMultipleRanges(spreadsheetId, ranges);
//    }
//}
