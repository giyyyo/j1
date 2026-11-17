public class Address {
    private String city;
    private String street;
    private String house;
    private String appartment;

    public Address (String city, String street, String house, String appartment){
        this.city = city;
        this.street = street;
        this.house = house;
        this.appartment = appartment;
    }

    public String getFullAddress(){
        return city + "," + street + ", д." + house + (appartment == null || appartment.isBlank() ?  "" : ", кв. " + appartment);
    }
    public String getCity() {return city;}
    public String getStreet() {return street;}
    public String getHouse() {return house;}
    public String getAppartment() {return appartment;}
}
