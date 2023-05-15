package addsynth.material.compat;

import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.Material;
import addsynth.overpoweredmod.config.Features;
import net.minecraftforge.fml.InterModComms;
import moze_intel.projecte.api.imc.CustomEMCRegistration;
import moze_intel.projecte.api.imc.IMCMethods;
import moze_intel.projecte.api.nss.NSSItem;

public final class ProjectE {

  public static final void register_emc_values(){

    final String sender = ADDSynthMaterials.MOD_ID;
    final String mod = Compatibility.PROJECT_E.modid;
    final String message = IMCMethods.REGISTER_CUSTOM_EMC;
    
    if(Features.crystal_matter_generator.get()){ // REMOVE shards
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.getGemShard()),     EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.getGemShard()),    EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.getGemShard()),  EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.EMERALD.getGemShard()),  EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.DIAMOND.getGemShard()),  EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.getGemShard()), EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.getGemShard()), EMCValue.gem_shard));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.QUARTZ.getGemShard()),   EMCValue.gem_shard));
    }
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.getGem()),     EMCValue.gemstone)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.getGem()),    EMCValue.gemstone));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.getGem()),  EMCValue.gemstone));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.getGem()), EMCValue.gemstone)); // ProjectE OreDictionary defaults to 2024 emc
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.getGem()), EMCValue.gemstone));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.RUBY.getBlock()),     EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TOPAZ.getBlock()),    EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.CITRINE.getBlock()),  EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SAPPHIRE.getBlock()), EMCValue.gem_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.AMETHYST.getBlock()), EMCValue.gem_block));

    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.getIngot()),      EMCValue.common_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.getIngot()), EMCValue.common_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.getIngot()),    EMCValue.strong_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.getIngot()),   EMCValue.strong_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.getIngot()),   EMCValue.uncommon_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.getIngot()), EMCValue.rare_metal));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.getIngot()), EMCValue.rare_metal));
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.getBlock()),      EMCValue.common_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.getBlock()), EMCValue.common_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.getBlock()),    EMCValue.strong_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.getBlock()),   EMCValue.strong_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.getBlock()),   EMCValue.uncommon_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.getBlock()), EMCValue.rare_metal_block));
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.getBlock()), EMCValue.rare_metal_block));
    
    if(Compatibility.ADDSYNTH_ENERGY.loaded){
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.IRON.getMetalPlate()),     EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TIN.getMetalPlate()),      EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.ALUMINUM.getMetalPlate()), EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.COPPER.getMetalPlate()),   EMCValue.common_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.STEEL.getMetalPlate()),    EMCValue.strong_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.BRONZE.getMetalPlate()),   EMCValue.strong_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILVER.getMetalPlate()),   EMCValue.uncommon_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.GOLD.getMetalPlate()),     EMCValue.uncommon_metal));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.PLATINUM.getMetalPlate()), EMCValue.rare_metal_block));
      InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.TITANIUM.getMetalPlate()), EMCValue.rare_metal_block));
    }
    
    InterModComms.sendTo(sender, mod, message, () -> new CustomEMCRegistration(NSSItem.createItem(Material.SILICON.getItem()), EMCValue.silicon));
  }

}
