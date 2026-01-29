package dev.msky.pixelui.utils.settings.validator;


import dev.msky.pixelui.utils.settings.SettingsManager;
import dev.msky.pixelui.utils.settings.ValueValidator;

public class BooleanValueValidator implements ValueValidator {
    @Override
    public boolean isValueValid(String value) {
        return SettingsManager.isValidBoolean(value);
    }
}