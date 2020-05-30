package com.example.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBhelper {
    private  Connection conn;
    private  Statement statmt;
    private  ResultSet resSet;
    private  PreparedStatement prepStat;
    public  void conn() throws ClassNotFoundException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:DBdictianary.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("База Подключена!");
    }

    public  void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'dictionary' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'EnglishWord' text, RussianWord text );");

        System.out.println("Таблица создана или уже существует.");
    }
    public  void addWordToDB(Word NewWord) throws SQLException {
        String AddWordQuery ="INSERT INTO 'dictionary' ('EnglishWord', 'RussianWord') VALUES ('?', '?'); ";
        prepStat = conn.prepareStatement(AddWordQuery);
        prepStat.setString(1,NewWord.getEnglishWord());
        prepStat.setString(2,NewWord.getRussianWord());
        prepStat.executeUpdate();

    }
    public  ArrayList<Word>  readAllWord() throws ClassNotFoundException, SQLException
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
    public  void closeDB() throws ClassNotFoundException
    {
        try {
            conn.close();
            statmt.close();
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Соединения закрыты");
    }
}
