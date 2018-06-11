package model;

import shared.Figure;
import shared.FieldType;
import shared.MoveStraight;
import shared.MovesAcross;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jędrzej Frankowski
 */
public class King implements MovesAcross, MoveStraight, Figure {
    @Override
    public Set<Integer> getMovementPositions(Integer length, Integer height, Integer startingPosition) {
        return Stream.of(
                getCloseCrossPositions(length,height,startingPosition),
                getCloseStraightHorizontalPositions(length,startingPosition),
                getCloseStraightVerticalPositions(length,length*height, startingPosition)
        ).flatMap(Collection::stream).collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public FieldType getType() {
        return FieldType.KING;
    }
}
