package dev.msky.pixelui.theme;

import dev.msky.pixelui.media.*;

public class Theme8x8 extends UIEngineTheme {
    private static final String DIR_THEME = MediaManager.DIR_GRAPHICS + "pixelui/base8x8/";
    private final CMediaArray window;
    private final CMediaArray button;
    private final CMediaArray buttonPressed;
    private final CMediaArray scrollBarVertical;
    private final CMediaArray scrollBarHorizontal;
    private final CMediaArray scrollBarButtonVertical;
    private final CMediaArray scrollBarButtonHorizontal;
    private final CMediaImage list;
    private final CMediaImage listCell;
    private final CMediaImage listCellSelected;
    private final CMediaArray listDrag;
    private final CMediaArray comboBox;
    private final CMediaArray comboBoxTop;
    private final CMediaArray comboBoxOpen;
    private final CMediaArray comboBoxList;
    private final CMediaArray comboBoxCell;
    private final CMediaArray comboBoxListCell;
    private final CMediaArray comboBoxListCellSelected;
    private final CMediaArray tabBorders;
    private final CMediaArray borders;
    private final CMediaArray tab;
    private final CMediaArray tabSelected;
    private final CMediaImage tabBigIcon;
    private final CMediaImage tabBigIconSelected;
    private final CMediaImage knobBackground;
    private final CMediaArray knob;
    private final CMediaArray knobEndless;
    private final CMediaArray separatorHorizontal;
    private final CMediaArray separatorVertical;
    private final CMediaArray tooltipCell;
    private final CMediaArray tooltip;
    private final CMediaArray tooltipTop;
    private final CMediaArray tooltipSegmentBorder;
    private final CMediaImage tooltipLineHorizontal;
    private final CMediaImage tooltipLineVertical;
    private final CMediaArray contextMenu;
    private final CMediaArray contextMenuTop;
    private final CMediaArray contextMenuCell;
    private final CMediaArray contextMenuCellSelected;
    private final CMediaArray textField;
    private final CMediaArray textFieldCellValidation;
    private final CMediaArray textFieldCell;
    private final CMediaAnimation textFieldCaret;
    private final CMediaArray grid;
    private final CMediaArray gridDragged;
    private final CMediaArray gridCell;
    private final CMediaArray gridCellSelected;
    private final CMediaArray gridX2;
    private final CMediaArray gridDraggedX2;
    private final CMediaArray gridCellX2;
    private final CMediaArray gridCellSelectedX2;
    private final CMediaArray progressBar;
    private final CMediaArray progressBarBar;
    private final CMediaImage notificationBar;
    private final CMediaArray checkBoxCheckbox;
    private final CMediaImage checkBoxCheckboxCell;
    private final CMediaArray checkBoxRadio;
    private final CMediaImage checkBoxRadioCell;
    private final CMediaArray mouseTextInputButton;
    private final CMediaImage mouseTextInputConfirm;
    private final CMediaImage mouseTextInputDelete;
    private final CMediaImage mouseTextInputLowercase;
    private final CMediaImage mouseTextInputUppercase;
    private final CMediaImage mouseTextInputSelected;
    private final CMediaImage cursorArrow;
    private final CMediaImage iconClose;
    private final CMediaImage iconColorPicker;
    private final CMediaImage iconInformation;
    private final CMediaImage iconQuestion;
    private final CMediaImage iconExtend;
    private final CMediaImage iconBack;
    private final CMediaImage iconForward;
    private final CMediaImage iconKeyDelete;
    private final CMediaImage iconKeyCase;
    private final CMediaFont font;
    private final CMediaImage pixel;

