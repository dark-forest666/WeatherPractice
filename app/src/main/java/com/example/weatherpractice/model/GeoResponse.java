// model/GeoResponse.java
package com.example.weatherpractice.model;



public class GeoResponse {


    private String status;
    private String info;
    private String infocode;
    private Geocode[] geocodes;

    public boolean isSuccess() {
        return "1".equals(status) && geocodes != null && geocodes.length > 0;
    }

    public String getAdcode() {
        if (isSuccess()) {
            return geocodes[0].getAdcode();
        }
        return null;
    }

    public static class Geocode {
        private String adcode;
        private String city;
        private String province;
        public String getAdcode() { return adcode; }
        public String getCity() { return city; }
        public String getProvince() { return province; }
    }
}