package domain;

import lombok.Data;
import model.Figure;
import shared.FieldType;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static shared.FieldType.FREE;

/**
 * @author Jędrzej Frankowski
 */
@Data
public class ChessSolver {

    private List<Figure> figures;
    private Integer height;
    private Integer length;

    private Duration cacheTime;
    private Duration totalTime;
    private List<FieldType[]> configurations;

    public ChessSolver(List<Figure> figures, Integer height, Integer length) {
        this.figures = figures;
        this.height = height;
        this.length = length;
        configurations = Collections.synchronizedList(new LinkedList<>());
    }

    public void solveConfigurations() {
        Instant startCache = Instant.now();
        Set<Figure> distinctFigures = figures.stream().filter(distinctByKey(Figure::getType)).collect(Collectors.toSet());
        SolverHelper.init(length, height, distinctFigures);
        Instant stopCache = Instant.now();
        List<Integer> positionsWithFiguresOn = Collections.emptyList();
        FieldType[] board = Collections.nCopies(SolverHelper.maxSize, FREE).toArray(new FieldType[SolverHelper.maxSize]);
        List<Integer> freePositions = IntStream.range(0, board.length).boxed().collect(toList());
        distinctFigures
                .parallelStream()
                .forEach(figure -> {
                    IntStream.range(0, board.length)
                            .parallel()
                            .forEach(boardIndex -> solveConfiguration(figures, figure, board, boardIndex, freePositions, positionsWithFiguresOn));
                });
        Instant end = Instant.now();
        cacheTime = Duration.between(startCache, stopCache);
        totalTime = Duration.between(stopCache, end);
    }

    private void solveConfiguration(List<Figure> figures,
                                    Figure figure,
                                    FieldType[] board,
                                    int boardIndex,
                                    List<Integer> freePositions,
                                    List<Integer> withFigureOn) {
        FieldType[] newBoard = board.clone();
        newBoard[boardIndex] = figure.getType();
        List<Integer> newFreePositions = new ArrayList<>(freePositions);
        List<Integer> newPositionsWithFigureOn = new ArrayList<>(withFigureOn);
        fillFreeAndTakenAndFigureOn(newFreePositions, newPositionsWithFigureOn, figure, boardIndex);
        List<Figure> remainingFigures = getRemainingFigures(figures, figure);
        solveConfiguration(newFreePositions, newPositionsWithFigureOn, remainingFigures, newBoard);
    }

    private void solveConfiguration(
            List<Integer> freePositions,
            List<Integer> positionsWithFigureOn,
            List<Figure> figures,
            FieldType[] board) {
        if (figures.size() == 0) {
            configurations.add(board);
            return;
        }
        Set<FieldType> fields = new HashSet<>();
        for (Figure figure : figures) {
            if (!fields.contains(figure.getType())) {
                fields.add(figure.getType());
                for (Integer index : freePositions) {
                    if (isFreeAndNoConflicts(positionsWithFigureOn, figure, index)) {
                        solveConfiguration(figures, figure, board, index, freePositions, positionsWithFigureOn);
                    }
                }
            }
        }
    }

    private void fillFreeAndTakenAndFigureOn(List<Integer> newFreePositions, List<Integer> newWithFigureOn, Figure figure, Integer index) {
        Set<Integer> knockedForIndex = SolverHelper.getChessBoard().get(figure.getType().value).get(index);
        knockedForIndex.add(index);
        newFreePositions.removeAll(knockedForIndex);
        newWithFigureOn.add(index);
        newFreePositions.removeIf(position -> position < index);
    }

    private boolean isFreeAndNoConflicts(List<Integer> withFigureOn, Figure figure, int index) {
        return Collections.disjoint(SolverHelper.getChessBoard().get(figure.getType().value).get(index), withFigureOn);
    }


    private List<Figure> getRemainingFigures(List<Figure> figures, Figure currentFigure) {
        List<Figure> remainingFigures = new ArrayList<>(figures);
        remainingFigures.remove(currentFigure);
        return remainingFigures;
    }

    /**
     * For filtering by duplicate type
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