    public Theme8x8() {
        super(8);
        window = new CMediaArray(DIR_THEME + "ui/window.png",1, tileSize, tileSize);
        final int tile = tileSize;
        final int doubleTile = twoTileSize;
        final int oneAndHalfTile = oneAndHalfTileSize;

        button = new CMediaArray(DIR_THEME + "ui/button.png",1, tile, tile);
        buttonPressed = new CMediaArray(DIR_THEME + "ui/button_pressed.png",1, tile, tile);
        scrollBarVertical = new CMediaArray(DIR_THEME + "ui/scrollbar_vertical.png",1, tile, tile);
        scrollBarHorizontal = new CMediaArray(DIR_THEME + "ui/scrollbar_horizontal.png",1, tile, tile);
        scrollBarButtonVertical = new CMediaArray(DIR_THEME + "ui/scrollbar_button_vertical.png",1, tile, tile);
        scrollBarButtonHorizontal = new CMediaArray(DIR_THEME + "ui/scrollbar_button_horizontal.png",1, tile, tile);
        list = new CMediaImage(DIR_THEME + "ui/list.png",1);
        listCell = new CMediaImage(DIR_THEME + "ui/list_cell.png",1);
        listCellSelected = new CMediaImage(DIR_THEME + "ui/list_cell_selected.png",1);
        listDrag = new CMediaArray(DIR_THEME + "ui/list_drag.png",1, tile, doubleTile);
        comboBox = new CMediaArray(DIR_THEME + "ui/combobox.png",1, tile, tile);
        comboBoxTop = new CMediaArray(DIR_THEME + "ui/combobox_top.png",1, tile, tile);
        comboBoxOpen = new CMediaArray(DIR_THEME + "ui/combobox_open.png",1, tile, tile);
        comboBoxList = new CMediaArray(DIR_THEME + "ui/combobox_list.png",1, tile, tile);
        comboBoxCell = new CMediaArray(DIR_THEME + "ui/combobox_cell.png",1, tile, tile);
        comboBoxListCell = new CMediaArray(DIR_THEME + "ui/combobox_list_cell.png",1, tile, tile);
        comboBoxListCellSelected = new CMediaArray(DIR_THEME + "ui/combobox_list_cell_selected.png",1, tile, tile);
        tabBorders = new CMediaArray(DIR_THEME + "ui/tab_border.png",1, tile, tile);
        borders = new CMediaArray(DIR_THEME + "ui/border.png",1, tile, tile);
        tab = new CMediaArray(DIR_THEME + "ui/tab.png",1, tile, tile);
        tabSelected = new CMediaArray(DIR_THEME + "ui/tab_selected.png",1, tile, tile);
        tabBigIcon = new CMediaImage(DIR_THEME + "ui/tab_bigicon.png",1);
        tabBigIconSelected = new CMediaImage(DIR_THEME + "ui/tab__bigicon_selected.png",1);
        knobBackground = new CMediaImage(DIR_THEME + "ui/knob_background.png",1);
        knob = new CMediaArray(DIR_THEME + "ui/knob.png",1, doubleTile, doubleTile);
        knobEndless = new CMediaArray(DIR_THEME + "ui/knob_endless.png",1, doubleTile, doubleTile);
        separatorHorizontal = new CMediaArray(DIR_THEME + "ui/separator_horizontal.png",1, tile, tile);
        separatorVertical = new CMediaArray(DIR_THEME + "ui/separator_vertical.png",1, tile, tile);
        tooltipCell = new CMediaArray(DIR_THEME + "ui/tooltip_cell.png",1, tile, tile);
        tooltip = new CMediaArray(DIR_THEME + "ui/tooltip.png",1, tile, tile);
        tooltipTop = new CMediaArray(DIR_THEME + "ui/tooltip_top.png",1, tile, tile);
        tooltipSegmentBorder = new CMediaArray(DIR_THEME + "ui/tooltip_segment_border.png",1,8,8);
        tooltipLineHorizontal = new CMediaImage(DIR_THEME + "ui/tooltip_line_horizontal.png",1);
        tooltipLineVertical = new CMediaImage(DIR_THEME + "ui/tooltip_line_vertical.png",1);
        contextMenu = new CMediaArray(DIR_THEME + "ui/context_menu.png",1, tile, tile);
        contextMenuTop = new CMediaArray(DIR_THEME + "ui/context_menu_top.png",1, tile, tile);
        contextMenuCell = new CMediaArray(DIR_THEME + "ui/context_menu_cell.png",1, tile, tile);
        contextMenuCellSelected = new CMediaArray(DIR_THEME + "ui/context_menu_cell_selected.png",1, tile, tile);
        textField = new CMediaArray(DIR_THEME + "ui/textfield.png",1, tile, tile);
        textFieldCellValidation = new CMediaArray(DIR_THEME + "ui/textfield_cell_validation.png",1, tile, tile);
        textFieldCell = new CMediaArray(DIR_THEME + "ui/textfield_cell.png",1, tile, tile);
        textFieldCaret = new CMediaAnimation(DIR_THEME + "ui/textfield_caret.png",1, 1,tile, 0.4f);
        grid = new CMediaArray(DIR_THEME + "ui/grid.png",1, tile, tile);
        gridDragged = new CMediaArray(DIR_THEME + "ui/grid_dragged.png",1, tile, tile);
        gridCell = new CMediaArray(DIR_THEME + "ui/grid_cell.png",1, tile, tile);
        gridCellSelected = new CMediaArray(DIR_THEME + "ui/grid_cell_selected.png",1, tile, tile);
        gridX2 = new CMediaArray(DIR_THEME + "ui/grid_x2.png",1, doubleTile, doubleTile);
        gridDraggedX2 = new CMediaArray(DIR_THEME + "ui/grid_dragged_x2.png",1, doubleTile, doubleTile);
        gridCellX2 = new CMediaArray(DIR_THEME + "ui/grid_cell_x2.png",1, doubleTile, doubleTile);
        gridCellSelectedX2 = new CMediaArray(DIR_THEME + "ui/grid_cell_selected_x2.png",1, doubleTile, doubleTile);
        progressBar = new CMediaArray(DIR_THEME + "ui/progressbar.png",1, tile, tile);
        progressBarBar = new CMediaArray(DIR_THEME + "ui/progressbar_bar.png",1, tile, tile);
        notificationBar = new CMediaImage(DIR_THEME + "ui/notification_bar.png",1);
        checkBoxCheckbox = new CMediaArray(DIR_THEME + "ui/checkbox.png",1, tile, tile);
        checkBoxCheckboxCell = new CMediaImage(DIR_THEME + "ui/checkbox_cell.png",1);
        checkBoxRadio = new CMediaArray(DIR_THEME + "ui/radio.png",1, tile, tile);
        checkBoxRadioCell = new CMediaImage(DIR_THEME + "ui/radio_cell.png",1);
        mouseTextInputButton = new CMediaArray(DIR_THEME + "ui/mousetextinput_button.png",1, oneAndHalfTile, oneAndHalfTile);
        mouseTextInputConfirm = new CMediaImage(DIR_THEME + "ui/mousetextinput_confirm.png",1);
        mouseTextInputDelete = new CMediaImage(DIR_THEME + "ui/mousetextinput_delete.png",1);
        mouseTextInputLowercase = new CMediaImage(DIR_THEME + "ui/mousetextinput_lowercase.png",1);
        mouseTextInputUppercase = new CMediaImage(DIR_THEME + "ui/mousetextinput_uppercase.png",1);
        mouseTextInputSelected = new CMediaImage(DIR_THEME + "ui/mousetextinput_selected.png",1);

        // Cursors
        cursorArrow = new CMediaImage(DIR_THEME + "cursors/arrow.png",1);

        // Icons
        iconClose = new CMediaImage(DIR_THEME + "icons/close.icon.png",1);
        iconColorPicker = null;
        iconInformation = new CMediaImage(DIR_THEME + "icons/information.icon.png",1);
        iconQuestion = new CMediaImage(DIR_THEME + "icons/question.icon.png",1);
        iconExtend = new CMediaImage(DIR_THEME + "icons/extend.icon.png",1);
        iconBack = new CMediaImage(DIR_THEME + "icons/back.icon.png",1);
        iconForward = new CMediaImage(DIR_THEME + "icons/forward.icon.png",1);
        iconKeyDelete = null;
        iconKeyCase = null;

        // Fonts
        font = new CMediaFont(DIR_THEME + "fonts/font.fnt",1);

        // Misc
        pixel = new CMediaImage(DIR_THEME + "pixel.png",1);

    }

