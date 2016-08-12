/*
 *         Week 2
 The program should allow a user to do the following:
 • Add, edit, delete different types of animals
 • Select an animal, and the corresponding characteristics will be displayed 
 (such as color, vertebrate or invertebrate, can swim, etc.)
 • The program must use ArrayList(s) to work with these animal objects.
 */
package animallist;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jason Garrick
 */
public class AnimalList {
// Declare variables and created methods for get and set.
    
    private String name;
    private String color;
    private String habitat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public static void main(String[] args) {
        
        boolean quit = false; // Variable for the do while to end
        Scanner in = new Scanner(System.in);// Scanner to accept user input
        
        //ArrayList to hold Animal information
        ArrayList<AnimalList> animalName = new ArrayList<>();
// The start of the do while loop 
        // User must enter a number to perform the action wanted.
        //*** Note that the list of animals must be created first*****
        do {
            System.out.print("To enter animal select 1, To veiw animal list enter 2, To Remove an animal enter 3, or 00 to quit: ");

            AnimalList animal = new AnimalList();
            int userChoice;
            userChoice = in.nextInt();
            // First choice to enter animal data sets NAME, COLOR and HABITAT data to the arrayList
            if (userChoice == 1) {

                System.out.print("Animal Name: ");
                animal.setName(in.next());

                System.out.print("Animal Color: ");
                animal.setColor(in.next());

                System.out.print("Habitat : ");
                animal.setHabitat(in.next());

                animalName.add(animal);

                // Second choice to view the list of animals and their characteristics
            } else if (userChoice == 2) {
                System.out.println("List of Animals Entered:");
                for (AnimalList lst : animalName) {
                    System.out.println("" + animalName.indexOf(lst) + ": " + lst.name + " Color :" + lst.color + " Habitat:" + lst.habitat);
                }
                //Third choice to select and delete an animal
            } else if (userChoice == 3) {
                System.out.println("Enter the coresponding number to Delete :");
                int delete;
                delete = in.nextInt();
                animalName.remove(delete);
                //Fourth choice to select and change the animals data
            } else if (userChoice == 4) {
                System.out.println("Enter the animal number you want to Change :");
                int change;
                change = in.nextInt();

                System.out.print("Animal Name: ");
                animal.setName(in.next());

                System.out.print("Animal Color: ");
                animal.setColor(in.next());

                System.out.print("Habitat : ");
                animal.setHabitat(in.next());

                animalName.set(change, animal);
                // To exit loop by changing value of quit to true
            } else if (userChoice == 00) {

                quit = true;

            }

        } while (!quit); // end of loop
        {

        }
        // to display the final list of animals
        for (AnimalList lst : animalName) {
            System.out.println("" + animalName.indexOf(lst) + ": " + lst.name + " Color :" + lst.color + " Habitat:" + lst.habitat);
        }
        System.out.println("Bye!");
    }

}
