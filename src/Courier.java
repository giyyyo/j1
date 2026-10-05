public class Courier {
    private String name;
    private boolean isBisy;

    public Courier(String name) {
        this.name = name;
        this.isBisy = false;
    }

    public String getName() { return name;}
    public boolean isBisy() {return isBisy;}

    public void assignToOrder() { this.isBisy = true; }
    public void release() { this.isBisy = false; }
}
