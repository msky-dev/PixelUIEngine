package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;
import dev.msky.pixelui.engine.actions.MouseTextInputAction;
import dev.msky.pixelui.media.MediaManager;

import java.util.ArrayList;

public final class APIMouseTextInput {
    private final API api;
    private final UIEngineState uiEngineState;
    private final UICommonUtils uiCommonUtils;
    private final MediaManager mediaManager;
    private final UIEngineConfig uiEngineConfig;


    APIMouseTextInput(API api, UIEngineState uiEngineState, UICommonUtils uiCommonUtils, MediaManager mediaManager) {
        this.api = api;
        this.uiEngineState = uiEngineState;
        this.uiCommonUtils = uiCommonUtils;
        this.mediaManager = mediaManager;
        this.uiEngineConfig = uiEngineState.config;
    }

    public final MouseTextInputAction DEFAULT_MOUSE_TEXT_INPUT_ACTION = new MouseTextInputAction() {
    };

    public void open(int x, int y) {
        this.open(x, y, DEFAULT_MOUSE_TEXT_INPUT_ACTION,
                null,
                uiEngineConfig.mouseTextInput.defaultLowerCaseCharacters,
                uiEngineConfig.mouseTextInput.defaultUpperCaseCharacters);
    }

    public void open(int x, int y, MouseTextInputAction mouseTextInputAction) {
        this.open(x, y, mouseTextInputAction,
                null,
                uiEngineConfig.mouseTextInput.defaultLowerCaseCharacters,
                uiEngineConfig.mouseTextInput.defaultUpperCaseCharacters);
    }

    public void open(int x, int y, MouseTextInputAction onConfirm, Character selectedCharacter) {
        this.open(x, y, onConfirm,
                selectedCharacter,
                uiEngineConfig.mouseTextInput.defaultLowerCaseCharacters,
                uiEngineConfig.mouseTextInput.defaultUpperCaseCharacters
        );
    }

    public void open(int x, int y, MouseTextInputAction mouseTextInputAction, Character selectedCharacter, char[] charactersLC, char[] charactersUC) {
        if(uiEngineState.openMouseTextInput != null)
            return;
        charactersLC = charactersLC != null ? charactersLC : new char[]{};
        charactersUC = charactersUC != null ? charactersUC : new char[]{};

        MouseTextInput mouseTextInput = new MouseTextInput();
        mouseTextInput.color = new Color(uiEngineConfig.mouseTextInput.defaultColor);
        mouseTextInput.color2 = new Color(uiEngineConfig.mouseTextInput.defaultColor).mul(0.5f);
        mouseTextInput.fontColor = uiEngineConfig.ui.fontDefaultColor.cpy();
        mouseTextInput.x = x - 6;
        mouseTextInput.y = y - 12;
        mouseTextInput.mouseTextInputAction = mouseTextInputAction != null ? mouseTextInputAction : DEFAULT_MOUSE_TEXT_INPUT_ACTION;
        mouseTextInput.upperCase = false;
        mouseTextInput.selectedIndex = 0;
        mouseTextInput.enterCharacterQueue = new IntArray();
        int maxCharacters = Math.min(charactersLC.length, charactersUC.length);
        mouseTextInput.charactersLC = new char[maxCharacters + 3];
        mouseTextInput.charactersUC = new char[maxCharacters + 3];
        for (int i = 0; i < maxCharacters; i++) {
            mouseTextInput.charactersLC[i] = charactersLC[i];
            mouseTextInput.charactersUC[i] = charactersUC[i];
            if (selectedCharacter != null && (mouseTextInput.charactersLC[i] == selectedCharacter || mouseTextInput.charactersUC[i] == selectedCharacter)) {
                mouseTextInput.selectedIndex = i;
                mouseTextInput.upperCase = mouseTextInput.charactersUC[i] == selectedCharacter;
            }
        }
        mouseTextInput.charactersLC[maxCharacters] = mouseTextInput.charactersUC[maxCharacters] = UIEngine.M_TEXTINPUT_CHAR_CHANGE_CASE;
        mouseTextInput.charactersLC[maxCharacters + 1] = mouseTextInput.charactersUC[maxCharacters + 1] = UIEngine.M_TEXTINPUT_CHAR_BACK_;
        mouseTextInput.charactersLC[maxCharacters + 2] = mouseTextInput.charactersUC[maxCharacters + 2] = UIEngine.M_TEXTINPUT_CHAR_ACCEPT;

        uiCommonUtils.mouseTextInput_open(mouseTextInput);
    }

    public void close() {
        if(uiEngineState.openMouseTextInput != null)
            return;
        uiCommonUtils.mouseTextInput_close(uiEngineState);
    }

    public boolean isOpen() {
        return uiCommonUtils.mouseTextInput_isOpen();
    }

    public MouseTextInput mouseTextInput() {
        return uiEngineState.openMouseTextInput;
    }

