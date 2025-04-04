package addsynth.core.util.game.data;

import java.util.ArrayList;
import addsynth.core.gameplay.team_manager.data.TeamDataUnit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;

/** Especially used in Team Manger, holds an ID name and a display Name, where
 *  the Display Name may have formatting codes, and the ID name is used to find
 *  the Team or Player in the game's data.
 */
public final class CombinedNameComponent {

  public static final CombinedNameComponent EMPTY = new CombinedNameComponent();

  private final Component displayName;
  private final String name;

  private CombinedNameComponent(){
    displayName = TextComponent.EMPTY;
    name = "";
  }

  private CombinedNameComponent(final Component component, final String name){
    displayName = component;
    this.name = name;
  }

  public CombinedNameComponent(final PlayerTeam team){
    displayName = team.getDisplayName();
    name = team.getName();
  }
  
  public CombinedNameComponent(final TeamDataUnit team_data){
    displayName = team_data.display_name;
    name = team_data.name;
  }
  
  public CombinedNameComponent(final Player player){
    displayName = player.getDisplayName();
    name = player.getScoreboardName();
  }

  /// If this was constructed with a Team, returns the Team ID.
  /// For players, this returns the player's name.  
  public final String getName(){
    return name;
  }

  /// Returns a Component with all stylings. If this is a player, the player's
  /// name is styled based on team settings, and includes prefixes and suffixes.
  public final Component getDisplayName(){
    return displayName;
  }

  /** Returns a copy of the Component without any styling. */
  public final Component getPlainComponent(){
    return displayName.plainCopy();
  }

  // TODO: These functions are here, for now, but I'll want my own interface that objects can implement
  //       that proves they have an Encode and Decode function, so I only have to call a common library
  //       function that automatically saves an array of these objects, while only requiring they implemne
  //       the Encode/Decode interface.
  public static final void encodeArray(final FriendlyByteBuf buf, final ArrayList<CombinedNameComponent> list){
    encodeArray(buf, list.toArray(new CombinedNameComponent[list.size()]));
  }
  
  public static final void encodeArray(final FriendlyByteBuf buf, final CombinedNameComponent[] list){
    buf.writeInt(list.length);
    for(CombinedNameComponent name : list){
      buf.writeComponent(name.displayName);
      buf.writeUtf(name.name);
    }
  }

  public static final CombinedNameComponent[] decodeArray(final FriendlyByteBuf buf){
    final int length = buf.readInt();
    final CombinedNameComponent[] list = new CombinedNameComponent[length];
    for(int i = 0; i < length; i++){
      list[i] = new CombinedNameComponent(buf.readComponent(), buf.readUtf());
    }
    return list;
  }

  @Override
  public final String toString(){
    return name;
  }

}
