import java.util.Date;

public class Designer extends Person{

    String programUsed;
    public Designer(int id, String name, Date birthDate, String programUsed){
        super(id, name, birthDate);
        this.programUsed = programUsed;
    }

}
