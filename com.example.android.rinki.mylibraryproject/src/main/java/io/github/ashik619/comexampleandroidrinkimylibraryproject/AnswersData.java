package io.github.ashik619.comexampleandroidrinkimylibraryproject;

/**
 * Created by dilip on 23/4/19.
 */

public class AnswersData {
    String question,rating;

    public AnswersData(String question, String rating) {
        this.question = question;
        this.rating = rating;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
