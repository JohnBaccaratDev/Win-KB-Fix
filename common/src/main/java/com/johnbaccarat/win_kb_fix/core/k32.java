package com.johnbaccarat.win_kb_fix.core;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;

public class k32 {

    public static final k32 INSTANCE;

    static {
        INSTANCE = new k32();
        Native.register("kernel32");
    }

    public native WinNT.HANDLE CreateMutexW(WinBase.SECURITY_ATTRIBUTES lpMutexAttributes,
                                            boolean bInitialOwner,
                                            String lpName);

    public native boolean ReleaseMutex(WinNT.HANDLE handle);

    public native boolean Process32Next(WinNT.HANDLE hSnapshot, Tlhelp32.PROCESSENTRY32 lppe);
}
