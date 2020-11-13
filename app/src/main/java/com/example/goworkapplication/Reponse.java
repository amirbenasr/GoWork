package com.example.goworkapplication;

public class Reponse {
    private String idReponse;
    private String idUser;
    private String isquestion_sondage;
    private String UserReponse;

    public Reponse(String idUser, String userReponse) {
        this.idUser = idUser;
        UserReponse = userReponse;
    }

    public Reponse(String idUser, String isquestion_sondage, String userReponse) {
        this.idUser = idUser;
        this.isquestion_sondage = isquestion_sondage;
        UserReponse = userReponse;
    }

    public Reponse(String idReponse, String idUser, String isquestion_sondage, String userReponse) {
        this.idReponse = idReponse;
        this.idUser = idUser;
        this.isquestion_sondage = isquestion_sondage;
        UserReponse = userReponse;
    }

    public String getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(String idReponse) {
        this.idReponse = idReponse;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIsquestion_sondage() {
        return isquestion_sondage;
    }

    public void setIsquestion_sondage(String isquestion_sondage) {
        this.isquestion_sondage = isquestion_sondage;
    }

    public String getUserReponse() {
        return UserReponse;
    }

    public void setUserReponse(String userReponse) {
        UserReponse = userReponse;
    }
}
