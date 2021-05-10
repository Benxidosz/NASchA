javac -d build src\Graphics\*.java src\Graphics\controller\*.java src\Graphics\controller\controllers\*.java src\Graphics\material\*.java src\Graphics\material\materials\*.java src\Graphics\observable\*.java src\Graphics\observable\thing\*.java src\Graphics\observable\thing\things\*.java src\Graphics\observable\entity\*.java src\Graphics\observable\entity\entities\*.java src\Graphics\simulator\*.java src\Graphics\ui\game\*.java src\Graphics\ui\game\drawable\*.java src\Graphics\ui\game\drawable\drawables\*.java src\Graphics\ui\game\views\asteroidView\*.java src\Graphics\ui\game\views\boardView\*.java src\Graphics\ui\game\views\messegeBox\*.java src\Graphics\ui\menu\*.java --module-path javafx-sdk-windows\lib --add-modules javafx.controls,javafx.fxml -Xlint:unchecked

mkdir build\sprites
copy src\sprites build\sprites
copy src\Graphics\ui\menu\menu.fxml build\Graphics\ui\menu\menu.fxml
copy src\Graphics\ui\game\views\asteroidView\asteroidView.fxml build\Graphics\ui\game\views\asteroidView\asteroidView.fxml
copy src\Graphics\ui\game\views\boardView\boardView.fxml build\Graphics\ui\game\views\boardView\boardView.fxml
copy src\Graphics\ui\game\views\messegeBox\gameMassage.fxml build\Graphics\ui\game\views\messegeBox\gameMassage.fxml
copy src\basicStyle.css build\basicStyle.css

java -cp build --module-path javafx-sdk-windows\lib --add-modules javafx.controls,javafx.fxml Graphics.App

