import domain.BoardWriter;
import domain.ChessSolver;
import model.*;
import shared.Figure;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Jędrzej Frankowski
 */
public class ChessMain {

    private static final String MS = "ms";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ExecutionException, InterruptedException, FileNotFoundException {
        println("Set all required parameters:");
        int length = readParameter("Length");
        int height = readParameter("Height");
        int numberOfRooks = readParameter("Number of rooks");
        int numberOfKnights = readParameter("Number of knights");
        int numberOfKings = readParameter("Number of kings");
        int numberOfQueens = readParameter("Number of queens");
        int numberOfBishops = readParameter("Number of bishops");
        List<Figure> figures = Stream.of(
                IntStream.range(0, numberOfRooks).mapToObj(i -> new Rook()).collect(toCollection(ArrayList::new)),
                IntStream.range(0, numberOfKnights).mapToObj(i -> new Knight()).collect(toCollection(ArrayList::new)),
                IntStream.range(0, numberOfKings).mapToObj(i -> new King()).collect(toCollection(ArrayList::new)),
                IntStream.range(0, numberOfQueens).mapToObj(i -> new Queen()).collect(toCollection(ArrayList::new)),
                IntStream.range(0, numberOfBishops).mapToObj(i -> new Bishop()).collect(toCollection(ArrayList::new))
        ).flatMap(Collection::stream).collect(toCollection(ArrayList::new));

        for (int i = 0; i < 10; i++) {
            warmup();
            ChessSolver chessSolver = new ChessSolver(figures, height, length);
            chessSolver.solveConfigurations();


            if (args.length > 0) {
                List<String> arglist = Arrays.asList(args);
                if (arglist.contains("-p")) BoardWriter.drawToConsole(chessSolver.getConfigurations());
                if (arglist.contains("-pf")) BoardWriter.drawToFile(chessSolver.getConfigurations());
            }
            println("Cache time: " + chessSolver.getCacheTime().toMillis() + MS);
            println("Total time: " + chessSolver.getTotalTime().toMillis() + MS);
            println("Number of configurations: " + chessSolver.getConfigurations().size());
        }

    }

    private static int readParameter(String parameterName) {
        System.out.print(parameterName + ": ");
        return scanner.nextInt();
    }

    private static void warmup() throws ExecutionException, InterruptedException {
        println("================WARMUP START=======================");
        for (int i = 0; i < 20; i++) {
            List<Figure> figures = Arrays.asList(new Rook(), new King(), new Queen(), new Bishop(), new Knight());
            ChessSolver chessSolver = new ChessSolver(figures, 5, 5);
            chessSolver.solveConfigurations();
        }
        println("================WARMUP STOP========================");
    }

    private static void println(Object object) {
        System.out.println(object);
    }

    private static void printResults() {

    }
}
