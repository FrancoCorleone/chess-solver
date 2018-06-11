package shared;

import java.util.*;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Jędrzej Frankowski
 */
public interface MovesAcross {
    default Set<Integer> getCloseCrossPositions(Integer length, Integer height, Integer startingPosition) {
        return getCrossPositions(length, height, startingPosition, true);
    }

    default Set<Integer> getAllCrossPositions(Integer length, Integer height, Integer startingPosition) {
        return getCrossPositions(length, height, startingPosition, false);
    }

    default Set<Integer> getCrossPositions(Integer length, Integer height, Integer startingPosition, Boolean oneLevelOnly) {
        Integer maxPosition = length * height;
        return Stream.of(
                getPositions(length, startingPosition, x -> x >= 0, getIntPredicateForLeft(length, startingPosition), this::upLeftUnaryOperator, oneLevelOnly),
                getPositions(length, startingPosition, x -> x >= 0, getIntPredicateForRight(length, startingPosition), this::upRightUnaryOperator, oneLevelOnly),
                getPositions(length, startingPosition, x -> x < maxPosition, getIntPredicateForLeft(length, startingPosition), this::downLeftUnaryOperator, oneLevelOnly),
                getPositions(length, startingPosition, x -> x < maxPosition, getIntPredicateForRight(length, startingPosition), this::downRightUnaryOperator, oneLevelOnly)
        ).flatMap(Collection::stream).collect(toCollection(HashSet::new));
    }

    /**
     * Returns predicate for checking if one can move left from starting position
     *
     * @param length
     * @param startingPosition
     * @return
     */
    default IntPredicate getIntPredicateForLeft(Integer length, Integer startingPosition) {
        return x -> x % length < startingPosition % length;
    }

    /**
     * Returns predicate for checking if one can move right from starting position
     *
     * @param length
     * @param startingPosition
     * @return
     */
    default IntPredicate getIntPredicateForRight(Integer length, Integer startingPosition) {
        return x -> x % length > startingPosition % length;
    }


    /**
     * Generic mechanism for getting move-to positions for given starting position and given operators
     *
     * @param length
     * @param startingPosition
     * @param predicate
     * @param borderPredicate
     * @param unaryOperator
     * @return
     */
    default List<Integer> getPositions(Integer length,
                                       Integer startingPosition,
                                       IntPredicate predicate,
                                       IntPredicate borderPredicate,
                                       IntBinaryOperator unaryOperator,
                                       Boolean oneLevelOnly) {
        List<Integer> positions = new ArrayList<>();
        for (int position = startingPosition; predicate.test(position); position = unaryOperator.applyAsInt(position, length)) {
            if (position != startingPosition) {
                if (!borderPredicate.test(position)) {
                    break;
                }
                positions.add(position);
                if (oneLevelOnly) break;
            }
        }
        ;
        return positions;
    }


    default int upLeftUnaryOperator(int position, int length) {
        return position - (length + 1);
    }

    default int upRightUnaryOperator(int position, int length) {
        return position - (length - 1);
    }

    default int downLeftUnaryOperator(int position, int length) {
        return position + (length - 1);
    }

    default int downRightUnaryOperator(int position, int length) {
        return position + (length + 1);
    }
}
