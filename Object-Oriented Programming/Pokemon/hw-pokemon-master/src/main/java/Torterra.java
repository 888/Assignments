import java.awt.Rectangle;

public class Torterra extends GrassType {

    /**
     * Constructor
     * @param x The X position of Torterra
     * @param y The Y position of Torterra
     * @param bounds The bounding Rectangle
     */
    public Torterra(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        setImage("../resources/torterra.png");
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
        if (other instanceof FireType) {
            threshold = 40;
        } else if (other instanceof Poliwhirl) {
            threshold = 29;
        }
        if (other instanceof WaterType) {
            threshold = 30;
        } else if (other instanceof FireType) {
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
        int crossbreed = this.getRand().nextInt(10);
        if (other instanceof Torterra && this.getChildren() < 2
            && other.getChildren() < 2) {
            return true;
        }
        if (other instanceof GrassType && crossbreed < 2) {
            return true;
        }
        return false;
    }

    @Override
    public Pokemon reproduceWithPokemon(Pokemon other) {
        int fertility = this.getRand().nextInt(100);
        if (this.canReproduceWithPokemon(other) && fertility > 45) {
            Torterra baby =
                new Torterra(this.getXPos(), this.getYPos(), this.getBounds());
            this.setChildren(this.getChildren() + 1);
            other.setChildren(other.getChildren() + 1);
            return baby;
        }
        return null;
    }

    @Override
    public boolean isOld() {
        int maxlevel = 100;  //arbitrarily set max, can be changed
        if (this.getLevel() > maxlevel) {
            return true;
        }
        return false;
    }
}
