package agh.cs.projekt1_1;

//import org.json.simple.JSONObject;
import  org.itest.json.simple.*;

import java.util.Random;

public class World {
    public static void main(String[] args) {
        int startingEnergy = 25;
        Map map = new Map(25,25,new Position(10,10), new Position(15,15), 10, startingEnergy);
        Animal a1 = new Animal(new Position(5,5), startingEnergy);
        Animal a2 = new Animal(new Position(1,23), startingEnergy);
        Animal a3 = new Animal(new Position(14,3), startingEnergy);
        Animal a4 = new Animal(new Position(14,11), startingEnergy);
        Animal a5 = new Animal(new Position(22,3), startingEnergy);
        Animal a6 = new Animal(new Position(4,7), startingEnergy);
        map.place(a1);
        map.place(a2);
        map.place(a3);
        map.place(a4);
        map.place(a5);
        map.place(a6);
        map.lifeSimulator(100000);
//        JSONParser parser = new JSONParser();

/*
        try {
            Object obj = parser.parse(new FileReader("c:\\file.json"));

            JSONObject jsonObject =  (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            String city = (String) jsonObject.get("city");
            System.out.println(city);

            String job = (String) jsonObject.get("job");
            System.out.println(job);

            // loop array
            JSONArray cars = (JSONArray) jsonObject.get("cars");
            Iterator<String> iterator = cars.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }
}
