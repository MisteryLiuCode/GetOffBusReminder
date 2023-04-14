package com.hc.getOffBusReminder.entity.addressEncode;

/**
 * @author liushuaibiao
 * @date 2023/4/4 11:32
 */
import java.util.List;
public class Geocodes
{
    private String formatted_address;

    private String country;

    private String province;

    private String citycode;

    private String city;

    private String district;

    private List<String> township;

    private Neighborhood neighborhood;

    private Building building;

    private String adcode;

    private String street;

    private String number;

    private String location;

    private String level;

    public void setFormatted_address(String formatted_address){
        this.formatted_address = formatted_address;
    }
    public String getFormatted_address(){
        return this.formatted_address;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getProvince(){
        return this.province;
    }
    public void setCitycode(String citycode){
        this.citycode = citycode;
    }
    public String getCitycode(){
        return this.citycode;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setDistrict(String district){
        this.district = district;
    }
    public String getDistrict(){
        return this.district;
    }
    public void setTownship(List<String> township){
        this.township = township;
    }
    public List<String> getTownship(){
        return this.township;
    }
    public void setNeighborhood(Neighborhood neighborhood){
        this.neighborhood = neighborhood;
    }
    public Neighborhood getNeighborhood(){
        return this.neighborhood;
    }
    public void setBuilding(Building building){
        this.building = building;
    }
    public Building getBuilding(){
        return this.building;
    }
    public void setAdcode(String adcode){
        this.adcode = adcode;
    }
    public String getAdcode(){
        return this.adcode;
    }
    public void setStreet(String street){
        this.street = street;
    }
    public String getStreet(){
        return this.street;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getNumber(){
        return this.number;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
    public void setLevel(String level){
        this.level = level;
    }
    public String getLevel(){
        return this.level;
    }
}
