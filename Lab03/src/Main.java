
void main() {

    List<Profile> profileList = new ArrayList<>();

    profileList.add(new Company(1,"Apple"));
    profileList.add(new Company(2,"Microsoft"));
    profileList.add(new Person(3,"Popescu Ion"));
    profileList.add(new Person(4,"Ionescu Petru"));

    Collections.sort(profileList);

    System.out.println(profileList);

    profileList.sort(new ProfileComparator());

}
