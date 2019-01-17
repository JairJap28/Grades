package com.example.jairjap.worksdidacticoscsj.SQLite;

public class Utilities {

    private static String CREATE_TABLE = "CREATE TABLE Subjects(id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT, teacher TEXT," +
            " grades TEXT, credits INTEGER, priority REAL, final REAL)";
    private static String NAME_TABLE = "Subjects";
    private static final String DATABASE_NAME = "Subjects.db";
    private static String DROP_TABLE = "DROP TABLE IF EXIST Subjects";
    private static String INSERT_DB = "INSERT INTO Subjects (subject, teacher, grades, credits, priority, final) VALUES (?,?,?,?,?,?)";
    private static String SELECT_ALL = "SELECT * FROM Subjects";


    public static String getCreateTable() {
        return CREATE_TABLE;
    }

    public static void setCreateTable(String createTable) {
        CREATE_TABLE = createTable;
    }

    public static String getNameTable() {
        return NAME_TABLE;
    }

    public static void setNameTable(String nameTable) {
        NAME_TABLE = nameTable;
    }

    public static String getDropTable() {
        return DROP_TABLE;
    }

    public static void setDropTable(String dropTable) {
        DROP_TABLE = dropTable;
    }

    public static String getInsertDb() {
        return INSERT_DB;
    }

    public static void setInsertDb(String insertDb) {
        INSERT_DB = insertDb;
    }

    public static String getSelectAll() {
        return SELECT_ALL;
    }

    public static void setSelectAll(String selectAll) {
        SELECT_ALL = selectAll;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
}
