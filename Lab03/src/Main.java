
void main() {

   SocialNetwork network = new SocialNetwork();

    Company Apple = new Company(1,"Apple", "Tech");
    Company Microsoft = new Company(2,"Microsoft", "Tech");

    Programmer Popescu_Ion = new Programmer(3,"Popescu Ion", new Date(1999,10,1), "C++");
    Designer Ionescu_Petru = new Designer(4,"Ionescu Petru", new Date(2001, 6, 16), "Photoshop");

    Popescu_Ion.addProfileToMap(Ionescu_Petru,"friends");
    Ionescu_Petru.addProfileToMap(Popescu_Ion,"friends");

    Popescu_Ion.addProfileToMap(Apple, "programmer");
    Ionescu_Petru.addProfileToMap(Microsoft,"designer");
    Ionescu_Petru.addProfileToMap(Apple,"designer");

    network.addProfile(Apple);
    network.addProfile(Microsoft);
    network.addProfile(Popescu_Ion);
    network.addProfile(Ionescu_Petru);


    network.printList();

    network.sortByInitial();
    network.printList();

    network.sortByImportance();
    network.printList();

}
