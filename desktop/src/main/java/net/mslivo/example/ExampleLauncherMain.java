package net.mslivo.example;


import dev.msky.pixelui.utils.PixelUILaunchConfig;
import dev.msky.pixelui.utils.Tools;

public class ExampleLauncherMain {

    static void main(String[] args) {
        PixelUILaunchConfig pixelUILaunchConfig = new PixelUILaunchConfig();
        pixelUILaunchConfig.fps = 60;
        pixelUILaunchConfig.idleFPS = 60;
        pixelUILaunchConfig.appTile = ExampleMainConstants.APP_TITLE;
        pixelUILaunchConfig.resolutionWidth = ExampleMainConstants.INTERNAL_RESOLUTION_WIDTH;
        pixelUILaunchConfig.resolutionHeight = ExampleMainConstants.INTERNAL_RESOLUTION_HEIGHT;
        pixelUILaunchConfig.windowsGLEmulation = PixelUILaunchConfig.GLEmulation.GL32_OPENGL;
        pixelUILaunchConfig.fullScreen = false;
        Tools.App.launch(new ExampleMain(), pixelUILaunchConfig);
    }
}
