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

/**
 * The menu controller
 */
public class MenuController {
    /**
     * reference to the controller
     */
    private static MenuController ref;

    /**
     * Slider for sets the ufo num
     */
    @FXML
    public Slider ufoSlider;
    /**
     * Slider for sets the settler num
     */
    @FXML
    public Slider settlerSlider;
    ToggleGroup systemSize;

    /**
     * button for the large map
     */
    @FXML
    public RadioButton largeRadio;

    /**
     * button for the samll map
     */
    @FXML
    public RadioButton smallRadio;

    /**
     * set the controller reference
     * @return
     */
    public static MenuController getInstance() {
        return ref;
    }

    /**
     * The primary stage for the scene
     */
    private final Stage primaryStage;
    /**
     * the menu scene
     */
    private final Scene myScene;

    /**
     * initialize the mai stage
     * @param stage
     */
    public static void init(Stage stage) {
        try {
            ref = new MenuController(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for set up the menu in the given stage
     * @param stage
     * @throws IOException
     */
    private MenuController(Stage stage) throws IOException {
        primaryStage = stage;
        // load fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        loader.setControllerFactory(c -> this);

        Parent main = loader.load();
        myScene = new Scene(main);
    }

    /**
     * Set up the fxml components
     */
    @FXML
    public void initialize() {
        systemSize = new ToggleGroup();
        smallRadio.setToggleGroup(systemSize);
        largeRadio.setToggleGroup(systemSize);

        smallRadio.setSelected(true);
    }

    /**
     * Set active the scene
     */
    public void setActive() {
        primaryStage.close();

        primaryStage.setScene(myScene);
        primaryStage.setTitle("Menu");

        primaryStage.show();
    }

    /**
     * Make a new game
     */
    @FXML
    public void start() {
        int asteroidNum = systemSize.getSelectedToggle() == smallRadio ? 24 : 34;
        primaryStage.close();
        UIController.init((int) settlerSlider.getValue(),(int) ufoSlider.getValue(), asteroidNum);
        UIController.getInstance().newGame();
    }

    /**
     * Event for the sliders if they were scrolled
     * @param slider
     * @param scrollEvent
     */
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

    /**
     * Event for the settler scrollbar to set the settler num
     * @param scrollEvent
     */
    @FXML
    public void settlerSliderScroll(ScrollEvent scrollEvent) {
        scrollSlider(settlerSlider, scrollEvent);
    }

    /**
     * Event for the ufo scrollbar to set the ufo num
     * @param scrollEvent
     */
    @FXML
    public void ufoSliderScroll(ScrollEvent scrollEvent) {
        scrollSlider(ufoSlider, scrollEvent);
    }
}
