package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import dev.msky.pixelui.engine.constants.KeyCode;
import dev.msky.pixelui.media.CMediaFont;
import dev.msky.pixelui.media.CMediaSprite;
import dev.msky.pixelui.rendering.SpriteRenderer;
import dev.msky.pixelui.theme.UIEngineTheme;

public final class UIEngineConfig {

    private static final Color DEFAULT_COlOR = Color.valueOf("CECECE");
    private static final Color DEFAULT_COlOR_BRIGHT = Color.valueOf("FFFFFF");
    private static final Color DEFAULT_COLOR_FONT = Color.valueOf("000000");

    public static final int GAMEPAD_MOUSE_BUTTONS = 7;
    public static final int KEYBOARD_MOUSE_BUTTONS = 11;

    public final UIConfig ui;
    public final InputConfig input;
    public final WindowConfig window;
    public final ComponentConfig component;
    public final Notification notification;
    public final TooltipConfig tooltip;
    public final MouseTextInputConfig mouseTextInput;

    public UIEngineConfig(UIEngineTheme theme) {
        this.ui = new UIConfig(theme);
        this.input = new InputConfig(theme);
        this.window = new WindowConfig(theme);
        this.component = new ComponentConfig(theme);
        this.notification = new Notification(theme);
        this.tooltip = new TooltipConfig(theme);
        this.mouseTextInput = new MouseTextInputConfig(theme);
    }

    public class InputConfig {
        public boolean hardwareMouseEnabled;

        public boolean keyboardMouseEnabled;
        public float keyboardMouseCursorSpeed;
        public float keyboardMouseCursorSpeedUpFactor;
        public float keyboardMouseCursorSmoothing;
        public int[] keyboardMouseButtonsUp;
        public int[] keyboardMouseButtonsDown;
        public int[] keyboardMouseButtonsLeft;
        public int[] keyboardMouseButtonsRight;
        public int[] keyboardMouseButtonsMouse1;
        public int[] keyboardMouseButtonsMouse2;
        public int[] keyboardMouseButtonsMouse3;
        public int[] keyboardMouseButtonsMouse4;
        public int[] keyboardMouseButtonsMouse5;
        public int[] keyboardMouseButtonsScrollUp;
        public int[] keyboardMouseButtonsScrollDown;
        public int[] keyboardMouseButtonsCursorSpeedUp;

        public boolean gamePadMouseEnabled;
        public float gamepadMouseCursorSpeed;
        public float gamepadMouseCursorSpeedUpFactor;
        public float gamePadMouseJoystickDeadZone;
        public boolean gamePadMouseStickLeftEnabled;
        public boolean gamePadMouseStickRightEnabled;
        public int[] gamePadMouseButtonsMouse1;
        public int[] gamePadMouseButtonsMouse2;
        public int[] gamePadMouseButtonsMouse3;
        public int[] gamePadMouseButtonsMouse4;
        public int[] gamePadMouseButtonsMouse5;
        public int[] gamePadMouseButtonsScrollUp;
        public int[] gamePadMouseButtonsScrollDown;
        public int[] gamePadMouseButtonsCursorSpeedUp;

