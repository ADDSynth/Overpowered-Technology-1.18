package addsynth.core.compat;

import java.util.Map;
import java.util.TreeMap;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.java.JavaUtils;
import net.minecraftforge.fml.ModList;

/**
 * <p>This class asks the Forge Mod Loader if certain mods are loaded.
 *    Since a specific string value is used, they are all listed here in case the
 *    mod developer changes their mod ID, then I can easily change it here.</p>
 * <p>The Forge Mod Loader automatically designates a mod to be loaded as soon as it finds its jar file.</p>
 * <p>Whatever code first requests a value from here will then initialize the whole class.</p>
 * @author ADDSynth
 * @since October 28, 2019
 */
public final class Compatibility {

  // Java doesn't have structs either?! That's another stupid thing about Java!

  // NOTE: The original purpose of doing it like this, individual classes, all static references,
  //       was because in the 1.12 and earlier versions used Forges @Optional annotation to denote
  //       certain classes and methods as Optional, but available ONLY if a certain mod was loaded.
  //       The annotation required a constant expression for the Mod ID.
  // There's no reason to change this back to extending from a base MOD class.
  // I CAN, but it's better to leave it as static constant values in case I need them again.
  // I can't use an enum either. Enum fields are not constant expressions.

  // TODO: Add authors, What Minecraft versions are available, and possibly but not likely a short description.

  // REMOVE: When Minecraft 1.20 comes out, remove all the code defining Minecraft 1.12 mods.

  public enum ModType {
    Tech, Magic, Library, Misc, Vanilla, Client, Recipe, Biomes, Food, Decoration,
    Blocks, Tools, Weapons, Computer, Rail_Transport, Info, Map, Compatibility,
    API, Dimension, CoreMod, Shader, Tweak, Diagnostics, Materials, Utility
  }

  // a better way to determine if this is an API or Library mod is to check the ModType. But I can't do that yet.
  public static final boolean isAPI(final String modid){
    return modid.equals(CURIOS.modid)    || modid.equals(FORGE_MULTIPART.modid) || modid.equals(REDSTONE_FLUX.modid)        ||
           modid.equals(TESLA.modid)     || modid.equals(ADDSynthCore.MOD_ID)   || modid.equals(CODE_CHICKEN_LIB.modid)     ||
           modid.equals(MANTLE.modid)    || modid.equals(ADDSYNTH_ENERGY.modid) || modid.equals(SHADOWFACTS_FORGELIN.modid) ||
           modid.equals(TRACK_API.modid) || modid.equals(MCJTY_LIB.modid)       || modid.equals(CYCLOPS_CORE.modid)         ||
           modid.equals(COFH_CORE.modid) || modid.equals(PATCHOULI.modid)       || modid.equals(CRAFTTWEAKER.modid);
  }

  // TODO: Mods will soon be loaded and unloaded dynamically. This class will need to be updated to extend from an abstract class.

