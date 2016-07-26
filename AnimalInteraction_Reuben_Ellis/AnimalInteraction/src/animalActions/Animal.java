/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalActions;

/**
 *
 * @author ethri
 */
public class Animal {
    private String name;
    private String color;
    private String uniqueTrait;
    private String numberOfLegs;
    
    //A constructor which allows for the creation of a new animal
    public Animal(String animalName,String animalColor, String trait, String legs)
    {
        this.name = animalName;
        this.color = animalColor;
        this.uniqueTrait = trait;
        this.numberOfLegs = legs;
    }
    
    //Get the animal name
    public String animalName()
    {
        return this.name;
    }
    
    //Set the animal name when adding a new animal
    public void changeAnimalName(String animalName)
    {
        this.name = animalName;
    }
    
    //Get the animal color
    public String animalColor()
    {
        return this.color;
    }
    
    //Set the animal color when adding a new animal
    public void changeAnimalColor(String animalColor)
    {
        this.color = animalColor;
    }
    
    //Get the unique trait for the animal
    public String uniqueAnimalTrait()
    {
        return this.uniqueTrait;
    }
    
    //Set the unique trait for the animal when adding a new animal
    public void changeUniqueTrait(String trait)
    {
        this.uniqueTrait = trait;
    }
    
    //Get the number of legs for the animal
    public String numberOfLegs()
    {
        return this.numberOfLegs;
    }
    
    //Set the number of legs for the animal when adding a new animal
    public void changeLegCount(String legs)
    {
        this.numberOfLegs = legs;
    }
    
    //Prints out the properties when an animal is selected in the main program
    public void animalProperties()
    {
        System.out.println("Animal Name: " + this.name + "\nAnimal Color: " +
        this.color + "\nDoes Animal have trait hair, feathers, or coldBlood: " 
                + this.uniqueTrait + "\nNumber of Legs: " + this.numberOfLegs);
    }
}


