/**
 * A grass type pokemon
 *
 * @author Farhan Tejani
 */

import java.awt.Rectangle;

public abstract class GrassType extends Pokemon {

    /**
     * Constructor
     * @param x The X position of the Grass type Pokemon
     * @param y The Y position of the Grass type Pokemon
     * @param bounds The bounding Rectangle
     */
    public GrassType(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }

    @Override
    public void move() {
        if (this.getXPos() <= this.getBounds().getWidth() / 2
            && this.getYPos() <= this.getBounds().getHeight() / 2) {
            this.setHealth(this.getHealth() + 50);
        }
        super.move();
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
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
}
