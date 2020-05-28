package com.example.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBhelper  {
    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;
    private PreparedStatement prepStat;
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");

        System.out.println("База Подключена!");
    }

    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'dictionary' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'EnglishWord' text, RussianWord text );");

        System.out.println("Таблица создана или уже существует.");
    }
    public static void AddWordToDB(Word NewWord) throws SQLException {
        String AddWordQuery ="INSERT INTO 'dictionary' ('EnglishWord', 'RussianWord') VALUES ('?', '?'); ";
        PreparedStatement prepStat = conn.prepareStatement(AddWordQuery);
        prepStat.setString(1,NewWord.getEnglishWord());
        prepStat.setString(2,NewWord.getRussianWord());
        prepStat.executeUpdate();

    }
    public static ArrayList<Word>  ReadAllWord() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM dictionary");
        ArrayList<Word> AllWordDB = new ArrayList<>();

        while(resSet.next())
        {
            Word word = new Word();
            int id = resSet.getInt("id");
            String  EnglishWord = resSet.getString("EnglishWord");
            String  RussianWord = resSet.getString("RussianWord");
            word.setId(id);
            word.setEnglishWord(EnglishWord);
            word.setRussianWord(RussianWord);
            AllWordDB.add(word);
        }

        System.out.println("Таблица выведена");
        return AllWordDB;
    }
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }


}