    @Override
    public CMediaArray window() {
        return window;
    }
    @Override
    public CMediaArray button() {
        return button;
    }
    @Override
    public CMediaArray buttonPressed() {
        return buttonPressed;
    }
    @Override
    public CMediaArray scrollBarVertical() {
        return scrollBarVertical;
    }
    @Override
    public CMediaArray scrollBarHorizontal() {
        return scrollBarHorizontal;
    }
    @Override
    public CMediaArray scrollBarButtonVertical() {
        return scrollBarButtonVertical;
    }
    @Override
    public CMediaArray scrollBarButtonHorizontal() {
        return scrollBarButtonHorizontal;
    }
    @Override
    public CMediaImage list() {
        return list;
    }
    @Override
    public CMediaImage listCell() {
        return listCell;
    }
    @Override
    public CMediaImage listCellSelected() {
        return listCellSelected;
    }
    @Override
    public CMediaArray listDrag() {
        return listDrag;
    }
    @Override
    public CMediaArray comboBox() {
        return comboBox;
    }
    @Override
    public CMediaArray comboBoxTop() {
        return comboBoxTop;
    }
    @Override
    public CMediaArray comboBoxOpen() {
        return comboBoxOpen;
    }
    @Override
    public CMediaArray comboBoxList() {
        return comboBoxList;
    }
    @Override
    public CMediaArray comboBoxCell() {
        return comboBoxCell;
    }
    @Override
    public CMediaArray comboBoxListCell() {
        return comboBoxListCell;
    }
    @Override
    public CMediaArray comboBoxListCellSelected() {
        return comboBoxListCellSelected;
    }
    @Override
    public CMediaArray tabBorders() {
        return tabBorders;
    }
    @Override
    public CMediaArray borders() {
        return borders;
    }
    @Override
    public CMediaArray tab() {
        return tab;
    }
    @Override
    public CMediaArray tabSelected() {
        return tabSelected;
    }
    @Override
    public CMediaImage tabBigIcon() {
        return tabBigIcon;
    }
    @Override
    public CMediaImage tabBigIconSelected() {
        return tabBigIconSelected;
    }
    @Override
    public CMediaImage knobBackground() {
        return knobBackground;
    }
    @Override
    public CMediaArray knob() {
        return knob;
    }
    @Override
    public CMediaArray knobEndless() {
        return knobEndless;
    }
    @Override
    public CMediaArray separatorHorizontal() {
        return separatorHorizontal;
    }
    @Override
    public CMediaArray separatorVertical() {
        return separatorVertical;
    }
    @Override
    public CMediaArray tooltipCell() {
        return tooltipCell;
    }
    @Override
    public CMediaArray tooltip() {
        return tooltip;
    }
    @Override
    public CMediaArray tooltipTop() {
        return tooltipTop;
    }
    @Override
    public CMediaArray tooltipSegmentBorder() {
        return tooltipSegmentBorder;
    }
    @Override
    public CMediaImage tooltipLineHorizontal() {
        return tooltipLineHorizontal;
    }
    @Override
    public CMediaImage tooltipLineVertical() {
        return tooltipLineVertical;
    }
    @Override
    public CMediaArray contextMenu() {
        return contextMenu;
    }
    @Override
    public CMediaArray contextMenuTop() {
        return contextMenuTop;
    }
    @Override
    public CMediaArray contextMenuCell() {
        return contextMenuCell;
    }
    @Override
    public CMediaArray contextMenuCellSelected() {
        return contextMenuCellSelected;
    }
    @Override
    public CMediaArray textField() {
        return textField;
    }
    @Override
    public CMediaArray textFieldCellValidation() {
        return textFieldCellValidation;
    }
    @Override
    public CMediaArray textFieldCell() {
        return textFieldCell;
    }
    @Override
    public CMediaAnimation textFieldCaret() {
        return textFieldCaret;
    }
    @Override
    public CMediaArray grid() {
        return grid;
    }
    @Override
    public CMediaArray gridDragged() {
        return gridDragged;
    }
    @Override
    public CMediaArray gridCell() {
        return gridCell;
    }
    @Override
    public CMediaArray gridCellSelected() {
        return gridCellSelected;
    }
    @Override
    public CMediaArray gridX2() {
        return gridX2;
    }
    @Override
    public CMediaArray gridDraggedX2() {
        return gridDraggedX2;
    }
    @Override
    public CMediaArray gridCellX2() {
        return gridCellX2;
    }
    @Override
    public CMediaArray gridCellSelectedX2() {
        return gridCellSelectedX2;
    }
    @Override
    public CMediaArray progressBar() {
        return progressBar;
    }
    @Override
    public CMediaArray progressBarBar() {
        return progressBarBar;
    }
    @Override
    public CMediaImage notificationBar() {
        return notificationBar;
    }
    @Override
    public CMediaArray checkBoxCheckbox() {
        return checkBoxCheckbox;
    }
    @Override
    public CMediaImage checkBoxCheckboxCell() {
        return checkBoxCheckboxCell;
    }
    @Override
    public CMediaArray checkBoxRadio() {
        return checkBoxRadio;
    }
    @Override
    public CMediaImage checkBoxRadioCell() {
        return checkBoxRadioCell;
    }
    @Override
    public CMediaArray mouseTextInputButton() {
        return mouseTextInputButton;
    }
    @Override
    public CMediaImage mouseTextInputConfirm() {
        return mouseTextInputConfirm;
    }
    @Override
    public CMediaImage mouseTextInputDelete() {
        return mouseTextInputDelete;
    }
    @Override
    public CMediaImage mouseTextInputLowercase() {
        return mouseTextInputLowercase;
    }
    @Override
    public CMediaImage mouseTextInputUppercase() {
        return mouseTextInputUppercase;
    }
    @Override
    public CMediaImage mouseTextInputSelected() {
        return mouseTextInputSelected;
    }
    @Override
    public CMediaImage cursorArrow() {
        return cursorArrow;
    }
    @Override
    public CMediaImage iconClose() {
        return iconClose;
    }
    @Override
    public CMediaImage iconColorPicker() {
        return iconColorPicker;
    }
    @Override
    public CMediaImage iconInformation() {
        return iconInformation;
    }
    @Override
    public CMediaImage iconQuestion() {
        return iconQuestion;
    }
    @Override
    public CMediaImage iconExtend() {
        return iconExtend;
    }
    @Override
    public CMediaImage iconBack() {
        return iconBack;
    }
    @Override
    public CMediaImage iconForward() {
        return iconForward;
    }
    @Override
    public CMediaImage iconKeyDelete() {
        return iconKeyDelete;
    }
    @Override
    public CMediaImage iconKeyCase() {
        return iconKeyCase;
    }
    @Override
    public CMediaFont font() {
        return font;
    }
    @Override
    public CMediaImage pixel() {
        return pixel;
    }

}
