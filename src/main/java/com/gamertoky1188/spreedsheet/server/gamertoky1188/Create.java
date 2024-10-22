//package com.gamertoky1188.spreedsheet.server.gamertoky1188;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.SheetsScopes;
//import com.google.api.services.sheets.v4.model.Spreadsheet;
//import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//
///* Class to demonstrate the use of Spreadsheet Create API */
//public class Create {
//
//    private static final String CLIENT_SECRET_FILE = "G:\\java\\gradle\\project2\\gamertoky1188\\gamertoky1188\\src\\main\\resources\\credentials.json"; // Path to your client_secret.json file
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
//    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // Directory to store tokens
//
//    // Constructor
//    private Create() {
//    }
//
//    /**
//     * Authorizes the installed application to access user's protected data.
//     */
//    private static Credential authorize(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        // Load client secrets from credentials.json file
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(CLIENT_SECRET_FILE));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setAccessType("offline")
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .build();
//
//        // LocalServerReceiver to receive the auth code.
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//
//        // Authorize and get credentials.
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    /**
//     * Create a new spreadsheet.
//     *
//     * @param title - the name of the sheet to be created.
//     * @return newly created spreadsheet id
//     * @throws IOException - if credentials file not found.
//     */
//    public static String createSpreadsheet(String title) throws IOException {
//        // Initialize HTTP Transport
//        final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//
//        // Authorize and get credentials
//        Credential credential = authorize(HTTP_TRANSPORT);
//
//        // Create the sheets API client
//        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName("Sheets samples")
//                .build();
//
//        // Create new spreadsheet with a title
//        Spreadsheet spreadsheet = new Spreadsheet()
//                .setProperties(new SpreadsheetProperties()
//                        .setTitle(title));
//        spreadsheet = service.spreadsheets().create(spreadsheet)
//                .setFields("spreadsheetId")
//                .execute();
//
//        // Prints the new spreadsheet ID
//        System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
//        return spreadsheet.getSpreadsheetId();
//    }
//
//    public static void main(String[] args) {
//        try {
//            // Create a new spreadsheet with the title "My Spreadsheet"
//            createSpreadsheet("My Spreadsheet");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
