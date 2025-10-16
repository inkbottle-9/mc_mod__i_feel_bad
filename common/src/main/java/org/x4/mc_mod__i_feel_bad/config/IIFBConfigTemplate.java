package org.x4.mc_mod__i_feel_bad.config;

public interface IIFBConfigTemplate {


    public IFBConfigCommon.IIFeelBadConfigTemplate config__i_feel_bad();

    public IFBConfigCommon.IModificationConfigTemplate config__hp_modification();
    public IFBConfigCommon.IModificationConfigTemplate config__fullness_modification();
    public IFBConfigCommon.IModificationConfigTemplate config__saturation_modification();

    public IFBConfigCommon.IStatusEffectAssignmentConfigTemplate config__status_assignment();

    public IFBConfigCommon.ILevelExpRequirementCoverageConfigTemplate config__level_exp_req_coverage();


    public IFBConfigCommon.IEntityJoiningAttributesModificationConfigTemplate config__entity_attributes_modification();


    // 将配置同步到 common 中的结构
    default public void sync_config_to_common() {
        // 将数据更新至 common 中

        config__i_feel_bad().sync(IFBConfigCommon.CONFIG.config__i_feel_bad);

        config__hp_modification().sync(IFBConfigCommon.CONFIG.config__hp_modification);
        config__fullness_modification().sync(IFBConfigCommon.CONFIG.config__fullness_modification);
        config__saturation_modification().sync(IFBConfigCommon.CONFIG.config__saturation_modification);

        config__status_assignment().sync(IFBConfigCommon.CONFIG.config__status_assignment);

        config__level_exp_req_coverage().sync(IFBConfigCommon.CONFIG.config__level_exp_req_coverage);

        config__entity_attributes_modification().sync(IFBConfigCommon.CONFIG.config__entity_attributes_modification);
    }



}
