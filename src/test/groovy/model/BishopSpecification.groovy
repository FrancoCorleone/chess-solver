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
 * Since algorythm for creation of knocked fields is mathematic formula it is reasonable to test corner, border
 * and middle positions:
 * - corner : 0,3,12,15
 * - border : 1,4,7,13
 * - middle : 5
 */
class BishopSpecification extends Specification {

    def bishop = new Bishop()

    def "should return valid bishop moves"() {
        given:
        def length = 4
        def height = 4
        expect:
        bishop.getMovementPositions(length, height, position) == (expectedKnockedOuts as Set)

        where:
        position | expectedKnockedOuts
        //corner
        0        | [5, 10, 15]
        3        | [6, 9, 12]
        12       | [9, 6, 3]
        15       | [0, 5, 10]
        //border
        1        | [4, 6, 11]
        4        | [1, 9, 14]
        7        | [2, 10, 13]
        13       | [8, 10, 7]
        //middle
        5        | [0, 2, 8, 10, 15]
    }

    def "should return correct type"() {
        expect:
        bishop.getType() == FieldType.BISHOP
    }
}
