package dev.msky.pixelui.utils.json;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectIntMap;
import dev.msky.pixelui.utils.Tools;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassScanner {

    public static void main(String[] args) {
        ClassScanner.findJSONClassTags("dev.msky");
    }

    public static Array<ClassTag> findJSONClassTags(String basePackage) {
        Array<ClassTag> result = new Array<>();

        final Array<Class<?>> classes = findAnnotatedClasses(basePackage, JsonClassTag.class);
        classes.sort(Comparator.comparing((Class<?> a) -> a.getSimpleName()).thenComparing(Class::getPackageName));
        final ObjectIntMap<String> classNameCounts = new ObjectIntMap<>();

        for(Class<?> clazz :classes ){
            final JsonClassTag jsonClassTagAnnotation = clazz.getAnnotation(JsonClassTag.class);
            final String simpleName = clazz.getSimpleName();
            int count = classNameCounts.get(simpleName, 0);
            classNameCounts.put(simpleName, count + 1);
            // First occurrence keeps the original name.
            String defaultName = count == 0 ? simpleName : simpleName + "." + (count + 1);

            Array<String> tags = new Array<>();
            tags.add(defaultName);
            tags.addAll(jsonClassTagAnnotation.aliases());
            result.add(new ClassTag(clazz, tags));
        }

        return result;
    }


    public static Array<Class<?>> findAnnotatedClasses(
            String packageName,
            Class<? extends Annotation> annotation) {

        final Array<Class<?>> result = new Array<>();
        final String packagePath = packageName.replace('.', '/');

        final ClassLoader classLoader = ClassScanner.class.getClassLoader();

        try {
            // First try the normal classloader resource mechanism.
            final Enumeration<URL> resources =
                    classLoader.getResources(packagePath);

            while (resources.hasMoreElements()) {
                final URL url = resources.nextElement();

                switch (url.getProtocol()) {
                    case "file" -> scanDirectory(
                            classLoader,
                            packageName,
                            new File(url.toURI()),
                            annotation,
                            result
                    );

                    case "jar" -> {
                        final JarURLConnection connection =
                                (JarURLConnection) url.openConnection();

                        scanJar(
                                classLoader,
                                packageName,
                                connection.getJarFile(),
                                annotation,
                                result
                        );
                    }
                }
            }

            // Packaged applications can fail to expose package directories
            // through ClassLoader.getResources(), even though the classes
            // are inside the application JAR.
            if (result.size == 0) {
                final URL codeSource =
                        ClassScanner.class
                                .getProtectionDomain()
                                .getCodeSource()
                                .getLocation();

                if (codeSource != null && "file".equals(codeSource.getProtocol())) {

                    final File location = new File(codeSource.toURI());

                    if (location.isFile()
                            && location.getName().endsWith(".jar")) {

                        try (JarFile jar = new JarFile(location)) {
                            scanJar(
                                    classLoader,
                                    packageName,
                                    jar,
                                    annotation,
                                    result
                            );
                        }

                    } else if (location.isDirectory()) {

                        final File packageDirectory =
                                new File(location, packagePath);

                        scanDirectory(
                                classLoader,
                                packageName,
                                packageDirectory,
                                annotation,
                                result
                        );
                    }
                }
            }

        } catch (Exception e) {
            Tools.App.logError(e);
        }

        return result;
    }
    private static void scanDirectory(
            ClassLoader classLoader,
            String packageName,
            File directory,
            Class<? extends Annotation> annotation,
            Array<Class<?>> result) {

        final File[] files = directory.listFiles();

        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(
                        classLoader,
                        packageName + "." + file.getName(),
                        file,
                        annotation,
                        result
                );
            } else if (file.getName().endsWith(".class")
                    && !file.getName().contains("$")) {

                final String className =
                        packageName + "." +
                                file.getName().substring(0, file.getName().length() - 6);

                addIfJsonClass(classLoader, className, annotation, result);
            }
        }
    }

    private static void scanJar(
            ClassLoader classLoader,
            String packageName,
            JarFile jar,
            Class<? extends Annotation> annotation,
            Array<Class<?>> result) {

        final String prefix = packageName.replace('.', '/') + "/";

        final Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            final String name = entries.nextElement().getName();

            if (!name.startsWith(prefix)
                    || !name.endsWith(".class")
                    || name.contains("$")) {
                continue;
            }

            final String className = name
                    .substring(0, name.length() - 6)
                    .replace('/', '.');

            addIfJsonClass(classLoader, className, annotation, result);
        }
    }

    private static void addIfJsonClass(
            ClassLoader classLoader,
            String className,
            Class<? extends Annotation> annotation,
            Array<Class<?>> result
    ) {

        try {
            final Class<?> clazz = Class.forName(
                    className,
                    false,
                    classLoader
            );

            if (clazz.isAnnotationPresent(annotation)) {
                result.add(clazz);
            }

        } catch (ClassNotFoundException | LinkageError ignored) {
            // Ignore classes that cannot be loaded.
        }
    }

}
