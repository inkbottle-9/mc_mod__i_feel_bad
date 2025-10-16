package org.x4.mc_mod__i_feel_bad.fabric.mixin;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;
import org.x4.mc_mod__i_feel_bad.handler.IFBHandler;

import java.util.Arrays;

@Mixin(EntityType.class)
public abstract class IFBMixinEntityTypeFabric<T extends Entity>
    implements FeatureElement, EntityTypeTest<Entity, T> {

    @Inject(
        // 目标方法; 这里是创建实体的函数
        method = "create(" +
            "Lnet/minecraft/server/level/ServerLevel;" +
            "Ljava/util/function/Consumer;" +
            "Lnet/minecraft/core/BlockPos;" +
            "Lnet/minecraft/world/entity/MobSpawnType;" +
            "ZZ)" +
            "Lnet/minecraft/world/entity/Entity;",
        at = @At("RETURN"),       // 在方法末尾返回时注入
        cancellable = true        // 允许覆盖返回值
    )
    public void exp_change(CallbackInfoReturnable<T> _cir) {

        IFBHandler.LOGGER.warn(Arrays.toString(Thread.currentThread().getStackTrace()));

        // 检查是否开启功能
        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_i_feel_bad) {
            if (
                IFBConfigCommon.CONFIG.config__entity_attributes_modification
                    .flag__enable_entity_attributes_modification
            ) {
                // 获取函数的返回值
                T result = _cir.getReturnValue();
                // 添加属性
                org.x4.mc_mod__i_feel_bad.handler.IFBHandler.modify_entity_attributes(result);
                _cir.setReturnValue(result);
            }
        }
    }
}
