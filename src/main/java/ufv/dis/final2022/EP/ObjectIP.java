package ufv.dis.final2022.EP;

public class ObjectIP {

    private long ip_from, ip_to;
    private String country_code,country_name,region_name,city_name,time_zone;
    private Double latitude,longitude;
    private String zip_code;

    public ObjectIP(long ip_from, long ip_to, String country_code, String country_name, String region_name, String city_name, String time_zone, Double latitude, Double longitude, String zip_code) {
        this.ip_from = ip_from;
        this.ip_to = ip_to;
        this.country_code = country_code;
        this.country_name = country_name;
        this.region_name = region_name;
        this.city_name = city_name;
        this.time_zone = time_zone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zip_code = zip_code;
    }


    public ObjectIP() {

    }

    public long getIp_from() {
        return ip_from;
    }

    public void setIp_from(long ip_from) {
        this.ip_from = ip_from;
    }

    public long getIp_to() {
        return ip_to;
    }

    public void setIp_to(long ip_to) {
        this.ip_to = ip_to;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    /*

     {
    "ip_from": 2532573184,
    "ip_to": 2532638719,
    "country_code": "ES",
    "country_name": "Spain",
    "region_name": "Madrid, Comunidad de",
    "city_name": "Madrid",
    "latitude": 40.4165,
    "longitude": -3.70256,
    "zip_code": 28049,
    "time_zone": "+01:00"
  },
     */
}

