package com.golems_addon_waila.addons;

import java.util.List;

import com.golems.entity.GolemBase;
import com.golems_addon_futurum.main.FuturumConfig;

import cpw.mods.fml.common.Optional;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaEntityProvider", modid = "golems_addon_futurum")
public class WailaFuturumGolems implements IWailaEntityProvider
{
	@Optional.Method(modid = "golems_addon_futurum")
	public static void callbackRegister(IWailaRegistrar register) 
	{
		WailaFuturumGolems instance = new WailaFuturumGolems();

		register.registerBodyProvider(instance, GolemBase.class);

		// debug:
		//System.out.print("Calling register for Extra Golems\n");
	}

	@Override
	@Optional.Method(modid = "golems_addon_futurum")
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) 
	{
		NBTTagCompound tag2 = new NBTTagCompound();
		entity.writeToNBT(tag2);
		return tag2;
	}

	@Override
	@Optional.Method(modid = "golems_addon_futurum")
	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		if(config.getConfig("show_special_abilities_tip"))
		{
			/////// BEGIN SPECIFIC CLASS CHECKS ////////

			// add planting to tip if possible
			if(entity instanceof com.golems_addon_futurum.entity.EntityMushroomGolem)
			{
				String sPlant = EnumChatFormatting.RED + trans("tooltip.plants_shrooms");
				if(FuturumConfig.ALLOW_MUSHROOM_SPECIAL) currenttip.add(sPlant);
			}

			// add knockback to tip if possible
			if(entity instanceof com.golems_addon_futurum.entity.EntitySlimeGolem)
			{
				String sKnock = EnumChatFormatting.GREEN + trans("tooltip.has_knockback");
				if(FuturumConfig.ALLOW_SLIME_SPECIAL) currenttip.add(sKnock);
			}
		}

		return currenttip;
	}

	@Override
	@Optional.Method(modid = "golems_addon_futurum")
	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "golems_addon_futurum")
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) 
	{
		return accessor.getEntity();
	}

	@Override
	@Optional.Method(modid = "golems_addon_futurum")
	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	private String trans(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
