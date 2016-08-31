import java.awt.Rectangle;

public class Poliwhirl extends WaterType {

    /**
     * Constructor
     * @param x The X position of Poliwhirl
     * @param y The Y position of Poliwhirl
     * @param bounds The bounding Rectangle
     */
    public Poliwhirl(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        setImage("../resources/poliwhirl.png");
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
        if (other instanceof Poliwhirl) {
            threshold = 62;
        }
        if (other instanceof FireType) {
            threshold = 30;
        } else if (other instanceof GrassType) {
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
        if (other instanceof Poliwhirl && this.getChildren() < 2
            && other.getChildren() < 2) {
            return true;
        }
        return false;
    }

    @Override
    public Pokemon reproduceWithPokemon(Pokemon other) {
        int fertility = this.getRand().nextInt(10);
        if (this.canReproduceWithPokemon(other) && fertility > 5) {
            Poliwhirl baby =
                new Poliwhirl(this.getXPos(), this.getYPos(), this.getBounds());
            this.setChildren(this.getChildren() + 1);
            other.setChildren(other.getChildren() + 1);
            return baby;
        } else {
            return null;
        }
    }

    @Override
    public boolean isOld() {
        int maxlevel = 200;  //arbitrarily set max, can be changed
        if (this.getLevel() > maxlevel) {
            return true;
        }
        return false;
    }
}
