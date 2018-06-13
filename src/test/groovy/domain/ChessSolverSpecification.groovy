package domain

import model.Figure
import model.King
import model.Knight
import model.Rook
import shared.FieldType
import spock.lang.Specification

/**
 * @author Jędrzej Frankowski
 */
class ChessSolverSpecification extends Specification {


    def "should solve for 3x3 board with 2 kings and 1 rook"() {
        given:
        def oneRookTwoKingsFor3x3 = parseToConfigurations([
                '''
                K*K
                ***
                *R*
                ''',
                '''
                K**
                **R
                K**
                ''',
                '''
                **K
                R**
                **K
                ''',
                '''
                *R*
                ***
                K*K
                '''
        ])
        def figures = [new Rook(), new King(), new King()] as List<Figure>
        def chessSolver = new ChessSolver(figures, 3, 3)
        when:
        chessSolver.solveConfigurations()
        then:
        chessSolver.getConfigurations() as Set == oneRookTwoKingsFor3x3 as Set
    }

    def "should solve for 4x4 board with 2 rooks and 4 knights"() {
        given:
        def twoRooksAnd4KnightsFor4x4 = parseToConfigurations([
                '''
                *N*N
                **R*
                *N*N
                R***
                ''',
                '''
                *N*N
                R***
                *N*N
                **R*
                ''',
                '''
                R***
                *N*N
                **R*
                *N*N
                ''',
                '''
                **R*
                *N*N
                R***
                *N*N
                ''',
                '''
                *R**
                N*N*
                ***R
                N*N*
                ''',
                '''
                ***R
                N*N*
                *R**
                N*N*
                ''',
                '''
                N*N*
                ***R
                N*N*
                *R**
                ''',
                '''
                N*N*
                *R**
                N*N*
                ***R
                '''
        ])
        def figures = [new Rook(), new Rook(), new Knight(), new Knight(), new Knight(), new Knight()] as List<Figure>
        def chessSolver = new ChessSolver(figures, 4, 4)
        when:
        chessSolver.solveConfigurations()
        then:
        chessSolver.getConfigurations() as Set == twoRooksAnd4KnightsFor4x4 as Set
    }


    def parseToConfigurations(List<String> stringConfigurations) {
        return stringConfigurations.stream()
                .map { stringConfiguration -> stringConfiguration.replaceAll("\\s", "") }
                .map {
            stringConfiguration ->
                Arrays.stream(stringConfiguration.toCharArray()).map {
                    character ->
                        switch (character) {
                            case 'K': return FieldType.KING;
                            case 'R': return FieldType.ROOK;
                            case 'N': return FieldType.KNIGHT;
                            case '*': return FieldType.FREE;
                        }

                }
                .collect()
        }.collect()
    }

}
