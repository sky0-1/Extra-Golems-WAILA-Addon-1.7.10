package com.golems_addon_waila.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;

@Mod(modid = WailaGolems.MODID, name = WailaGolems.NAME, version = WailaGolems.VERSION, dependencies = "required-after:" + WailaGolems.DEPENDENCY_MODID, acceptedMinecraftVersions = WailaGolems.MCVERSION)
public class WailaGolems 
{
	public static final String MODID = "golems_addon_waila";
	public static final String DEPENDENCY_MODID = "golems";
	public static final String NAME = "Extra Golems Waila Addon";
	public static final String VERSION = "1.03";
	public static final String MCVERSION = "1.7.10";
	
	@Mod.Instance(WailaGolems.MODID)
	public static WailaGolems instance;
		
	@EventHandler
	public static void init(FMLInitializationEvent event) 
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.golems_addon_waila.addons.WailaExtraGolems.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.golems_addon_waila.addons.WailaFuturumGolems.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.golems_addon_waila.addons.WailaTinkersGolems.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.golems_addon_waila.addons.WailaMetalGolems.callbackRegister");
	}
}

