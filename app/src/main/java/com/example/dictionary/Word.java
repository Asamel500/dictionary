package com.example.dictionary;

public class Word {
    private String EnglishWord,RussianWord;

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
