package model

import shared.FieldType
import spock.lang.Specification

/**
 * @author Jędrzej Frankowski
 *
 * Test checks whether good "knocked" positions are returned on getMovementPositions invocation.
 * It is safe to assume that 4x4 board is sufficient to fulfill all possible scenarios with a board numerated like this:
 * |    0   |   1   |   2   |   3   |
 * |    4   |   5   |   6   |   7   |
 * |    8   |   9   |   10  |   11  |
 * |    12  |   13  |   14  |   15  |
 *
 * Due to simple movement definition the only position tested are corners and one from the middle:
 * - corners: 0,3,12,15
 * - middle: 9
 */
class RookSpecification extends Specification {

    def rook = new Rook()

    def "should return valid rook moves"() {
        given:
        def length = 4
        def height = 4
        expect:
        rook.getMovementPositions(length, height, position) == (expectedKnockedOuts as Set)

        where:
        position | expectedKnockedOuts
        //corner
        0        | [1, 2, 3, 4, 8, 12]
        3        | [0, 1, 2, 7, 11, 15]
        12       | [0, 4, 8, 13, 14, 15]
        15       | [3, 7, 11, 12, 13, 14]
        //middle
        9        | [1, 5, 13, 8, 10, 11]
    }

    def "should return correct type"() {
        expect:
        rook.getType() == FieldType.ROOK
    }
}
