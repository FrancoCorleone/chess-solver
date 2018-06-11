package domain;

import shared.FieldType;
import shared.Figure;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Jędrzej Frankowski
 * <p>
 * This class creates cache for movement positions starting from every position on defined board for every piece defined
 * which are then stored in chessBoard field. External list constist of 7 (all fields in FieldType) elements. Each one
 * consists of List of positions on board and every position contains a Set of knocked positions.
 */
public class SolverHelper {

    public static Integer height;
    public static Integer length;
    public static Integer maxSize;
    private static List<List<Set<Integer>>> chessBoard;

    public static void init(Integer length, Integer height, Collection<Figure> figures) {
        SolverHelper.length = length;
        SolverHelper.height = height;
        SolverHelper.maxSize = length * height;

        cacheMovementPositions(length, height, figures);
    }

    private static void cacheMovementPositions(Integer length, Integer height, Collection<Figure> distinctFigures) {
        EnumSet<FieldType> fieldTypes = EnumSet.allOf(FieldType.class);
        chessBoard = new ArrayList<>(fieldTypes.size());
        fieldTypes.forEach(fieldType -> {
            Optional<Figure> optionalFigure = distinctFigures.stream()
                    .filter(figure -> figure.getType().equals(fieldType))
                    .findAny();
            if (optionalFigure.isPresent()) {
                List<Set<Integer>> positions = IntStream.range(0, maxSize)
                        .boxed()
                        .map(i -> optionalFigure.get().getMovementPositions(length, height, i))
                        .collect(toCollection(() -> new ArrayList<>(maxSize)));
                chessBoard.add(positions);
            } else {
                chessBoard.add(Collections.emptyList());
            }
        });
    }


    public static List<List<Set<Integer>>> getChessBoard() {
        return chessBoard;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getLength() {
        return length;
    }
}
