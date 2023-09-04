package addsynth.overpoweredmod.game.core;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum Laser {

  WHITE  (DeviceColor.WHITE),
  RED    (DeviceColor.RED),
  ORANGE (DeviceColor.ORANGE),
  YELLOW (DeviceColor.YELLOW),
  GREEN  (DeviceColor.GREEN),
  CYAN   (DeviceColor.CYAN),
  BLUE   (DeviceColor.BLUE),
  MAGENTA(DeviceColor.MAGENTA);

  public final RegistryObject<Block> cannon;
  public final RegistryObject<Block> beam;

  public static final Laser[] index = Laser.values();
  
  private Laser(final DeviceColor color){
    this.cannon = RegistryObject.create(color.laser_cannon, ForgeRegistries.BLOCKS);
    this.beam   = RegistryObject.create(color.laser_beam,   ForgeRegistries.BLOCKS);
  }

}
