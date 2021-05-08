package Graphics.ui.game;

import Graphics.controller.GameManager;
import Graphics.ui.game.views.asteroidView.AsteroidViewController;
import Graphics.ui.game.views.boardView.BoardViewController;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController extends GameManager {
    private static UIController ref;
    public static UIController getInstance() {
        return ref;
    }

    private View activeView;

    private final BoardViewController boardView;
    private final AsteroidViewController asteroidView;
    private boolean boardActive;

    private final Stage gameStage;

    public static void init() {
        try {
            ref = new UIController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UIController() throws IOException {
        super();
        gameStage = new Stage();
        gameStage.setResizable(false);
        boardView = new BoardViewController();
        asteroidView = new AsteroidViewController();
    }

    @Override
    public void newGame() {
        activeView = boardView;
        boardActive = true;

        activeView.setActive();
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void switchView() {
        if (boardActive)
            activeView = asteroidView;
        else
            activeView = boardView;

        boardActive = !boardActive;
        activeView.setActive();
    }
}
