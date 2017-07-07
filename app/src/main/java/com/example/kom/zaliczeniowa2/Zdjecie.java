package com.example.kom.zaliczeniowa2;

/**
 * Created by Kom on 2017-07-06.
 */

public class Zdjecie
{
    private Long nr;
    private String nazwa;
    private String gps;
    private String lokalizacja;

    public Zdjecie() {
    }

    public Long getNr() {
        return this.nr;
    }

    public void setNr(Long nr) {
        this.nr = nr;
    }

    public String getNazwa() {
        return this.nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa=nazwa;
    }

    public String getGps() {
        return this.gps;
    }

    public void setGps(String gps) {
        this.gps=gps;
    }

    public String getLokalizacja() {
        return this.lokalizacja;
    }

    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja=lokalizacja;
    }

    @Override
    public String toString()
    {
        return getNazwa()+" "+ getGps()+ " "+getLokalizacja();
    }

}
