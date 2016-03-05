package com.golems_addon_waila.addons;

import java.util.List;

import com.golems.entity.EntityBedrockGolem;
import com.golems.entity.EntityBookshelfGolem;
import com.golems.entity.EntityCoalGolem;
import com.golems.entity.EntityEndstoneGolem;
import com.golems.entity.EntityIceGolem;
import com.golems.entity.EntityLapisGolem;
import com.golems.entity.EntityLeafGolem;
import com.golems.entity.EntityMelonGolem;
import com.golems.entity.EntityNetherBrickGolem;
import com.golems.entity.EntitySpongeGolem;
import com.golems.entity.EntityTNTGolem;
import com.golems.entity.GolemBase;
import com.golems.entity.GolemLightProvider;
import com.golems.entity.GolemMultiTextured;
import com.golems.main.Config;
import com.golems_addon_futurum.entity.EntityMushroomGolem;
import com.golems_addon_futurum.entity.EntitySlimeGolem;

import cpw.mods.fml.common.Optional;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaEntityProvider", modid = "Waila")
public class WailaExtraGolems implements IWailaEntityProvider
{
	@Optional.Method(modid = "Waila")
	public static void callbackRegister(IWailaRegistrar register) 
	{
		WailaExtraGolems instance = new WailaExtraGolems();

		register.registerBodyProvider(instance, GolemBase.class);

		register.addConfig("Extra-Golems", "extragolems.show_attack_damage_tip", true);
		register.addConfig("Extra-Golems", "extragolems.show_special_abilities_tip", true);
		register.addConfig("Extra-Golems", "extragolems.show_knockback_resistance_tip", true);
		register.addConfig("Extra-Golems", "extragolems.show_multitexture_tip", true);
		register.addConfig("Extra-Golems", "extragolems.show_fireproof_tip", true);

		// debug:
		//System.out.print("Calling register for Extra Golems\n");
	}

	@Override
	@Optional.Method(modid = "Waila")
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) 
	{
		NBTTagCompound tag2 = new NBTTagCompound();
		entity.writeToNBT(tag2);
		return tag2;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		if(entity instanceof GolemBase)
		{
			GolemBase golem = (GolemBase)entity;
			
			// add attack damage to tip
			float attack = golem.getAttackDamage();
			String sAttack = EnumChatFormatting.GRAY + trans("tooltip.attack") + " : " + EnumChatFormatting.WHITE + attack;
			if(config.getConfig("show_attack_damage_tip")) currenttip.add(sAttack);
			
			// add right-click-texture to tip if possible
			if(entity instanceof GolemMultiTextured)
			{
				String sTexture = EnumChatFormatting.BLUE + trans("tooltip.click_change_texture");
				if(config.getConfig("extragolems.show_multitexture_tip")) currenttip.add(sTexture);
			}
			
			// add fire immunity to tip if possible
			if(config.getConfig("extragolems.show_fireproof_tip") && entity.isImmuneToFire() && !(entity instanceof EntityBedrockGolem))
			{
				String sFire = EnumChatFormatting.GOLD + trans("tooltip.is_fireproof");
				currenttip.add(sFire);
			}
			
			// add knockback resist to tip if possible
			if(config.getConfig("extragolems.show_knockback_resistance_tip") &&
			  ((GolemBase) entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getBaseValue() >= 0.9D)
			{
				String sResist = EnumChatFormatting.GRAY + trans("tooltip.knockback_resist");
				currenttip.add(sResist);
			}

			if(config.getConfig("show_special_abilities_tip"))
			{
				// add glowing to tip if possible
				if(entity instanceof GolemLightProvider)
				{
					String sLight = EnumChatFormatting.YELLOW + trans("tooltip.lights_area");
					currenttip.add(sLight);
				}

				/////// BEGIN SPECIFIC CLASS CHECKS ////////

				// add indestructible to tip if possible
				if(entity instanceof EntityBedrockGolem)
				{
					String sIndestructible = EnumChatFormatting.WHITE + trans("tooltip.indestructible");
					currenttip.add(sIndestructible);
				}

				// add potion effect to tip if possible
				if(entity instanceof EntityBookshelfGolem)
				{
					String sPotion = EnumChatFormatting.LIGHT_PURPLE + trans("tooltip.grants_self_potion_effects");
					if(Config.ALLOW_BOOKSHELF_SPECIAL) currenttip.add(sPotion);
				}

				// add blinding effect to tip if possible
				if(entity instanceof EntityCoalGolem)
				{
					String sPotion = EnumChatFormatting.GRAY + trans("tooltip.blinds_creatures");
					if(Config.ALLOW_COAL_SPECIAL) currenttip.add(sPotion);
				}

				// add teleporting to tip if possible
				if(entity instanceof EntityEndstoneGolem)
				{
					String sTeleport = EnumChatFormatting.DARK_AQUA + trans("tooltip.can_teleport");
					if(Config.ALLOW_ENDSTONE_SPECIAL) currenttip.add(sTeleport);
				}

				// add freezing to tip if possible
				if(entity instanceof EntityIceGolem)
				{
					String sFreeze = EnumChatFormatting.AQUA + trans("tooltip.freezes_blocks");
					if(Config.ALLOW_ICE_SPECIAL) currenttip.add(sFreeze);
				}

				// add potion effects to tip if possible
				if(entity instanceof EntityLapisGolem)
				{
					String sPotion = EnumChatFormatting.LIGHT_PURPLE + trans("tooltip.attacks_use_potion_effects");
					if(Config.ALLOW_LAPIS_SPECIAL) currenttip.add(sPotion);
				}

				// add potion effects to tip if possible
				if(entity instanceof EntityLeafGolem)
				{
					String sPotion = EnumChatFormatting.DARK_GREEN + trans("tooltip.has_regen_1");
					if(Config.ALLOW_LEAF_SPECIAL) currenttip.add(sPotion);
				}

				// add planting to tip if possible
				if(entity instanceof EntityMelonGolem)
				{
					String sPlant = EnumChatFormatting.GREEN + trans("tooltip.plants_flowers");
					if(Config.ALLOW_MELON_SPECIAL) currenttip.add(sPlant);
				}


				// add netherbrick specials to tip if possible
				if(entity instanceof EntityNetherBrickGolem)
				{
					String sFire = EnumChatFormatting.RED + trans("tooltip.lights_mobs_on_fire");
					String sLava = EnumChatFormatting.RED + trans("tooltip.slowly_melts") + " " + trans("tile.stonebrick.name");
					if(Config.ALLOW_NETHERBRICK_SPECIAL_FIRE) currenttip.add(sFire);
					if(Config.ALLOW_NETHERBRICK_SPECIAL_LAVA) currenttip.add(sLava);
				}

				// add water-drying to tip if possible
				if(entity instanceof EntitySpongeGolem)
				{
					String sWater = EnumChatFormatting.YELLOW + trans("tooltip.absorbs_water");
					if(Config.ALLOW_SPONGE_SPECIAL) currenttip.add(sWater);
				}

				// add boom to tip if possible
				if(entity instanceof EntityTNTGolem)
				{
					String sBoom = EnumChatFormatting.RED + trans("tooltip.explodes");
					if(Config.ALLOW_SPONGE_SPECIAL) currenttip.add(sBoom);
				}
			}
		}
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		if(entity instanceof GolemBase)
		{
			GolemBase golem = (GolemBase)entity;
			String name = golem.hasCustomNameTag() ? golem.getCustomNameTag() : "Golem";
			currenttip.add(name);
		}
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) 
	{
		return accessor.getEntity();
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor,	IWailaConfigHandler config) 
	{
		return currenttip;
	}

	private String trans(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
