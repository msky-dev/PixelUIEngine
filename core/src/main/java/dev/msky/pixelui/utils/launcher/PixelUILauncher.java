package dev.msky.pixelui.utils.launcher;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.github.dgzt.gdx.lwjgl3.Lwjgl3VulkanApplication;
import dev.msky.pixelui.utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class PixelUILauncher {
    public static void launch(ApplicationAdapter applicationAdapter, PixelUILaunchConfig launchConfig) {
        if (SharedLibraryLoader.os != Os.Windows && SharedLibraryLoader.os != Os.Linux) {
            throw new RuntimeException("Operating System \"" + System.getProperty("os.name") + "\n not supported");
        }

        boolean useVulkan = launchConfig.useVulkan;
        if (useVulkan) {
            try {
                if (SharedLibraryLoader.os == Os.Windows) {
                    System.loadLibrary("vulkan-1");
                } else if (SharedLibraryLoader.os == Os.Linux) {
                    System.loadLibrary("vulkan");
                }
            } catch (UnsatisfiedLinkError _) {
                Tools.App.logError("Vulkan not available, fallback to OpenGL");
                useVulkan = false;
            }
        }

        if(useVulkan){
            com.github.dgzt.gdx.lwjgl3.Lwjgl3ApplicationConfiguration config = new com.github.dgzt.gdx.lwjgl3.Lwjgl3ApplicationConfiguration();
            config.setOpenGLEmulation(com.github.dgzt.gdx.lwjgl3.Lwjgl3ApplicationConfiguration.GLEmulation.ANGLE_GLES32, 4, 5);

            setConfigUniversal(config, launchConfig);
            try {
                new Lwjgl3VulkanApplication(applicationAdapter, config);
            } catch (Exception e) {
                handleLaunchException(e, launchConfig);
            }
        }else{
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL32, 4, 5);
            setConfigUniversal(config, launchConfig);
            try {
                new Lwjgl3Application(applicationAdapter, config);
            } catch (Exception e) {
                handleLaunchException(e, launchConfig);
            }
        }
    }

    private static void setConfigUniversal(Object config, PixelUILaunchConfig launchConfig) {

        try {

            final Class c = config.getClass();
            c.getMethod("setResizable", boolean.class).invoke(config, launchConfig.resizeAble);
            c.getMethod("setResizable", boolean.class).invoke(config, launchConfig.decorated);
            c.getMethod("setDecorated", boolean.class).invoke(config, launchConfig.maximized);
            c.getMethod("setMaximized", boolean.class).invoke(config, launchConfig.maximized);
            c.getMethod("setWindowPosition", int.class, int.class).invoke(config, -1, -1);
            c.getMethod("setWindowSizeLimits", int.class, int.class, int.class, int.class).invoke(config, launchConfig.resolutionWidth, launchConfig.resolutionHeight, -1, -1);
            c.getMethod("setTitle", String.class).invoke(config, launchConfig.appTile);
            c.getMethod("setForegroundFPS", int.class).invoke(config, launchConfig.fps);
            c.getMethod("setIdleFPS", int.class).invoke(config, launchConfig.idleFPS);
            c.getMethod("useVsync", boolean.class).invoke(config, launchConfig.vSync);

            if (launchConfig.fullScreen) {
                switch (config) {
                    case Lwjgl3ApplicationConfiguration libgdxConfig -> {
                        libgdxConfig.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
                    }
                    case com.github.dgzt.gdx.lwjgl3.Lwjgl3ApplicationConfiguration vulkanConfig -> {
                        vulkanConfig.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + config);
                }
            } else {
                c.getMethod("setWindowedMode", int.class, int.class).invoke(config, launchConfig.resolutionWidth, launchConfig.resolutionHeight);
            }
            c.getMethod("setBackBufferConfig", int.class, int.class, int.class, int.class, int.class, int.class, int.class)
                    .invoke(config, launchConfig.r, launchConfig.g, launchConfig.b, launchConfig.a, launchConfig.depth, launchConfig.stencil, launchConfig.samples);
            if (launchConfig.iconPath != null)
                c.getMethod("setWindowIcon", String[].class).invoke(config, (Object) new String[]{launchConfig.iconPath});
        } catch (Exception e) {
            handleLaunchException(e, launchConfig);
        }

    }

    private static void handleLaunchException(Exception e, PixelUILaunchConfig launchConfig) {
        Tools.App.logException(e);
        launchConfig.onException.accept(e);
        if (launchConfig.showExceptionDialog) {
            StringBuilder dialogMessage = new StringBuilder();
            dialogMessage.append("Error occurred: \"" + e.getMessage() + "\"" + System.lineSeparator());
            dialogMessage.append("Details can be found in \"" + Tools.App.ERROR_LOG_FILE.toAbsolutePath() + "\"" + System.lineSeparator());
            dialogMessage.append(System.lineSeparator()).append("Press OK to copy to Clipboard" + System.lineSeparator());
            JOptionPane.showMessageDialog(
                    null,
                    dialogMessage,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            // Stacktrace to clipboard
            String stackTrace;
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                try (PrintWriter printWriter = new PrintWriter(byteArrayOutputStream)) {
                    e.printStackTrace(printWriter);
                    printWriter.flush();
                    stackTrace = byteArrayOutputStream.toString();
                }
            } catch (IOException ex) {
                stackTrace = ex.getMessage();
            }
            final String stackTraceFinal = stackTrace;
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(stackTraceFinal), null);
        }
        System.exit(0);
    }
}
