package week7.assignment;

/**
 * Abstract gallery art piece with final auto-generated catalog identifier.
 */
abstract class ArtPiece {
    private static int counter = 100;
    private final String pieceId;
    protected String title;

    public ArtPiece(String title) {
        this.pieceId = "ART-" + (++counter);
        this.title = (title != null) ? title.trim() : "Untitled";
    }

    public String getPieceId() {
        return pieceId;
    }

    public String getTitle() {
        return title;
    }

    public abstract String describe();
}

class Painting extends ArtPiece {
    public Painting(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return String.format("Painting: %s, framed on canvas", title);
    }
}

class Sculpture extends ArtPiece {
    public Sculpture(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return String.format("Sculpture: %s, carved from stone", title);
    }
}

/**
 * Problem 2: Gallery Description Cards
 * Employs abstract classes with required describe() contracts and auto-incrementing static ID counters.
 */
public class GalleryDescriptionCards {

    public static void main(String[] args) {
        try {
            Painting p = new Painting("Sunset Fields");
            Sculpture s = new Sculpture("The Thinker II");

            System.out.println(p.describe());
            System.out.println(s.describe());
        } catch (Exception e) {
            System.err.println("Error generating gallery description cards: " + e.getMessage());
        }
    }
}
