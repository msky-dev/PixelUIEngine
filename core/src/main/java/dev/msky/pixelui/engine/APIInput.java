package dev.msky.pixelui.engine;

import com.badlogic.gdx.utils.ObjectFloatMap;
import dev.msky.pixelui.engine.constants.INPUT_METHOD;
import dev.msky.pixelui.engine.constants.KeyCode;
import dev.msky.pixelui.engine.constants.MOUSE_CONTROL_MODE;
import dev.msky.pixelui.media.MediaManager;

public final class APIInput {

    private final API api;
    private final UIEngineState uiEngineState;
    private final UICommonUtils uiCommonUtils;
    private final MediaManager mediaManager;
    private final UIEngineConfig uiEngineConfig;
    private final UIInputEvents inputEvents;

    public final APIMouse mouse;
    public final APIKeyboard keyboard;
    public final APIGamepad gamepad;

    APIInput(API api, UIEngineState uiEngineState, UICommonUtils uiCommonUtils, MediaManager mediaManager) {
        this.api = api;
        this.uiEngineState = uiEngineState;
        this.uiCommonUtils = uiCommonUtils;
        this.mediaManager = mediaManager;
        this.uiEngineConfig = uiEngineState.config;

        this.inputEvents = uiEngineState.inputEvents;
        this.mouse = new APIMouse();
        this.keyboard = new APIKeyboard();
        this.gamepad = new APIGamepad();
    }

    public INPUT_METHOD lastUsedInputMethod() {
        return inputEvents.lastUsedInputMethod;
    }

    public boolean isHoverOrInteractingWithUI() {
        if (mouse.hoverUIObject() != null) return true;
        if (interactingUIObject() != null) return true;
        return false;
    }

    public Object interactingUIObject(){
        return uiCommonUtils.ui_getInteractedUIObject();
    }

    public boolean isInteractingUIObject(Object object){
        if(object == null) return false;
        return uiCommonUtils.ui_getInteractedUIObject() == object;
    }

    public boolean isInteractingUIObjectName(String name){
        if(name == null) return false;
        return name.equals(getUIObjectName(uiCommonUtils.ui_getInteractedUIObject()));
    }


    public final class APIMouse {
        public final APIEvent event;
        public final APIState state;

        APIMouse() {
            event = new APIEvent();
            state = new APIState();
        }

        public Object hoverUIObject() {
            return uiEngineState.lastUIMouseHover;
        }

        public boolean isHoverUIObject(Object object) {
            if (object == null) return false;
            return uiEngineState.lastUIMouseHover == object;
        }

        public boolean isHoverUIObjectName(String name) {
            if (name == null) return false;
            return name.equals(getUIObjectName(uiEngineState.lastUIMouseHover));
        }

        public void setEmulatedMousePosition(int x, int y){
            uiCommonUtils.emulatedMouse_setPosition(x, y);
        }

        public MOUSE_CONTROL_MODE currentControlMode() {
            return uiEngineState.currentControlMode;
        }

        public final class APIEvent {
            /* ---- MOUSE EVENTS --- */
            public boolean buttonDown() {
                return inputEvents.mouseDown;
            }

            public boolean doubleClick() {
                return inputEvents.mouseDoubleClick;
            }

            public boolean buttonUp() {
                return inputEvents.mouseUp;
            }

            public boolean dragged() {
                return inputEvents.mouseDragged;
            }

            public boolean moved() {
                return inputEvents.mouseMoved;
            }

            public boolean scrolled() {
                return inputEvents.mouseScrolled;
            }

            public float scrolledAmount() {
                return inputEvents.mouseScrolledAmount;
            }

            public boolean buttonUpHasNext() {
                return inputEvents.mouseUpButtonIndex < inputEvents.mouseUpButtons.size;
            }

            public int buttonUpNext() {
                return buttonUpHasNext() ? inputEvents.mouseUpButtons.get(inputEvents.mouseUpButtonIndex++) : KeyCode.NONE;
            }

