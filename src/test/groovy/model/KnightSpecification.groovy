package model

import shared.FieldType
import spock.lang.Specification

/**
 * @author Jędrzej Frankowski
 *
 * Test checks whether good "knocked" positions are returned on getMovementPositions invocation.
 * To get full look on knight moves a 5x5 board should be created
 * |    0   |   1   |   2   |   3   |   4   |
 * |    5   |   6   |   7   |   8   |   9   |
 * |    10  |   11  |   12  |   13  |   14  |
 * |    15  |   16  |   17  |   18  |   19  |
 * |    20  |   21  |   22  |   23  |   24  |
 *
 * Since algorythm for creation of knocked fields is mathematic formula it is reasonable to test corner, border
 * and middle positions in multiple places since there are quite few different moves. Some of them might go
 * left/right/up/down long and some only short
 * - corner : 0,4,20,24
 * - border : 1,2,3,5,9,10,14,15,19,21,22,23
 * - middle : 6,7,8,11,13,16,17,18
 * - allMovesAvailable: 12
 */
class KnightSpecification extends Specification {

    def knight = new Knight()

    def "should return valid knight moves"() {
        given:
        def length = 5
        def height = 5

        expect:
        knight.getMovementPositions(length, height, position) == (expectedKnockedOuts as Set)


        where:
        position | expectedKnockedOuts
        //corner
        0        | [7, 11]
        4        | [7, 13]
        20       | [11, 17]
        24       | [13, 17]
//        //border
        1        | [10, 12, 8]
        2        | [9, 13, 11, 5]
        3        | [6, 12, 14]
        5        | [2, 12, 16]
        9        | [2, 12, 18]
        10       | [1, 7, 17, 21]
        14       | [3, 7, 17, 23]
        15       | [6, 12, 22]
        19       | [8, 12, 22]
        21       | [10, 12, 18]
        22       | [15, 11, 13, 19]
        23       | [16, 12, 14]
        //middle
        6        | [3, 13, 17, 15]
        7        | [0, 4, 14, 18, 16, 10]
        8        | [1, 19, 17, 11]
        11       | [0, 2, 8, 18, 22, 20]
        13       | [2, 4, 24, 22, 16, 6]
        16       | [5, 7, 13, 23]
        17       | [6, 8, 14, 24, 20, 10]
        18       | [7, 9, 21, 11]
//        //all positions available
        12       | [1, 3, 9, 19, 23, 21, 15, 5]
    }

    def "should return correct type"() {
        expect:
        knight.getType() == FieldType.KNIGHT
    }
}
