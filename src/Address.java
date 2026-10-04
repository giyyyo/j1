public class Address {
    private String city;
    private String street;
    private int house;
    private int appartment;

    public Address (String city, String street, int house, int appartment){
        this.city = city;
        this.street = street;
        this.house = house;
        this.appartment = appartment;
    }

    public String getFullAddress(){
        return city + "," + street + ", д." + house + (appartment > 0? ", кв. " + appartment : "");
    }
    public String getCity() {return city;}
    public String getStreet() {return street;}
    public int getHouse() {return house;}
    public int getAppartment() {return appartment;}
}
