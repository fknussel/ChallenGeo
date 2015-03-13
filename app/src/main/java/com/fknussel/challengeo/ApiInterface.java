package com.fknussel.challengeo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiInterface {

    @GET("/all")
    public void getAllCountries(Callback<List<Country>> callback);

    @GET("/alpha/{code}")
    public void getCountry(@Path("code") String code, Callback<Country> callback);

    // http://restcountries.eu/rest/v1/all
    // http://restcountries.eu/rest/v1/name/argentina
    // http://restcountries.eu/rest/v1/alpha/ar
    // http://www.geonames.org/flags/x/ar.gif
}