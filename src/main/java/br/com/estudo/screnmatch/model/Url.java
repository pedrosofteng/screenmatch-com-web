package br.com.estudo.screnmatch.model;

public class Url {
    private final String ENDERECO_OMDB = "https://www.omdbapi.com/?t=";
    private final String API_KEY_OMDB = "&apikey=b8b965e1";
    private final String SEASON = "&Season=";

    public String getENDERECO_OMDB() {
        return ENDERECO_OMDB;
    }

    public String getAPI_KEY_OMDB() {
        return API_KEY_OMDB;
    }

    public String getSEASON() {
        return SEASON;
    }
}
