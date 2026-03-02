import java.util.Comparator;

public class ProfileComparator implements Comparator<Profile> {

    @Override
    public int compare(Profile o1, Profile o2) {
        return o1.getName().compareTo(o2.getName());
    }

}
