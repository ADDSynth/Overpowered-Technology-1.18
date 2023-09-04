package addsynth.core.gameplay.registers;

import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.reference.Names;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class Tiles {

  public static final RegistryObject<BlockEntityType<TileMusicBox>> MUSIC_BOX =
    RegistryObject.create(Names.MUSIC_BOX, ForgeRegistries.BLOCK_ENTITIES);

}
