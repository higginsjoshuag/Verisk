import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class CSVReader {

    public static void PopulateDatabase(String csvFilePath) throws FileNotFoundException, ClassNotFoundException, SQLException {

        String line = "";
        String csvSplitBy = ",";
        Scanner sc = new Scanner(new File(csvFilePath));

        DataBase db = new DataBase();
        db.connect();

        String address = null;
        String city = null;
        String state = null;
        String zip = null;

        String[] result = new String[4];

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] splitStr = line.split(csvSplitBy);

            // These are ones with valid inputs. 4 is the correct number of fields
            if (splitStr.length == 4) {
                result = parseFourInputs(splitStr);
            } else if (splitStr.length == 3) {
                result = parseThreeInputs(splitStr);
            } else if (splitStr.length == 1) {
                result = parseOneInput(splitStr);
            }

            address = result[0];
            city = result[1];
            state = result[2];
            zip = result[3];

            // verify the zip code and state are valid
            if (isValidZip(zip) == false) {
                zip = null;
            }
            if (state != null) {
                state = state.toUpperCase();
            }
            if (isValidState(state) == false) {
                state = null;
            }
            if (city.equals("")) {
                city = null;
            }
            if (address.equals("")) {
                address = null;
            }
            // insert into the database
            db.insert(address, city, state, zip);
        }
        db.disconnect();
        sc.close();
    }

    public static String[] parseFourInputs(String[] splitStr) {
        String address = null;
        String city = null;
        String state = null;
        String zip = null;

        String[] result = new String[4];
        address = splitStr[0].trim();
        city = splitStr[1].trim();
        state = splitStr[2].trim();
        if (state.length() > 2) {
            // the 2 major exceptions are Utah and Nevada
            if (state.equals("Utah")) {
                state = "UT";
            }
            if (state.equals("Nevada")) {
                state = "NV";
            }
        }
        zip = splitStr[3].trim();
        // exception found: FL 33315
        if (state.equals("FL 33315")) {
            state = "FL";
            zip = "33315";
        }
        result[0] = address;
        result[1] = city;
        result[2] = state;
        result[3] = zip;

        return result;
    }

    public static String[] parseThreeInputs(String[] splitStr) {
        String address = null;
        String city = null;
        String state = null;
        String zip = null;
        String[] result = new String[4];
        if (splitStr[2].trim().indexOf(" ") != -1) {
            // For the below examples...
            // First: 2717 COUNTY ROAD 269 Second:  EARLY Third:  Texas 76802
            // Split the last element by " " to get Texas and 76802
            String[] splitStr2 = splitStr[2].split(" ");
            address = splitStr[0].trim();
            city = splitStr[1].trim();
            state = "TX";     // This is always Texas
            zip = splitStr2[2].trim();
        } else {
            address = splitStr[0].trim();
            city = splitStr[1].trim();
            state = splitStr[2].trim();
            zip = null;
        }
        result[0] = address;
        result[1] = city;
        result[2] = state;
        result[3] = zip;

        return result;
    }

    public static String[] parseOneInput(String[] splitStr) {
        String address = null;
        String city = null;
        String state = null;
        String zip = null;

        // these values are separated by | instead of ,
        String[] splitStr2 = splitStr[0].split("\\|");
        address = splitStr2[0].trim();
        city = splitStr2[1].trim();
        state = splitStr2[2].trim();
        zip = splitStr2[3].trim();

        String[] result = new String[4];
        result[0] = address;
        result[1] = city;
        result[2] = state;
        result[3] = zip;

        return result;
    }

    public static boolean isValidZip(String zip) {
        if (zip == null) {
            return true;
        }
        // Some Canadian Zip Codes
        else if (zip.length() == 3 && zip.matches("[A-Z][0-9][A-Z]")) {
            return true;
        }
        // US Zip Codes
        else if (zip.length() == 5 && zip.matches("[0-9]+")) {
            return true;
        }
        // Canadian w/o middle space
        else if (zip.length() == 6 && zip.matches("[A-Z][0-9][A-Z][0-9][A-Z][0-9]")) {
            // add the space
            zip = zip.substring(0, 3) + " " + zip.substring(3);
            return true;
        }
        // Canadian Zip Codes
        else if (zip.length() == 7 && zip.matches("[A-Z][0-9][A-Z] [0-9][A-Z][0-9]")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidState(String state) {
        if (state == null) {
            return true;
        }
        if (state.length() == 2 && state.matches("[A-Z][A-Z]")) {
            return true;
        } else {
            return false;
        }
    }
}