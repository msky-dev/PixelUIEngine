package dev.msky.pixelui.media;

import dev.msky.pixelui.utils.helper.Copyable;

public final class CMediaMusic extends CMediaSound implements Copyable<CMediaMusic> {

    public CMediaMusic() {
        super();
    }

    public CMediaMusic(String filename, int fileID) {
        super(filename, fileID);
    }

    @Override
    public CMediaMusic copy() {
        CMediaMusic copy = new CMediaMusic();
        return copy;
    }

}
