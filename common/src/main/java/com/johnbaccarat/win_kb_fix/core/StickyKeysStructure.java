package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class StickyKeysStructure extends Structure {
    public UINT cbSize = new UINT(UINT.SIZE*2);
    public UINT dwFlags;

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("cbSize", "dwFlags");
    }
}
