import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocialNetwork {
    List<Profile> profileList = new ArrayList<>();

    public void addProfile(Profile profile){
        profileList.add(profile);
    }

    public void sortByInitial(){
        Collections.sort(profileList);
    }

    public void printList(){
        for(Profile p : profileList){
        System.out.print(p.toString() + "Importance: " + importance(p) + "; ");
        }

        System.out.println("\n");
    }

    /**
     * Method to calculate importance of a certain profile; Goes through the entire list
     * to calculate the amount of relationships a person or company has within the social network.
     *
     * @param p
     * @return
     */
    public int importance(Profile p) {

        int count = 0;

        if (p instanceof Person)
        {
            count += ((Person) p).relationshipMap.size();
        }

        for (Profile other : profileList)
        {
            if(other instanceof Person && !other.equals(p))
            {
                if(((Person) other).relationshipMap.containsKey(p))
                    count++;
            }
        }

        return count;

    }

    /**
     * Method to sort the existing list inside the social network by the importance
     * of each member.
     */
    public void sortByImportance() {
        profileList.sort(new java.util.Comparator<Profile>() {

            @Override
            public int compare(Profile p1, Profile p2) {
                int scorP1 = importance(p1);
                int scorP2 = importance(p2);

                return Integer.compare(scorP1, scorP2);
            }
        });
    }



}
