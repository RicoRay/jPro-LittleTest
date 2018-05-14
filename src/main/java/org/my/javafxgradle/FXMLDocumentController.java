/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.javafxgradle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.*;

/**
 *
 * @author Titi
 */
public class FXMLDocumentController implements Initializable {

    Image imMessage = new Image("/img/message.png");

    @FXML
    private Label lStatus;

    @FXML
    private TextField tfMessage;

    @FXML
    private TextField tfUser;

    @FXML
    private JFXSpinner jfxSpinner;

    @FXML
    private JFXListView<String> jfxListView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        jfxListView.setCellFactory(param -> new ListCell<String>() {
            ImageView img = new ImageView();

            @Override
            public void updateItem(String msg, boolean empty) {
                super.updateItem(msg, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(msg);
                    img.setImage(imMessage);
                    setGraphic(img);
                }
            }
        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //URL filename = getClass().getResource("voice_ringing.wav");                        
        jfxSpinner.setVisible(true);
        lStatus.setText("Maj en cours...");
        //PlayWaveFile.playwav("F:\\jeux\\Steam\\resource\\voice_dialing.wav");                
        Thread monThread = new Thread(() -> {

            Data.insertMessage(tfUser.getText(), tfMessage.getText());

            // Select de la table tb_Message
            ObservableList<String> monOL = Data.getMessage("");

            Platform.runLater(
                    () -> {
                        // maj de la jfxListView
                        jfxListView.setItems(monOL);
                        jfxSpinner.setVisible(false);
                        lStatus.setText("");
                    }
            );

        });
        monThread.start();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Update UI here.
            }
        });
    }
}
