package model;

import shared.FieldType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * 0   1   2   3   4
 * 5   6   7   8   9
 * 10  11  12  13  14
 * 15  16  17  18  19
 * 20  21  22  23  24
 *
 * @author Jędrzej Frankowski
 */
public class Knight implements Figure {
    @Override
    public Set<Integer> getMovementPositions(Integer length, Integer height, Integer startingPosition) {
        return Stream.of(
                getUpLeftMove(startingPosition, length),
                getUpRightMove(startingPosition, length),
                getRightUpMove(startingPosition, length),
                getRightDownMove(startingPosition, length, height),
                getDownRightMove(startingPosition, length, height),
                getDownLeftMove(startingPosition, length, height),
                getLeftDownMove(startingPosition, length, height),
                getLeftUpMove(startingPosition, length)
        )
                .filter(Objects::nonNull)
                .collect(toCollection(HashSet::new));
    }

    @Override
    public FieldType getType() {
        return FieldType.KNIGHT;
    }

    private Integer getUpLeftMove(int startingPosition, int length) {
        return canMoveUpLong(startingPosition, length) && canMoveLeftShort(startingPosition, length) ? startingPosition - 2 * length - 1 : null;
    }

    private Integer getUpRightMove(int startingPosition, int length) {
        return canMoveUpLong(startingPosition, length) && canMoveRightShort(startingPosition, length) ? startingPosition - 2 * length + 1 : null;
    }

    private Integer getRightUpMove(int startingPosition, int length) {
        return canMoveRightLong(startingPosition, length) && canMoveUpShort(startingPosition, length) ? startingPosition - length + 2 : null;
    }

    private Integer getRightDownMove(int startingPosition, int length, int height) {
        return canMoveRightLong(startingPosition, length) && canMoveDownShort(startingPosition, length, height) ? startingPosition + length + 2 : null;
    }

    private Integer getDownRightMove(int startingPosition, int length, int height) {
        return canMoveDownLong(startingPosition, length, height) && canMoveRightShort(startingPosition, length) ? startingPosition + 2 * length + 1 : null;
    }

    private Integer getDownLeftMove(int startingPosition, int length, int height) {
        return canMoveDownLong(startingPosition, length, height) && canMoveLeftShort(startingPosition, length) ? startingPosition + 2 * length - 1 : null;
    }

    private Integer getLeftDownMove(int startingPosition, int length, int height) {
        return canMoveLeftLong(startingPosition, length) && canMoveDownShort(startingPosition, length, height) ? startingPosition + length - 2 : null;
    }

    private Integer getLeftUpMove(int startingPosition, int length) {
        return canMoveLeftLong(startingPosition, length) && canMoveUpShort(startingPosition, length) ? startingPosition - length - 2 : null;
    }

    private Boolean canMoveUpShort(int startingPosition, int length) {
        return startingPosition > length - 1;
    }

    private Boolean canMoveUpLong(int startingPosition, int length) {
        return startingPosition > 2 * length - 1;
    }

    private Boolean canMoveRightShort(int startingPosition, int length) {
        return length - startingPosition % length > 1;
    }

    private Boolean canMoveRightLong(int startingPosition, int length) {
        return length - startingPosition % length > 2;
    }

    private Boolean canMoveDownShort(int startingPosition, int length, int height) {
        return startingPosition < length * (height - 1);
    }

    private Boolean canMoveDownLong(int startingPosition, int length, int height) {
        return startingPosition < length * (height - 2);
    }

    private Boolean canMoveLeftShort(int startingPosition, int length) {
        return startingPosition % length > 0;
    }

    private Boolean canMoveLeftLong(int startingPosition, int length) {
        return startingPosition % length > 1;
    }

}