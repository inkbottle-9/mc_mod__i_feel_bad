package org.x4.mc_mod__i_feel_bad.mixin;


import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mob.class)
public interface IIFBMixinMob {

    @Accessor
    GoalSelector getTargetSelector();

}
