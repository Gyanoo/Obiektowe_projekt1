package agh.cs.projekt1_1;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Animal{
    private MapDirection orientation;
    private Position position;
    protected int[] genotype = new int[32];
    protected int energy;
    private int maxEnergy = 30;
    private int maxHeight = 25;
    private int maxWidth = 25;

    public Animal() {
        this.orientation = generateOrientation();
        this.position = new Position(2,2);
        this.energy=20;
        this.genotype = Genotypes.generateGenotype();
    }

    public Animal(Position initialVector, int startingEnergy){
        this.orientation = generateOrientation();
        this.position = initialVector;
        this.energy = startingEnergy;
        this.genotype = Genotypes.generateGenotype();
    }

    public Animal(Position initialVector, int startingEnergy, int[] firstParentGenotype, int[] secondParentGenotype){
        this.orientation = generateOrientation();
        this.position = initialVector;
        this.energy = startingEnergy;
        this.genotype = Genotypes.concatenateGenotypes(firstParentGenotype, secondParentGenotype);
    }

    private MapDirection generateOrientation(){
        Random r = new Random();
        int x = r.nextInt(7);
        switch(x) {
            case 0:
                return MapDirection.NORTH;
            case 1:
                return MapDirection.NORTHEAST;
            case 2:
                return MapDirection.EAST;
            case 3:
                return MapDirection.SOUTHEAST;
            case 4:
                return MapDirection.SOUTH;
            case 5:
                return MapDirection.SOUTHWEST;
            case 6:
                return MapDirection.WEST;
            case 7:
                return MapDirection.NORTHWEST;
        }
        return MapDirection.NORTH;
    }


    protected void addEnergy(int energy){
        this.energy=Math.min(this.energy+energy,5*maxEnergy);//, 2*maxEnergy);
    }

    public Position getPosition(){
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return ("x" + this.energy);
    }

    public void move(){
        Random r = new Random();
        int moveNum = r.nextInt(8);
        Position last = this.position;
        MapDirection orientation = this.getOrientation();
        for(int i=0;i<moveNum;i++){
            orientation = orientation.getNext();
        }
        this.orientation = orientation;
        this.position = this.position.add(orientation.getVector());
        if (this.position.y>maxHeight) this.position.y = 0;
        if (this.position.y<0) this.position.y = maxHeight;
        if (this.position.x>maxWidth) this.position.x = 0;
        if (this.position.x<0) this.position.x = maxWidth;
        this.energy--;
    }
}







//    private int[] generateGenotype(){
//        Random r = new Random();
//        int[] result = new int[32];
//        for(int i=0;i<8;i++){
//            result[i]=i;
//        }
//        for(int i=8;i<32;i++){
//            result[i]=r.nextInt(8);
//        }
//        Arrays.sort(result,0,31);
//        return result;
//    }

//    private int[] concatenateGenotypes(int[] firstParentGenotype,int[] secondParentGenotype){
//        int [] result = new int[32];
//        Random r = new Random();
//        int[][] parents = {firstParentGenotype, secondParentGenotype};
//        int who = r.nextInt(2);//who is the parent giving only one segment of genotype;
//        int firstDivision = r.nextInt(32);//where 1st segment ends;
//        int secondDivision = r.nextInt(32);//where 2nd segment ends;
//        while (firstDivision == secondDivision){
//            secondDivision = r.nextInt(32);
//        }
//        int which = r.nextInt(3);//which segment of genotype is from parent who gives only 1 segment
//        for(int i=0;i<firstDivision;i++){
//            if(which == 0)
//                result[i] = parents[who][i];
//            else
//                result[i] = parents[(who+1)%2][i];
//        }
//        for(int i=firstDivision;i<secondDivision;i++){
//            if(which == 1)
//                result[i] = parents[who][i];
//            else
//                result[i] = parents[(who+1)%2][i];
//        }
//        for(int i=secondDivision;i<32;i++) {
//            if (which == 2)
//                result[i] = parents[who][i];
//            else
//                result[i] = parents[(who + 1) % 2][i];
//        }
//        result = checkIfEveryMoveInGenotype(result);
//        Arrays.sort(result,0,31);
//        return result;
//    }

//    private int[] checkIfEveryMoveInGenotype(int[] result){
//        int[] isMoveInside = new int[8];
//        for(int i : result){
//            isMoveInside[i]++;
//        }
//        List<Integer> missing = new ArrayList<>();
//        List<Integer> removable = new ArrayList<>();
//        for(int i = 0; i<8;i++){
//            if (isMoveInside[i]==0)
//                missing.add(i);
//            if (isMoveInside[i]>1)
//                removable.add(i);
//        }
//        for(int toAdd : missing) {
//            Random r = new Random();
//            int toSwap = r.nextInt(removable.size());
//            int i = 0;
//            while (result[i] != toSwap && i < 31)
//                i++;
//            result[i] = toAdd;
//            isMoveInside[toSwap]--;
//            if (isMoveInside[toSwap] <= 1) {
//                    removable.remove(toSwap);
//            }
//        }
//        return result;
//    }