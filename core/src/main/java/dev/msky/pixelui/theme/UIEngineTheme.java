package dev.msky.pixelui.theme;

import dev.msky.pixelui.media.*;

public abstract class UIEngineTheme {

    public final int tsHalf;
    public final int ts;
    public final int ts2;
    public final int ts3;
    public final int ts4;
    public final float tsFHalf;
    public final float tsF;
    public final float tsF2;
    public final float tsF3;
    public final float tsF4;

    public UIEngineTheme(int ts) {
        this.ts = ts;
        this.ts2 = ts * 2;
        this.ts3 = ts * 3;
        this.ts4 = ts * 4;
        this.tsHalf = ts / 2;
        this.tsF = (float) ts;
        this.tsF2 = (float) ts2;
        this.tsF3 = (float) ts3;
        this.tsF4 = (float) ts4;
        this.tsFHalf = (float) tsHalf;

    }

    public int toPixels(int tiles) {
        return tiles * ts;
    }

    public abstract CMediaArray window();
    public abstract CMediaArray button();
    public abstract CMediaArray buttonPressed();
    public abstract CMediaArray scrollBarVertical();
    public abstract CMediaArray scrollBarHorizontal();
    public abstract CMediaArray scrollBarButtonVertical();
    public abstract CMediaArray scrollBarButtonHorizontal();
    public abstract CMediaImage list();
    public abstract CMediaImage listCell();
    public abstract CMediaImage listCellSelected();
    public abstract CMediaArray listDrag();
    public abstract CMediaArray comboBox();
    public abstract CMediaArray comboBoxTop();
    public abstract CMediaArray comboBoxOpen();
    public abstract CMediaArray comboBoxList();
    public abstract CMediaArray comboBoxCell();
    public abstract CMediaArray comboBoxListCell();
    public abstract CMediaArray comboBoxListCellSelected();
    public abstract CMediaArray tabBorders();
    public abstract CMediaArray borders();
    public abstract CMediaArray tab();
    public abstract CMediaArray tabSelected();
    public abstract CMediaImage tabBigIcon();
    public abstract CMediaImage tabBigIconSelected();
    public abstract CMediaImage knobBackground();
    public abstract CMediaArray knob();
    public abstract CMediaArray knobEndless();
    public abstract CMediaArray separatorHorizontal();
    public abstract CMediaArray separatorVertical();
    public abstract CMediaArray tooltipCell();
    public abstract CMediaArray tooltip();
    public abstract CMediaArray tooltipTop();
    public abstract CMediaArray tooltipSegmentBorder();
    public abstract CMediaImage tooltipLineHorizontal();
    public abstract CMediaImage tooltipLineVertical();
    public abstract CMediaArray contextMenu();
    public abstract CMediaArray contextMenuTop();
    public abstract CMediaArray contextMenuCell();
    public abstract CMediaArray contextMenuCellSelected();
    public abstract CMediaArray textField();
    public abstract CMediaArray textFieldCellValidation();
    public abstract CMediaArray textFieldCell();
    public abstract CMediaAnimation textFieldCaret();
    public abstract CMediaArray grid();
    public abstract CMediaArray gridDragged();
    public abstract CMediaArray gridCell();
    public abstract CMediaArray gridCellSelected();
    public abstract CMediaArray gridX2();
    public abstract CMediaArray gridDraggedX2();
    public abstract CMediaArray gridCellX2();
    public abstract CMediaArray gridCellSelectedX2();
    public abstract CMediaArray progressBar();
    public abstract CMediaArray progressBarBar();
    public abstract CMediaImage notificationBar();
    public abstract CMediaArray checkBoxCheckbox();
    public abstract CMediaImage checkBoxCheckboxCell();
    public abstract CMediaArray checkBoxRadio();
    public abstract CMediaImage checkBoxRadioCell();
    public abstract CMediaArray mouseTextInputButton();
    public abstract CMediaImage mouseTextInputConfirm();
    public abstract CMediaImage mouseTextInputDelete();
    public abstract CMediaImage mouseTextInputLowercase();
    public abstract CMediaImage mouseTextInputUppercase();
    public abstract CMediaImage mouseTextInputSelected();

    // Cursors
    public abstract CMediaImage cursorArrow();

    // Icons
    public abstract CMediaImage iconClose();
    public abstract CMediaImage iconColorPicker();
    public abstract CMediaImage iconInformation();
    public abstract CMediaImage iconQuestion();
    public abstract CMediaImage iconExtend();
    public abstract CMediaImage iconBack();
    public abstract CMediaImage iconForward();
    public abstract CMediaImage iconKeyDelete();
    public abstract CMediaImage iconKeyCase();

    // Fonts
    public abstract CMediaFont font();

    // Misc
    public abstract CMediaImage pixel();

    public CMedia[] cMedia() {
        return new CMedia[]{
                window(),
                button(),
                buttonPressed(),
                scrollBarVertical(),
                scrollBarHorizontal(),
                scrollBarButtonVertical(),
                scrollBarButtonHorizontal(),
                list(),
                listCell(),
                listCellSelected(),
                listDrag(),
                comboBox(),
                comboBoxTop(),
                comboBoxOpen(),
                comboBoxList(),
                comboBoxCell(),
                comboBoxListCell(),
                comboBoxListCellSelected(),
                tabBorders(),
                borders(),
                tab(),
                tabSelected(),
                tabBigIcon(),
                tabBigIconSelected(),
                knobBackground(),
                knob(),
                knobEndless(),
                separatorHorizontal(),
                separatorVertical(),
                tooltipCell(),
                tooltip(),
                tooltipTop(),
                tooltipSegmentBorder(),
                tooltipLineHorizontal(),
                tooltipLineVertical(),
                contextMenu(),
                contextMenuTop(),
                contextMenuCell(),
                contextMenuCellSelected(),
                textField(),
                textFieldCellValidation(),
                textFieldCell(),
                textFieldCaret(),
                grid(),
                gridDragged(),
                gridCell(),
                gridCellSelected(),
                gridX2(),
                gridDraggedX2(),
                gridCellX2(),
                gridCellSelectedX2(),
                progressBar(),
                progressBarBar(),
                notificationBar(),
                checkBoxCheckbox(),
                checkBoxCheckboxCell(),
                checkBoxRadio(),
                checkBoxRadioCell(),
                mouseTextInputButton(),
                mouseTextInputConfirm(),
                mouseTextInputDelete(),
                mouseTextInputLowercase(),
                mouseTextInputUppercase(),
                mouseTextInputSelected(),
                cursorArrow(),
                iconClose(),
                iconColorPicker(),
                iconInformation(),
                iconQuestion(),
                iconExtend(),
                iconBack(),
                iconForward(),
                iconKeyDelete(),
                iconKeyCase(),
                font(),
                pixel(),
        };
    }


}
