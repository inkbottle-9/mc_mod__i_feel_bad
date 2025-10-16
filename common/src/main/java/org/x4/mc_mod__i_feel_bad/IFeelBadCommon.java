package org.x4.mc_mod__i_feel_bad;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class IFeelBadCommon {
    // 模组全局 ID
    public static final String MOD_ID = "i_feel_bad";

    // 模组初始化
    public static void init() {
        // Write common init code here.

        // 需要引入 arch API 依赖才可使用
//        EntityEvent.LIVING_HURT.register((LivingEntity entity, DamageSource source, float amount) -> {
//            return EventResult.interruptFalse();
//        });

    }
}