        public InputConfig(UIEngineTheme theme) {

            this.hardwareMouseEnabled = true;
            this.keyboardMouseEnabled = false;
            this.keyboardMouseCursorSpeed = 3.0f;
            this.keyboardMouseCursorSpeedUpFactor = 2.0f;
            this.keyboardMouseCursorSmoothing = 0.25f;
            this.keyboardMouseButtonsUp = new int[]{KeyCode.Key.UP};
            this.keyboardMouseButtonsDown = new int[]{KeyCode.Key.DOWN};
            this.keyboardMouseButtonsLeft = new int[]{KeyCode.Key.LEFT};
            this.keyboardMouseButtonsRight = new int[]{KeyCode.Key.RIGHT};
            this.keyboardMouseButtonsMouse1 = new int[]{KeyCode.Key.CONTROL_LEFT};
            this.keyboardMouseButtonsMouse2 = new int[]{KeyCode.Key.ALT_LEFT};
            this.keyboardMouseButtonsMouse3 = null;
            this.keyboardMouseButtonsMouse4 = null;
            this.keyboardMouseButtonsMouse5 = null;
            this.keyboardMouseButtonsScrollUp = new int[]{KeyCode.Key.PAGE_UP};
            this.keyboardMouseButtonsScrollDown = new int[]{KeyCode.Key.PAGE_DOWN};
            this.keyboardMouseButtonsCursorSpeedUp = new int[]{KeyCode.Key.SHIFT_LEFT};

            this.gamePadMouseEnabled = false;
            this.gamepadMouseCursorSpeed = 3.0f;
            this.gamepadMouseCursorSpeedUpFactor = 2.0f;
            this.gamePadMouseJoystickDeadZone = 0.3f;
            this.gamePadMouseStickLeftEnabled = true;
            this.gamePadMouseStickRightEnabled = true;
            this.gamePadMouseButtonsMouse1 = new int[]{KeyCode.GamePad.A};
            this.gamePadMouseButtonsMouse2 = new int[]{KeyCode.GamePad.B};
            this.gamePadMouseButtonsMouse3 = null;
            this.gamePadMouseButtonsMouse4 = null;
            this.gamePadMouseButtonsMouse5 = null;
            this.gamePadMouseButtonsScrollUp = new int[]{KeyCode.GamePad.DPAD_UP};
            this.gamePadMouseButtonsScrollDown = new int[]{KeyCode.GamePad.DPAD_DOWN};
            this.gamePadMouseButtonsCursorSpeedUp = new int[]{KeyCode.GamePad.X};
        }

