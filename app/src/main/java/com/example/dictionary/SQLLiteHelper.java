package com.example.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLLiteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    final private static String DATABASE_NAME="DBdictianary.db";

    public SQLLiteHelper(Context cont){
        super(cont, DATABASE_NAME,null,1);
        try {
            db = this.getWritableDatabase();
        }
        catch (SQLiteException ex){
            db = this.getReadableDatabase();
        }
    }

    private void refresh(){
        try {
            db = this.getWritableDatabase();
        }
        catch (SQLiteException ex){
            db = this.getReadableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE if not exists dictionary (id INTEGER PRIMARY KEY AUTOINCREMENT, EnglishWord text, RussianWord text );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dictionary");
        onCreate(db);
    }

    public void deleteWordOnDB(int id) throws SQLException{
        refresh();
        db.delete("dictionary","id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Word> getAllWordOnDB(){
        refresh();
        ArrayList<Word> AllWord = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM  dictionary;",null);
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(0));
            word.setEnglishWord(cursor.getString(1));
            word.setRussianWord(cursor.getString(2));
            AllWord.add(word);
        }
        return AllWord;
    }

    public  void addWordToDB(Word NewWord) throws SQLException {
        refresh();
        ContentValues newWord = new ContentValues();
        newWord.put("EnglishWord",NewWord.getEnglishWord());
        newWord.put("RussianWord",NewWord.getRussianWord());
        db.insert("dictionary",null,newWord);
    }

}
