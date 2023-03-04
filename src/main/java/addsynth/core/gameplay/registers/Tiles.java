package addsynth.core.gameplay.registers;

import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.music_box.TileMusicBox;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class Tiles {

  public static final BlockEntityType<TileMusicBox> MUSIC_BOX =
    BlockEntityType.Builder.of(TileMusicBox::new, Core.music_box).build(null);

}