            public boolean buttonDownHasNext() {
                return inputEvents.mouseDownButtonIndex < inputEvents.mouseDownButtons.size;
            }

            public int buttonDownNext() {
                return buttonDownHasNext() ? inputEvents.mouseDownButtons.get(inputEvents.mouseDownButtonIndex++) : KeyCode.NONE;
            }
        }

        public final class APIState {
            public int x() {
                return uiEngineState.mouseApp.x;
            }

            public int y() {
                return uiEngineState.mouseApp.y;
            }

            public int xUI() {
                return uiEngineState.mouseUI.x;
            }

            public int yUI() {
                return uiEngineState.mouseUI.y;
            }

            public int xDelta() {
                return uiEngineState.mouseDelta.x;
            }

            public int yDelta() {
                return uiEngineState.mouseDelta.y;
            }

            public boolean isButtonUp(int keyCode) {
                return !inputEvents.mouseButtonsDown[keyCode];
            }

            public boolean isAnyButtonUp(int[] keyCodes) {
                for (int i = 0; i < keyCodes.length; i++) {
                    if (isButtonUp(keyCodes[i])) return true;
                }
                return false;
            }

            public boolean isButtonDown(int button) {
                return inputEvents.mouseButtonsDown[button];
            }

            public boolean isAnyButtonDown(int[] keyCodes) {
                for (int i = 0; i < keyCodes.length; i++) {
                    if (isButtonDown(keyCodes[i])) return true;
                }
                return false;
            }
        }

    }

    public final class APIKeyboard {

        public final APIEvent event;
        public final APIState state;

        APIKeyboard() {
            this.event = new APIEvent();
            this.state = new APIState();
        }

        public final class APIEvent {
            public boolean keyDown() {
                return inputEvents.keyDown;
            }

            public boolean keyUp() {
                return inputEvents.keyUp;
            }

            public boolean keyTyped() {
                return inputEvents.keyTyped;
            }

            public boolean keyTypedCharacter(Character character) {
                for (int i = 0; i < inputEvents.keyTypedCharacters.size; i++)
                    if (character == inputEvents.keyTypedCharacters.get(i)) return true;
                return false;
            }

            public boolean keyUpHasNext() {
                return inputEvents.keyUpKeyCodeIndex < inputEvents.keyUpKeyCodes.size;
            }

            public int keyUpNext() {
                return keyUpHasNext() ? inputEvents.keyUpKeyCodes.get(inputEvents.keyUpKeyCodeIndex++) : KeyCode.NONE;
            }

            public boolean keyDownHasNext() {
                return inputEvents.keyDownKeyCodeIndex < inputEvents.keyDownKeyCodes.size;
            }

            public int keyDownNext() {
                return keyDownHasNext() ? inputEvents.keyDownKeyCodes.get(inputEvents.keyDownKeyCodeIndex++) : KeyCode.NONE;
            }

            public boolean keyTypedHasNext() {
                return inputEvents.keyTypedCharacterIndex < inputEvents.keyTypedCharacters.size;
            }

            public char keyTypedNext() {
                return (char) (keyTypedHasNext() ? inputEvents.keyTypedCharacters.get(inputEvents.keyTypedCharacterIndex++) : -1);
            }
        }

        public final class APIState {
            public boolean isKeyDown(int keyCode) {
                return inputEvents.keysDown[keyCode];
            }

            public boolean isAnyKeyDown(int[] keyCodes) {
                for (int i = 0; i < keyCodes.length; i++) {
                    if (isKeyDown(keyCodes[i])) return true;
                }
                return false;
            }

            public boolean isKeyUp(int keyCode) {
                return !inputEvents.keysDown[keyCode];
            }

            public boolean isAnyKeyUp(int[] keyCodes) {
                for (int i = 0; i < keyCodes.length; i++) {
                    if (isKeyUp(keyCodes[i])) return true;
                }
                return false;
            }
        }

