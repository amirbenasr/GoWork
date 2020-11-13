package com.example.goworkapplication;

public class QuestionnaireClass {

    private String idquestion;
    private String question;

    public QuestionnaireClass() {
    }

    public QuestionnaireClass(String idquestion, String question) {
        this.idquestion = idquestion;
        this.question = question;
    }

    public QuestionnaireClass(String question) {
        this.question = question;
    }

    public String getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(String idquestion) {
        this.idquestion = idquestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
