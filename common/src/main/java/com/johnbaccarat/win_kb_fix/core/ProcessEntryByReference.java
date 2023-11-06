package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.platform.win32.Tlhelp32;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProcessEntryByReference extends Tlhelp32.PROCESSENTRY32.ByReference{

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("dwSize", "cntUsage", "th32ProcessID", "th32DefaultHeapID",
                "th32ModuleID", "cntThreads", "th32ParentProcessID", "pcPriClassBase",
                "dwFlags", "szExeFile");
    }
}
