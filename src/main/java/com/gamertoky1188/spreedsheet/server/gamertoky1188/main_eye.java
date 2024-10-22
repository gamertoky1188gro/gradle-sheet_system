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
//import java.util.Collections;
//import java.util.List;
//
//public class main_eye {
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
//    // RangeData class
//    public static class RangeData {
//        private String range;
//        private List<List<Object>> values;
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
//    // 1. Read a single range
//    public static List<List<Object>> readSingleRange(String spreadsheetId, String range) throws IOException, GeneralSecurityException {
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                getCredentials())
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
//        return response.getValues();
//    }
//
//    // 2. Write to a single range
//    public static void writeToSingleRange(String spreadsheetId, String range, List<List<Object>> values) throws IOException, GeneralSecurityException {
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                getCredentials())
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        ValueRange body = new ValueRange()
//                .setRange(range)
//                .setValues(values);
//
//        service.spreadsheets().values().update(spreadsheetId, range, body)
//                .setValueInputOption("RAW")
//                .execute();
//
//        System.out.println("Data written to range: " + range);
//    }
//
//    // 3. Read multiple ranges
//    public static List<ValueRange> readMultipleRanges(String spreadsheetId, List<String> ranges) throws IOException, GeneralSecurityException {
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                getCredentials())
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        List<ValueRange> response = service.spreadsheets().values().batchGet(spreadsheetId)
//                .setRanges(ranges)
//                .execute()
//                .getValueRanges();
//        return response;
//    }
//
//    // 4. Write multiple ranges
//    public static void writeMultipleRanges(String spreadsheetId, List<RangeData> rangeDataList) throws IOException, GeneralSecurityException {
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                getCredentials())
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
//        service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
//
//        System.out.println("Data written to multiple ranges.");
//    }
//
//    // 5. Display and filter data
//    public static void displayAndFilterData(String spreadsheetId, String range, String filter) throws IOException, GeneralSecurityException {
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                getCredentials())
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
//        List<List<Object>> values = response.getValues();
//
//        if (values == null || values.isEmpty()) {
//            System.out.println("No data found.");
//            return;
//        }
//
//        // Print all data
//        System.out.println("All Data:");
//        for (List<Object> row : values) {
//            System.out.println(String.join(" | ", row.stream().map(Object::toString).toArray(String[]::new)));
//        }
//
//        // Filter logic
//        if (filter != null && !filter.isEmpty()) {
//            String[] filterParts = filter.split(":");
//            if (filterParts.length == 2) {
//                String key = filterParts[0].trim().toLowerCase();
//                String value = filterParts[1].trim();
//
//                System.out.println("\nFiltered Data:");
//                for (List<Object> row : values) {
//                    for (int i = 0; i < row.size(); i++) {
//                        String header = values.get(0).get(i).toString().toLowerCase();
//                        if (header.equals(key) && row.get(i).toString().equals(value)) {
//                            System.out.println(String.join(" | ", row.stream().map(Object::toString).toArray(String[]::new)));
//                        }
//                    }
//                }
//            } else {
//                System.out.println("Invalid filter format. Use 'key: value'.");
//            }
//        }
//    }
//
//    // 6. Add data to the next available row
//    public static void addDataToNextAvailableRow(String spreadsheetId, String range, List<Object> newData) throws IOException, GeneralSecurityException {
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                getCredentials())
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        // Read the entire sheet data to find the last non-empty row
//        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
//        List<List<Object>> values = response.getValues();
//
//        int nextEmptyRow = values.size() + 1; // Next available row
//
//        String nextRowRange = "Sheet1!A" + nextEmptyRow; // Adjust based on sheet layout
//
//        // Prepare new data
//        List<List<Object>> data = new ArrayList<>();
//        data.add(newData);
//
//        ValueRange body = new ValueRange()
//                .setRange(nextRowRange)
//                .setValues(data);
//
//        service.spreadsheets().values().update(spreadsheetId, nextRowRange, body)
//                .setValueInputOption("RAW")
//                .execute();
//
//        System.out.println("Data added to next available row: " + nextRowRange);
//    }
//
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        // Example usage
//        String spreadsheetId = "1tVdSzXsM0ddWvOHozWFmm5kqAbPeC_XCriqdTTi2N4E"; // Replace with your spreadsheet ID
//        String range = "Sheet1!1:1000"; // Example range for all data
//
//        // Display all data
//        displayAndFilterData(spreadsheetId, range, null);
//
//        // Filter example
//        System.out.println("\n--- Filtering by 'name: A' ---");
//        displayAndFilterData(spreadsheetId, range, "Alice");
//
//        System.out.println("\n--- Filtering by 'roll: 10' ---");
//        displayAndFilterData(spreadsheetId, range, "25");
//
//        // Add new data to the next available row
//        List<Object> newData = List.of("gamertoky1188", "4005", "4", "13", "06-12-2011");
//        addDataToNextAvailableRow(spreadsheetId, range, newData);
//    }
//}
