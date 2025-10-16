package org.x4.mc_mod__i_feel_bad.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;

@Mixin(Player.class)
public abstract class IFBMixinPlayer {

    // 映射 (相当于让该类型可以访问原实例中的成员)
    @Shadow
    public int experienceLevel;

    /**
     * MC 获取经验需求的函数
     * 使用 @Overwrite 替换
     * 使用 @Inject 注入
     * 经验等级:
     * ((Player)(Object)this).experienceLevel
     */
//    @Overwrite
    @Inject(
        method = "getXpNeededForNextLevel",      // 目标方法
        at = @At("HEAD"),                        // 在方法入口注入
        cancellable = true                       // 允许覆盖返回值
    )
    public void exp_change(CallbackInfoReturnable<Integer> _cir) {
        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_i_feel_bad) {
            if (IFBConfigCommon.CONFIG.config__level_exp_req_coverage.flag__enable_coverage) {
                // 计算经验值需求
                int exp = get_exp_value();

                // 设置被注入函数的返回值
                _cir.setReturnValue(exp);
            }
        }
    }

    @Unique
    public int get_exp_value() {
        // 获取配置中的参数
        int basic = IFBConfigCommon.CONFIG.config__level_exp_req_coverage.basic__exp_requirement;
        int increment = IFBConfigCommon.CONFIG.config__level_exp_req_coverage.increment__exp_req_per_level;
        int min = IFBConfigCommon.CONFIG.config__level_exp_req_coverage.minimum__exp_req_for_each_level;
        int max = IFBConfigCommon.CONFIG.config__level_exp_req_coverage.maximum__exp_req_for_each_level;
        int exp = basic + this.experienceLevel * increment;

        min = min > 0 ? min : 1;
        max = max > min ? max : min;

        exp = Math.clamp(exp, min, max);
        return exp;
    }
}
