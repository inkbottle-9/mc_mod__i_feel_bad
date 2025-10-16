package org.x4.mc_mod__i_feel_bad.data_structure;

import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;

// 类 IFB 线性映射默认值包
// 主要用于传递默认值
public class IFBLinearMappingDefaultValuePackage {
    public boolean flag__enabled;
    public double default__k;
    public double default__b;
    public double default__minimum;
    public double default__maximum;

    public static final String config_path__basic = "i_feel_bad.configuration.gui.";

    public String config_path__title = null;

    public String config_path__flag__enabled = "flag__enabled";

    public String config_path__coefficient__k = "coefficient__k";

    public String config_path__coefficient__b = "coefficient__b";

    public String config_path__minimum = "minimum";

    public String config_path__maximum = "maximum";

    public IFBLinearMappingDefaultValuePackage set_config_path__title(String _value) {
        config_path__title = _value;
        return this;
    }

    public IFBLinearMappingDefaultValuePackage set_config_path__flag__enabled(String _value) {
        config_path__flag__enabled = _value;
        return this;
    }

    public IFBLinearMappingDefaultValuePackage set_config_path__coefficient__k(String _value) {
        config_path__coefficient__k = _value;
        return this;
    }

    public IFBLinearMappingDefaultValuePackage set_config_path__coefficient__b(String _value) {
        config_path__coefficient__b = _value;
        return this;
    }

    public IFBLinearMappingDefaultValuePackage set_config_path__minimum(String _value) {
        config_path__minimum = _value;
        return this;
    }

    public IFBLinearMappingDefaultValuePackage set_config_path__maximum(String _value) {
        config_path__maximum = _value;
        return this;
    }

    public static final IFBLinearMappingDefaultValuePackage default_value = new IFBLinearMappingDefaultValuePackage();

    public IFBLinearMappingDefaultValuePackage() {
        flag__enabled = true;
        default__k = 0.5f;
        default__b = 2.0f;
        default__minimum = 0.0f;
        default__maximum = 20.0f;
    }

    public IFBLinearMappingDefaultValuePackage(
        boolean _flag__enabled,
        double _default__k,
        double _default__b,
        double _default__minimum,
        double _default__maximum
    ) {
        flag__enabled = _flag__enabled;
        default__k = _default__k;
        default__b = _default__b;
        default__minimum = _default__minimum;
        default__maximum = _default__maximum;
    }

    public IFBLinearMappingDefaultValuePackage(
        @NotNull IFBConfigCommon.ModificationConfig _config
    ) {
        flag__enabled = _config.flag__enable_modification;
        default__k = _config.coefficient__k;
        default__b = _config.coefficient__b;
        default__minimum = _config.minimum;
        default__maximum = _config.maximum;
    }

    public IFBLinearMappingDefaultValuePackage(
        boolean _flag__enabled,
        double _default__k,
        double _default__b
    ) {
        flag__enabled = _flag__enabled;
        default__k = _default__k;
        default__b = _default__b;
        default__minimum = default_value.default__minimum;
        default__maximum = default_value.default__maximum;
    }
}
