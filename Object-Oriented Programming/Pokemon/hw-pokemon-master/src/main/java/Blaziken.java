import java.awt.Rectangle;

public class Blaziken extends FireType {

    /**
     * Constructor
     * @param x The X position of Blaziken
     * @param y The Y position of Blaziken
     * @param bounds The bounding Rectangle
     */
    public Blaziken(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        setImage("../resources/blaziken.png");
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
        if ((other instanceof FireType)
            && this.getLevel() > other.getLevel()) {
            threshold = 9;
        } else if ((other instanceof FireType)
            && this.getLevel() < other.getLevel()) {
            threshold = 87;
        }
        if (other instanceof GrassType) {
            threshold = 30;
        } else if (other instanceof WaterType) {
            threshold = 70;
        } else if (other instanceof CheeseType) {
            threshold = 10;
        } else {
            threshold = 50;
        }
        if (chance > threshold) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canReproduceWithPokemon(Pokemon other) {
        if (other instanceof Blaziken && this.getChildren() < 2
            && other.getChildren() < 2) {
            return true;
        }
        return false;
    }

    @Override
    public Pokemon reproduceWithPokemon(Pokemon other) {
        int fertility = this.getRand().nextInt(10);
        if (this.canReproduceWithPokemon(other) && fertility > 1) {
            Blaziken baby =
                new Blaziken(this.getXPos(), this.getYPos(), this.getBounds());
            this.setChildren(this.getChildren() + 1);
            other.setChildren(other.getChildren() + 1);
            return baby;
        } else {
            return null;
        }
    }

    @Override
    public boolean isOld() {
        int maxlevel = 150;  //arbitrarily set max, can be changed
        if (this.getLevel() > maxlevel) {
            return true;
        }
        return false;
    }
}