package dev.msky.example;


import dev.msky.pixelui.utils.launcher.PixelUILaunchConfig;
import dev.msky.pixelui.utils.Tools;
import dev.msky.pixelui.utils.launcher.PixelUILauncher;

public class ExampleLauncherMain {

    static void main(String[] args) {
        PixelUILaunchConfig pixelUILaunchConfig = new PixelUILaunchConfig();
        pixelUILaunchConfig.fps = 60;
        pixelUILaunchConfig.idleFPS = 60;
        pixelUILaunchConfig.appTile = ExampleMainConstants.APP_TITLE;
        pixelUILaunchConfig.resolutionWidth = ExampleMainConstants.INTERNAL_RESOLUTION_WIDTH;
        pixelUILaunchConfig.resolutionHeight = ExampleMainConstants.INTERNAL_RESOLUTION_HEIGHT;
        pixelUILaunchConfig.fullScreen = false;
        PixelUILauncher.launch(new ExampleMain(), pixelUILaunchConfig);
    }
}
