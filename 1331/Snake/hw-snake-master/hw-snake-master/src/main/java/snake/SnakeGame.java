package snake;

import engine.Difficulty;
import engine.GameWorld;

import java.util.Scanner;

import javafx.animation.AnimationTimer;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.stage.Stage;

import model.Direction;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * The main entry point for the snake program. This class handles all graphics
 * not related to the actual game (i.e the start screen and score screen), user
 * input for each screen, etc. Also handles the updating of world on a timed
 * interval.
 *
 * @author  Susanna Dong, Jim Harris
 * @version 1.0
 */
public class SnakeGame extends Application {

    private Stage window;
    private Scene startScene;
    private Scene gameScene;
    private Scene scoreScene;
    private ToggleGroup gameMode;
    private int finalScore;
    private GameWorld world;
    private long lastUpdateTime;

    public static final int SCREEN_WIDTH = 512;
    public static final int TILE_WIDTH = 32;

    @Override
    public void start(Stage stage) {
        gameMode = new ToggleGroup();
        setupStartScene();
        finalScore = 0;
        this.window = new Stage();
        this.window.setScene(this.startScene);
        this.window.setResizable(false);
        this.window.show();
    }

    /**
     * Sets startScene and adds elements to it. startScene is composed of:
     *     1) A title label
     *     2) A group of radio buttons for setting the game mode
     *     3) A button that when pressed will call setupGameScene and call play
     */
    private void setupStartScene() {
        Label title = new Label("Python");
        title.setFont(new Font(63));
        RadioButton easyDiff = new RadioButton("Easy");
        RadioButton normalDiff = new RadioButton("Normal");
        RadioButton hardDiff = new RadioButton("Hard");
        easyDiff.setUserData(Difficulty.EASY);
        normalDiff.setUserData(Difficulty.NORMAL);
        hardDiff.setUserData(Difficulty.HARD);
        easyDiff.setToggleGroup(gameMode);
        normalDiff.setToggleGroup(gameMode);
        hardDiff.setToggleGroup(gameMode);
        normalDiff.setSelected(true);
        easyDiff.setSelected(false);
        hardDiff.setSelected(false);
        HBox hDisplay = new HBox(10.0);
        hDisplay.getChildren().addAll(easyDiff, normalDiff, hardDiff);
        hDisplay.setAlignment(Pos.CENTER);
        Button begin = new Button("Start");
        begin.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                setupGameScene();
                window.setScene(gameScene);
                play();
            }
        });
        VBox vDisplay = new VBox(10.0);
        vDisplay.getChildren().addAll(title, hDisplay, begin);
        vDisplay.setAlignment(Pos.CENTER);
        StackPane startParent = new StackPane();
        startParent.getChildren().add(vDisplay);
        this.startScene = new Scene(startParent, SnakeGame.SCREEN_WIDTH,
            SnakeGame.SCREEN_WIDTH);
    }

    /**
     * Sets the gameScene and adds elements to it. gameScene is composed of:
     *     1) A Rectangle for the background
     *     2) All of the elements from world
     * world handles the addition of all the game graphics to the screen with
     * the exception of the background, which you must add to gameScene
     * manually. You will need to set world in this method as well. Also, this
     * method must add an event to gameScene such that when WASD or the arrow
     * keys are pressed the snake will change direction.
     */
    private void setupGameScene() {
        Group gameSceneParent = new Group();
        this.gameScene = new Scene(gameSceneParent,
            SnakeGame.SCREEN_WIDTH, SnakeGame.SCREEN_WIDTH);
        Rectangle background = new Rectangle(0, 0, SnakeGame.SCREEN_WIDTH,
            SnakeGame.SCREEN_WIDTH);
        background.setFill(Color.DIMGREY);
        gameSceneParent.getChildren().add(background);
        Difficulty gameDiff =
            (Difficulty) this.gameMode.getSelectedToggle().getUserData();
        this.world = new GameWorld(background, this.gameScene, gameDiff);
        this.gameScene.setOnKeyPressed((KeyEvent e) -> {
                if (e.getText().equals("w") || e.getCode().equals(KeyCode.UP)) {
                    world.setDirection(Direction.UP);
                } else if (e.getText().equals("a")
                    || e.getCode().equals(KeyCode.LEFT)) {
                    world.setDirection(Direction.LEFT);
                } else if (e.getText().equals("s")
                    || e.getCode().equals(KeyCode.DOWN)) {
                    world.setDirection(Direction.DOWN);
                } else if (e.getText().equals("d")
                    || e.getCode().equals(KeyCode.RIGHT)) {
                    world.setDirection(Direction.RIGHT);
                }
            });
    }

    /**
     * Sets the scoreScene and adds elements to it. scoreScene is composed of:
     *     1) A label that shows the user's score from world.
     *     2) A highscore list of the top 10 scores that is composed of:
     *         a) A ListView of Nodes for player usernames.
     *             - If the player makes it into the top 10, they need to be
     *             able to set their username, so a TextField should be at the
     *             point in the list where they belong. All other fields can
     *             just be labels for existing users.
     *         b) A ListView of Integers for player scores.
     *             - If the player makes it into the top 10, they're score
     *             should be displayed at the proper place in the list.
     *         * Existing high scores can be found in highScores.csv in the
     *         resources folder.
     *     3) A button that when pressed will write the high scores in the list
     *     to highScores.csv in the resources folder in the same format in which
     *     you originally accessed them. The button should also change the scene
     *     for window to startScene.
     */
    private void setupScoreScene() {
        Label displayScore = new Label("Top Scores");
        displayScore.setFont(new Font(54));
        ObservableList<Node> nodesOL = FXCollections.observableArrayList();
        ObservableList<Integer> integersOL
            = FXCollections.observableArrayList();
        HBox highScores = new HBox(5.0);
        int userScore = world.getScore();
        File file = new File("src/main/resources/highScores.csv");
        try {
            Scanner scores = new Scanner(file);
            String[] breaks = new String[2];
            while (scores.hasNextLine()) {
                String line = scores.nextLine();
                breaks = line.split(",");
                Label name = new Label(breaks[0]);
                if (Integer.parseInt(breaks[1]) > userScore
                    && nodesOL.size() < 10) {
                    nodesOL.add(name);
                    integersOL.add(Integer.parseInt(breaks[1]));
                } else if (Integer.parseInt(breaks[1]) < userScore
                    && !(integersOL.contains(userScore))
                    && nodesOL.size() < 10) {
                    TextField userName = new TextField("Enter Username Here");
                    nodesOL.add(userName);
                    integersOL.add(userScore);
                    nodesOL.add(name);
                    integersOL.add(Integer.parseInt(breaks[1]));
                } else if (Integer.parseInt(breaks[1]) < userScore
                    && integersOL.contains(userScore) && nodesOL.size() < 10) {
                    nodesOL.add(name);
                    integersOL.add(Integer.parseInt(breaks[1]));
                }
            }
            if (nodesOL.size() < 10) {
                TextField userName = new TextField("Enter Username Here");
                nodesOL.add(userName);
                integersOL.add(userScore);
            }
            scores.close();
            ListView<Node> nodes = new ListView<>(nodesOL);
            ListView<Integer> integers = new ListView<>(integersOL);
            highScores.getChildren().addAll(nodes, integers);
            highScores.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            ListView<Node> nodes = new ListView<>();
            ListView<Integer> integers = new ListView<>();
            highScores.getChildren().addAll(nodes, integers);
            highScores.setAlignment(Pos.CENTER);
        }
        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    PrintWriter saveScores = new PrintWriter(file);
                    for (int i = 0; i < 10; i++) {
                        if (nodesOL.get(i) instanceof Label) {
                            Label extract = (Label) nodesOL.get(i);
                            saveScores.write(extract.getText()
                                + "," + integersOL.get(i).toString() + "\n");
                        } else if (nodesOL.get(i)
                            instanceof TextField) {
                            TextField extract = (TextField) nodesOL.get(i);
                            saveScores.write(extract.getText()
                                + "," + integersOL.get(i).toString() + "\n");
                        }
                    }
                    saveScores.close();
                } catch (FileNotFoundException f) {
                    System.out.println(f.getMessage());
                }
                window.setScene(startScene);
            }
        });
        VBox sDisplay = new VBox(5.0);
        sDisplay.getChildren().addAll(displayScore, highScores, save);
        sDisplay.setAlignment(Pos.CENTER);
        StackPane scoreParent = new StackPane();
        scoreParent.getChildren().add(sDisplay);
        this.scoreScene = new Scene(scoreParent, SnakeGame.SCREEN_WIDTH,
            SnakeGame.SCREEN_WIDTH);
    }

    /**
     * Starts the game loop. Assumes that the scene for window has been set to
     * gameScene and that world has been properly reset to the starting game
     * state.
     */
    public void play() {
        AnimationTimer timey = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (System.currentTimeMillis()
                    - lastUpdateTime > world.getDelayTime()) {
                    world.update();
                    // DO NOT MODIFY ABOVE THIS LINE
                    if (world.isGameOver()) {
                        setupScoreScene();
                        window.setScene(scoreScene);
                        stop();
                    }

                    // DO NOT MODIFY BELOW THIS LINE
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
        };
        lastUpdateTime = System.currentTimeMillis();
        timey.start();
    }
}
