package addsynth.material.types;

import net.minecraft.world.level.material.MaterialColor;

/** Manufactured metals do not have an Ore Block. */
public final class ManufacturedMetal extends Metal {

  /** Placeholder Metal */
  public ManufacturedMetal(final String name){
    super(name);
  }

  /** MiningStrength here might be used in the future if I want the metal blocks to
   *  require a certain pickaxe strength to mine them.
   * @param unlocalized_name
   * @param color
   */
  public ManufacturedMetal(final String unlocalized_name, final MaterialColor color){
    super(unlocalized_name, color);
  }

}
