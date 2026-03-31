package dev.msky.pixelui.utils.launcher;

import dev.msky.pixelui.utils.helper.Copyable;

import java.util.function.Consumer;

public final class PixelUILaunchConfig implements Copyable<PixelUILaunchConfig> {

    public String appTile = "Pixel UI Game";
    public int resolutionWidth = 320;
    public int resolutionHeight = 240;
    public String iconPath = null;
    public GLEmulation windowsGLEmulation = GLEmulation.ANGLE_GL32_VULKAN;
    public GLEmulation linuxGLEmulation = GLEmulation.ANGLE_GL32_VULKAN;
    public GLEmulation macOSGLEmulation = GLEmulation.ANGLE_GL30_METAL;
    public int fps = 60;
    public int idleFPS = 60;
    public boolean vSync = false;
    public int r, g, b, a = 8;
    public int depth = 16;
    public int stencil = 0;
    public int samples = 0;
    public boolean resizeAble = true;
    public boolean decorated = true;
    public boolean maximized = true;
    public boolean fullScreen = false;
    public boolean showExceptionDialog = true;
    public Consumer<Exception> onException = e -> {
    };

    public PixelUILaunchConfig() {
    }

    @Override
    public PixelUILaunchConfig copy() {
        PixelUILaunchConfig copy = new PixelUILaunchConfig();
        copy.appTile = this.appTile;
        copy.resolutionWidth = this.resolutionWidth;
        copy.resolutionHeight = this.resolutionHeight;
        copy.iconPath = this.iconPath;
        copy.windowsGLEmulation = this.windowsGLEmulation;
        copy.linuxGLEmulation = this.linuxGLEmulation;
        copy.macOSGLEmulation = this.macOSGLEmulation;
        copy.fps = this.fps;
        copy.idleFPS = this.idleFPS;
        copy.vSync = this.vSync;
        copy.r = this.r;
        copy.g = this.g;
        copy.b = this.b;
        copy.a = this.a;
        copy.depth = this.depth;
        copy.stencil = this.stencil;
        copy.samples = this.samples;
        copy.resizeAble = this.resizeAble;
        copy.decorated = this.decorated;
        copy.maximized = this.maximized;
        copy.fullScreen = this.fullScreen;
        return copy;
    }


    public enum GLEmulation {
        GL30_OPENGL,
        GL32_OPENGL,
        ANGLE_GL32_VULKAN,
        ANGLE_GL30_DESKTOP_GL,
        ANGLE_GL30_METAL,
        ANGLE_GL30_DIRECT3D_11
    }

}