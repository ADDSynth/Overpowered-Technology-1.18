package addsynth.energy.gameplay;

import addsynth.energy.gameplay.reference.Names;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EnergyBlocks {

  public static final RegistryObject<Block> wire                     = RegistryObject.create(Names.ENERGY_WIRE,                ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> generator                = RegistryObject.create(Names.GENERATOR,                  ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> energy_storage           = RegistryObject.create(Names.ENERGY_STORAGE,             ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> compressor               = RegistryObject.create(Names.COMPRESSOR,                 ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> electric_furnace         = RegistryObject.create(Names.ELECTRIC_FURNACE,           ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> circuit_fabricator       = RegistryObject.create(Names.CIRCUIT_FABRICATOR,         ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> universal_energy_machine = RegistryObject.create(Names.UNIVERSAL_ENERGY_INTERFACE, ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> energy_diagnostics_block = RegistryObject.create(Names.ENERGY_DIAGNOSTICS_BLOCK,   ForgeRegistries.BLOCKS);

}
