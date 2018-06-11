package domain;

import shared.FieldType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.IntStream;

/**
 * @author Jędrzej Frankowski
 */
public class BoardWriter {
    public static void drawToFile(Collection<FieldType[]> configurations) {
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(Instant.now().toEpochMilli() + "_configurations"))) {
            draw(configurations, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void drawToConsole(Collection<FieldType[]> configurations) {
        PrintWriter printWriter = new PrintWriter(System.out);
        draw(configurations, printWriter);
    }

    private static void draw(Collection<FieldType[]> arrangements, PrintWriter printWriter) {
        arrangements.forEach(arrangement -> draw(arrangement, printWriter));
    }

    private static void draw(FieldType[] chessArrangement, PrintWriter printWriter) {
        IntStream.range(0, chessArrangement.length).forEach(i -> {
            if (i % SolverHelper.length == 0) {
                printWriter.println("");
            }
            char sign = getChar(chessArrangement[i]);
            printWriter.print(sign);
        });
        printWriter.println("");
        printWriter.flush();
    }

    private static char getChar(FieldType fieldType) {
        switch (fieldType) {
            case FREE:
            case KNOCKED:
                return '*';
            case KING:
                return 'K';
            case ROOK:
                return 'R';
            case QUEEN:
                return 'Q';
            case BISHOP:
                return 'B';
            case KNIGHT:
                return 'N';
            default:
                return '*';

        }
    }
}
