package org.doit.airport.domain;

public class Airport {

    private String airportId;
    private String airportName;
    private String airportEngName;
    private String cityKor;
    private String cityEng;

    // 기본 생성자
    public Airport() {
    }

    // 전체 필드를 받는 생성자
    public Airport(String airportId, String airportName, String airportEngName, String cityKor, String cityEng) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportEngName = airportEngName;
        this.cityKor = cityKor;
        this.cityEng = cityEng;
    }

    // getter
    public String getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportEngName() {
        return airportEngName;
    }

    public String getCityKor() {
        return cityKor;
    }

    public String getCityEng() {
        return cityEng;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportId='" + airportId + '\'' +
                ", airportName='" + airportName + '\'' +
                ", airportEngName='" + airportEngName + '\'' +
                ", cityKor='" + cityKor + '\'' +
                ", cityEng='" + cityEng + '\'' +
                '}';
    }
	  
}
