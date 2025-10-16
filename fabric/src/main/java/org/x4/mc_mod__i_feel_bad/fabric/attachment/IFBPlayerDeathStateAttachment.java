package org.x4.mc_mod__i_feel_bad.fabric.attachment;


import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.ResourceLocation;
import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBPlayerDeathState;

public class IFBPlayerDeathStateAttachment {
    // 1. 换成你的类型，用你写好的 CODEC
    public static final AttachmentType<IFBPlayerDeathState> player_death_state =
        AttachmentRegistry.<IFBPlayerDeathState>builder()
            .persistent(IFBPlayerDeathState.CODEC)
            .copyOnDeath()
            .buildAndRegister(ResourceLocation.fromNamespaceAndPath(IFeelBadCommon.MOD_ID, "player_death_state"));
}
