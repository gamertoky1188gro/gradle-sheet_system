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
//import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
//import com.google.api.services.sheets.v4.model.ValueRange;
//import com.google.api.services.sheets.v4.SheetsScopes;
//
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Arrays;
//
//public class BatchGetValues {
//
//    // Scopes required for the Google Sheets API
//    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final String CREDENTIALS_FILE_PATH = "G:\\java\\gradle\\project2\\gamertoky1188\\gamertoky1188\\src\\main\\resources\\credentials.json"; // Path to OAuth credentials JSON file
//
//    /**
//     * Creates an authorized Credential object using OAuth 2.0 flow.
//     *
//     * @return Credential object for accessing Google Sheets API.
//     * @throws IOException If credentials file is not found.
//     * @throws GeneralSecurityException If there's an issue with the transport security.
//     */
//    public static Credential getCredentials() throws IOException, GeneralSecurityException {
//        // Load client secrets from the credentials.json file
//        InputStream credentialsStream = new FileInputStream(CREDENTIALS_FILE_PATH);
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(credentialsStream));
//
//        // Build the flow and trigger user authorization
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                clientSecrets,
//                SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//
//        // LocalServerReceiver to handle the redirect back after user authorization
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    /**
//     * Retrieves multiple ranges of values from a Google Sheets spreadsheet.
//     *
//     * @param spreadsheetId ID of the Google Spreadsheet.
//     * @param ranges        List of ranges to retrieve values from (e.g., "Sheet1!A1:D1", "Sheet1!E1:E2").
//     * @return A BatchGetValuesResponse containing the values retrieved from the spreadsheet.
//     * @throws IOException              If there is an issue with the Google Sheets API request.
//     * @throws GeneralSecurityException If there is an issue with Google authentication.
//     */
//    public static BatchGetValuesResponse batchGetValues(String spreadsheetId, List<String> ranges) throws IOException, GeneralSecurityException {
//        // Get authorized credentials using OAuth 2.0 flow
//        Credential credentials = getCredentials();
//
//        // Create the Sheets API client
//        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
//                GsonFactory.getDefaultInstance(),
//                credentials)
//                .setApplicationName("Sheets samples")
//                .build();
//
//        // Use batchGet to retrieve multiple ranges
//        BatchGetValuesResponse response = service.spreadsheets().values().batchGet(spreadsheetId)
//                .setRanges(ranges)
//                .execute();
//
//        return response;
//    }
//
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        // The ID of the spreadsheet and the specific ranges to retrieve
//        String spreadsheetId = "1tVdSzXsM0ddWvOHozWFmm5kqAbPeC_XCriqdTTi2N4E";  // Replace with your spreadsheet ID
//
//        // Specify multiple ranges
//        List<String> ranges = Arrays.asList("Sheet1!A1:D1", "Sheet1!E1:E2", "Sheet1!A2");  // Adjust to the ranges you want to read
//
//        // Call the batchGetValues method
//        BatchGetValuesResponse response = batchGetValues(spreadsheetId, ranges);
//
//        if (response != null && response.getValueRanges() != null) {
//            for (ValueRange valueRange : response.getValueRanges()) {
//                System.out.println("Values from range " + valueRange.getRange() + ": " + valueRange.getValues());
//            }
//        } else {
//            System.out.println("No data found.");
//        }
//    }
//}
