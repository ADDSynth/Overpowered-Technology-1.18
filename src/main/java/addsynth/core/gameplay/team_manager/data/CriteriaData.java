package addsynth.core.gameplay.team_manager.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.java.list.Sorters;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class CriteriaData {

  private static final ArrayList<ResourceLocation> statistics = new ArrayList<ResourceLocation>();
  private static final ArrayList<Component> blocks = new ArrayList<Component>();
  private static final ArrayList<Component> items_with_durability = new ArrayList<Component>();
  private static final ArrayList<Component> items = new ArrayList<Component>();
  private static final ArrayList<Component> entities = new ArrayList<Component>();
  public static final Component[] standard_criteria = {
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.dummy"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.trigger"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.death_count"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.player_kills"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.total_kills"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.health"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.xp"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.xp_level"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.air"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.food"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria.armor")
  };

  // TODO: Add ability to use ID names or translatable names, not sure if it will be helpful or not.
  public static boolean translate_names;

  public static final void calculate(){
    try{
      calculateBlocks();
      calculateItems();
      calculateStatistics();
      calculateEntities();
    }
    catch(Exception e){
      ADDSynthCore.log.error("Encountered an error while calculating Criteria Data for the Team Manager.", e);
    }
  }
  
  private static final void calculateBlocks(){
    final ArrayList<ResourceLocation> all_blocks = new ArrayList<ResourceLocation>(CommonUtil.getAllBlockIDs());
    Collections.sort(all_blocks, Sorters.NameComparer);
    blocks.clear();
    blocks.ensureCapacity(all_blocks.size());
    for(ResourceLocation id : all_blocks){
      blocks.add(new TextComponent(id.toString()));
    }
  }
  
  @SuppressWarnings("null")
  private static final void calculateItems(){
    final Collection<Item> items = CommonUtil.getAllItems();
    final int size = items.size();
    // create new lists
    final ArrayList<String> item_list = new ArrayList<String>(size);
    final ArrayList<String> item_with_durability_list = new ArrayList<String>();
    // add items
    for(Item item : items){
      item_list.add(item.getRegistryName().toString());
      if(item.canBeDepleted()){
        item_with_durability_list.add(item.getRegistryName().toString());
      }
    }
    // sort alphabetically
    Collections.sort(item_list);
    Collections.sort(item_with_durability_list);
    // create components.
    CriteriaData.items.clear();
    CriteriaData.items.ensureCapacity(size);
    for(String name : item_list){
      CriteriaData.items.add(new TextComponent(name));
    }
    CriteriaData.items_with_durability.clear();
    CriteriaData.items_with_durability.ensureCapacity(item_with_durability_list.size());
    for(String name : item_with_durability_list){
      CriteriaData.items_with_durability.add(new TextComponent(name));
    }
  }
  
  private static final void calculateStatistics(){
    statistics.clear();
    statistics.addAll(Registry.CUSTOM_STAT.keySet());
    Collections.sort(statistics, Sorters.NameComparer);
  }
  
  private static final void calculateEntities(){
    final ArrayList<ResourceLocation> all_entities = new ArrayList<ResourceLocation>(CommonUtil.getAllEntityIDs());
    Collections.sort(all_entities, Sorters.NameComparer);
    entities.clear();
    entities.ensureCapacity(all_entities.size());
    for(ResourceLocation id : all_entities){
      entities.add(new TextComponent(id.toString()));
    }
  }
  
  
  
  
  // Statistics have a client-translated Display Name, so they must be calculated each time,
  // in case the player changes the language in-game.
  public static final Component[] getStatistics(){
    final int length = statistics.size();
    final Component[] statistic_names = new Component[length];
    ResourceLocation id;
    String name;
    Optional<ResourceLocation> statistic;
    int i;
    for(i = 0; i < length; i++){
      id = statistics.get(i);
      name = id.toString();
      statistic = Registry.CUSTOM_STAT.getOptional(id);
      if(statistic.isPresent()){
        statistic_names[i] = new TranslatableComponent("stat."+(name.replace(':', '.')));
      }
      else{
        statistic_names[i] = new TextComponent("stat."+(name.replace(':', '.')));
      }
    }
    return statistic_names;
  }
  
  public static final String getStatisticID(int id){
    return ArrayUtil.isInsideBounds(id, statistics.size()) ? statistics.get(id).toString() : "null";
  }
  
  public static final int getStatisticIndex(String statistic_id){
    return statistics.indexOf(new ResourceLocation(statistic_id));
  }
  
  public static final Component[] getAllBlocks(){
    return blocks.toArray(new Component[blocks.size()]);
  }
  
  public static final Component[] getAllItems(){
    return items.toArray(new Component[items.size()]);
  }
  
  public static final Component[] getItemsWithDurability(){
    return items_with_durability.toArray(new Component[items_with_durability.size()]);
  }

  public static final TextComponent[] chat_colors = {
    // the Minecraft colors are translated, however the teams use the TextFormatting colors.
    new TextComponent(ChatFormatting.BLACK.getName()),
    new TextComponent(ChatFormatting.DARK_BLUE.getName()),
    new TextComponent(ChatFormatting.DARK_GREEN.getName()),
    new TextComponent(ChatFormatting.DARK_AQUA.getName()),
    new TextComponent(ChatFormatting.DARK_RED.getName()),
    new TextComponent(ChatFormatting.DARK_PURPLE.getName()),
    new TextComponent(ChatFormatting.GOLD.getName()),
    new TextComponent(ChatFormatting.GRAY.getName()),
    new TextComponent(ChatFormatting.DARK_GRAY.getName()),
    new TextComponent(ChatFormatting.BLUE.getName()),
    new TextComponent(ChatFormatting.GREEN.getName()),
    new TextComponent(ChatFormatting.AQUA.getName()),
    new TextComponent(ChatFormatting.RED.getName()),
    new TextComponent(ChatFormatting.LIGHT_PURPLE.getName()),
    new TextComponent(ChatFormatting.YELLOW.getName()),
    new TextComponent(ChatFormatting.WHITE.getName()),
  };

  public static final Component[] getEntities(){
    return entities.toArray(new Component[entities.size()]);
  }

}
