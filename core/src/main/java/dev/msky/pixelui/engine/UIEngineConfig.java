package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import dev.msky.pixelui.engine.constants.KeyCode;
import dev.msky.pixelui.media.CMediaFont;
import dev.msky.pixelui.media.CMediaSprite;
import dev.msky.pixelui.theme.UIEngineTheme;

public final class UIEngineConfig {

    private static final Color DEFAULT_COlOR = Color.valueOf("CECECE");
    private static final Color DEFAULT_COlOR_BRIGHT = Color.valueOf("FFFFFF");
    private static final Color DEFAULT_COLOR_FONT = Color.valueOf("000000");

    public static final int GAMEPAD_MOUSE_BUTTONS_COUNT = 6;

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

        public boolean gamePadMouseEnabled;
        public float gamePadMouseJoystickDeadZone;
        public float gamePadMouseJoystickMaxSpeed;
        public float gamePadMouseJoystickResponse;
        public float gamePadMouseJoystickSmoothing;
        public boolean gamePadMouseStickLeftEnabled;
        public boolean gamePadMouseStickRightEnabled;
        public int[] gamePadMouseButtonsMouse1;
        public int[] gamePadMouseButtonsMouse2;
        public int[] gamePadMouseButtonsMouse3;
        public int[] gamePadMouseButtonsMouse4;
        public int[] gamePadMouseButtonsMouse5;
        public int[] gamePadMouseButtonsScrollUp;
        public int[] gamePadMouseButtonsScrollDown;

        public InputConfig(UIEngineTheme theme) {
            this.hardwareMouseEnabled = true;
            this.gamePadMouseEnabled = false;
            this.gamePadMouseJoystickMaxSpeed = 8f;
            this.gamePadMouseJoystickDeadZone = 0.15f;
            this.gamePadMouseJoystickResponse = 1.2f;
            this.gamePadMouseJoystickSmoothing = 0.3f;
            this.gamePadMouseStickLeftEnabled = true;
            this.gamePadMouseStickRightEnabled = true;
            this.gamePadMouseButtonsMouse1 = new int[]{KeyCode.GamePad.A};
            this.gamePadMouseButtonsMouse2 = new int[]{KeyCode.GamePad.B};
            this.gamePadMouseButtonsMouse3 = null;
            this.gamePadMouseButtonsMouse4 = null;
            this.gamePadMouseButtonsMouse5 = null;
            this.gamePadMouseButtonsScrollUp = null;
            this.gamePadMouseButtonsScrollDown = null;
        }

        public int[] gamepadMouseButtons(int index) {
            index = Math.clamp(index, 0, GAMEPAD_MOUSE_BUTTONS_COUNT);
            return switch (index) {
                case 0 -> this.gamePadMouseButtonsMouse1;
                case 1 -> this.gamePadMouseButtonsMouse2;
                case 2 -> this.gamePadMouseButtonsMouse3;
                case 3 -> this.gamePadMouseButtonsMouse4;
                case 4 -> this.gamePadMouseButtonsMouse5;
                case 5 -> this.gamePadMouseButtonsScrollUp;
                case 6 -> this.gamePadMouseButtonsScrollDown;
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

        public TextRenderHook textRenderHook = new TextRenderHook() {
        };

        public AnimationTimerHook animationTimerHook = new AnimationTimerHook() {
        };
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
        public int appViewportDefaultUpdateTimeMS;
        public float listDragAlpha;
        public float gridDragAlpha;
        public float knobSensitivity;
        public float scrollbarSensitivity;
        public char[] textFieldDefaultAllowedCharacters;
        public Color textFieldDefaultMarkerColor;

        public ComponentConfig(UIEngineTheme theme) {
            this.defaultColor = DEFAULT_COlOR.cpy();
            this.contextMenuDefaultColor = DEFAULT_COlOR_BRIGHT.cpy();
            this.appViewportDefaultUpdateTimeMS = 0;
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
        public int defaultDisplayTimeMS;
        public Color defaultColor;
        public int foldTimeMS;
        public int toolTipNotificationDefaultDisplayTimeMS;
        public int toolTipNotificationFadeoutTimeMS;

        public Notification(UIEngineTheme theme) {
            this.maxNotifications = 5;
            this.defaultDisplayTimeMS = 2000;
            this.defaultColor = DEFAULT_COlOR.cpy();
            this.foldTimeMS = 12;
            this.toolTipNotificationDefaultDisplayTimeMS = 2200;
            this.toolTipNotificationFadeoutTimeMS = 200;

        }

    }

    public class TooltipConfig {
        public Color defaultCellColor;
        public long fadeInTimeMS;
        public long fadeInDelayMS;
        public long fadeOutTimeMS;

        public TooltipConfig(UIEngineTheme theme) {
            this.defaultCellColor = DEFAULT_COlOR_BRIGHT.cpy();
            this.fadeInTimeMS = 100;
            this.fadeInDelayMS = 50;
            this.fadeOutTimeMS = 100;
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

}