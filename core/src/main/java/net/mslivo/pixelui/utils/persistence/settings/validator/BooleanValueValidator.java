package net.mslivo.pixelui.utils.persistence.highscore.settings.validator;

import net.mslivo.pixelui.utils.persistence.highscore.settings.SettingsManager;
import net.mslivo.pixelui.utils.persistence.highscore.settings.ValueValidator;

public class BooleanValueValidator implements ValueValidator {
    @Override
    public boolean isValueValid(String value) {
        return SettingsManager.isValidBoolean(value);
    }
}