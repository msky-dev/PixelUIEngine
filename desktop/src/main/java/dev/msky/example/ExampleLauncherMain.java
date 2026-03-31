package dev.msky.example;


import dev.msky.pixelui.utils.launcher.PixelUILaunchConfig;
import dev.msky.pixelui.utils.launcher.PixelUILauncher;

public class ExampleLauncherMain {

    public static void main(String[] args) {
        PixelUILaunchConfig pixelUILaunchConfig = new PixelUILaunchConfig();
        pixelUILaunchConfig.fps = 60;
        pixelUILaunchConfig.idleFPS = 60;
        pixelUILaunchConfig.appTile = ExampleMainConstants.APP_TITLE;
        pixelUILaunchConfig.resolutionWidth = ExampleMainConstants.INTERNAL_RESOLUTION_WIDTH;
        pixelUILaunchConfig.resolutionHeight = ExampleMainConstants.INTERNAL_RESOLUTION_HEIGHT;
        pixelUILaunchConfig.windowsGLEmulation = PixelUILaunchConfig.GLEmulation.ANGLE_GL32_VULKAN;
        pixelUILaunchConfig.linuxGLEmulation = PixelUILaunchConfig.GLEmulation.ANGLE_GL32_VULKAN;
        pixelUILaunchConfig.macOSGLEmulation = PixelUILaunchConfig.GLEmulation.ANGLE_GL30_METAL;
        pixelUILaunchConfig.fullScreen = false;
        PixelUILauncher.launch(new ExampleMain(), pixelUILaunchConfig);
    }
}
