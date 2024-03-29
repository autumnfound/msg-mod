package com.msg.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MsgCore.MODID, version = MsgCore.VERSION, acceptableRemoteVersions = "*")
public class MsgCore {
    public static final String MODID = "msg-core";
    public static final String VERSION = "0.1";
    
    private MsgWorldGen worldGen;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	worldGen = new MsgWorldGen();
    	// Pretty sure this is early enough.
    	GameRegistry.registerWorldGenerator(worldGen, 1);
    }
}