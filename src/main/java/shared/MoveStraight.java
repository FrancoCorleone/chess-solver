package shared;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jędrzej Frankowski
 */
public interface MoveStraight {

    default Set<Integer> getCloseStraightHorizontalPositions(int length, int startingPosition) {
        Set<Integer> positions = new HashSet<>();
        int left = startingPosition - 1;
        int right = startingPosition + 1;
        if (startingPosition % length > left % length && left >= 0) positions.add(left);
        if (startingPosition % length < right % length && right % length != 0) positions.add(right);
        return positions;
    }

    default Set<Integer> getCloseStraightVerticalPositions(int length, int maxPosition, int startingPosition) {
        Set<Integer> positions = new HashSet<>();
        int up = startingPosition - length;
        int down = startingPosition + length;
        if (up >= 0) positions.add(up);
        if (down < maxPosition) positions.add(down);
        return positions;
    }


    default Set<Integer> getStraightHorizontalPositions(int length, int startingPosition) {
        Set<Integer> positions = new HashSet<>();
        int leftBorder = startingPosition - startingPosition % length;
        for (int position = leftBorder; position < leftBorder + length; position++) {
            if (position != startingPosition) {
                positions.add(position);
            }
        }
        return positions;
    }

    default Set<Integer> getStraightVerticalPositions(int length, int maxPosition, int startingPosition) {
        Set<Integer> positions = new HashSet<>();
        for (int position = startingPosition; position < maxPosition; position += length) {
            if (position != startingPosition) {
                positions.add(position);
            }
        }
        for (int position = startingPosition; position >= 0; position -= length) {
            if (position != startingPosition) {
                positions.add(position);
            }
        }
        return positions;
    }
}
