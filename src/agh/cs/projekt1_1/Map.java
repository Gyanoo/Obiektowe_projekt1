package agh.cs.projekt1_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {
    private int energyForEatingPlant;
    private int height;
    private int width;
    private Position mapLowerLeft;
    private Position mapUpperRight;
    private Position jungleLowerLeft;
    private Position jungleUpperRight;
    private int animalsCount;
    private int startingEnergy;
    protected List<Animal> listAnimal = new ArrayList<>();
    protected java.util.Map<Position, List<Animal>> animals = new HashMap<>();
    protected java.util.Map<Position, Plant> plants = new HashMap<>();

    public Map(int mapWidth, int mapHeight, Position jungleLowerLeft, Position jungleUpperRight, int energyForEatingPlant, int startingEnergy) {//, int animalStartCount
        this.width = mapWidth;
        this.height = mapHeight;
        this.mapLowerLeft = new Position(0, 0);
        this.mapUpperRight = new Position(mapWidth, mapHeight);
        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
        this.energyForEatingPlant = energyForEatingPlant;
        this.animalsCount = 0;
        this.startingEnergy = startingEnergy;
//        for(int i=0;i<animalStartCount;i++){
//            spawnRandomAnimal();
//        }
    }

//    private void spawnRandomAnimal(){
//        Random r = new Random();
//        int x = r.nextInt(width);
//        int y = r.nextInt(height);
//        Animal newAnimal = new Animal(new Position(x,y), startingEnergy);
//    }

    private void run() {
        for (Animal animal : listAnimal) {
            Position oldVec = animal.getPosition();
            List<Animal> animalsOnOldPosition = animals.get(oldVec);
            animalsOnOldPosition.remove(animal);
            animal.move();
            Position newPosition = animal.getPosition();
//            if (plants.get(newPosition) != null) {
//                plants.remove(newPosition);
//                animal.addEnergy(energyForEatingPlant);
//            }
            List<Animal> AnimalsOnNewPosition = animals.get(newPosition);
            if (AnimalsOnNewPosition == null) {
                AnimalsOnNewPosition = new ArrayList<>();
            }
            AnimalsOnNewPosition.add(animal);
            animals.put(animal.getPosition(), AnimalsOnNewPosition);
        }
    }

    private void eat(){
        for (Animal animal : listAnimal){
            Position position = animal.getPosition();
            if (animals.get(position).size() == 1){
                if (plants.get(position) != null){
                    plants.remove(position);
                    animal.addEnergy(energyForEatingPlant);//energy += energyForEatingPlant; // animal.energy = Math.max(animal.energy + energyForEatingPlant, animal.maxEnergy)
                }
            }
            else{
                int max = -1;
                int occurences = 0;
                List<Animal> maxEnergyAnimals = new ArrayList<>();
                for(Animal animal1 : listAnimal){
                    if (animal1.energy > max){
                        max = animal1.energy;
                        occurences = 1;
                        maxEnergyAnimals = new ArrayList<>();
                        maxEnergyAnimals.add(animal1);
                    }
                    else if (animal1.energy == max){
                        occurences++;
                        maxEnergyAnimals.add(animal1);
                    }
                }
                int portionOfEnergy = energyForEatingPlant / maxEnergyAnimals.size();
                for(Animal animal2 : maxEnergyAnimals){
                    animal2.addEnergy(portionOfEnergy);//energy+=portionOfEnergy; //animal.energy = Math.max(animal.energy + portionOfEnergy, animal.maxEnergy)
                }
            }
        }
    }

    private Object objectAt(Position vector) {
        if (animals.get(vector) != null)
            return animals.get(vector);
        else
            return plants.get(vector);
    }

    public void place(Animal animal) {
        animalsCount++;
        listAnimal.add(animal);
        List<Animal> atThisPosition = animals.get(animal.getPosition());
        if (atThisPosition == null) {
            atThisPosition = new ArrayList<>();
            atThisPosition.add(animal);
            animals.put(animal.getPosition(), atThisPosition);
        }
    }
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
//        System.out.println(visualizer.draw(mapLowerLeft, mapUpperRight);
        return visualizer.draw(mapLowerLeft, mapUpperRight);
    }

    private void removeDead(){
        if (listAnimal.isEmpty()) return;
        List<Animal> deadOnes = new ArrayList<>();
        for (Animal animal : listAnimal){
            if (animal.energy <= 1){
                Position position = animal.getPosition();
                deadOnes.add(animal);
            }
        }
        for (Animal animal : deadOnes){
            Position position = animal.getPosition();
            listAnimal.remove(animal);
            List<Animal> atThisPosition = animals.get(position);
            atThisPosition.remove(animal);
            animalsCount--;
        }
    }
    private boolean isOutsideJungle(Position position){
        if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight))
            return false;
        return true;
    }

    private void reproductionSession(){
        List<Animal> toAdd = new ArrayList<>();
//        for (Animal animal : listAnimal){
        for (int i=0;i<width;i++) {
            for (int j = 0; j < height; j++) {
                Animal animal = null;
                Position temp = new Position(i, j);
                List<Animal> tempList = animals.get(temp);
                if (tempList != null && tempList.size()>0){
                    animal = tempList.get(0);
                    if (animals.get(animal.getPosition()).size() >= 2) {
                        Animal baby = reproduce(animals.get(animal.getPosition()));
                        if (baby != null)
                            toAdd.add(baby);
                    }
                }
            }
        }
            for (Animal newAnimal : toAdd) {
                place(newAnimal);
        }
    }

    private Animal reproduce(List<Animal> listOfAnimalsToReproduce){
        Animal a1 = null, a2 = null;
        if (listOfAnimalsToReproduce.size() == 2){
            a1=listOfAnimalsToReproduce.get(0);
            a2=listOfAnimalsToReproduce.get(1);
        }
        else {
            int max = 0;
            for (Animal animal : listOfAnimalsToReproduce) {
                if (animal.energy > max){
                    a1 = animal;
                    max = animal.energy;
                }
            }
            max = 0;
            for (Animal animal : listOfAnimalsToReproduce) {
                if (animal.energy > max && animal != a1){
                    a2 = animal;
                    max = animal.energy;
                }
            }
        }
        if(a1.energy >= startingEnergy/2 && a2.energy >= startingEnergy/2) {
            int babyEnergy = a1.energy / 4 + a2.energy / 4;
            a1.energy = a1.energy * 3 / 4;
            a2.energy = a2.energy * 3 / 4;

            Animal babyAnimal = new Animal(a1.getPosition(), babyEnergy, a1.genotype, a2.genotype);
            return babyAnimal;
        }
        return null;
    }



    private void addPlants(){
        Random r = new Random();
        int xJungle,yJungle,xOutsideJungle,yOutsideJungle;
        xJungle = r.nextInt((jungleUpperRight.x - jungleLowerLeft.x) + 1) + jungleLowerLeft.x;
        yJungle = r.nextInt((jungleUpperRight.y - jungleLowerLeft.y) + 1) + jungleLowerLeft.y;
        int numberOfTries = 0;
        while(plants.get(new Position(xJungle, yJungle))!=null && animals.get(new Position(xJungle, yJungle))!=null && numberOfTries < 20){
            xJungle = r.nextInt((jungleUpperRight.x - jungleLowerLeft.x) + 1) + jungleLowerLeft.x;
            yJungle = r.nextInt((jungleUpperRight.y - jungleLowerLeft.y) + 1) + jungleLowerLeft.y;
            numberOfTries++;
        }
        xOutsideJungle = r.nextInt((mapUpperRight.x - mapLowerLeft.x) + 1) + mapLowerLeft.x;
        yOutsideJungle = r.nextInt((mapUpperRight.y - mapLowerLeft.y) + 1) + mapLowerLeft.y;

        numberOfTries = 0;
        while(numberOfTries < 20 && (!isOutsideJungle(new Position(xOutsideJungle, yOutsideJungle)) || animals.get(new Position(xOutsideJungle, yOutsideJungle))!=null || plants.get(new Position(xOutsideJungle, yOutsideJungle))!=null)){
            xOutsideJungle = r.nextInt((mapUpperRight.x - mapLowerLeft.x) + 1) + mapLowerLeft.x;
            yOutsideJungle = r.nextInt((mapUpperRight.y - mapLowerLeft.y) + 1) + mapLowerLeft.y;
            numberOfTries++;
        }
        Position plantInsideJunglePosition = new Position(xJungle,yJungle);
        Position plantOutsideJunglePosition = new Position(xOutsideJungle,yOutsideJungle);
        plants.put(plantInsideJunglePosition, new Plant(plantInsideJunglePosition));
        plants.put(plantOutsideJunglePosition, new Plant(plantOutsideJunglePosition));
    }


    void lifeSimulator(int numberOfRounds){
        int maxNumOfAnimals = animalsCount;
        for(int i=0;i<numberOfRounds;i++){
            removeDead();
            if (animalsCount==0){
                System.out.println(toString());
                System.out.println("All animals died looking for food in year "+ i+" AC");
                System.out.println(maxNumOfAnimals);
                break;
            }
            else
                System.out.println("start roku "+i+ "AC");
            run();
            eat();
            reproductionSession();
            addPlants();
            maxNumOfAnimals = Math.max(maxNumOfAnimals, animalsCount);
            System.out.println(toString());
            System.out.println("number of animals that survive that year: " + animalsCount);
            if (i == numberOfRounds-1) {
//                System.out.println("Number of animals that were alive in last year of planets existance is " + animalsCount);
                System.out.println("number of diffrent animals alive throught existence of this planet is " + maxNumOfAnimals);
            }
        }
    }
}

//
//
//    public void reproduction(){
//        Random r = new Random();
//        int first = r.nextInt(animalsCount);
//        int second = r.nextInt(animalsCount);
//        int maxTries = 10;
//        int tries=0;
//        while(second == first && tries < maxTries) {
//            second = r.nextInt(animalsCount);
//            tries++;
//        }
//        Animal firstParent = listAnimal.get(first);
//        Animal secondParent = listAnimal.get(second);
//        if (firstParent.energy >= 12 && secondParent.energy >= 12){
//            firstParent.energy = firstParent.energy * 3 / 4;
//            secondParent.energy = secondParent.energy * 3 / 4;
//            int xBabyPosition = (firstParent.getPosition().x + secondParent.getPosition().x)/2;
//            int yBabyPosition = (firstParent.getPosition().y + secondParent.getPosition().y)/2;
//            int babyEnergy = (firstParent.energy + secondParent.energy)/3;
//
//            Animal babyAnimal = new Animal(new Position(xBabyPosition,yBabyPosition), babyEnergy,
//                    firstParent.genotype, secondParent.genotype);
//            place(babyAnimal);
//        }
//    }