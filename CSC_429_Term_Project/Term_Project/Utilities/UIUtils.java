package Utilities;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public class UIUtils {
    //restricts textfield so that user can only enter digits
    public static void limitToDigits(TextField restrictedField) {
        restrictedField.textProperty().addListener((obs, oldVal, newVal) -> {
            int position = restrictedField.getCaretPosition();
            if (!newVal.matches("\\d*")) {
                Platform.runLater(() -> {
                    restrictedField.setText(newVal.replaceAll("[^\\d]", ""));
                    restrictedField.positionCaret(position);
                });
            }
        });
    }
    //restricts textfield so that user can only enter characters up to "length"
    public static void limitLength(TextField restrictedField, int length) {
        restrictedField.textProperty().addListener((obs, oldVal, newVal) -> {
            int position = restrictedField.getCaretPosition();
            if (newVal.length() > length) {
                Platform.runLater(() -> {
                    restrictedField.setText(oldVal);
                    restrictedField.positionCaret(position);
                });
            }
        });
    }
}
