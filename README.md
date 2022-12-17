# Verisk Interview

## Folder Structure

- `src`: the folder to maintain sources
- `bin`: the folder to maintain compiled class files    
- `lib`: the folder to maintain dependencies

## What does this project do?
This is a Java program that opens the provided "addresses.csv" file and reads all the addresses.

Using SQLite as the database, this program saves each address and its data values, as needed, to the database.

Using the values pulled from the SQLite database, this program generates a report that shows the counts for all the addresses per:

1. State

2. City

3. Zip

---

## How to Use this Project

1. Using VSCode, open the project folder.
2. Download "SQLite" extension from the marketplace
3. Download "Extension Pack for Java" extension from the marketplace
4. Cmd + Shift + P to open the command palette.
5. Type "SQLite: Open Database" and select "verisk.db".
6. Right click "Populate.java" file and click "Run Java." This will delete all tables and recreate the addresses table. This will take some time to recreate the addresses table.
7. Right click "GetQueryResults.java" file and click "Run Java." This will print out the query results. This also saves the results of the three queries as HashMaps, and as CSV files.


## How to View Query Results
1. View the respective CSV files in the project folder. The CSV files are named "state.csv", "city.csv", and "zip.csv". They are associated with the respective query results:
    - state.csv: state, count --> `SELECT state, count(*) FROM addresses GROUP BY state;`
    - city.csv: city, count --> `SELECT city, count(*) FROM addresses GROUP BY city;`
    - zip.csv: zip, count --> `SELECT zip, count(*) FROM addresses GROUP BY zip;`
2. View the HashMaps in the GetQueryResults.java file. The HashMaps are named "state", "city", and "zip". They are associated with the respective query results in a HashMap format.

---
## Troubleshooting

- If after running "[file].java" file, it says "Table 'addresses' not found" or "Database 'verisk' not found," then do command + shift + p and type "SQLite: Open Database" and select "verisk.db". This will open the database and you can run the file again.

