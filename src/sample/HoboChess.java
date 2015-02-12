package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;


public class HoboChess implements Initializable {

    @FXML Label systemOut;
    @FXML GridPane gridPane;
    final static int ROWS = 3;
    final static int COLUMNS = 3;
    int[][] grid;
    boolean turn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid = new int[COLUMNS][ROWS];
        newVisualField(ROWS, COLUMNS);
    }

    public void newGame(ActionEvent action) {
        grid = new int[COLUMNS][ROWS];
        newVisualField(ROWS, COLUMNS);
    }

    public void WhoWon(int x, int[] zone){
        if (zone[x] == 3){
            systemOut.setText("Os won!");
        }else if (zone[x] == 30){
            systemOut.setText("Xs won!");
        }
    }

    public void winCheck(){
        int[] zone = new int[COLUMNS * ROWS];
        int zoneNumber = 0;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                zone[zoneNumber] = grid[y][x];
                zoneNumber++;
            }
        }

        for (int x = 0; x < 3; x++) {
            if (zone[x * 3] == zone[x * 3 + 1] && zone[x * 3] == zone[x * 3 + 2]){
                WhoWon(x * 3, zone);
            }
        }
        for (int x = 0; x < 3; x++) {
            if (zone[x] == zone[x + 3] && zone[x] == zone[x + 6]){
                WhoWon(x, zone);
            }
        }
        if (zone[0] == zone[4] && zone[0] == zone[8]){
            WhoWon(0, zone);
        }else if (zone[2] == zone[4] && zone[2] == zone[6]){
            WhoWon(2, zone);
        }
    }

    public void newVisualField(int rows, int columns){
        gridPane.getChildren().clear();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                ButtonCell button = new ButtonCell(grid[x][y], x, y, this);
                gridPane.add(button.aButton, x, y);
            }
        }
    }
}

class ButtonCell {

    public Button aButton;
    boolean buttonPushed;

    ButtonCell(final int button, final int x, final int y, final HoboChess hoboChess) {
        aButton = new Button(String.valueOf(button));
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (buttonPushed == false) {
                    if (hoboChess.turn) {
                        hoboChess.grid[y][x] = 3;
                        aButton.setText("O");
                    } else {
                        hoboChess.grid[y][x] = 30;
                        aButton.setText("X");
                    }
                    hoboChess.turn = !hoboChess.turn;
                    buttonPushed = !buttonPushed;
                    hoboChess.winCheck();
                }
            }
        });
    }
}