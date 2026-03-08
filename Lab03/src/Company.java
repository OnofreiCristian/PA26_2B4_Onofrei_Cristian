public class Company implements Profile {

    private String name;
    private int id;
    private String domain;
    public Company(int id, String name, String domain) {
        this.id=id;
        this.name=name;
        this.domain=domain;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int compareTo(Profile o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public String toString(){
        return id + "-" + name;
    }


}
