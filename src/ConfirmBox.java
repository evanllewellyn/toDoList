import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by evanllewellyn on 8/14/15. ConfirmBox creates the pop-up window that
 * appears when clicking the save or clear buttons on the main GUI.
 */
public class ConfirmBox {

    static boolean result;

    public static boolean confirm(String title, String text) {


        //Setting up stage, configuring modality so it must be closed before proceeding.
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setHeight(125);
        stage.setWidth(200);

        //Setting up the label
        Label label = new Label(text);
        label.setTextAlignment(TextAlignment.CENTER);

        //Setting up VBox
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(40);

        //Setting up HBox
        HBox hbox = new HBox();
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);

        //Creating and handling buttons and corresponding values.
        Button yes = new Button("Yes");
        yes.setOnAction(event -> {
            result = true;
            stage.close();
        });

        Button no = new Button("No");
        no.setOnAction(event -> {
            result = false;
            stage.close();
        });

        //Adding all buttons/labels to the HBox/VBox
        hbox.getChildren().addAll(yes, no);
        vbox.getChildren().addAll(label, hbox);

        //Adding CSS design to the scene and adding to stage.
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("ConfirmBox.css");
        stage.setScene(scene);
        stage.showAndWait();

        return result;
    }

}
