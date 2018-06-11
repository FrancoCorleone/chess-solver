package shared;

import java.util.List;
import java.util.Set;

/**
 * @author Jędrzej Frankowski
 */
public interface Figure {
    Set<Integer> getMovementPositions(Integer length, Integer height, Integer startingPosition);
    FieldType getType();
}