  public static final class ACTUALLY_ADDITIONS {
    public static final String name = "Actually Additions";
    public static final String modid = "actuallyadditions";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class ADDSYNTH_ENERGY {
    public static final String name = "ADDSynth Energy";
    public static final String modid = "addsynth_energy";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class ADDSYNTH_MATERIALS { // currently bundled with ADDSynthCore, so it will always be loaded.
    public static final String name = "ADDSynth Materials";
    public static final String modid = "addsynth_materials";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Materials;
  }

  public static final class APPLIED_ENERGISTICS {
    public static final String name  = "Applied Energistics 2";
    public static final String modid = "appliedenergistics2";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class ARCHERS_PARADOX {
    public static final String name = "Archer's Paradox";
    public static final String modid = "archers_paradox";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Weapons;
  }

  public static final class BIOMES_O_PLENTY {
    public static final String name  = "Biomes O' Plenty";
    public static final String modid = "biomesoplenty";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Biomes;
  }
  
  public static final class BLOCK_RENDERER {
    public static final String name = "BlockRenderer";
    public static final String modid = "block_renderer";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Utility;
  }
  
  public static final class BLOOD_MAGIC {
    public static final String name = "Blood Magic";
    public static final String modid = "bloodmagic";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  public static final class BOTANIA {
    public static final String name = "Botania";
    public static final String modid = "botania";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  public static final class BUILDCRAFT {
    public static final String name  = "Buildcraft";
    public static final String modid = "buildcraftcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class CHISELS_AND_BITS {
    public static final String name = "Chisels & Bits";
    public static final String modid = "chiselsandbits";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Decoration;
  }
  
  public static final class CODE_CHICKEN_LIB {
    public static final String name = "CodeChicken Lib";
    public static final String modid = "codechickenlib";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Library;
  }
  
  public static final class COFH_CORE {
    public static final String name = "CoFH Core";
    public static final String modid = "cofh_core";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.CoreMod;
  }
  
  public static final class COOKING_FOR_BLOCKHEADS {
    public static final String name = "Cooking for Blockheds";
    public static final String modid = "cookingforblockheads";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Food;
  }
  
  public static final class CRAFTTWEAKER {
    // helps modders modify the game
    public static final String name = "CraftTweaker";
    public static final String modid = "crafttweaker";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Utility;
  }
  
  public static final class CREATE {
    public static final String name = "Create";
    public static final String modid = "create";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class CURIOS {
    public static final String name = "Curios";
    public static final String modid = "curios";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.API;
  }
  
  public static final class CYCLOPS_CORE {
    public static final String name = "Cyclops Core";
    public static final String modid = "cyclopscore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Library;
  }
  
  /* Another mod that was last updated since Minecraft 1.12. 
  public static final class DECOCRAFT {
    public static final String name = "Decocraft";
    public static final String modid = "decocraft";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Decoration;
  }
  */
  
  /* Deep Resonance does not exist for Minecraft 1.14+.
  public static final class DEEP_RESONANCE {
    public static final String name = "Deep Resonance";
    public static final String modid = "deepresonance";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  */
  
  public static final class DRACONIC_EVOLUTION {
    public static final String name = "Draconic Evolution";
    public static final String modid = "draconicevolution";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  public static final class DYNAMIC_SURROUNDINGS {
    public static final String name = "Dynamic Surroundings";
    public static final String modid = "dsurround";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Client;
  }
  
  public static final class ENDER_IO {
    public static final String name  = "Ender IO";
    public static final String modid = "enderio";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class ENSORCELLATION {
    public static final String name = "Ensorcellation";
    public static final String modid = "ensorcellation";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  // By Team Abnormals
  public static final class ENVIRONMENTAL {
    public static final String name = "Environmental";
    public static final String modid = "environmental";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Biomes;
  }
  
  public static final class ENVIRONMENTAL_MATERIALS {
    public static final String name = "Environmental Materials";
    public static final String modid = "enviromats";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Blocks;
  }
  
  // Another 1.12 mod. Has alpha builds for Minecraft 1.16.
  public static final class ENVIRONMENTAL_TECH {
    public static final String name = "Environmental Tech";
    public static final String modid = "envirotech";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  // Ex Nihilo (1.7.10) -> Ex Nihilo: Adscensio (1.10) -> Ex Nihilo: Creatio (1.12) -> Ex Nihilo: Sequentia (1.15+)
  public static final class EX_NIHILO_SEQUENTIA {
    public static final String name = "Ex Nihilo: Sequentia";
    public static final String modid = "exnihilosequentia";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Misc;
  }
  
  @Deprecated // Another Minecraft 1.12 mod.
  public static final class EXTRA_UTILITIES {
    public static final String name = "Extra Utilities 2";
    public static final String modid = "extrautils2";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class EXTREME_REACTORS {
    public static final String name  = "Extreme Reactors";
    public static final String modid = "bigreactors";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class FOAMFIX {
    public static final String name = "FoamFix";
    public static final String modid = "foamfix";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Diagnostics;
  }
  
  public static final class FORESTRY {
    public static final String name = "Forestry";
    public static final String modid = "forestry";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Misc;
  }
  
  public static final class FORGE_MULTIPART {
    public static final String name = "Forge Multipart CBE";
    public static final String modid = "forgemultipartcbe";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.API;
  }
  
  public static final class GALACTICRAFT {
    public static final String name  = "Galacticraft";
    public static final String modid = "galacticraftcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class IMMERSIVE_ENGINEERING {
    public static final String name = "Immersive Engineering";
    public static final String modid = "immersiveengineering"; // haven't you people ever heard of underscores?
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class IMMERSIVE_RAILROADING {
    public static final String name = "Immersive Railroading";
    public static final String modid = "immersiverailroading";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Rail_Transport;
  }
  
  public static final class INDUSTRIAL_CRAFT {
    public static final String name = "Industrial Craft";
    public static final String modid = "ic2";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class INDUSTRIAL_FOREGOING {
    public static final String name  = "Industrial Foregoing";
    public static final String modid = "industrialforegoing";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class INTEGRATION_FOREGOING {
    // Compatibility module for Industrial Foregoing
    public static final String name = "Integration Foregoing";
    public static final String modid = "integrationforegoing";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Compatibility;
  }
  
  public static final class INTEGRATED_DYNAMICS {
    public static final String name = "Integrated Dynamics";
    public static final String modid = "integrateddynamics";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class INTEGRATED_TERMINALS {
    public static final String name = "Integrated Terminals";
    public static final String modid = "integratedterminals";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class INTEGRATED_TUNNELS {
    public static final String name = "Integrated Tunnels";
    public static final String modid = "integratedtunnels";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class INVENTORY_TWEAKS_RENEWED {
    public static final String name = "Inventory Tweaks Renewed";
    public static final String modid = "invtweaks";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tweak;
  }
  
  public static final class ITEM_ZOOM {
    public static final String name = "Item Zoom";
    public static final String modid = "itemzoom"; // seriously! underscores! They're very useful!
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Client;
  }
  
  public static final class JEI {
    public static final String name  = "JEI";
    public static final String modid = "jei";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Recipe;
  }
  
  public static final class JOURNEY_MAP {
    public static final String name = "Journey Map";
    public static final String modid = "journeymap";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Map;
  }
  
  public static final class MANTLE {
    public static final String name = "Mantle";
    public static final String modid = "mantle";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Library;
  }
  
  public static final class MCJTY_LIB {
    public static final String name = "McJtyLib";
    public static final String modid = "mcjtylib_ng";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Library;
  }
  
  public static final class MEKANISM {
    public static final String name = "Mekanism";
    public static final String modid = "mekanism";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class MOUSE_TWEAKS {
    public static final String name = "Mouse Tweaks";
    public static final String modid = "mousetweaks";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tweak;
  }

  public static final class MYSTICAL_AGRICULTURE {
    public static final String name = "Mystical Agriculture";
    public static final String modid = "mysticalagriculture";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  public static final class NATURA {
    public static final String name = "Natura";
    public static final String modid = "natura";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  public static final class NEAT {
    public static final String name = "Neat";
    public static final String modid = "neat";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Info;
  }
  
  public static final class NO_CUBES {
    // Renders blocks differently, has collisions, may conflict with other mods, High FPS
    public static final String name = "NoCubes";
    public static final String modid = "nocubes";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Shader;
  }

  public static final class NO_CUBES_RELOADED {
    // Uses Forge API to change the shape of blocks, no collisions, High compatibility with other mods, Low FPS
    public static final String name = "No Cubes Reloaded";
    public static final String modid = "nocubesreloadedbase";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Shader;
  }
  
  public static final class OPENCOMPUTERS {
    public static final String name  = "OpenComputers";
    public static final String modid = "opencomputers";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Computer;
  }
  
  public static final class OVERPOWERED_TECHNOLOGY {
    public static final String name  = "Overpowered Technology";
    public static final String modid = "overpowered";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class PAMS_HARVESTCRAFT_2_FOOD_CORE {
    public static final String name = "Pam's Harvestcraft 2 - Food Core";
    public static final String modid = "pamhc2foodcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Food;
  }

  public static final class PAMS_HARVESTCRAFT_2_CROPS {
    public static final String name = "Pam's Harvestcraft 2 - Crops";
    public static final String modid = "pamhc2crops";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Food;
  }

  public static final class PAMS_HARVESTCRAFT_2_TREES {
    public static final String name = "Pam's Harvestcraft 2 - Trees";
    public static final String modid = "pamhc2trees";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Food;
  }

  public static final class PAMS_HARVESTCRAFT_2_FOOD_EXTENDED {
    public static final String name = "Pam's Harvestcraft 2 - Food Extended";
    public static final String modid = "pamhc2foodextended";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Food;
  }
  
  public static final class PATCHOULI {
    public static final String name = "Patchouli";
    public static final String modid = "patchouli";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Info;
  }
  
  /* Dang it. Another one that's stuck at Minecraft 1.12. I was too late!
  public static final class PROGRESSIVE_AUTOMATION {
    public static final String name = "Progressive Automation";
    public static final String modid = "progressiveautomation";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  */
  
  public static final class PROJECT_E {
    public static final String name  = "Project E";
    public static final String modid = "projecte";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Magic;
  }
  
  public static final class PROJECT_RED {
    public static final String name = "Project Red";
    public static final String modid = "projectred-core";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }
  
  public static final class QUARK {
    public static final String name  = "Quark";
    public static final String modid = "quark";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Vanilla;
  }
  
  // FUTURE: Railcraft potentially not coming to Minecraft 1.14+ (this message written Apr 2020)
  @Deprecated
  public static final class RAILCRAFT {
    public static final String name  = "Railcraft";
    public static final String modid = "railcraft";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Rail_Transport;
  }

  public static final class REDSTONE_FLUX {
    public static final String name  = "Redstone Flux API";
    public static final String modid = "redstoneflux";
    public static final boolean loaded = ModList.get().isLoaded(modid) || JavaUtils.packageExists("cofh.redstoneflux.api");
    public static final ModType type = ModType.API;
  }

  public static final class RFTOOLS_UTILITY {
    public static final String name = "RFTools Utility";
    public static final String modid = "rftoolsutility";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class SHADOWFACTS_FORGELIN {
    public static final String name = "Shadowfacts' Forgelin";
    public static final String modid = "forgelin";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Library;
  }
  
  public static final class SOUND_FILTERS {
    public static final String name = "Sound Filters";
    public static final String modid = "soundfilters";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Client;
  }
  
  public static final class TESLA {
    public static final String name  = "Tesla Energy";
    public static final String modid = "tesla";
    public static final boolean loaded = ModList.get().isLoaded(modid) || JavaUtils.packageExists("net.darkhax.tesla.api");
    public static final ModType type = ModType.API;
  }

  public static final class THE_BENEATH {
    public static final String name = "The Beneath";
    public static final String modid = "beneath";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Dimension;
  }

  public static final class THE_ONE_PROBE {
    public static final String name = "The One Probe";
    public static final String modid = "theoneprobe";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Info;
  }

  public static final class THERMAL_CULTIVATION {
    public static final String name = "Thermal Cultivation";
    public static final String modid = "thermal_cultivation";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Food;
  }

  public static final class THERMAL_EXPANSION { // The original
    public static final String name = "Thermal Expansion";
    public static final String modid = "thermal_expansion";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final class THERMAL_FOUNDATION {
    public static final String name = "Thermal Foundation";
    public static final String modid = "thermal_foundation";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Materials;
  }

  public static final class THERMAL_INNOVATION {
    public static final String name = "Thermal Innovation";
    public static final String modid = "thermal_innovation";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tools;
  }

  public static final class THERMAL_INTEGRATION {
    // Compatibility module for the Thermal series
    public static final String name = "Thermal Integration";
    public static final String modid = "thermal_integration";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Compatibility;
  }

  public static final class THERMAL_LOCOMOTION {
    public static final String name = "Thermal Locomotion";
    public static final String modid = "thermal_locomotion";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Rail_Transport;
  }

  public static final class TINKERS_CONSTRUCT {
    public static final String name = "Tinkers' Construct";
    public static final String modid = "tconstruct";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tools;
  }
  
  public static final class TINY_PROGRESSIONS {
    public static final String name = "Tiny Progressions";
    public static final String modid = "tp";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Misc;
  }

  public static final class TOOLS_COMPLEMENT {
    public static final String name = "Tool's Complement";
    public static final String modid = "tools_complement";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tools;
  }

  public static final class TRACK_API {
    public static final String name = "Track API";
    public static final String modid = "trackapi";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.API;
  }

  public static final class WTHIT { // What The Hell Is That? replacement of Hwyla for MC 1.16+
    public static final String name = "WTHIT";
    public static final String modid = "wthit";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Info;
  }
  
  public static final class XNET {
    public static final String name = "XNet";
    public static final String modid = "xnet";
    public static final boolean loaded = ModList.get().isLoaded(modid);
    public static final ModType type = ModType.Tech;
  }

  public static final void debug(){
    ADDSynthCore.log.info("Begin printing ADDSynthCore mod detection results:");

    final TreeMap<String, Boolean> modlist = new TreeMap<>();
    String mod_name;
    String mod_id;
    boolean loaded;
    ModType type;
    int max_string = 0;

    try{
      for(final Class clazz : Compatibility.class.getClasses()){
        if(clazz.isEnum() == false){
          try{
            mod_name = (String)(clazz.getField("name").get(null));
          }
          catch(NoSuchFieldException e){
            ADDSynthCore.log.error("Mod "+clazz.getSimpleName()+" has the name field misspelled.");
            continue;
          }

          try{
            mod_id = (String)(clazz.getField("modid").get(null));
          }
          catch(NoSuchFieldException e){
            ADDSynthCore.log.error("Mod "+clazz.getSimpleName()+" has the modid field misspelled.");
            continue;
          }

          try{
            loaded = (boolean)(clazz.getField("loaded").get(null));
          }
          catch(NoSuchFieldException e){
            ADDSynthCore.log.error("Mod "+clazz.getSimpleName()+" has the loaded field misspelled.");
            continue;
          }

          try{
            type = (ModType)(clazz.getField("type").get(null));
          }
          catch(NoSuchFieldException e){
            ADDSynthCore.log.error("Mod "+clazz.getSimpleName()+" has the type field misspelled.");
            continue;
          }
  
          modlist.put(mod_name, loaded);
          if(mod_name.length() > max_string){
            max_string = mod_name.length();
          }
        }
      }
    }
    catch(Exception e){
      ADDSynthCore.log.error("An error occured while printing mod detection results.", e);
    }

    for(final Map.Entry<String, Boolean> mod : modlist.entrySet()){
      mod_name = adjust_mod_name(mod.getKey(), max_string);
      ADDSynthCore.log.info(mod_name + ": "+(mod.getValue() ? "loaded" : "not detected"));
    }

    ADDSynthCore.log.info("Done checking. ADDSynthCore does not check for any mods that are not listed here.");
  }

  private static final String adjust_mod_name(final String mod_name, final int length){
    boolean first = true;
    final StringBuilder str = new StringBuilder(mod_name);
    while(str.length() < length){
      if(first){
        str.append(' ');
        first = false;
      }
      else{
        str.append('-');
      }
    }
    return str.toString();
  }

}
