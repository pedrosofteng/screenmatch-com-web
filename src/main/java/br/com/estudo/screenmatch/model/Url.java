package br.com.estudo.screenmatch.model;

import io.github.cdimascio.dotenv.Dotenv;

public class Url {
    Dotenv dotenv = Dotenv.load();

    private final String ENDERECO_OMDB = "https://www.omdbapi.com/?t=";
    private final String API_KEY_OMDB = dotenv.get("API_KEY_OMDB");
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
