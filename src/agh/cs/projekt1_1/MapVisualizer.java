package agh.cs.projekt1_1;

import java.util.List;

/**
 * The map visualizer converts the {@link Map} map into a string
 * representation.
 *
 * @author apohllo
 */
public class MapVisualizer {
    private static final String EMPTY_CELL = " ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
    private Map map;

    /**
     * Initializes the MapVisualizer with an instance of map to visualize.
     *
     * @param map
     */
    public MapVisualizer(Map map) {
        this.map = map;
    }

    /**
     * Convert selected region of the map into a string. It is assumed that the
     * indices of the map will have no more than two characters (including the
     * sign).
     *
     * @param lowerLeft  The lower left corner of the region that is drawn.
     * @param upperRight The upper right corner of the region that is drawn.
     * @return String representation of the selected region of the map.
     */
    public String draw(Position lowerLeft, Position upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.y + 1; i >= lowerLeft.y - 1; i--) {
            if (i == upperRight.y + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.x; j <= upperRight.x + 1; j++) {
                if (i < lowerLeft.y || i > upperRight.y) {
                    builder.append(drawFrame(j <= upperRight.x));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.x) {
                        builder.append(drawObject(new Position(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Position lowerLeft, Position upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }


    private String drawObject(Position currentPosition) {
        String result = null;
         if (this.map.animals.get(currentPosition)!=null) {
            List<Animal> object = this.map.animals.get(currentPosition);
//            if (object.isEmpty())
//                result = EMPTY_CELL;
//            else if (object != null) {
//                result = object.toString();
             if (object != null && object.size() > 0){
                 result = object.toString();
            }
        }
         if (result==null) {
//        else if (this.map.plants.get(currentPosition) != null) {
                 Object object = this.map.plants.get(currentPosition);
                 if (object != null) {
                     result = object.toString();
                 }
//             } else {
//                 result = EMPTY_CELL;
//             }
             if (result == null) return EMPTY_CELL;
         }
        return result;
    }
}