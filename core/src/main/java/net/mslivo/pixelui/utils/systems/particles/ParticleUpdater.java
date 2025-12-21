package net.mslivo.pixelui.utils.systems.particles;


import net.mslivo.pixelui.utils.systems.particles.particles.Particle;

public interface ParticleUpdater<T> {
    default boolean updateParticle(Particle<T> particle){
        return true;
    };

    default void resetParticleData(T particleData){
        return;
    };
}
