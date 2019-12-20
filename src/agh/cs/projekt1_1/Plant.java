package agh.cs.projekt1_1;

public class Plant {
    private Position position;


    public Plant(Position vector) {
        this.position=vector;
    }

    @Override
    public String toString() {
        return "P";
    }

    public Position getPosition() {
        return position;
    }

}
