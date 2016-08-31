import java.awt.Rectangle;

public abstract class CheeseType extends Pokemon {
    public CheeseType(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }

    @Override
    public void move() {
        if (this.getXPos() > this.getBounds().getWidth() / 2
            && this.getYPos() > this.getBounds().getHeight() / 2) {
            this.setSpeed(this.getSpeed() + 4);
            this.setHealth(this.getHealth() - 100);
        } else {
            this.setSpeed(20);
        }
        super.move();
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
        if (other instanceof WaterType) {
            threshold = 20;
        } else if (other instanceof FireType) {
            threshold = 20;
        } else if (other instanceof CheeseType) {
            threshold = 90;
        } else {
            threshold = 30;
        }
        if (chance > threshold) {
            return true;
        }
        return false;
    }
}
