package model;

import shared.FieldType;
import shared.Figure;
import shared.MovesAcross;

import java.util.Set;

/**
 * @author Jędrzej Frankowski
 */
public class Bishop implements MovesAcross, Figure {

    @Override
    public Set<Integer> getMovementPositions(Integer length, Integer height, Integer startingPosition) {
        return getAllCrossPositions(length, height, startingPosition);
    }


    @Override
    public FieldType getType() {
        return FieldType.BISHOP;
    }


}
