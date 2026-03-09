
void main() {

   SocialNetwork network = new SocialNetwork();

    Company apple = new Company(1,"apple", "Tech");
    Company microsoft = new Company(2,"microsoft", "Tech");

    Programmer popescu = new Programmer(3,"Popescu Ion", new Date(1999,10,1), "C++");
    Designer ionescu = new Designer(4,"Ionescu Petru", new Date(2001, 6, 16), "Photoshop");

    popescu.addProfileToMap(ionescu,"friends");
    ionescu.addProfileToMap(popescu,"friends");

    popescu.addProfileToMap(apple, "programmer");
    ionescu.addProfileToMap(microsoft,"designer");
    ionescu.addProfileToMap(apple,"designer");

    network.addProfile(apple);
    network.addProfile(microsoft);
    network.addProfile(popescu);
    network.addProfile(ionescu);


    network.printList();

    network.sortByInitial();
    network.printList();

    network.sortByImportance();
    network.printList();

}
