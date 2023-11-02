package com.johnbaccarat.win_kb_fix.wrappers;


import com.johnbaccarat.win_kb_fix.Constants;
import com.johnbaccarat.win_kb_fix.core.McWrapper;
import com.johnbaccarat.win_kb_fix.core.u32;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class mc implements McWrapper {

    Minecraft mc;

    Class screenWithKeybindings;

    ByteBuffer keyboardReadBuffer;

    public mc(Minecraft m){
        mc = m;

        try{
            screenWithKeybindings = Class.forName("net.minecraft.client.gui.GuiControls");
        }catch (Exception e){
            Constants.LOG.error("Could not find class of GUI with controls.");
            screenWithKeybindings = null;
        }

        try{
            Class c = Class.forName("org.lwjgl.input.Keyboard");
            Field f = c.getDeclaredField("readBuffer");
            f.setAccessible(true);
            keyboardReadBuffer = (ByteBuffer) f.get(null);
        }
        catch (Exception e){
            keyboardReadBuffer = null;
        }
    }

    byte down = 0x1;
    byte up = 0x0;

    public void putInKeyboardBuffer(int key, byte state){
        if(keyboardReadBuffer.hasRemaining()){
            keyboardReadBuffer.position(keyboardReadBuffer.limit());
        }

        if(keyboardReadBuffer.remaining() < (18)){
            keyboardReadBuffer.limit(keyboardReadBuffer.limit() - keyboardReadBuffer.remaining() + (18));
        }
        keyboardReadBuffer.putInt(key); // key
        keyboardReadBuffer.put(state); // state - up/down
        keyboardReadBuffer.putInt(0); // character
        keyboardReadBuffer.putLong(0); // nanos
        keyboardReadBuffer.put(up); // repeat
        keyboardReadBuffer.rewind();
/*
        event.key = readBuffer.getInt() & 0xFF;
        event.state = readBuffer.get() != 0;
        event.character = readBuffer.getInt();
        event.nanos = readBuffer.getLong();
        event.repeat = readBuffer.get() == 1;*/
    }

    @Override
    public void lWinUp() {
        putInKeyboardBuffer(219, up);
    }

    @Override
    public void lWinDown() {
        putInKeyboardBuffer(219, down);
    }

    @Override
    public void rWinUp() {
        putInKeyboardBuffer(220, up);
    }

    @Override
    public void rWinDown() {
        putInKeyboardBuffer(220, down);
    }

    @Override
    public boolean redirectWinKey() {
        if (Display.isActive()){
            if(mc.currentScreen == null){
                return true;
            }else{
                return screenWithKeybindings != null ? screenWithKeybindings.isInstance(mc.currentScreen) : false;
            }
        }
        return false;
    }

    @Override
    public long getLGFWWindowPointer() {
        try{
            Class c = Class.forName("org.lwjgl.opengl.Display");
            Field f = c.getDeclaredField("display_impl");
            f.setAccessible(true);
            Object displayImplentation = f.get(null);
            c = Class.forName("org.lwjgl.opengl.WindowsDisplay");
            f = c.getDeclaredField("hwnd");
            f.setAccessible(true);
            return (long) f.get(displayImplentation);
        }catch (Exception e2){
            Constants.LOG.error("The instance of the main Window could not be obtained.");
        }

        throw new RuntimeException("Could not get");
    }


    @Override
    public void error(String s) {
        Constants.LOG.error(s);
    }

    @Override
    public void warning(String s) {
        Constants.LOG.warn(s);
    }

    @Override
    public void info(String s) {
        Constants.LOG.info(s);
    }
}
