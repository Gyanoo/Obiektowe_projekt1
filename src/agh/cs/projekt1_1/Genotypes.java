package agh.cs.projekt1_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genotypes {

    protected static int[] generateGenotype(){
        Random r = new Random();
        int[] result = new int[32];
        for(int i=0;i<8;i++){
            result[i]=i;
        }
        for(int i=8;i<32;i++){
            result[i]=r.nextInt(8);
        }
        Arrays.sort(result,0,31);
        return result;
    }

    protected static int[] concatenateGenotypes(int[] firstParentGenotype, int[] secondParentGenotype){
        int [] result = new int[32];
        Random r = new Random();
        int[][] parents = {firstParentGenotype, secondParentGenotype};
        int who = r.nextInt(2);//who is the parent giving only one segment of genotype;
        int firstDivision = r.nextInt(32);//where 1st segment ends;
        int secondDivision = r.nextInt(32);//where 2nd segment ends;
        while (firstDivision == secondDivision){
            secondDivision = r.nextInt(32);
        }
        int which = r.nextInt(3);//which segment of genotype is from parent who gives only 1 segment
        for(int i=0;i<firstDivision;i++){
            if(which == 0)
                result[i] = parents[who][i];
            else
                result[i] = parents[(who+1)%2][i];
        }
        for(int i=firstDivision;i<secondDivision;i++){
            if(which == 1)
                result[i] = parents[who][i];
            else
                result[i] = parents[(who+1)%2][i];
        }
        for(int i=secondDivision;i<32;i++) {
            if (which == 2)
                result[i] = parents[who][i];
            else
                result[i] = parents[(who + 1) % 2][i];
        }
        result = checkIfEveryMoveInGenotype(result);
        Arrays.sort(result,0,31);
        return result;
    }

    private static int[] checkIfEveryMoveInGenotype(int[] result){
        int[] isMoveInside = new int[8];
        for(int i : result){
            isMoveInside[i]++;
        }
        List<Integer> missing = new ArrayList<>();
        List<Integer> removable = new ArrayList<>();
        for(int i = 0; i<8;i++){
            if (isMoveInside[i]==0)
                missing.add(i);
            if (isMoveInside[i]>1)
                removable.add(i);
        }
        for(int toAdd : missing) {
            Random r = new Random();
            int toSwap = r.nextInt(removable.size());
            int i = 0;
            while (result[i] != toSwap && i < 31)
                i++;
            result[i] = toAdd;
            isMoveInside[toSwap]--;
            if (isMoveInside[toSwap] <= 1) {
                removable.remove(toSwap);
            }
        }
        return result;
    }
}
