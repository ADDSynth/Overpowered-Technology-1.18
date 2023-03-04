package addsynth.overpoweredmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class UnidentifiedItemDropConfig {

  public final String mob_name;
  private final double default_drop_chance;
  public ForgeConfigSpec.BooleanValue drop;
  public ForgeConfigSpec.DoubleValue chance;

  public UnidentifiedItemDropConfig(final String mob_name){
    this.mob_name = mob_name;
    this.default_drop_chance = Values.DEFAULT_MOB_DROP_CHANCE;
  }

  public UnidentifiedItemDropConfig(final String mob_name, final double default_drop_chance){
    this.mob_name = mob_name;
    this.default_drop_chance = default_drop_chance;
  }

  public final void build(final ForgeConfigSpec.Builder builder){
    builder.push(mob_name);
    drop = builder.define("Drop", true);
    chance = builder.defineInRange("Chance", default_drop_chance, 0, Float.MAX_VALUE);
    builder.pop();
  }

}
