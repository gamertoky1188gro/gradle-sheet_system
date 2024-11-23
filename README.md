# Sheet Submit Project

This repository contains the **Sheet Submit Project**, which is a Spring Boot application integrating with the Google Sheets API. The project allows users to interact with Google Sheets through various API endpoints for data manipulation. 

👉 **Project Link:** [Sheet Submit Project](https://github.com/gamertoky1188gro/gradle-sheet_system)

---

## How It Works

1. **Run the Server**  
   When the application starts, it spins up a server and provides a URL for Google OAuth 2.0 authentication.  
   
2. **Authentication**  
   - The authentication is currently restricted to **test emails** listed in the Google Cloud Console under OAuth 2.0 credentials.  
   - If you want to test this project, share your email with me, and I'll add it to the test email list.  
   - **Why not in production?**  
     - Moving to production is possible but involves several complications. Contact me if you'd like to know the details.  

3. **Post-Authentication**  
   - Once authenticated, the server shuts down automatically or must be closed manually.  
   - Restart the server to use the endpoints provided for interacting with Google Sheets.

---

## Available API Endpoints

### 1. **Get All Data**  
   Fetches all data from a specific range in the spreadsheet.  
   - **Endpoint:** `/api/sheets/getAllData`  
   - **Method:** `GET`  
   - **Parameters:**  
     - `spreadsheetId` (String): The ID of the Google Sheet.  
     - `range` (String): The range to retrieve.  

### 2. **Filter Values**  
   Filters rows containing a specific value.  
   - **Endpoint:** `/api/sheets/filterValues`  
   - **Method:** `POST`  
   - **Parameters:**  
     - `spreadsheetId` (String)  
     - `range` (String)  
     - `filterValue` (String)  

### 3. **Add Data to Next Available Row**  
   Appends data to the next available row.  
   - **Endpoint:** `/api/sheets/addDataToNextAvailableRow`  
   - **Method:** `POST`  
   - **Parameters:**  
     - `spreadsheetId` (String)  
     - `range` (String): A starting range (e.g., `Sheet1!A1`).  
   - **Request Body:** List of values to add.  

### 4. **Read Single Range**  
   Reads data from a specified range.  
   - **Endpoint:** `/api/sheets/readSingleRange`  
   - **Method:** `GET`  
   - **Parameters:**  
     - `spreadsheetId` (String)  
     - `range` (String)  

### 5. **Read Multiple Ranges**  
   Reads data from multiple ranges.  
   - **Endpoint:** `/api/sheets/readMultipleRanges`  
   - **Method:** `POST`  
   - **Parameters:**  
     - `spreadsheetId` (String)  
   - **Request Body:** List of ranges to retrieve.  

### 6. **Write to Single Range**  
   Writes data to a specified range.  
   - **Endpoint:** `/api/sheets/writeToSingleRange`  
   - **Method:** `POST`  
   - **Request Body:**  
     ```json
     {
       "range": "Sheet1!A1",
       "values": [[...]]
     }
     ```  

### 7. **Write Multiple Ranges**  
   Writes data to multiple ranges.  
   - **Endpoint:** `/api/sheets/writeMultipleRanges`  
   - **Method:** `POST`  
   - **Request Body:** List of range-data pairs.  

### 8. **Replace Value in Sheet**  
   Replaces data in a specified cell range.  
   - **Endpoint:** `/api/sheets/replaceValueInSheet`  
   - **Method:** `POST`  
   - **Parameters:**  
     - `spreadsheetId` (String)  
     - `range` (String): The cell range to update.  
   - **Request Body:** New value(s) to replace.  

### 9. **Filter Values and Return Vertically**  
   Filters data and transforms the results into a vertical list.  
   - **Endpoint:** `/api/sheets/filterValuesVertical`  
   - **Method:** `POST`  
   - **Parameters:**  
     - `spreadsheetId` (String)  
     - `range` (String)  
     - `filterValue` (String)  

---

## Notes

1. **Credentials**  
   The credentials file path must be set to the correct location:  
   `G:\java\gradle\project2\gamertoky1188\gamertoky1188\src\main\resources\credentials.json`.  
   Ensure this file exists and is configured correctly.

2. **Authorization**  
   Only test emails added in the Google Cloud Console can authenticate. Moving to production would allow any user to authenticate but involves challenges.

3. **Feedback and Issues**  
   If you encounter any problems or have suggestions, feel free to open an issue or contact me.

---

Thank you for exploring the project! 😊
