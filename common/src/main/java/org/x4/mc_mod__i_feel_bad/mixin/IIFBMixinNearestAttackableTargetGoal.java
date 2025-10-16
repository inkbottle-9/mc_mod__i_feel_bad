package org.x4.mc_mod__i_feel_bad.mixin;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NearestAttackableTargetGoal.class)
public interface IIFBMixinNearestAttackableTargetGoal {
    @Accessor
    TargetingConditions getTargetConditions();

}
