package h04;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H04_RubricProviderPrivate implements RubricProvider {

    private static final Criterion H4_1_1 = Criterion.builder()
        .shortDescription("H4.1.1 | Erstellen von Tetrobot")
        .addChildCriteria(
            criterion("Die Klasse Tetrobot wurde korrekt Deklariert.", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testTetrobotClassDeclaration"))),
            criterion("Der Konstruktor ist korrekt.", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testTetrobotConstructor")))
        ).build();

    private static final Criterion H4_1_2 = Criterion.builder()
        .shortDescription("H4.1.2 | Überschreiben des Kollisionschecks")
        .addChildCriteria(
            criterion("Der Kollisionscheck der Superklasse wird überschrieben", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testIsFrontClearIsOverridden"))),
            criterion("Die Methode isFrontClear liefert das Korrekte Ergebnis", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testIsFrontClearResult")))
        ).build();

    private static final Criterion H4_1_3 = Criterion.builder()
        .shortDescription("H4.1.3 | Neuimplementierung des Kollisionschecks")
        .addChildCriteria(
            criterion("Die übergebenen FieldEntities werden ignoriert.", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testIgnoredFieldEntitiesAreIgnored"))),
            criterion("Es wird überprüft, ob sich die Position vor dem Tetrobot im Spielfeld befindet", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testOverloadedIsFrontClearChecksBounds"))),
            criterion("Die überladene isFrontClear Methode liefert das korrekte Ergebnis", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testOverloadedIsFrontClearResult")))
        ).build();

    private static final Criterion H4_1_4 = Criterion.builder()
        .shortDescription("H4.1.4 | Despawning")
        .addChildCriteria(
            criterion("Der Tetrobot wird ausgeschaltet", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testDespawnTurnsTetrobotOff"))),
            criterion("Der Tetrobot wird vom Spielfeld entfernt", 1,
                JUnitTestRef.ofMethod(() -> TetrobotTests.class.getDeclaredMethod("testDespawnRemovesTetrobotFromWorld")))
        ).build();

    private static final Criterion H4_1 = Criterion.builder()
        .shortDescription("H4.1 | Tetrobot")
        .addChildCriteria(H4_1_1, H4_1_2, H4_1_3, H4_1_4)
        .build();

    private static final Criterion H4_2_1 = Criterion.builder()
        .shortDescription("H4.2.1 | L und J")
        .addChildCriteria(
            criterion("Die Klasse L wurde korrekt deklariert.", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testLClassDeclaration"))),
            criterion("Der Konstruktor von L wurde korrekt implementiert", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testLConstructor"))),
            criterion("Die Methode spawn ruft die spawn Methode von Swarm korrekt auf", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testLSpawnCallsSuperSpawn"))),
            criterion("Die Methode spawn wurde korrekt implementiert", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testLSpawn"))),
            criterion("Die Methode toString wurde korrekt implementiert. Insbesondere wurde der Bedingungsoperator verwendet", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testLToString")))
        ).build();

    private static final Criterion H4_2_2 = Criterion.builder()
        .shortDescription("H4.2.2 | S und Z")
        .addChildCriteria(
            criterion("Die Klasse S wurde korrekt deklariert.", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testSClassDeclaration"))),
            criterion("Der Konstruktor von S wurde korrekt implementiert", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testSConstructor"))),
            criterion("Die Methode spawn ruft die spawn Methode von Swarm korrekt auf", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testSSpawnCallsSuperSpawn"))),
            criterion("Die Methode spawn wurde korrekt implementiert", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testSSpawn"))),
            criterion("Die Methode toString wurde korrekt implementiert. Insbesondere wurde der Bedingungsoperator verwendet", 1,
                JUnitTestRef.ofMethod(() -> SwarmTests.class.getDeclaredMethod("testSToString")))
        ).build();

    private static final Criterion H4_2 = Criterion.builder()
        .shortDescription("H4.2 | Swarms")
        .addChildCriteria(H4_2_1, H4_2_2)
        .build();

    private static final Criterion H4_3_1 = Criterion.builder()
        .shortDescription("H4.3.1 | Verarbeiten von Tastatureingaben")
        .addChildCriteria(
            criterion("Tastendrücke von A und D werden Korrekt behandelt.", 1,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testHandleInputAAndD"))),
            criterion("Tastendrücke von S werden Korrekt behandelt.", 1,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testHandleInputS"))),
            criterion("Tastendrücke der linken und rechten Pfeiltaste werden korrekt behandelt.", 1,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testHandleInputArrowKeys"))),
            criterion("Tastendrücke der Leertaste werden korrekt behandelt", 1,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testHandleInputSpace"))),
            criterion("Die Methode handleInput gibt den korrekten Wert zurück", 1,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testHandleInputReturnValue")))
        ).build();

    private static final Criterion H4_3_2 = Criterion.builder()
        .shortDescription("H4.3.2 | Durchführung von SRS-Style-Rotationen")
        .addChildCriteria(
            criterion("Die Rotation wird am aktiven Schwarm ausgeführt", 1,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testRotateCheckedCommitsRotationOnActiveSwarm"))),
            criterion("Die Projektionsmatrix wird nur dann gesetzt, wenn sie gültig ist.", 2,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testRotateCheckedSetsProjectionMatrixOnlyWhenValid"))),
            criterion("Die Rotation wird in die richtige Richtung ausgeführt.", 2,
                JUnitTestRef.ofMethod(() -> GameLoopTests.class.getDeclaredMethod("testRotateCheckedDirection")))
        ).build();

    private static final Criterion H4_3 = Criterion.builder()
        .shortDescription("H4.3 | Spiellogik")
        .addChildCriteria(H4_3_1, H4_3_2)
        .build();

    private static final Criterion JAVADOC = Criterion.builder()
        .shortDescription("Dokumentation mit Javadoc")
        .minPoints(0)
        .maxPoints(3)
        .build();

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H04 | Tetrobot")
        .addChildCriteria(H4_1, H4_2, H4_3, JAVADOC)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
