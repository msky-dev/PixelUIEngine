package dev.msky.pixelui.utils.helper;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectLongMap;
import com.badlogic.gdx.utils.ObjectSet;

import java.util.Arrays;
import java.util.Stack;

public interface Copyable<T> {
    T copy();


    static <D extends Copyable<D>> D copyNullSafe(D from){
        return from != null ? from.copy() : null;
    }

    static <D extends Copyable<D>> Array<D> copyCollection(Array<D> array) {
        Array<D> copy = new Array<>(array.size);
        for (int i = 0; i < array.size; i++) {
            copy.add(array.get(i).copy());
        }
        return copy;
    }

    static <D extends Copyable<D>> ObjectSet<D> copyCollection(ObjectSet<D> objectSet) {
        ObjectSet<D> copy = new ObjectSet<>();
        ObjectSet.ObjectSetIterator<D> iterator = objectSet.iterator();
        while (iterator.hasNext)
            copy.add(iterator.next());
        return copy;
    }

    static <D extends Copyable<D>> ObjectLongMap<D> copyCollection(ObjectLongMap<D> objectLongMap) {
        ObjectLongMap<D> copy = new ObjectLongMap<>();
        ObjectLongMap.Keys<D> keys = objectLongMap.keys();
        while (keys.hasNext()){
            final D key = keys.next();
            copy.put(key.copy(), objectLongMap.get(key,0));
        }
        return copy;
    }

    static <D extends Copyable<D>> IntMap<D> copyCollection(IntMap<D> intMap) {
        IntMap<D> copy = new IntMap<>();
        IntMap.Keys keys = intMap.keys();
        while (keys.hasNext){
            final int key = keys.next();
            final D value = intMap.get(key);
            copy.put(key, value != null ? value.copy() : null);
        }
        return copy;
    }


    static <D extends Copyable<D>> D[] copyCollection(D[] array) {
        D[] copy = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length; i++) {
            D element = array[i];
            copy[i] = (element != null) ? element.copy() : null;
        }
        return copy;
    }

    static <D extends Copyable<D>> D[][] copyCollection(D[][] array) {
        // Copy outer array (keeps runtime type)
        D[][] copy = Arrays.copyOf(array, array.length);

        for (int i = 0; i < array.length; i++) {
            D[] inner = array[i];
            if (inner != null) {
                // Copy inner array structure
                D[] innerCopy = Arrays.copyOf(inner, inner.length);
                // Deep copy elements
                for (int j = 0; j < inner.length; j++) {
                    D element = inner[j];
                    innerCopy[j] = (element != null) ? element.copy() : null;
                }
                copy[i] = innerCopy;
            } else {
                copy[i] = null;
            }
        }

        return copy;
    }


}
