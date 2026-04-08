package dev.msky.pixelui.utils.helper;

import com.badlogic.gdx.utils.Array;

public interface Copyable<T> {
    Copyable<T> copy();

    static Array<Copyable> copyArray(Array<Copyable> array){
        Array<Copyable> copy = new Array<>();
        for(int i = 0; i < array.size; i++){
            copy.add(array.get(i).copy());
        }
        return array;
    }

}