        public int[] gamepadMouseButtons(int index) {
            index = Math.clamp(index, 0, GAMEPAD_MOUSE_BUTTONS);
            return switch (index) {
                case 0 -> this.gamePadMouseButtonsMouse1;
                case 1 -> this.gamePadMouseButtonsMouse2;
                case 2 -> this.gamePadMouseButtonsMouse3;
                case 3 -> this.gamePadMouseButtonsMouse4;
                case 4 -> this.gamePadMouseButtonsMouse5;
                case 5 -> this.gamePadMouseButtonsScrollUp;
                case 6 -> this.gamePadMouseButtonsScrollDown;
                case 7 -> this.gamePadMouseButtonsCursorSpeedUp;
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

        public int[] keyboardMouseButtons(int index) {
            index = Math.clamp(index, 0, KEYBOARD_MOUSE_BUTTONS);
            return switch (index) {
                case 0 -> this.keyboardMouseButtonsUp;
                case 1 -> this.keyboardMouseButtonsDown;
                case 2 -> this.keyboardMouseButtonsLeft;
                case 3 -> this.keyboardMouseButtonsRight;
                case 4 -> this.keyboardMouseButtonsMouse1;
                case 5 -> this.keyboardMouseButtonsMouse2;
                case 6 -> this.keyboardMouseButtonsMouse3;
                case 7 -> this.keyboardMouseButtonsMouse4;
                case 8 -> this.keyboardMouseButtonsMouse5;
                case 9 -> this.keyboardMouseButtonsScrollUp;
                case 10 -> this.keyboardMouseButtonsScrollDown;
                case 11 -> this.keyboardMouseButtonsCursorSpeedUp;
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

    }

    public class UIConfig {
        public CMediaFont font;
        public Color fontDefaultColor;
        public CMediaSprite cursor;
        public boolean keyInteractionsDisabled;
        public boolean mouseInteractionsDisabled;
        public boolean foldWindowsOnDoubleClick;

        public UIConfig(UIEngineTheme theme) {
            this.font = theme.UI_FONT;
            this.fontDefaultColor = DEFAULT_COLOR_FONT.cpy();
            this.cursor = theme.UI_CURSOR_ARROW;
            this.keyInteractionsDisabled = false;
            this.mouseInteractionsDisabled = false;
            this.foldWindowsOnDoubleClick = true;
        }

        public TextRenderHook textRenderHook = new TextRenderHook(){};

        public AnimationTimerHook animationTimerHook = new AnimationTimerHook(){};
    }

    public class WindowConfig {
        public boolean defaultEnforceScreenBounds;
        public Color defaultColor;

        public WindowConfig(UIEngineTheme theme) {
            this.defaultEnforceScreenBounds = true;
            this.defaultColor = DEFAULT_COlOR.cpy();
        }

    }

    public class ComponentConfig {
        public Color defaultColor;
        public Color contextMenuDefaultColor;
        public int appViewportDefaultUpdateTime;
        public float listDragAlpha;
        public float gridDragAlpha;
        public float knobSensitivity;
        public float scrollbarSensitivity;
        public char[] textFieldDefaultAllowedCharacters;
        public Color textFieldDefaultMarkerColor;

        public ComponentConfig(UIEngineTheme theme) {
            this.defaultColor = DEFAULT_COlOR.cpy();
            this.contextMenuDefaultColor = DEFAULT_COlOR_BRIGHT.cpy();
            this.appViewportDefaultUpdateTime = 0;
            this.listDragAlpha = 0.8f;
            this.gridDragAlpha = 0.8f;
            this.knobSensitivity = 1f;
            this.scrollbarSensitivity = 1f;
            this.textFieldDefaultAllowedCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '?', '.', '+', '-', '=', '&', '%', '*', '$', '/', ':', ';', ',', '"', '(', ')', '_', ' '};
            this.textFieldDefaultMarkerColor = Color.valueOf("8FD3FF");

        }

    }

    public class Notification {
        public int maxNotifications;
        public int defaultDisplayTime;
        public Color defaultColor;
        public int foldTime;
        public int toolTipNotificationDefaultDisplayTime;
        public int toolTipNotificationFadeoutTime;

        public Notification(UIEngineTheme theme) {
            this.maxNotifications = 20;
            this.defaultDisplayTime = 120;
            this.defaultColor = DEFAULT_COlOR.cpy();
            this.foldTime = 12;
            this.toolTipNotificationDefaultDisplayTime = 140;
            this.toolTipNotificationFadeoutTime = 12;

        }

    }

    public class TooltipConfig {
        public Color defaultCellColor;
        public float fadeInSpeed;
        public int fadeInDelay;
        public float fadeOutSpeed;

        public TooltipConfig(UIEngineTheme theme) {
            this.defaultCellColor = DEFAULT_COlOR_BRIGHT.cpy();
            this.fadeInSpeed = 0.2f;
            this.fadeInDelay = 20;
            this.fadeOutSpeed = 0.2f;
        }

    }

    public class MouseTextInputConfig {
        public char[] defaultLowerCaseCharacters;
        public char[] defaultUpperCaseCharacters;
        public Color defaultColor;
        public int charsPerRow;

        public MouseTextInputConfig(UIEngineTheme theme) {
            this.defaultLowerCaseCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            ;
            this.defaultUpperCaseCharacters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '!', '?', '.', '+', '-', '=', '&', '%', '*', '$'};
            this.defaultColor = DEFAULT_COlOR.cpy();
            this.charsPerRow = 8;
        }
    }

    public abstract class TextRenderHook {

        public CMediaFont replaceFont(Object uiObject, CMediaFont configFont){
            return configFont;
        };

        public String replaceText(Object uiObject, String text){
            return text;
        };

        public void render(Object uiObject, SpriteRenderer spriteRenderer, CMediaFont font, int x, int y, String text, int textOffset, int textLength, int maxWidth){
            spriteRenderer.drawCMediaFont(font, x, y, text, textOffset, textLength, false, false, maxWidth);
        };
    }

    public abstract class AnimationTimerHook {

        private float animationTimer;

        public void updateAnimationTimer(){
            animationTimer += 1/60f;
        };

        public float getAnimationTimer(){
            return animationTimer;
        };
    }

}