    public boolean isUpperCase() {
        if (uiEngineState.openMouseTextInput == null) return false;
        return uiEngineState.openMouseTextInput.upperCase;
    }

    public void enterChangeCase() {
        enterChangeCase(!uiEngineState.openMouseTextInput.upperCase);
    }

    public void enterChangeCase(boolean upperCase) {
        if (uiEngineState.openMouseTextInput.upperCase != upperCase) {
            enterCharacter('\t');
        }
    }

    public void enterDelete() {
        enterCharacter('\b');
    }

    public void enterConfirm() {
        enterCharacter('\n');
    }

    public void enterCharacters(String text) {
        if (uiEngineState.openMouseTextInput == null) return;
        for (int i = 0; i < text.length(); i++)
            uiEngineState.openMouseTextInput.enterCharacterQueue.add(text.charAt(i));
    }

    public void enterCharacter(char character) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiEngineState.openMouseTextInput.enterCharacterQueue.add(character);
    }

    public void selectCharacter( char character) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiCommonUtils.mouseTextInput_selectCharacter(uiEngineState.openMouseTextInput, character);
    }

    public void selectIndex( int index) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiCommonUtils.mouseTextInput_selectIndex(uiEngineState.openMouseTextInput, index);
    }

    public void setCharacters( char[] charactersLC, char[] charactersUC) {
        if (uiEngineState.openMouseTextInput == null) return;
        charactersLC = charactersLC != null ? charactersLC : new char[]{};
        charactersUC = charactersUC != null ? charactersUC : new char[]{};
        uiCommonUtils.mouseTextInput_setCharacters(uiEngineState.openMouseTextInput, charactersLC, charactersUC);
    }

    public void setAlpha( float alpha) {
        if (uiEngineState.openMouseTextInput == null) return;
        Color color = uiEngineState.openMouseTextInput.color;
        uiEngineState.openMouseTextInput.color.set(color.r, color.g, color.b, alpha);
    }

    public void setColor( Color color) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiEngineState.openMouseTextInput.color.set(color);
    }

    public void setColor2( Color color2) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiEngineState.openMouseTextInput.color2.set(color2);
    }

    public void setPosition( int x, int y) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiEngineState.openMouseTextInput.x = x - 6;
        uiEngineState.openMouseTextInput.y = y - 12;
    }

    public void setMouseTextInputAction( MouseTextInputAction mouseTextInputAction) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiEngineState.openMouseTextInput.mouseTextInputAction = mouseTextInputAction != null ? mouseTextInputAction : DEFAULT_MOUSE_TEXT_INPUT_ACTION;
    }

    public void setFontColor(Color color) {
        if (uiEngineState.openMouseTextInput == null) return;
        uiEngineState.openMouseTextInput.fontColor.set(color);
    }

    public void openForTextField(TextField textfield) {
        if(uiEngineState.openMouseTextInput != null) return;
        ArrayList<Character> filteredLower = new ArrayList<>();
        ArrayList<Character> filteredUpper = new ArrayList<>();

        char[] defaultLower = api.config.mouseTextInput.defaultLowerCaseCharacters;
        char[] defaultUpper = api.config.mouseTextInput.defaultUpperCaseCharacters;

        int maxLen = Math.max(defaultLower.length, defaultUpper.length);
        for (int i = 0; i < maxLen; i++) {
            char lower = i < defaultLower.length ? defaultLower[i] : 0;
            char upper = i < defaultUpper.length ? defaultUpper[i] : 0;

            boolean lowerAllowed = lower != 0 && textfield.allowedCharacters.contains(lower);
            boolean upperAllowed = upper != 0 && textfield.allowedCharacters.contains(upper);

            if (lowerAllowed && upperAllowed) {
                filteredLower.add(lower);
                filteredUpper.add(upper);
            } else if (lowerAllowed) {
                filteredLower.add(lower);
                filteredUpper.add(lower);
            } else if (upperAllowed) {
                filteredLower.add(upper);
                filteredUpper.add(upper);
            }
        }

        char[] allowedLowerCaseCharacters = new char[filteredLower.size()];
        char[] allowedUpperCaseCharacters = new char[filteredUpper.size()];

        for (int i = 0; i < filteredLower.size(); i++) {
            allowedLowerCaseCharacters[i] = filteredLower.get(i);
            allowedUpperCaseCharacters[i] = filteredUpper.get(i);
        }

        int xOffset = (uiEngineState.theme.ts.abs(textfield.width) / 2)+6;
        api.mouseTextInput.open(api.component.absoluteX(textfield) + xOffset, api.component.absoluteY(textfield), new MouseTextInputAction() {
            @Override
            public boolean onConfirm() {
                api.component.textfield.unFocus(textfield);
                textfield.textFieldAction.onEnter(textfield.content, textfield.contentValid);
                return true;
            }
        }, null, allowedLowerCaseCharacters, allowedUpperCaseCharacters);
    }


}