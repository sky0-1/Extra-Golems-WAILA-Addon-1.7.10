package com.golems_addon_waila.addons;

import java.util.List;

import com.golems.entity.GolemBase;
import com.golems.entity.GolemMultiTextured;
import com.golems_addon_tconstruct.main.TCGConfig;

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

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaEntityProvider", modid = "golems_addon_tconstruct")
public class WailaTinkersGolems implements IWailaEntityProvider
{
	@Optional.Method(modid = "golems_addon_tconstruct")
	public static void callbackRegister(IWailaRegistrar register) 
	{
		WailaTinkersGolems instance = new WailaTinkersGolems();

		register.registerBodyProvider(instance, GolemBase.class);

		// debug:
		//System.out.print("Calling register for Extra Golems\n");
	}

	@Override
	@Optional.Method(modid = "golems_addon_tconstruct")
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) 
	{
		NBTTagCompound tag2 = new NBTTagCompound();
		entity.writeToNBT(tag2);
		return tag2;
	}

	@Override
	@Optional.Method(modid = "golems_addon_tconstruct")
	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		if(config.getConfig("show_special_abilities_tip"))
		{
			/////// BEGIN SPECIFIC CLASS CHECKS ////////

			// add speed to tip if possible
			if(entity instanceof com.golems_addon_tconstruct.entity.EntityBrownstoneGolem)
			{
				String sFast = EnumChatFormatting.LIGHT_PURPLE + trans("tooltip.very_fast");
				currenttip.add(sFast);
			}
			
			// add slowing to tip if possible
			if(entity instanceof com.golems_addon_tconstruct.entity.EntityGlueGolem)
			{
				String sSlow = EnumChatFormatting.WHITE + trans("tooltip.slows_creatures");
				if(TCGConfig.ALLOW_GLUE_SPECIAL) currenttip.add(sSlow);
			}
			
			// add speed to tip if possible
			if(entity instanceof com.golems_addon_tconstruct.entity.EntityHamGolem)
			{
				String sTasty = EnumChatFormatting.RED + trans("tooltip.tasty");
				currenttip.add(sTasty);
			}
			
			// add fire-setting to tip if possible
			if(entity instanceof com.golems_addon_tconstruct.entity.EntitySearedGolem)
			{
				String sFire = EnumChatFormatting.RED + trans("tooltip.lights_mobs_on_fire");
				if(TCGConfig.ALLOW_SEARED_SPECIAL) currenttip.add(sFire);
			}
			
			// add knockback to tip if possible
			if(entity instanceof com.golems_addon_tconstruct.entity.EntitySlimeGolem)
			{
				GolemMultiTextured golem = (GolemMultiTextured)entity;
				EnumChatFormatting color;
				switch(golem.getTextureNum())
				{
				case 2: color = EnumChatFormatting.LIGHT_PURPLE;
				break;
				case 1: color = EnumChatFormatting.AQUA;	
				break;
				case 0:	default: color = EnumChatFormatting.GREEN;
				break;
				}
				
				String sKnock = color + trans("tooltip.has_knockback");
				if(TCGConfig.ALLOW_SLIME_SPECIAL) currenttip.add(sKnock);
			}
		}

		return currenttip;
	}

	@Override
	@Optional.Method(modid = "golems_addon_tconstruct")
	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "golems_addon_tconstruct")
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) 
	{
		return accessor.getEntity();
	}

	@Override
	@Optional.Method(modid = "golems_addon_tconstruct")
	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	private String trans(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
