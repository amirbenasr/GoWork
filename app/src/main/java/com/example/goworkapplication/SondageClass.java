package com.example.goworkapplication;

public class SondageClass {

    private String idquestion_sondage;
    private String question_sondage;

    public SondageClass(String idquestion_sondage, String question_sondage) {
        this.idquestion_sondage = idquestion_sondage;
        this.question_sondage = question_sondage;
    }

    public SondageClass() {
    }

    public String getIdquestion_sondage() {
        return idquestion_sondage;
    }

    public void setIdquestion_sondage(String idquestion_sondage) {
        this.idquestion_sondage = idquestion_sondage;
    }

    public String getQuestion_sondage() {
        return question_sondage;
    }

    public void setQuestion_sondage(String question_sondage) {
        this.question_sondage = question_sondage;
    }
}
