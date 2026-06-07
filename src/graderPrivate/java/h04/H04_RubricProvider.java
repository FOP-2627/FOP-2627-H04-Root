package h04;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H04_RubricProvider implements RubricProvider {

    private static final Criterion H4_1_1 = Criterion.builder()
        .shortDescription("H4.1.1 | Erstellen von Tetrobot")
        .addChildCriteria(
            criterion("Die Klasse Tetrobot wurde korrekt Deklariert.", 1),
            criterion("Der Konstruktor ist korrekt.", 1)
        ).build();

    private static final Criterion H4_1_2 = Criterion.builder()
        .shortDescription("H4.1.2 | Überschreiben des Kollisionschecks")
        .addChildCriteria(
            criterion("Der Kollisionscheck der Superklasse wird überschrieben", 1),
            criterion("Die Methode isFrontClear liefert das Korrekte Ergebnis", 1)
        ).build();

    private static final Criterion H4_1_3 = Criterion.builder()
        .shortDescription("H4.1.3 | Neuimplementierung des Kollisionschecks")
        .addChildCriteria(
            criterion("Die übergebenen FieldEntities werden ignoriert.", 1),
            criterion("Es wird überprüft, ob sich die Position vor dem Tetrobot im Spielfeld befindet", 1),
            criterion("Das überladene isFrontClear liefert das korrekte Ergebnis", 1)
        ).build();

    private static final Criterion H4_1_4 = Criterion.builder()
        .shortDescription("H4.1.4 | Despawning")
        .addChildCriteria(
            criterion("Der Tetrobot wird ausgeschaltet", 1),
            criterion("Der Tetrobot wird vom Spielfeld entfernt", 1)
        ).build();

    private static final Criterion H4_1 = Criterion.builder()
        .shortDescription("H4.1 | Tetrobot")
        .addChildCriteria(H4_1_1, H4_1_2, H4_1_3, H4_1_4)
        .build();

    private static final Criterion H4_2_1 = Criterion.builder()
        .shortDescription("H4.2.1 | L und J")
        .addChildCriteria(
            criterion("Die Klasse L wurde korrekt deklariert.", 1),
            criterion("Der Konstruktor von L wurde korrekt implementiert", 1),
            criterion("Die Methode spawn ruft die spawn Methode von Swarm korrekt auf", 1),
            criterion("Die Methode spawn wurde korrekt implementiert", 1),
            criterion("Die Methode toString wurde korrekt implementiert. Insbesondere wurde der Bedingungsoperator verwendet", 1)
        ).build();

    private static final Criterion H4_2_2 = Criterion.builder()
        .shortDescription("H4.2.2 | S und Z")
        .addChildCriteria(
            criterion("Die Klasse L wurde korrekt deklariert.", 1),
            criterion("Der Konstruktor von L wurde korrekt implementiert", 1),
            criterion("Die Methode spawn ruft die spawn Methode von Swarm korrekt auf", 1),
            criterion("Die Methode spawn wurde korrekt implementiert", 1),
            criterion("Die Methode toString wurde korrekt implementiert. Insbesondere wurde der Bedingungsoperator verwendet", 1)
        ).build();

    private static final Criterion H4_2 = Criterion.builder()
        .shortDescription("H4.2 | Swarms")
        .addChildCriteria(H4_2_1, H4_2_2)
        .build();

    private static final Criterion H4_3_1 = Criterion.builder()
        .shortDescription("H4.3.1 | Verarbeiten von Tastatureingaben")
        .addChildCriteria(
            criterion("Tastendrücke von A und D werden Korrekt behandelt.", 1),
            criterion("Tastendrücke von S werden Korrekt behandelt.", 1),
            criterion("Tastendrücke der linken und rechten Pfeiltaste werden korrekt behandelt.", 1),
            criterion("Tastendrücke der Leertaste werden korrekt behandelt", 1),
            criterion("Die Methode handleInput gibt den korrekten Wert zurück", 1)
        ).build();

    private static final Criterion H4_3_2 = Criterion.builder()
        .shortDescription("H4.3.2 | Durchführung von SRS-Style-Rotationen")
        .addChildCriteria(
            criterion("Die Rotation wird am aktiven Schwarm ausgeführt", 1),
            criterion("Die ProjectionMatrix wird nur dann gesetzt, wenn sie gültig ist.", 2),
            criterion("Die Rotation wird in die richtige Richtung ausgeführt.", 2)
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
