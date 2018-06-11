package model;

import shared.FieldType;
import shared.Figure;
import shared.MoveStraight;
import shared.MovesAcross;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Jędrzej Frankowski
 */
public class Queen implements MovesAcross, MoveStraight, Figure {
    @Override
    public Set<Integer> getMovementPositions(Integer length, Integer height, Integer startingPosition) {
        return Stream.of(
                getStraightVerticalPositions(length, length * height, startingPosition),
                getStraightHorizontalPositions(length, startingPosition),
                getAllCrossPositions(length, height, startingPosition)
        ).flatMap(Collection::stream).collect(toCollection(HashSet::new));
    }

    @Override
    public FieldType getType() {
        return FieldType.QUEEN;
    }
}
