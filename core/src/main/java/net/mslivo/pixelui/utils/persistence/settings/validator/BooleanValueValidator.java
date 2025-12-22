package net.mslivo.pixelui.utils.persistence.settings.validator;


import net.mslivo.pixelui.utils.persistence.settings.SettingsManager;
import net.mslivo.pixelui.utils.persistence.settings.ValueValidator;

public class BooleanValueValidator implements ValueValidator {
    @Override
    public boolean isValueValid(String value) {
        return SettingsManager.isValidBoolean(value);
    }
}