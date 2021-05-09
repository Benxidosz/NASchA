package Graphics.ui.menu;

import Graphics.ui.game.UIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private static MenuController ref;

    @FXML
    public Slider ufoSlider;
    @FXML
    public Slider settlerSlider;
    ToggleGroup systemSize;

    @FXML
    public RadioButton largeRadio;
    @FXML
    public RadioButton smallRadio;

    public static MenuController getInstance() {
        return ref;
    }

    private final Stage primaryStage;
    private final Scene myScene;

    public static void init(Stage stage) {
        try {
            ref = new MenuController(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuController(Stage stage) throws IOException {
        primaryStage = stage;
        // load fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        myScene = new Scene(main);
    }

    @FXML
    public void initialize() {
        systemSize = new ToggleGroup();
        smallRadio.setToggleGroup(systemSize);
        largeRadio.setToggleGroup(systemSize);

        smallRadio.setSelected(true);
    }

    public void setActive() {
        primaryStage.close();

        primaryStage.setScene(myScene);
        primaryStage.setTitle("Menu");

        primaryStage.show();
    }

    @FXML
    public void start() {
        int asteroidNum = systemSize.getSelectedToggle() == smallRadio ? 24 : 34;
        primaryStage.close();
        UIController.init((int) settlerSlider.getValue(),(int) ufoSlider.getValue(), asteroidNum);
        UIController.getInstance().newGame();
    }

    private void scrollSlider(Slider slider, ScrollEvent scrollEvent) {
        int scroll = (int) scrollEvent.getDeltaY();
        int value = (int) slider.getValue();
        int tick = (int) slider.getMajorTickUnit();

        if (scroll != 0) {
            scroll = scroll / Math.abs(scroll);
            value = value + scroll * tick;
            slider.adjustValue(value);
        }
    }

    @FXML
    public void settlerSliderScroll(ScrollEvent scrollEvent) {
        scrollSlider(settlerSlider, scrollEvent);
    }

    @FXML
    public void ufoSliderScroll(ScrollEvent scrollEvent) {
        scrollSlider(ufoSlider, scrollEvent);
    }
}