        private String keyboardUIObjectName(Object keyBoardobject) {
            if (keyBoardobject != null) {
                if (keyBoardobject instanceof Component component) {
                    return component.name;
                } else if (keyBoardobject instanceof HotKey hotKey) {
                    return hotKey.name;
                }
            }
            return "";
        }

    }

    public final class APIGamepad {

        public final APIEvent event;
        public final APIState state;

        APIGamepad() {
            this.event = new APIEvent();
            this.state = new APIState();
        }

        public final class APIEvent {
            public boolean buttonDown() {
                return inputEvents.gamePadButtonDown;
            }

            public boolean buttonUp() {
                return inputEvents.gamePadButtonUp;
            }

            public boolean connected() {
                return inputEvents.gamePadConnected;
            }

            public boolean disconnected() {
                return inputEvents.gamePadDisconnected;
            }

            public boolean leftXMoved() {
                return inputEvents.gamePadLeftXMoved;
            }

            public boolean leftYMoved() {
                return inputEvents.gamePadLeftYMoved;
            }

            public boolean leftMoved() {
                return leftXMoved() || leftYMoved();
            }

            public boolean rightXMoved() {
                return inputEvents.gamePadRightXMoved;
            }

            public boolean rightYMoved() {
                return inputEvents.gamePadRightYMoved;
            }

            public boolean rightMoved() {
                return rightXMoved() || rightYMoved();
            }

            public boolean leftTriggerMoved() {
                return inputEvents.gamePadLeftTriggerMoved;
            }

            public boolean rightTriggerMoved() {
                return inputEvents.gamePadRightTriggerMoved;
            }

            public boolean buttonDownHasNext() {
                return inputEvents.gamePadButtonDownIndex < inputEvents.gamePadButtonDownKeyCodes.size;
            }

            public int buttonDownNext() {
                return buttonDownHasNext() ? inputEvents.gamePadButtonDownKeyCodes.get(inputEvents.gamePadButtonDownIndex++) : KeyCode.NONE;
            }

            public boolean buttonUpHasNext() {
                return inputEvents.gamePadButtonUpIndex < inputEvents.gamePadButtonUpKeyCodes.size;
            }

            public int buttonUpNext() {
                return buttonUpHasNext() ? inputEvents.gamePadButtonUpKeyCodes.get(inputEvents.gamePadButtonUpIndex++) : KeyCode.NONE;
            }
        }

        public final class APIState {
            public boolean isButtonDown(int keyCode) {
                return inputEvents.gamePadButtonsDown[keyCode];
            }

            public boolean isAnyButtonDown(int[] keyCodes) {
                for (int i = 0; i < keyCodes.length; i++) {
                    if (isButtonDown(keyCodes[i])) return true;
                }
                return false;
            }

            public boolean isButtonUp(int keyCode) {
                return !inputEvents.gamePadButtonsDown[keyCode];
            }

            public boolean isAnyButtonUp(int[] keyCodes) {
                for (int i = 0; i < keyCodes.length; i++) {
                    if (isButtonUp(keyCodes[i])) return true;
                }
                return false;
            }

            public float leftTrigger() {
                return inputEvents.gamePadLeftTrigger;
            }

            public float rightTrigger() {
                return inputEvents.gamePadRightTrigger;
            }

            public float leftX() {
                return inputEvents.gamePadLeftX;
            }

            public float leftY() {
                return inputEvents.gamePadLeftY;
            }

            public float rightX() {
                return inputEvents.gamePadRightX;
            }

            public float rightY() {
                return inputEvents.gamePadRightY;
            }
        }

    }

    private String getUIObjectName(Object uiObject) {
        if (uiObject != null) {
            if (uiObject instanceof Component component) {
                return component.name;
            } else if (uiObject instanceof Window window) {
                return window.name;
            }
        }
        return "";
    }


}
