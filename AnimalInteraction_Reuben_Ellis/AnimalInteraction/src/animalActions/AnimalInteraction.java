package animalActions;

import java.util.*;

/*
 * @author Reuben Ellis
 * Date: 2016-07-24
 * ArrayList Animal Interaction for PRG/421
 * The AnimalInteraction class provides a scanner property to get user inputs
 * and assign the values respectively.  
 * An ArrayList is used to hold the name values of the animal classes.
 * Three animals are created initially, one of type mammal, bird, and reptile.

 * The select method accepts only an existing name from within the wildlife 
 * reserve and if the name exists will display the traits of the animal to the 
 * user.

 * The add method allows a user to choose whether they want to add an animal
 * and the animal is then added to either the mammal, bird, or reptile class
 * based on the unique trait value entered.  The user must enter a name, color,
 * unique identifier, and the number of legs for the animal.

 * The edit method allows a user to enter the name of an existing animal to
 * edit either the color of the animal or the number of legs.  The name is 
 * unique to the animal and cannot be entered. Also, the unique identifier 
 * dictates what type the animal is so this property cannot be changed either.

 * The delete method allows a user to enter an existing animal name for deletion
 * and then deletes the animal from the wildlife reserve.  If the user deletes
 * all animals, the user will get a message when trying to delete another 
 * animal, stating no animals exist in the wildlife reserve.
 */
public class AnimalInteraction {
    
    private static Scanner 
        userInput = new Scanner(System.in);
    
    private static ArrayList<String>
        animalList = new ArrayList<String>();
    
    private static String
            input = "";
    
    private static Animal
        mammal = new Animal("Panther", "black", "hair", "4"),
        bird = new Animal("Falcon", "grey and black", "feathers", "2"),
        reptile = new Animal("Tortoise", "yellowish", "coldBlood",
        "4");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        animalList.add(mammal.animalName());
        animalList.add(bird.animalName());
        animalList.add(reptile.animalName());
        
