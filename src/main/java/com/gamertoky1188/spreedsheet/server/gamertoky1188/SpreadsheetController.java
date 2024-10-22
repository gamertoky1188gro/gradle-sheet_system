package com.gamertoky1188.spreedsheet.server.gamertoky1188;

import com.google.api.client.auth.oauth2.Credential;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/sheets")
@CrossOrigin(origins = "*") 
public class SpreadsheetController {

    private static final String APPLICATION_NAME = "Sheets API";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "G:\\java\\gradle\\project2\\gamertoky1188\\gamertoky1188\\src\\main\\resources\\credentials.json";

    private Credential getCredentials() throws IOException, GeneralSecurityException {
        InputStream credentialsStream = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(credentialsStream));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singletonList(SheetsScopes.SPREADSHEETS))
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static class RangeData {
        private String range;
        private List<List<Object>> values;

        public RangeData() {}

        public RangeData(String range, List<List<Object>> values) {
            this.range = range;
            this.values = values;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public List<List<Object>> getValues() {
            return values;
        }

        public void setValues(List<List<Object>> values) {
            this.values = values;
        }
    }

    // 1. Get all data (read full sheet)
    @GetMapping("/getAllData")
    public List<List<Object>> getAllData(@RequestParam String spreadsheetId, @RequestParam String range) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        return response.getValues();
    }

    // 2. Filter values
    @PostMapping("/filterValues")
    public List<List<Object>> filterValues(@RequestParam String spreadsheetId, @RequestParam String range, @RequestParam String filterValue) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            return new ArrayList<>();
        }

        // Filter logic
        List<List<Object>> filteredData = new ArrayList<>();
        for (List<Object> row : values) {
            boolean valueFound = row.stream().anyMatch(cell -> cell.toString().equalsIgnoreCase(filterValue));
            if (valueFound) {
                filteredData.add(row);
            }
        }

        return filteredData;
    }

    // 3. Add data to next available row
    @PostMapping("/addDataToNextAvailableRow")
    public ResponseEntity<String> addDataToNextAvailableRow(@RequestParam String spreadsheetId, @RequestParam String range, @RequestBody List<Object> newData) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();

        // Check if 'values' is null, and initialize if necessary
        int nextEmptyRow;
        if (values == null || values.isEmpty()) {
            nextEmptyRow = 1;  // Assuming row 1 if the sheet is empty
        } else {
            nextEmptyRow = values.size() + 1;
        }

        String nextRowRange = "Sheet1!A" + nextEmptyRow;

        List<List<Object>> data = new ArrayList<>();
        data.add(newData);

        ValueRange body = new ValueRange().setRange(nextRowRange).setValues(data);
        service.spreadsheets().values().update(spreadsheetId, nextRowRange, body).setValueInputOption("RAW").execute();

        return ResponseEntity.ok("success" + nextEmptyRow);
    }

    // 4. Read single range
    @GetMapping("/readSingleRange")
    public List<List<Object>> readSingleRange(@RequestParam String spreadsheetId, @RequestParam String range) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        return response.getValues();
    }

    // 5. Read multiple ranges
    @PostMapping("/readMultipleRanges")
    public List<ValueRange> readMultipleRanges(@RequestParam String spreadsheetId, @RequestBody List<String> ranges) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<ValueRange> response = service.spreadsheets().values().batchGet(spreadsheetId).setRanges(ranges).execute().getValueRanges();
        return response;
    }

    // 6. Write to single range
    @PostMapping("/writeToSingleRange")
    public void writeToSingleRange(@RequestParam String spreadsheetId, @RequestBody RangeData rangeData) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange body = new ValueRange().setRange(rangeData.getRange()).setValues(rangeData.getValues());
        service.spreadsheets().values().update(spreadsheetId, rangeData.getRange(), body).setValueInputOption("RAW").execute();
    }

    // 7. Write multiple ranges
    @PostMapping("/writeMultipleRanges")
    public void writeMultipleRanges(@RequestParam String spreadsheetId, @RequestBody List<RangeData> rangeDataList) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<ValueRange> data = new ArrayList<>();
        for (RangeData rangeData : rangeDataList) {
            ValueRange valueRange = new ValueRange().setRange(rangeData.getRange()).setValues(rangeData.getValues());
            data.add(valueRange);
        }

        BatchUpdateValuesRequest body = new BatchUpdateValuesRequest().setValueInputOption("RAW").setData(data);
        service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute();
    }

    // 8. Replace value in sheet (Update a specific cell value)
    @PostMapping("/replaceValueInSheet")
    public void replaceValueInSheet(@RequestParam String spreadsheetId, @RequestParam String range, @RequestBody List<List<Object>> newValue) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange body = new ValueRange().setRange(range).setValues(newValue);
        service.spreadsheets().values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
    }

    // 9. Filter values and return them vertically (column-wise)
    @PostMapping("/filterValuesVertical")
    public List<List<Object>> filterValuesVertical(@RequestParam String spreadsheetId, @RequestParam String range, @RequestParam String filterValue) throws IOException, GeneralSecurityException {
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            return new ArrayList<>();
        }

        // Convert rows to columns
        List<List<Object>> columns = new ArrayList<>();
        int maxColumns = values.stream().mapToInt(List::size).max().orElse(0);

        for (int colIndex = 0; colIndex < maxColumns; colIndex++) {
            List<Object> column = new ArrayList<>();
            for (List<Object> row : values) {
                if (colIndex < row.size()) {
                    column.add(row.get(colIndex));
                } else {
                    column.add(""); // Handle empty cells
                }
            }
            columns.add(column);
        }

        // Filter columns based on the filterValue
        List<List<Object>> filteredColumns = new ArrayList<>();
        for (List<Object> column : columns) {
            if (column.stream().anyMatch(cell -> cell.toString().equalsIgnoreCase(filterValue))) {
                filteredColumns.add(column);
            }
        }

        // Prepare the response
        List<List<Object>> verticalResponse = new ArrayList<>();
        for (List<Object> column : filteredColumns) {
            for (Object cell : column) {
                List<Object> singleValueList = new ArrayList<>();
                singleValueList.add(cell);
                verticalResponse.add(singleValueList); // Add each value vertically
            }
        }

        return verticalResponse;
    }
}
