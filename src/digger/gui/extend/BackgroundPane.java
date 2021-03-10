package digger.gui.extend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BackgroundPane extends Pane {
	

    final ImageView bgImg;

    public BackgroundPane(String img) {

    	bgImg = new ImageView(new Image(getClass().getResourceAsStream(img)));

    	bgImg.setFitWidth(800);
    	bgImg.setFitHeight(600);

        this.getChildren().add(bgImg);
    }
}
