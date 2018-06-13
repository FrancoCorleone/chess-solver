package shared;

/**
 * @author Jędrzej Frankowski
 */
public enum  FieldType {
    ROOK(0),
    BISHOP(1),
    KING(2),
    QUEEN(3),
    KNIGHT(4),
    FREE(5);

    FieldType(int value) {
        this.value = value;
    }

    public final int value;
}
