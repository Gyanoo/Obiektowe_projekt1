package agh.cs.projekt1_1;

enum MapDirection {
    NORTH("NORTHEAST", "NORTHWEST", new Position(0, 1)),
    NORTHEAST("EAST", "NORTH", new Position(1, 1)),
    EAST("SOUTHEAST", "NORTHEAST", new Position(1, 0)),
    SOUTHEAST("WEST", "EAST", new Position(1, -1)),
    SOUTH("SOUTHWEST", "SOUTHEAST", new Position(0, -1)),
    SOUTHWEST("WEST", "SOUTH", new Position(-1, -1)),
    WEST("NORTHWEST", "SOUTHWEST", new Position(-1, 0)),
    NORTHWEST("NORTH", "WEST", new Position(-1, 1));

    private String next;
    private String previous;
    private Position vector;

    MapDirection(String next, String previous, Position vector) {
        this.next = next;
        this.previous = previous;
        this.vector = vector;
    }

    public MapDirection getNext() {
        return MapDirection.valueOf(next);
    }

    public MapDirection getPrevious() {
        return MapDirection.valueOf(previous);
    }

    public Position getVector() {
        return vector;
    }

    @Override
    public String toString() {
        return "MapDirection{" +
                "kierunek='" + this + '\'' + //na pewno?
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", vector=" + vector +
                '}';
    }
}
