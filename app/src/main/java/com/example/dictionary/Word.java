package com.example.dictionary;

import androidx.annotation.Nullable;

public class Word {
    private int id;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        Word word = (Word)obj;
        return id == word.getId()&&EnglishWord.equals(word.getEnglishWord())&&RussianWord.equals(word.RussianWord);
    }
}