        do
        {
            String
                    userResponse = "";
            welcomePrompt();
            userResponse = validateUserOption();

            if (userResponse.equals("select"))
            {
                selectAnimalAttributes();
            }
            else if (userResponse.equals("add"))
            {
                addNewAnimal();
            }
            else if (userResponse.equals("edit"))
            {
                editExistingAnimal();
            }
            else if (userResponse.equals("delete"))
            {
                deleteExistingAnimal();
            }

            System.out.println("Please enter quit to end the program "
                    + "\nor anything else to continue: ");
            input = userInput.nextLine();
            
        }while (!input.equals("quit"));
    }
    
    //Method which displays a welcome message to the user and displays the 
    //current animals within the wildlife reserve
    private static void welcomePrompt()
    {
        
        String
                welcomeMessage = "Welcome to the Animal Interaction Zone!"
                + "\nThe following animals are already in the wildlife reserve:"
                + "\n",
                optionsMessage = "\nPlease enter one of the following options: "
                + "select, add, edit, or delete.";
        
        Collections.sort(animalList);
        
        Object[] displayAnimals = animalList.toArray();
        
        System.out.println(welcomeMessage);
        for (Object animals : displayAnimals)
        {
            System.out.println(animals);
        }
        System.out.println(optionsMessage);
    }
    
    //A method to validate and return a value based on whether the user chose,
    //'select', 'add', 'edit', or 'delete'
    private static String validateUserOption()
    {
        String
                select = "select",
                add = "add",
                edit = "edit",
                delete = "delete";
        
        input = userInput.nextLine();
        while(!input.equals(select) && !input.equals(add) &&
                !input.equals(edit) && !input.equals(delete))
        {  
            System.out.println("Please enter either: select, add, edit,"
            + " or delete!");
            input = userInput.nextLine();
        }
        if (input.equals(select))
        {
            input = select;
        }
        else if (input.equals(add))
        {
            input = add;
        }
        else if (input.equals(edit))
        {
            input = edit;
        }
        else if (input.equals(delete))
        {
            input = delete;
        }
        return input;
    } 
    
    //Allows the user to get the attributes of an animal
    private static void selectAnimalAttributes()
    {   
        System.out.println("Enter an animal name already in the wildlife"
                + " reserve to see a full description: ");
        
        input = userInput.nextLine();
        do
        {
        while(!input.equals(mammal.animalName()) && 
                !input.equals(bird.animalName()) &&
                !input.equals(reptile.animalName()))
        {  
            System.out.println("\nAnimal does not exist!!!");
            input = userInput.nextLine();
        }
            if (input.equals(mammal.animalName()))
            {
            System.out.println("Animal Color: " + mammal.animalColor()
                + "\nUnique Animal Trait: " + mammal.uniqueAnimalTrait()
                    + "\nNumber of Legs Animal has: " + mammal.numberOfLegs());
            }
            else if (input.equals(bird.animalName()))
            {
            System.out.println("Animal Color: " + bird.animalColor()
                + "\nUnique Animal Trait: " + bird.uniqueAnimalTrait() 
                    + "\nNumber of Legs Animal has: " + bird.numberOfLegs());
            }
            else if (input.equals(reptile.animalName()))
            {
            System.out.println("Animal Color: " + reptile.animalColor() 
                    + "\nUnique Animal Trait: " + reptile.uniqueAnimalTrait() 
                    + "\nNumber of Legs Animal has: " + reptile.numberOfLegs());
            }
        System.out.println("\nPlease enter done to return to the main page "
            + "\nor an animal name for details: ");
        input = userInput.nextLine();
        
        }while (!input.equals("done"));
    }
    
    //Allows the user to add an animal to the reserve
    private static void addNewAnimal()
    {
        String
                name = "",
                color = "",
                uniqueTrait = "",
                legs = "";
        
        System.out.println("Do you want to add an animal to the reserve? Enter "
                + "'yes' or 'no'");
        input = userInput.nextLine();
        do
        {
            while (!input.equals("yes") && !input.equals("no"))
            {
                System.out.println("Please enter 'yes' or 'no' to continue: ");
                input = userInput.nextLine();
            }
            if (input.equals("yes"))
            {
                System.out.println("Enter the following information to add an "
                + "animal to the wildlife reserve: ");
                System.out.println("Animal Name: ");
                input = userInput.nextLine();
                name = input;
                System.out.println("Animal Color: ");
                input = userInput.nextLine();
                color = input;
                System.out.println("Enter either 'hair' for a mammal, "
                    + "'feathers' for a bird, or 'coldBlood' for a reptile: ");
    
                input = userInput.nextLine();

                uniqueTrait = input;

                while (!input.equals("hair") && !input.equals("feathers") && 
                        !input.equals("coldBlood"))
                {
                    System.out.println("\nPlease enter either hair, feathers, "
                            + "or coldBlood: ");
                    input = userInput.nextLine();
                }
                System.out.println("Number of Legs: ");
                input = userInput.nextLine();
                legs = input;
        
                if (uniqueTrait.equals("hair"))
                {
                    animalList.remove(mammal.animalName());
                    
                    mammal =  new Animal(name, color, uniqueTrait, legs);

                    animalList.add(mammal.animalName());
                }
                else if (uniqueTrait.equals("feathers"))
                {
                    animalList.remove(bird.animalName());
                    
                    bird =  new Animal(name, color, uniqueTrait, legs);

                    animalList.add(bird.animalName());
                }
                else if (uniqueTrait.equals("coldBlood"))
                {
                    animalList.remove(reptile.animalName());
                    
                    reptile =  new Animal(name, color, uniqueTrait, legs);

                    animalList.add(reptile.animalName());
                }
            }
            else if (input.equals("no"))
            {
                break;
            }
        System.out.println("\nPlease enter done to return to the main page "
        + "\nor anything else to continue: ");
        input = userInput.nextLine();
        
        }while (!input.equals("done"));
    }
    
    //Allows the user to edit certain characteristics of an animal
    private static void editExistingAnimal()
    {
        String
                animalName = "",
                color = "",
                legs = "";
        
        System.out.println("Enter the name of an animal"
        + " from the wildlife reserve to change an attribute: ");
        
        input = userInput.nextLine();
        do
        {
            if (animalList.isEmpty())
            {
                System.out.println("There are no animals in the"
                        + " wildlife reserve.");
                break;
            }
        while(!input.equals(mammal.animalName()) && 
                !input.equals(bird.animalName()) &&
                !input.equals(reptile.animalName()))
        {  
            System.out.println("\nAnimal does not exist!!!");
            input = userInput.nextLine();
        }
            if (input.equals(mammal.animalName()))
            {
                if (animalList.contains(input))
                {
                    animalName = input;
                    System.out.println("Please enter either 'color' or 'legs' "
                            + "to change the color or number of legs for the "
                            + "animal");
                    input = userInput.nextLine();
                    
                    while(!input.equals("color") && !input.equals("legs"))
                    {
                        System.out.println("Please enter either 'color' or "
                                + "'legs' to continue: ");
                        input = userInput.nextLine();
                    }
                    
                    if (input.equals("color"))
                    {
                        System.out.println("Enter a new color: ");
                        input = userInput.nextLine();
                        if (mammal.animalName().equals(animalName))
                        {
                            mammal.changeAnimalColor(input);
                            System.out.println(animalName + " color updated!");
                        }
                    }
                    else if (input.equals("legs"))
                    {
                        System.out.println("Enter a new number of legs: ");
                        input = userInput.nextLine();
                        if (mammal.animalName().equals(animalName))
                        {
                            mammal.changeLegCount(input);
                            System.out.println(animalName + " number of legs"
                                    + " updated!");
                        }
                    }

                }
                else
                {
                    System.out.println("\nAnimal does not exist!!!");
                }
            }
            else if (input.equals(bird.animalName()))
            {
                if (animalList.contains(input))
                {
                    animalName = input;
                    System.out.println("Please enter either 'color' or 'legs' "
                            + "to change the color or number of legs for the "
                            + "animal");
                    input = userInput.nextLine();
                    
                    while(!input.equals("color") && !input.equals("legs"))
                    {
                        System.out.println("Please enter either 'color' or "
                                + "'legs' to continue: ");
                        input = userInput.nextLine();
                    }
                                        
                    if (input.equals("color"))
                    {
                        System.out.println("Enter a new color: ");
                        input = userInput.nextLine();
                        if (bird.animalName().equals(animalName))
                        {
                            bird.changeAnimalColor(input);
                            System.out.println(animalName + " color updated!");
                        }
                    }
                    else if (input.equals("legs"))
                    {
                        System.out.println("Enter a new number of legs: ");
                        input = userInput.nextLine();
                        if (bird.animalName().equals(animalName))
                        {
                            bird.changeLegCount(input);
                            System.out.println(animalName + " number of legs"
                                    + " updated!");
                        }
                    }
                }
                else
                {
                    System.out.println("\nAnimal does not exist!!!");
                }
            }
            else if (input.equals(reptile.animalName()))
            {
                if (animalList.contains(input))
                {
                    animalName = input;
                    System.out.println("Please enter either 'color' or 'legs' "
                            + "to change the color or number of legs for the "
                            + "animal");
                    input = userInput.nextLine();
                    
                    while(!input.equals("color") && !input.equals("legs"))
                    {
                        System.out.println("Please enter either 'color' or "
                                + "'legs' to continue: ");
                        input = userInput.nextLine();
                    }
                                        
                    if (input.equals("color"))
                    {
                        System.out.println("Enter a new color: ");
                        input = userInput.nextLine();
                        if (reptile.animalName().equals(animalName))
                        {
                            reptile.changeAnimalColor(input);
                            System.out.println(animalName + " color updated!");
                        }
                    }
                    else if (input.equals("legs"))
                    {
                        System.out.println("Enter a new number of legs: ");
                        input = userInput.nextLine();
                        if (reptile.animalName().equals(animalName))
                        {
                            reptile.changeLegCount(input);
                            System.out.println(animalName + " number of legs"
                                    + " updated!");
                        }
                    }
                }
                else
                {
                    System.out.println("\nAnimal does not exist!!!");
                }
            }
        System.out.println("\nPlease enter done to return to the main page "
            + "\nor an animal name for editing: ");
        input = userInput.nextLine();
        
        }while (!input.equals("done"));
    }
    
    //Allows the user to delete an animal from the reserve
    private static void deleteExistingAnimal()
    {
        System.out.println("Enter the name of the animal being removed"
        + " from the wildlife reserve: ");
        
        input = userInput.nextLine();
        do
        {
            if (animalList.isEmpty())
            {
                System.out.println("There are no longer any animals in the"
                        + " wildlife reserve.");
                break;
            }
        while(!input.equals(mammal.animalName()) && 
                !input.equals(bird.animalName()) &&
                !input.equals(reptile.animalName()))
        {  
            System.out.println("\nAnimal does not exist!!!");
            input = userInput.nextLine();
        }
            if (input.equals(mammal.animalName()))
            {
                if (animalList.contains(input))
                {
                    animalList.remove(input);
                }
                else
                {
                    System.out.println("\nAnimal does not exist!!!");
                }
            }
            else if (input.equals(bird.animalName()))
            {
                if (animalList.contains(input))
                {
                    animalList.remove(input);
                }
                else
                {
                    System.out.println("\nAnimal does not exist!!!");
                }
            }
            else if (input.equals(reptile.animalName()))
            {
                if (animalList.contains(input))
                {
                    animalList.remove(input);
                }
                else
                {
                    System.out.println("\nAnimal does not exist!!!");
                }
            }
        System.out.println("\nPlease enter done to return to the main page "
            + "\nor an animal name for removal: ");
        input = userInput.nextLine();
        
        }while (!input.equals("done"));
    }
    
}
