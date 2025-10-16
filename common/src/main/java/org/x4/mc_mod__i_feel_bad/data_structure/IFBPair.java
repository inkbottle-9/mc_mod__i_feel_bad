package org.x4.mc_mod__i_feel_bad.data_structure;

import java.text.MessageFormat;

public final class IFBPair<A, B> {
    public final A first;
    public final B second;

    private IFBPair(A a, B b) {
        first = a;
        second = b;
    }

    public static <A, B> IFBPair<A, B> from(A _a, B _b) {
        return new IFBPair<>(_a, _b);
    }

    public String toString(){
        return MessageFormat.format("{0},{1}", first, second);
    }
}
