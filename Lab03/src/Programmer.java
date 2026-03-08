import java.util.Date;

public class Programmer extends Person{

    String programmingLanguage;
    public Programmer(int id, String name, Date birthDate, String programmingLanguage){
        super(id, name, birthDate);
        this.programmingLanguage = programmingLanguage;
    }

}
