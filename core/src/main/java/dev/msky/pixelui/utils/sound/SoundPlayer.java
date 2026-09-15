package dev.msky.pixelui.utils.sound;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.LongArray;
import com.badlogic.gdx.utils.ObjectMap;
import dev.msky.pixelui.media.CMediaSoundEffect;
import dev.msky.pixelui.media.MediaManager;
import dev.msky.pixelui.utils.Tools;

/*
 * Plays sounds and automatically adjusts volume to distance
 */
public class SoundPlayer implements Disposable{
    private static final int AUDIO_THROTTLING_DISABLED = -1;
    private int range;
    private float volume;
    private float camera_x, camera_y;
    private int audioThrottlingCooldownMs;
    private final MediaManager mediaManager;
    private final ObjectMap<CMediaSoundEffect, PlayedSoundMetaData> playedSoundMetaData;

    private final class PlayedSoundMetaData {
        public long lastPlayed;
        public final LongArray playedSoundIds;

        public PlayedSoundMetaData() {
            this.lastPlayed = 0;
            this.playedSoundIds = new LongArray();
        }
    }

    public SoundPlayer(MediaManager mediaManager) {
        this(mediaManager, 0);
    }

    public void endableAudioThrottling(int coolDown) {
        this.audioThrottlingCooldownMs = Math.max(coolDown, 0);
    }

    public void disableAudioThrottling() {
        this.audioThrottlingCooldownMs = AUDIO_THROTTLING_DISABLED;
    }

    public void setAudioThrottlingCooldownMs(int audioThrottlingCooldownMs) {
        this.audioThrottlingCooldownMs = audioThrottlingCooldownMs;
    }

    public SoundPlayer(MediaManager mediaManager, int range2D) {
        this.mediaManager = mediaManager;
        this.volume = 1f;
        this.playedSoundMetaData = new ObjectMap<>();
        this.audioThrottlingCooldownMs = AUDIO_THROTTLING_DISABLED;
        setRange2D(range2D);
    }

    public float volume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = Math.clamp(volume, 0f, 1f);
    }

    private void setRange2D(int range2D) {
        this.range = Math.max(range2D, 1);
    }


    public long playSound(CMediaSoundEffect cMediaSoundEffect) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, 1f,1f,0f,false,false);
    }

    public long playSound(CMediaSoundEffect cMediaSoundEffect,  float volume) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, volume,1f,0f,false,false);
    }

    public long playSound(CMediaSoundEffect cMediaSoundEffect,  float volume, float pitch) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, volume,pitch,0f,false,false);
    }

    public long playSound(CMediaSoundEffect cMediaSoundEffect,  float volume, float pitch, float pan) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, volume,pitch,pan,false,false);
    }

    public long loopSound(CMediaSoundEffect cMediaSoundEffect) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, 1f,1f,0f,true,false);
    }

    public long loopSound(CMediaSoundEffect cMediaSoundEffect,  float volume) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, volume,1f,0f,true,false);
    }

    public long loopSound(CMediaSoundEffect cMediaSoundEffect,  float volume, float pitch) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, volume,pitch,0f,true,false);
    }

    public long loopSound(CMediaSoundEffect cMediaSoundEffect,  float volume, float pitch, float pan) {
        return playSoundInternal(cMediaSoundEffect, 0f,0f, volume,pitch,pan,true,false);
    }

    public long playSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, 1f,1f,0f,false,true);
    }

    public long playSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y, float volume) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, volume,1f,0f,false,true);
    }

    public long playSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y, float volume, float pitch) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, volume,pitch,0f,false,true);
    }

    public long playSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y, float volume, float pitch, float pan) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, volume,pitch,pan,false,true);
    }

    public long loopSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, 1f,1f,0f,true,true);
    }

    public long loopSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y, float volume) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, volume,1f,0f,true,true);
    }

    public long loopSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y, float volume, float pitch) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, volume,pitch,0f,true,true);
    }

    public long loopSound2D(CMediaSoundEffect cMediaSoundEffect, float position_x, float position_y, float volume, float pitch, float pan) {
        return playSoundInternal(cMediaSoundEffect, position_x, position_y, volume,pitch,pan,true,true);
    }

    private long playSoundInternal(CMediaSoundEffect cMediaSoundEffect,float position_x, float position_y, float volume, float pitch, float pan, boolean loop, boolean play2D) {
        final long now = System.currentTimeMillis();
        if (!playedSoundMetaData.containsKey(cMediaSoundEffect))
            playedSoundMetaData.put(cMediaSoundEffect, new PlayedSoundMetaData());
        final PlayedSoundMetaData metaData = playedSoundMetaData.get(cMediaSoundEffect);

        if (this.audioThrottlingCooldownMs != AUDIO_THROTTLING_DISABLED) {
            if ((now - metaData.lastPlayed) <= this.audioThrottlingCooldownMs) // dont play again
                return -1;
        }


        final Sound sound = mediaManager.sound(cMediaSoundEffect);
        final float playVolume, playPan;
        if (play2D) {
            float positionVolume = (range - (Math.clamp(Tools.Calc.distance(camera_x, camera_y, position_x, position_y), 0, range))) / (float) range;
            float positionPan = 0;
            if (camera_x > position_x) {
                pan = Math.clamp(-((camera_x - position_x) / (float) range), -1, 0);
            } else if (camera_x < position_x) {
                pan = Math.clamp((position_x - camera_x) / (float) range, 0, 1);
            }
            playVolume = positionVolume * this.volume * volume;
            playPan = Math.clamp(positionPan + pan, -1f, 1f);
        } else {
            playVolume = this.volume*volume;
            playPan = pan;
        }

        final long soundId;
        if (loop) {
            soundId = sound.loop(playVolume, pitch, playPan);
        } else {
            soundId = sound.play(playVolume, pitch, playPan);
        }

        if(soundId != -1) {
            metaData.playedSoundIds.add(soundId);
            metaData.lastPlayed = now;
        }

        return soundId;
    }

    public void stopAllSounds(){
        final ObjectMap.Keys<CMediaSoundEffect> keys = this.playedSoundMetaData.keys();
        while (keys.hasNext)
            this.stopSound(keys.next());
    }

    public void stopSounds(Array<CMediaSoundEffect> stopSounds){
        for(int i=0;i<stopSounds.size;i++)
            stopSound(stopSounds.get(i));
    }

    public void stopSound(CMediaSoundEffect soundEffect){
        if(this.playedSoundMetaData.get(soundEffect) instanceof  PlayedSoundMetaData metaData) {
            for (int i = 0; i < metaData.playedSoundIds.size; i++)
                mediaManager.sound(soundEffect).stop(metaData.playedSoundIds.get(i));
            metaData.playedSoundIds.clear();
        }
    }

    public void update() {
        update(0, 0);
    }

    public void update(float camera_x, float camera_y) {
        this.camera_x = camera_x;
        this.camera_y = camera_y;
    }

    @Override
    public void dispose() {
        stopAllSounds();
    }


}
