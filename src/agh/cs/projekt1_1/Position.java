package agh.cs.projekt1_1;

public class Position {
    public int x;
    public int y;


    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position vector2d = (Position) o;
        return x == vector2d.x &&
                y == vector2d.y;
    }
    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public boolean precedes(Position other){
        if (this.x <= other.x && this.y <= other.y){
            return true;
        }
        return false;
    }
    public boolean follows (Position other){
        if (this.x >= other.x && this.y >= other.y){
            return true;
        }
        return false;
    }
    public Position upperRight(Position other){
        int first = Math.max(this.x, other.x);
        int second = Math.max(this.y, other.y);
        Position result = new Position(first,second);
        return result;
    }
    public Position lowerLeft(Position other){
        int first = Math.min(this.x, other.x);
        int second = Math.min(this.y, other.y);
        Position result = new Position(first,second);
        return result;
    }
    public Position add (Position other){
        Position result = new Position(this.x+other.x, this.y + other.y);
        return result;
    }
    public Position subtract (Position other){
        Position result = new Position(this.x - other.x, this.y - other.y);
        return result;
    }

    public Position opposite() {
        Position result = new Position(-this.x, -this.y);
        return result;
    }

    public String toString(){
        return ("x: " + this.x + " y: " + this.y);
    }
}