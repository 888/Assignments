/**
 * A water type pokemon
 *
 * @author Farhan Tejani
 */

import java.awt.Rectangle;

public abstract class WaterType extends Pokemon {

    /**
     * Constructor
     * @param x The X position of this Water Type Pokemon
     * @param y The Y position of this Water Type Pokemon
     * @param bounds The bounding Rectangle
     */
    public WaterType(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }

    @Override
    public void move() {
        if (this.getXPos() <= this.getBounds().getWidth() / 2
            && this.getYPos() > this.getBounds().getHeight() / 2
            && this.getYPos() <= this.getBounds().getHeight()) {
            this.setLevel(getLevel() + 1);
        }
        super.move();
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
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
}
