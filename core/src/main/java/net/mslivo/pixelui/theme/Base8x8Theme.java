package net.mslivo.pixelui.theme;

import net.mslivo.pixelui.media.*;

public class Base8x8Theme extends UIEngineTheme {
    private static final String DIR_THEME = MediaManager.DIR_GRAPHICS + "pixelui/base8x8/";

    public Base8x8Theme() {
        super(8);
        final int TL = ts.TS;
        final int TL2 = ts.TS2;
        final int TL1_2 = ts.TS_1_AND_HALF;

        UI_WINDOW = new CMediaArray(DIR_THEME + "ui/window.png",1, TL, TL);
        UI_BUTTON = new CMediaArray(DIR_THEME + "ui/button.png",1, TL, TL);
        UI_BUTTON_PRESSED = new CMediaArray(DIR_THEME + "ui/button_pressed.png",1, TL, TL);
        UI_SCROLLBAR_VERTICAL = new CMediaArray(DIR_THEME + "ui/scrollbar_vertical.png",1, TL, TL);
        UI_SCROLLBAR_HORIZONTAL = new CMediaArray(DIR_THEME + "ui/scrollbar_horizontal.png",1, TL, TL);
        UI_SCROLLBAR_BUTTON_VERTICAL = new CMediaArray(DIR_THEME + "ui/scrollbar_button_vertical.png",1, TL, TL);
        UI_SCROLLBAR_BUTTON_HORIZONAL = new CMediaArray(DIR_THEME + "ui/scrollbar_button_horizontal.png",1, TL, TL);
        UI_LIST = new CMediaImage(DIR_THEME + "ui/list.png",1);
        UI_LIST_CELL = new CMediaImage(DIR_THEME + "ui/list_cell.png",1);
        UI_LIST_CELL_SELECTED = new CMediaImage(DIR_THEME + "ui/list_cell_selected.png",1);
        UI_LIST_DRAG = new CMediaArray(DIR_THEME + "ui/list_drag.png",1, TL, TL2);
        UI_COMBO_BOX = new CMediaArray(DIR_THEME + "ui/combobox.png",1, TL, TL);
        UI_COMBO_BOX_TOP = new CMediaArray(DIR_THEME + "ui/combobox_top.png",1, TL, TL);
        UI_COMBO_BOX_OPEN = new CMediaArray(DIR_THEME + "ui/combobox_open.png",1, TL, TL);
        UI_COMBO_BOX_LIST = new CMediaArray(DIR_THEME + "ui/combobox_list.png",1, TL, TL);
        UI_COMBO_BOX_CELL = new CMediaArray(DIR_THEME + "ui/combobox_cell.png",1, TL, TL);
        UI_COMBO_BOX_LIST_CELL = new CMediaArray(DIR_THEME + "ui/combobox_list_cell.png",1, TL, TL);
        UI_COMBO_BOX_LIST_CELL_SELECTED = new CMediaArray(DIR_THEME + "ui/combobox_list_cell_selected.png",1, TL, TL);
        UI_TAB_BORDERS = new CMediaArray(DIR_THEME + "ui/tab_border.png",1, TL, TL);
        UI_BORDERS = new CMediaArray(DIR_THEME + "ui/border.png",1, TL, TL);
        UI_TAB = new CMediaArray(DIR_THEME + "ui/tab.png",1, TL, TL);
        UI_TAB_SELECTED = new CMediaArray(DIR_THEME + "ui/tab_selected.png",1, TL, TL);
        UI_TAB_BIGICON = new CMediaImage(DIR_THEME + "ui/tab_bigicon.png",1);
        UI_TAB_BIGICON_SELECTED = new CMediaImage(DIR_THEME + "ui/tab__bigicon_selected.png",1);
        UI_KNOB_BACKGROUND = new CMediaImage(DIR_THEME + "ui/knob_background.png",1);
        UI_KNOB = new CMediaArray(DIR_THEME + "ui/knob.png",1, TL2, TL2);
        UI_KNOB_ENDLESS = new CMediaArray(DIR_THEME + "ui/knob_endless.png",1, TL2, TL2);
        UI_SEPARATOR_HORIZONTAL = new CMediaArray(DIR_THEME + "ui/separator_horizontal.png",1, TL, TL);
        UI_SEPARATOR_VERTICAL = new CMediaArray(DIR_THEME + "ui/separator_vertical.png",1, TL, TL);
        UI_TOOLTIP_CELL = new CMediaArray(DIR_THEME + "ui/tooltip_cell.png",1, TL, TL);
        UI_TOOLTIP = new CMediaArray(DIR_THEME + "ui/tooltip.png",1, TL, TL);
        UI_TOOLTIP_TOP = new CMediaArray(DIR_THEME + "ui/tooltip_top.png",1, TL, TL);
        UI_TOOLTIP_SEGMENT_BORDER = new CMediaImage(DIR_THEME + "ui/tooltip_segment_border.png",1);
        UI_TOOLTIP_LINE_HORIZONTAL = new CMediaImage(DIR_THEME + "ui/tooltip_line_horizontal.png",1);
        UI_TOOLTIP_LINE_VERTICAL = new CMediaImage(DIR_THEME + "ui/tooltip_line_vertical.png",1);
        UI_CONTEXT_MENU = new CMediaArray(DIR_THEME + "ui/context_menu.png",1, TL, TL);
        UI_CONTEXT_MENU_TOP = new CMediaArray(DIR_THEME + "ui/context_menu_top.png",1, TL, TL);
        UI_CONTEXT_MENU_CELL = new CMediaArray(DIR_THEME + "ui/context_menu_cell.png",1, TL, TL);
        UI_CONTEXT_MENU_CELL_SELECTED = new CMediaArray(DIR_THEME + "ui/context_menu_cell_selected.png",1, TL, TL);
        UI_TEXT_FIELD = new CMediaArray(DIR_THEME + "ui/textfield.png",1, TL, TL);
        UI_TEXT_FIELD_CELL_VALIDATION = new CMediaArray(DIR_THEME + "ui/textfield_cell_validation.png",1, TL, TL);
        UI_TEXT_FIELD_CELL = new CMediaArray(DIR_THEME + "ui/textfield_cell.png",1, TL, TL);
        UI_TEXT_FIELD_CARET = new CMediaAnimation(DIR_THEME + "ui/textfield_caret.png",1, 1,TL, 0.4f);
        UI_GRID = new CMediaArray(DIR_THEME + "ui/grid.png",1, TL, TL);
        UI_GRID_DRAGGED = new CMediaArray(DIR_THEME + "ui/grid_dragged.png",1, TL, TL);
        UI_GRID_CELL = new CMediaArray(DIR_THEME + "ui/grid_cell.png",1, TL, TL);
        UI_GRID_CELL_SELECTED = new CMediaArray(DIR_THEME + "ui/grid_cell_selected.png",1, TL, TL);
        UI_GRID_X2 = new CMediaArray(DIR_THEME + "ui/grid_x2.png",1, TL2, TL2);
        UI_GRID_DRAGGED_X2 = new CMediaArray(DIR_THEME + "ui/grid_dragged_x2.png",1, TL2, TL2);
        UI_GRID_CELL_X2 = new CMediaArray(DIR_THEME + "ui/grid_cell_x2.png",1, TL2, TL2);
        UI_GRID_CELL_SELECTED_X2 = new CMediaArray(DIR_THEME + "ui/grid_cell_selected_x2.png",1, TL2, TL2);
        UI_PROGRESSBAR = new CMediaArray(DIR_THEME + "ui/progressbar.png",1, TL, TL);
        UI_PROGRESSBAR_BAR = new CMediaArray(DIR_THEME + "ui/progressbar_bar.png",1, TL, TL);
        UI_NOTIFICATION_BAR = new CMediaImage(DIR_THEME + "ui/notification_bar.png",1);
        UI_CHECKBOX_CHECKBOX = new CMediaArray(DIR_THEME + "ui/checkbox.png",1, TL, TL);
        UI_CHECKBOX_CHECKBOX_CELL = new CMediaImage(DIR_THEME + "ui/checkbox_cell.png",1);
        UI_CHECKBOX_RADIO = new CMediaArray(DIR_THEME + "ui/radio.png",1, TL, TL);
        UI_CHECKBOX_RADIO_CELL = new CMediaImage(DIR_THEME + "ui/radio_cell.png",1);
        UI_MOUSETEXTINPUT_BUTTON = new CMediaArray(DIR_THEME + "ui/mousetextinput_button.png",1, TL1_2, TL1_2);
        UI_MOUSETEXTINPUT_CONFIRM = new CMediaImage(DIR_THEME + "ui/mousetextinput_confirm.png",1);
        UI_MOUSETEXTINPUT_DELETE = new CMediaImage(DIR_THEME + "ui/mousetextinput_delete.png",1);
        UI_MOUSETEXTINPUT_LOWERCASE = new CMediaImage(DIR_THEME + "ui/mousetextinput_lowercase.png",1);
        UI_MOUSETEXTINPUT_UPPERCASE = new CMediaImage(DIR_THEME + "ui/mousetextinput_uppercase.png",1);
        UI_MOUSETEXTINPUT_SELECTED = new CMediaImage(DIR_THEME + "ui/mousetextinput_selected.png",1);

        // Cursors
        UI_CURSOR_ARROW = new CMediaImage(DIR_THEME + "cursors/arrow.png",1);

        // Icons
        UI_ICON_CLOSE = new CMediaImage(DIR_THEME + "icons/close.icon.png",1);
        UI_ICON_INFORMATION = new CMediaImage(DIR_THEME + "icons/information.icon.png",1);
        UI_ICON_QUESTION = new CMediaImage(DIR_THEME + "icons/question.icon.png",1);
        UI_ICON_EXTEND = new CMediaImage(DIR_THEME + "icons/extend.icon.png",1);
        UI_ICON_BACK = new CMediaImage(DIR_THEME + "icons/back.icon.png",1);
        UI_ICON_FORWARD = new CMediaImage(DIR_THEME + "icons/forward.icon.png",1);

        // Fonts
        UI_FONT = new CMediaFont(DIR_THEME + "fonts/font.fnt",1);

        // Misc
        UI_PIXEL = new CMediaImage(DIR_THEME + "pixel.png",1);
        UI_PIXEL_TRANSPARENT = new CMediaImage(DIR_THEME + "pixel_transparent.png",1);

    }

}
