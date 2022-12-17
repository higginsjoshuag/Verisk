import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public class GetQueryResults {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DataBase db = new DataBase();
        db.connect();

        // get count of addresses by state
        String sqlquery = "SELECT state, COUNT(*) FROM addresses GROUP BY state";
        HashMap<String, Integer> state = db.query(sqlquery);
        write2CSV(state, "state.csv", "state,count");
        // printResults(result1);

        // get count of addresses by city
        sqlquery = "SELECT city, COUNT(*) FROM addresses GROUP BY city";
        HashMap<String, Integer> city = db.query(sqlquery);
        write2CSV(city, "city.csv", "city,count");
        // printResults(result2);

        // get count of addresses by zip
        sqlquery = "SELECT zip, COUNT(*) FROM addresses GROUP BY zip";
        HashMap<String, Integer> zip = db.query(sqlquery);
        write2CSV(zip, "zip.csv", "zip,count");
        // printResults(result3);

        db.disconnect();
    }

    public static void printResults(HashMap<String, Integer> results) {
        for (String key : results.keySet()) {
            System.out.println(key + ": " + results.get(key));
        }
        System.out.println("Total: " + results.size());
    }

    public static void write2CSV(HashMap<String, Integer> myMap, String fileName, String header) {
        String eol = System.getProperty("line.separator");

        try (Writer writer = new FileWriter(fileName)) {
            writer.append(header).append(eol);
            for (Entry<String, Integer> entry : myMap.entrySet()) {
            writer.append(entry.getKey())
                    .append(',')
                    .append(entry.getValue().toString())
                    .append(eol);
            }
        } catch (IOException ex) {
          ex.printStackTrace(System.err);
        }
    }
}
