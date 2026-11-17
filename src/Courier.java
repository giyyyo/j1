public class Courier {
    private final String name;
    private boolean isBisy;

    public Courier(String name) {
        this.name = name;
        this.isBisy = false;
    }

    public String getName() { return name;}
    public boolean isBisy() {return isBisy;}

    public boolean assignToOrder() {
        if (isBisy) return false;
        isBisy = true;
        return true;
    }
    public void release() { isBisy = false; }
    @Override public String toString() {return name + (isBisy ? "занят" : "свободен" );}
}
