package org.x4.mc_mod__i_feel_bad.data_structure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IFBIdPair {

    public final String id__0;

    public final String id__1;

    public IFBIdPair() {
        id__0 = null;
        id__1 = null;
    }

    public IFBIdPair(String _id__0, String _id__1) {
        id__0 = _id__0;
        id__1 = _id__1;
    }

    public static final String regex__parse = "\\s*(\\w+)\\s*,\\s*(\\w+)\\s*";

    public static final Pattern pattern__parse = Pattern.compile(regex__parse);

    public static final IFBIdPair default_value;

    // 默认值 (用于提供给配置器)
    public static final List<String> str_list__default = new ArrayList<>();

    static {
        default_value = new IFBIdPair("entities__0", "modification__0");

        str_list__default.add(default_value.toString());
    }

    public String toString() {
        return IFBIdPair.to_string(id__0, id__1);
    }

    public static @NotNull String to_string(String _id__0, String _id__1) {
        return String.format("%s,%s", _id__0, _id__1);
    }

    public static @Nullable IFBIdPair from_string(@NotNull String _string) {
        Matcher matcher = pattern__parse.matcher(_string);

        if (matcher.find()) {
            return new IFBIdPair(matcher.group(1), matcher.group(2));
        }
        return null;
    }

}
