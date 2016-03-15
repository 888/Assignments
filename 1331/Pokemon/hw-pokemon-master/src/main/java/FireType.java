/**
 * A fire type pokemon
 *
 * @author Farhan Tejani
 */

import java.awt.Rectangle;

public abstract class FireType extends Pokemon {

    /**
     * Constructor
     * @param x The X position of this Fire type
     * @param y The Y position of this Fire type
     * @param bounds The bounding Rectangle
     */
    public FireType(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }

    @Override
    public void move() {
        if (this.getXPos() <= this.getBounds().getWidth()
            && this.getYPos() <= this.getBounds().getHeight() / 2
            && this.getXPos() > this.getBounds().getWidth() / 2) {
            this.setSpeed(this.getSpeed() + 2);
        } else {
            this.setSpeed(20);
        }
        super.move();
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
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
}