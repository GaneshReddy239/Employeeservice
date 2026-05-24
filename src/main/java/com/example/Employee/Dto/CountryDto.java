package com.example.Employee.Dto;


import java.util.List;

public class CountryDto {

    private Name name;
    private List<String> capital;
    private String region;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public static class Name {

        private String common;

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }
    }
}

