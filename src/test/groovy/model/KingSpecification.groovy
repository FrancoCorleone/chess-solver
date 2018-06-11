package model

import model.King
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
 * Since algorythm for creation of knocked fields is mathematic formula it is reasonable to test corner, border
 * and middle positions:
 * - corner : 0,3,12,15
 * - border : 1,4,7,13
 * - middle : 5
 */
class KingSpecification extends Specification {
    def king = new King()

    def "should return valid king moves"() {
        given:
        def length = 4
        def height = 4

        expect:
        king.getMovementPositions(length, height, position) == (expectedKnockedOuts as Set)

        where:
        position | expectedKnockedOuts
        //corner
        0        | [1, 4, 5]
        3        | [2, 6, 7]
        12       | [8, 9, 13]
        15       | [10, 11, 14]
        //border
        1        | [0, 2, 4, 5, 6]
        4        | [0, 1, 5, 8, 9]
        7        | [2, 3, 6, 10, 11]
        13       | [8, 9, 10, 12, 14]
        //middle
        5        | [0, 1, 2, 4, 6, 8, 9, 10]
    }

    def "should return correct type"() {
        expect:
        king.getType() == FieldType.KING
    }
}
