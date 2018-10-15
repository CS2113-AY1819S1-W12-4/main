package seedu.address.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import seedu.address.authentication.LoginUtils;
import seedu.address.model.LoginInfoManager;
import seedu.address.ui.Ui;



/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {

    protected static Ui ui;
    protected static LoginInfoManager loginInfoManager;
    private String username;
    private String password;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private javafx.scene.control.Label loginError;


    /**
     * handle when user press enter on login textfield or passwordField
     * @param key the key enter by user
     */
    @FXML
    public void handleEnterPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            verifyLoginInfo ();
        }
    }
    /**
     * handle event when Login is clicked
     * @param clicked mouse event clicked
     */
    @FXML
    public void handleLoginButtonClick(ActionEvent clicked) {
        verifyLoginInfo ();
    }

    /**
     * Check for Login information such as username and password.
     *
     * @throws Exception
     */
    public void verifyLoginInfo () {
        getUsername();
        getPassword ();
        LoginUtils loginUtils = new LoginUtils (username, password, loginInfoManager);
        if (!loginUtils.isUsernameValid ()) {
            loginError.setText("Please enter username");
            return;
        }
        if (!loginUtils.isPasswordValid ()) {
            loginError.setText("Please enter password");
            return;
        }
        if (loginUtils.passwordAndUserNameCheck()) {
            changeStageToMainUi();
        } else {
            loginError.setText("wrong username or password");
        }
    }

    /**
     * Close window
     *
     * @param e
     */
    @FXML
    private void handleClose(MouseEvent e) {
        Stage stageTheLabelBelongs = (Stage) passwordField.getScene().getWindow();
        stageTheLabelBelongs.close();
    }

    /**
     * gets the user name from textfield.
     */
    private void getUsername() {
        username = usernameField.getText();
        username = username.trim ();
    }

    /**
     * gets the password from passwordField.
     */
    private void getPassword() {
        password = passwordField.getText();
        password = password.trim ();
    }

    /**
     *change the stage to main UI
     */
    private void changeStageToMainUi() {
        Stage primaryStage = (Stage) passwordField.getScene().getWindow();
        primaryStage.close();
        ui.start(primaryStage);
    }

    /**
     * pass in LoginInfo list
     * @param loginInfoManager
     */
    public void getLoginInfoList (LoginInfoManager loginInfoManager) {
        this.loginInfoManager = loginInfoManager;
    }

    /**
     * pass in the main ui
     * @param ui
     */
    public void mainWindowInterface (Ui ui) {
        if (ui == null) {
            System.out.println ("ui is null");
        }
        this.ui = ui;
    }

}
