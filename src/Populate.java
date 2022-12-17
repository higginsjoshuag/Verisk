public class Populate {
    public static void main(String[] args) throws Exception {
        DataBase db = new DataBase();
        db.connect();
        db.deleteTable();
        db.createTable();
        CSVReader.PopulateDatabase("addresses.csv");
        db.disconnect();
    }
}