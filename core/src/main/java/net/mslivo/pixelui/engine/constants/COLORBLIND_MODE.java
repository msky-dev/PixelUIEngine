package net.mslivo.pixelui.engine.constants;

public enum COLORBLIND_MODE {
    NONE("None",0),
    PROTANOPIA("Protanopia",1),
    DEUTERANOPIA("Deuteranopia",2),
    TRITANOPIA("Tritanopia",3)
    ;

    public final String text;
    public final int u_mode;

    COLORBLIND_MODE(String text, int uMode) {
        this.text = text;
        u_mode = uMode;
    }
}
