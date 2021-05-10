javac -d build src/Graphics/*.java src/Graphics/controller/*.java src/Graphics/controller/controllers/*.java src/Graphics/material/*.java src/Graphics/material/materials/*.java src/Graphics/observable/*.java src/Graphics/observable/thing/*.java src/Graphics/observable/thing/things/*.java src/Graphics/observable/entity/*.java src/Graphics/observable/entity/entities/*.java src/Graphics/simulator/*.java src/Graphics/ui/game/*.java src/Graphics/ui/game/drawable/*.java src/Graphics/ui/game/drawable/drawables/*.java src/Graphics/ui/game/views/asteroidView/*.java src/Graphics/ui/game/views/boardView/*.java src/Graphics/ui/game/views/messegeBox/*.java src/Graphics/ui/menu/*.java --module-path javafx-sdk-linux/lib --add-modules javafx.controls,javafx.fxml -Xlint:unchecked

cp -r src/sprites build
cp src/Graphics/ui/menu/*.fxml build/Graphics/ui/menu/
cp src/Graphics/ui/game/views/asteroidView/*.fxml build/Graphics/ui/game/views/asteroidView/
cp src/Graphics/ui/game/views/boardView/*.fxml build/Graphics/ui/game/views/boardView/
cp src/Graphics/ui/game/views/messegeBox/*.fxml build/Graphics/ui/game/views/messegeBox/
cp src/*.css build 

java -cp build --module-path javafx-sdk-linux/lib --add-modules javafx.controls,javafx.fxml Graphics.App
