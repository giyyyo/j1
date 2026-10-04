public class Client {
    private String name;
    private Address address;

    public Client(String name, Address address){
        this.name = name;
        this.address = address;
    }
    public String getName(){return name;}
    public Address getAddress() { return address; }

    public void setName(String name){this.name = name;}
    public void setAddress(Address address) {this.address = address;}
}