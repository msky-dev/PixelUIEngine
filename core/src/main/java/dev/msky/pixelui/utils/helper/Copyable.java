package dev.msky.pixelui.utils.helper;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;

import java.util.Arrays;

public interface Copyable<T> {

    T copy();

    // -------------------------------------------------------------------------
    // Null safe
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> D copyNullSafe(D value) {
        return value != null ? value.copy() : null;
    }

    // -------------------------------------------------------------------------
    // Array
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> Array<D> copyCollection(Array<D> source) {
        Array<D> copy = new Array<>(source.size);

        for (int i = 0; i < source.size; i++) {
            copy.add(copyNullSafe(source.get(i)));
        }

        return copy;
    }

    // -------------------------------------------------------------------------
    // ObjectSet
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> ObjectSet<D> copyCollection(ObjectSet<D> source) {
        ObjectSet<D> copy = new ObjectSet<>(source.size);

        ObjectSet.ObjectSetIterator<D> iterator = source.iterator();

        while (iterator.hasNext) {
            copy.add(copyNullSafe(iterator.next()));
        }

        return copy;
    }

    // -------------------------------------------------------------------------
    // ObjectMap
    //
    // Keys are NOT copied.
    // This is especially useful for enum keys:
    // ObjectMap<MyEnum, MyCopyable>
    // -------------------------------------------------------------------------

    static <K, D extends Copyable<D>> ObjectMap<K, D> copyCollection(ObjectMap<K, D> source) {
        ObjectMap<K, D> copy = new ObjectMap<>(source.size);

        for (ObjectMap.Entry<K, D> entry : source) {
            copy.put(entry.key, copyNullSafe(entry.value));
        }

        return copy;
    }

    // -------------------------------------------------------------------------
    // IntMap
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> IntMap<D> copyCollection(IntMap<D> source) {
        IntMap<D> copy = new IntMap<>(source.size);

        IntMap.Keys keys = source.keys();

        while (keys.hasNext) {
            int key = keys.next();
            copy.put(key, copyNullSafe(source.get(key)));
        }

        return copy;
    }

    // -------------------------------------------------------------------------
    // LongMap
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> LongMap<D> copyCollection(LongMap<D> source) {
        LongMap<D> copy = new LongMap<>(source.size);

        LongMap.Keys keys = source.keys();

        while (keys.hasNext) {
            long key = keys.next();
            copy.put(key, copyNullSafe(source.get(key)));
        }

        return copy;
    }

    // -------------------------------------------------------------------------
    // Arrays
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> D[] copyCollection(D[] source) {
        D[] copy = Arrays.copyOf(source, source.length);

        for (int i = 0; i < source.length; i++) {
            copy[i] = copyNullSafe(source[i]);
        }

        return copy;
    }

    // -------------------------------------------------------------------------
    // 2D Arrays
    // -------------------------------------------------------------------------

    static <D extends Copyable<D>> D[][] copyCollection(D[][] source) {
        D[][] copy = Arrays.copyOf(source, source.length);

        for (int i = 0; i < source.length; i++) {
            D[] row = source[i];

            if (row == null) {
                copy[i] = null;
                continue;
            }

            D[] rowCopy = Arrays.copyOf(row, row.length);

            for (int j = 0; j < row.length; j++) {
                rowCopy[j] = copyNullSafe(row[j]);
            }

            copy[i] = rowCopy;
        }

        return copy;
    }

    static <D extends Copyable<D>> D[] copyArray(D[] array) {
        D[] copy = Arrays.copyOf(array, array.length);

        for (int i = 0; i < array.length; i++) {
            copy[i] = copyNullSafe(array[i]);
        }

        return copy;
    }

    static <D extends Copyable<D>> D[][] copyArray(D[][] array) {
        D[][] copy = Arrays.copyOf(array, array.length);

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static boolean[] copyArray(boolean[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static boolean[][] copyArray(boolean[][] array) {
        boolean[][] copy = new boolean[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static byte[] copyArray(byte[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static byte[][] copyArray(byte[][] array) {
        byte[][] copy = new byte[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static short[] copyArray(short[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static short[][] copyArray(short[][] array) {
        short[][] copy = new short[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static char[] copyArray(char[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static char[][] copyArray(char[][] array) {
        char[][] copy = new char[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static int[] copyArray(int[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static int[][] copyArray(int[][] array) {
        int[][] copy = new int[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static long[] copyArray(long[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static long[][] copyArray(long[][] array) {
        long[][] copy = new long[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static float[] copyArray(float[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static float[][] copyArray(float[][] array) {
        float[][] copy = new float[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

    static double[] copyArray(double[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static double[][] copyArray(double[][] array) {
        double[][] copy = new double[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i] != null ? copyArray(array[i]) : null;
        }

        return copy;
    }

}