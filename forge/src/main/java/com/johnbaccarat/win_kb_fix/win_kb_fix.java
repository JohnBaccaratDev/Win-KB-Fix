package com.johnbaccarat.win_kb_fix;

import com.johnbaccarat.win_kb_fix.core.interop;
import com.johnbaccarat.win_kb_fix.wrappers.mc;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.SystemUtils;

@Mod(modid = Constants.MOD_ID, acceptedMinecraftVersions = "[1.8,1.13)", clientSideOnly = true)
public class win_kb_fix {

    private static Boolean Inited = false;
    public win_kb_fix() {
        
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Constants.LOG = event.getModLog();
    }

    @Mod.EventHandler
    public void post(FMLLoadCompleteEvent event){
        if(!Inited){
            if(SystemUtils.IS_OS_WINDOWS){
                interop.init(new mc(Minecraft.getMinecraft()));
            }else {
                if(SystemUtils.IS_OS_MAC){
                    Constants.LOG.info("Satania_laughing.gif");
                }
            }
            Inited = true;
        }
    }
}