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
//import java.util.Collections;
//import java.util.List;
//
//public class GetValues {
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
//
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
//     * Retrieves a single value from a specific cell in a Google Sheets spreadsheet.
//     *
//     * @param spreadsheetId ID of the Google Spreadsheet.
//     * @param range Range of the spreadsheet to retrieve values from (e.g., "Sheet1!A1").
//     * @return The value from the specified cell, or null if empty.
//     * @throws IOException If there is an issue with the Google Sheets API request.
//     * @throws GeneralSecurityException If there is an issue with Google authentication.
//     */
//    public static String getSingleValue(String spreadsheetId, String range) throws IOException, GeneralSecurityException {
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
//        // Fetch the value from the specified cell in the spreadsheet
//        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
//
//        List<List<Object>> values = result.getValues();
//
//        if (values != null && !values.isEmpty()) {
//            // Return the single value (first item in the first row)
//            return values.get(0).get(0).toString();
//        } else {
//            System.out.println("No data found.");
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        // The ID of the spreadsheet and the specific cell to retrieve (e.g., "Sheet1!A1")
//        String spreadsheetId = "1tVdSzXsM0ddWvOHozWFmm5kqAbPeC_XCriqdTTi2N4E";  // Replace with your spreadsheet ID
//        String range = "Sheet1!A2";  // Adjust to the specific cell you want to read
//
//        // Call the getSingleValue method
//        String value = getSingleValue(spreadsheetId, range);
//        if (value != null) {
//            // Print the retrieved single value
//            System.out.println("Value from cell " + range + ": " + value);
//        }
//    }
//}
