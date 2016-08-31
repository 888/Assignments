import java.awt.Rectangle;

public class Ryuujinjakka extends FireType {
    public Ryuujinjakka(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        setImage("../resources/ryuujinjakka.jpg");
    }

    @Override
    public boolean canHarmPokemon(Pokemon other) {
        return super.canHarmPokemon(other);
    }

    @Override
    public boolean canReproduceWithPokemon(Pokemon other) {
        if (other instanceof Ryuujinjakka && this.getChildren() < 2
            && other.getChildren() < 2) {
            return true;
        }
        return false;
    }

    @Override
    public Pokemon reproduceWithPokemon(Pokemon other) {
        int fertility = this.getRand().nextInt(10);
        if (this.canReproduceWithPokemon(other) && fertility > 7) {
            Ryuujinjakka baby = new
                Ryuujinjakka(this.getXPos(), this.getYPos(), this.getBounds());
            this.setChildren(this.getChildren() + 1);
            other.setChildren(other.getChildren() + 1);
            return baby;
        } else {
            return null;
        }
    }
    @Override
    public boolean isOld() {
        int maxlevel = 190;  //arbitrarily set max, can be changed
        if (this.getLevel() > maxlevel) {
            return true;
        }
        return false;
    }
}