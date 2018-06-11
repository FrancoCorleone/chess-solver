package model;

import lombok.NoArgsConstructor;
import shared.FieldType;
import shared.Figure;
import shared.MoveStraight;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Jędrzej Frankowski
 */
@NoArgsConstructor
public class Rook implements MoveStraight, Figure {

    @Override
    public Set<Integer> getMovementPositions(Integer length, Integer height, Integer startingPosition) {
        return Stream.of(
                getStraightHorizontalPositions(length, startingPosition),
                getStraightVerticalPositions(length, length * height, startingPosition)
        ).flatMap(Collection::stream).collect(toCollection(HashSet::new));
    }


    @Override
    public FieldType getType() {
        return FieldType.ROOK;
    }
}
