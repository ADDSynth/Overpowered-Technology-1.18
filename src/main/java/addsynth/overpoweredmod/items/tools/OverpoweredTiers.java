package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

public enum OverpoweredTiers implements Tier {
  CELESTIAL        (4, Tiers.DIAMOND.getUses()*3, 12.0f, 4.0f, 0, Ingredient.of(OverpoweredItems.celestial_gem.get())),
  CELESTIAL_PICKAXE(4, Tiers.DIAMOND.getUses()*3, 16.0f, 4.0f, 0, Ingredient.of(OverpoweredItems.celestial_gem.get())),
  CELESTIAL_SWORD  (4, 1000,                      12.0f, 4.0f, 0, Ingredient.of(OverpoweredItems.celestial_gem.get())),
  VOID             (4, Tiers.DIAMOND.getUses()*5, 12.0f, 5.0f, 0, Ingredient.of(OverpoweredItems.void_crystal.get()));
  // MAYBE: Add Unimatter Tools, THESE will have Integer.MAX_VALUE durability.

  private final int harvestLevel;
  private final int maxUses;
  private final float efficiency;
  private final float attackDamage;
  private final int enchantability;
  private final Ingredient repairMaterial;

  private OverpoweredTiers(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Ingredient repair_item){
    this.harvestLevel = harvestLevel;
    this.maxUses = maxUses;
    this.efficiency = efficiency;
    this.attackDamage = damage;
    this.enchantability = enchantability;
    this.repairMaterial = repair_item;
  }

  @Override
  public int getUses(){ return maxUses; }

  @Override
  public float getSpeed(){ return efficiency; }

  @Override
  public float getAttackDamageBonus(){ return attackDamage; }

  @Override
  public int getLevel(){ return harvestLevel; }

  @Override
  public int getEnchantmentValue(){ return enchantability; }

  @Override
  public Ingredient getRepairIngredient(){ return repairMaterial; }
}
