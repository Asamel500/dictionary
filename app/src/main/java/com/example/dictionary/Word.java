package com.example.dictionary;

public class Word {
    int id;
    private String EnglishWord,RussianWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Word() {
    }

    public Word(String englishWord, String russianWord) {
        EnglishWord = englishWord;
        RussianWord = russianWord;
    }
    public void setEnglishWord(String englishWord) {
        EnglishWord = englishWord;
    }

    public void setRussianWord(String russianWord) {
        RussianWord = russianWord;
    }

    public String getEnglishWord() {
        return EnglishWord;
    }

    public String getRussianWord() {
        return RussianWord;
    }
}
