package dev.msky.pixelui.utils.particles;


import dev.msky.pixelui.utils.particles.particles.Particle;

public interface ParticleUpdater<T> {
    default boolean updateParticle(Particle<T> particle){
        return true;
    };

    default void resetParticleData(T particleData){
        return;
    };
}
