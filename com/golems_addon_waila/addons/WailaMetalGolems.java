package com.golems_addon_waila.addons;

import java.util.List;

import com.golems.entity.GolemBase;
import com.golems_addon_metals.entity.EntityWireCoilGolem;
import com.golems_addon_metals.main.MetalConfig;

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

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaEntityProvider", modid = "golems_addon_metals")
public class WailaMetalGolems implements IWailaEntityProvider
{
	@Optional.Method(modid = "golems_addon_metals")
	public static void callbackRegister(IWailaRegistrar register) 
	{
		WailaMetalGolems instance = new WailaMetalGolems();

		register.registerBodyProvider(instance, GolemBase.class);

		// debug:
		//System.out.print("Calling register for Extra Golems\n");
	}

	@Override
	@Optional.Method(modid = "golems_addon_metals")
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) 
	{
		NBTTagCompound tag2 = new NBTTagCompound();
		entity.writeToNBT(tag2);
		return tag2;
	}

	@Override
	@Optional.Method(modid = "golems_addon_metals")
	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		if(config.getConfig("extragolems.show_special_abilities_tip"))
		{
			/////// BEGIN SPECIFIC CLASS CHECKS ////////

			// add speed to tip if possible
			if(entity instanceof com.golems_addon_metals.entity.EntityElectrumGolem)
			{
				String sFast = EnumChatFormatting.GOLD + trans("tooltip.very_fast");
				currenttip.add(sFast);
			}
			
			// add resistance to tip if possible
			if(entity instanceof com.golems_addon_metals.entity.EntityReinfGlassGolem ||
			   entity instanceof com.golems_addon_metals.entity.EntityReinfStoneGolem)
			{
				String sResist = EnumChatFormatting.GRAY + trans("tooltip.damage_resist");
				currenttip.add(sResist);
			}
			
			// add speed to tip if possible
			if(entity instanceof com.golems_addon_metals.entity.EntityWireCoilGolem)
			{
				if(currenttip.remove(EnumChatFormatting.BLUE + trans("tooltip.click_change_texture")))
				{
					currenttip.add(EnumChatFormatting.BLUE + trans("tooltip.click_change_redstone"));
				}
				int redPower = this.getPowerMeta(((EntityWireCoilGolem)entity).getTextureNum());
				String sRedstone = EnumChatFormatting.RED + trans("tooltip.emits_redstone_signal") + " : " + EnumChatFormatting.WHITE + redPower;
				if(MetalConfig.ALLOW_WIRE_SPECIAL) 
				{
					currenttip.add(sRedstone);
				}
			}
			
			// add poison to tip if possible
			if(entity instanceof com.golems_addon_metals.entity.EntityUraniumGolem)
			{
				String sPoison = EnumChatFormatting.DARK_GREEN + trans("tooltip.poisons_mobs");
				if(MetalConfig.ALLOW_URANIUM_SPECIAL) currenttip.add(sPoison);
			}
		}

		return currenttip;
	}

	@Override
	@Optional.Method(modid = "golems_addon_metals")
	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "golems_addon_metals")
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) 
	{
		return accessor.getEntity();
	}

	@Override
	@Optional.Method(modid = "golems_addon_metals")
	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}
	
	// copied this (private) method from EntityWireCoilGolem
	// note to self:  make the original public later
	@Optional.Method(modid = "golems_addon_metals")
	private int getPowerMeta(int golemTextureNum)
	{
		switch(golemTextureNum)
		{
		case 0: return MetalConfig.WIRE_COIL_MIN;
		case 1: return MetalConfig.WIRE_COIL_MID;
		case 2: return MetalConfig.WIRE_COIL_MAX;
		default: return 0;
		}
	}

	private String trans(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
