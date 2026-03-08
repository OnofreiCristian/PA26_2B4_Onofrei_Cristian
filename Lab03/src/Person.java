
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Person implements Profile {

    private String name;
    private int id;
    private Date birthDate;
    private String hobby;
    public Person(int id, String name, Date birthDate){

        this.id=id;
        this.name=name;
        this.birthDate=birthDate;
        hobby = "nothing";
    }
    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby){
        this.hobby = hobby;
    }


    public Map<Profile, String> relationshipMap = new HashMap<>();

    void addProfileToMap (Profile profile, String relationship){
        relationshipMap.put(profile, relationship);
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
