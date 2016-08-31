import java.awt.Rectangle;

public class Swiss extends CheeseType {
    public Swiss(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        setImage("../resources/swiss.png");
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        int chance = this.getRand().nextInt(100);
        int threshold;
        if (other instanceof Torterra) {
            threshold = 15;
        } else if (other instanceof Poliwhirl) {
            threshold = 25;
        }
        if (other instanceof WaterType) {
            threshold = 40;
        } else if (other instanceof FireType) {
            threshold = 40;
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

    @Override
    public boolean canReproduceWithPokemon(Pokemon other) {
        if (other instanceof Swiss && this.getChildren() < 2
            && other.getChildren() < 2) {
            return true;
        }
        return false;
    }

    @Override
    public Pokemon reproduceWithPokemon(Pokemon other) {
        int fertility = this.getRand().nextInt(10);
        if (this.canReproduceWithPokemon(other) && fertility > 8) {
            Swiss baby =
                new Swiss(this.getXPos(), this.getYPos(), this.getBounds());
            this.setChildren(this.getChildren() + 1);
            other.setChildren(other.getChildren() + 1);
            return baby;
        }
        return null;
    }

    @Override
    public boolean isOld() {
        int maxlevel = 81;  //arbitrarily set max, can be changed
        if (this.getLevel() > maxlevel) {
            return true;
        }
        return false;
    }
}