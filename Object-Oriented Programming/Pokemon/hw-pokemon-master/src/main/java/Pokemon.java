/**
 * The abstract Pokemon for the PokeBattle Simulation
 *
 * @author Heather, Aniruddha
 */
import java.util.Random;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public abstract class Pokemon {

    private Rectangle bounds;
    private int xPos;
    private int yPos;
    private ImageIcon image;
    private int health;
    private int level;
    private int children;
    private int speed;
    private static Random rand = new Random();

    /**
     * Constructor
     *
     * Represents a Pokemon in the PokeWorld. Each Pokemon
     * has a location in the world and attributes which help
     * it reproduce and thrive.
     * @param xPos The X position of this Pokemon
     * @param yPos The Y position of this Pokemon
     * @param bounds The boundaries of the PokeWorld where
     *               the Pokemon can exist
     */
    public Pokemon(int xPos, int yPos, Rectangle bounds) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.bounds = bounds;
        this.level = 0;
        this.health = 1000;
        this.children = 0;
        this.speed = 20;
    }

    /**
     * @return the bounding rectangle of the PokeWorld
     *             that this Pokemon exists in
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @return the X position of this Pokemon
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * @return the Y position of this Pokemon
     */
    public int getYPos() {
        return yPos;
    }


    /**
     * @return the current health value of this Pokemon
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return the current level of this Pokemon
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the number of children this Pokemon has
     */
    public int getChildren() {
        return children;
    }

    /**
     * @return the current speed of this pokemon
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return the Random object used by Pokemon for random events
     */
    public static Random getRand() {
        return rand;
    }

    /**
    * Sets the image attribute for this pokemon
    * @param image the ImageIcon to use to represent this Pokemon
    */
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    /**
     * Sets the image attribute for this pokemon
     * @param image the path to the image to use for this Pokemon
     */
    public void setImage(String path) {
        this.image = new ImageIcon(path);
    }

    /**
     * Sets the Pokemon's health value
     * @param health The new health of the Pokemon
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the Pokemon's level
     * @param level The new level of the Pokemon
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets the number of children of this Pokemon
     * @param children The number of children this Pokemon now has
     */
    public void setChildren(int children) {
        this.children = children;
    }

    /**
     * Sets the speed of this Pokemon
     * @param speed the updated speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Should draw the Pokemon at its location.
     * @param Graphics object for drawing use
     */
    public void draw(Graphics g) {
        image.paintIcon(null, g, xPos, yPos);
    }

    /**
     * Will move the Pokemon in a random yet effective manner (it doesn't move
     * in circles). Each time a Pokemon moves, it's health should decrease and
     * its level should increase.
     */
    public void move() {
        int deltax = (rand.nextInt(12) - 6) * speed;
        int deltay = (rand.nextInt(12) - 6) * speed;
        int nxPos = getXPos() + deltax;
        int nyPos = getYPos() + deltay;
        if (nxPos < this.getBounds().getX()) {
            nxPos = (int) this.getBounds().getX();
        } else if (nxPos > this.getBounds().getWidth()
            - this.image.getIconWidth()) {
            nxPos = (int) this.getBounds().getWidth()
                - this.image.getIconWidth();
        }
        if (nyPos < this.getBounds().getY()) {
            nyPos = (int) this.getBounds().getY();
        } else if (nyPos > this.getBounds().getHeight()
            - this.image.getIconHeight()) {
            nyPos = (int) this.getBounds().getHeight()
                - this.image.getIconHeight();
        }
        this.xPos = nxPos;
        this.yPos = nyPos;
        this.setLevel(getLevel() + 1);
        this.setHealth(getHealth() - 1);
    }

    /**
     * Returns whether or not this Pokemon is colliding with a given Pokemon.
     * This should be determined by the Pokemon's location and the dimensions of
     * its image.
     * @param other the Pokemon to check for collision with
     * @return true if the two Pokemon have collided, false otherwise
     */
    public boolean collidesWithPokemon(Pokemon other) {
        int thisxdim = this.getXPos() + this.image.getIconWidth();
        int thisydim = this.getYPos() + this.image.getIconHeight();
        int otherxdim = other.getXPos() + other.image.getIconWidth();
        int otherydim = other.getYPos() + other.image.getIconHeight();
        int thisx = this.getXPos();
        int thisy = this.getYPos();
        int otherx = other.getXPos();
        int othery = other.getYPos();
        for (; thisx < thisxdim; thisx++) {
            for (; thisy < thisydim; thisy++) {
                if ((thisx <= otherxdim && thisx >= otherx)
                    && thisy <= otherydim && thisy >= othery) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether or not the two Pokemon can reproduce
     * @param other the Pokemon to check if this can reproduce with
     * @return true if the two Pokemon can reproduce, false otherwise
     */
    public abstract boolean canReproduceWithPokemon(Pokemon other);
        /* FOR SUBCLASSES
        if (this.getClass().equals(other.getClass())) {
            return true;
        } else {
            return false;
        }
        */

    /**
     * If the two Pokemon are able to reproduce (as determined by
     * canReproduceWithPokemon(Pokemon)), this method returns a new Pokemon of
     * the same type and in the same location. If for some reason, reproduction
     * does not happen, null should be returned.
     * Reproduction rates should be controlled in some manner so that infinite
     * population growth does not occur. This can be acheived in a variety of
     * ways: reproduction probability, limiting total offspring per
     * Pokemon, etc.
     * @param other the Pokemon to reproduce with
     * @return the offspring Pokemon
     */
    public abstract Pokemon reproduceWithPokemon(Pokemon other);
        /* FOR SUBLCLASSES
        int fertility = this.getRand().nextInt(10);
    if (this.canReproduceWithPokemon() && fertility > 5 && this.children < 2) {
            Pokemon baby = new Pokemon(this.xPos, this.yPos, this.bounds);
            return baby;
        }
        */

    /**
     * Returns whether or not a Pokemon has surpassed some determined max level.
     * @return true if the Pokemon has passed the specified level,
     *              false otherwise
     */
    public abstract boolean isOld();
        /* FOR SUBCLASSES
        int maxlevel = 140;  //arbitrarily set max, can be changed
        if (this.getLevel > maxlevel) {
            return true;
        } else {
            return false;
        }
        */

    /**
    * Returns whether or not a Pokemon can damage another Pokemon
    * @param other the Pokemon for which to check if harming is possible
    * @return true if this can harm Pokemon a, false otherwise
    */
    public abstract boolean canHarmPokemon(Pokemon other);
        /* Implement this in each Type
        int chance = this.getRand().nextInt(10);
        int threshold;
        if (chance > threshold) {
            return true;
        } else {
            return false;
        }

    /**
    * Harms another instance of a Pokemon by decreasing its health by a fixed
    * amount.
    * @param other the Pokemon to harm
    */
    public void harmPokemon(Pokemon other) {
        if (this.canHarmPokemon(other)) {
            other.setHealth(other.getHealth() - 25); //arbitrarily set value
        }
    }

    /**
     * Called when an Pokemon faints.
     */
    public void faint() {
        this.setHealth(0);
    }

    /**
     * Returns whether or not the Pokemon has fainted (ie health 0).
     * @return true if this Pokemon has fainted, false otherwise
     */
    public boolean hasFainted() {
        if (this.getHealth() <= 0) {
            return true;
        } else if (this.isOld()) {
            return true;
        }
        return false;
    }
}
