package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.data.TeamData;
import addsynth.core.util.game.MessageUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team.Visibility;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.network.NetworkEvent;

public final class TeamManagerCommand {

  public static final class ClearDisplaySlot {
    private final int display_slot;
    public ClearDisplaySlot(int display_slot){
      this.display_slot = display_slot;
    }
    public static void encode(ClearDisplaySlot message, FriendlyByteBuf data){
      data.writeInt(message.display_slot);
    }
    public static ClearDisplaySlot decode(FriendlyByteBuf data){
      return new ClearDisplaySlot(data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(ClearDisplaySlot message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          scoreboard.setDisplayObjective(message.display_slot, null);
          TeamData.sync();
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class DeleteTeam {
    private final String team_name;
    public DeleteTeam(String team_name){
      this.team_name = team_name;
    }
    public static void encode(DeleteTeam message, FriendlyByteBuf data){
      data.writeUtf(message.team_name);
    }
    public static DeleteTeam decode(FriendlyByteBuf data){
      return new DeleteTeam(data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(DeleteTeam message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          scoreboard.removePlayerTeam(scoreboard.getPlayerTeam(message.team_name));
          TeamData.sync();
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class DeleteObjective {
    private final String objective_name;
    public DeleteObjective(String objective_name){
      this.objective_name = objective_name;
    }
    public static void encode(DeleteObjective message, FriendlyByteBuf data){
      data.writeUtf(message.objective_name);
    }
    public static DeleteObjective decode(FriendlyByteBuf data){
      return new DeleteObjective(data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(DeleteObjective message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          scoreboard.removeObjective(scoreboard.getObjective(message.objective_name));
          TeamData.sync();
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class SetDisplaySlot {
    private final String objective;
    private final int display_slot;
    public SetDisplaySlot(String objective, int display_slot){
      this.objective = objective;
      this.display_slot = display_slot;
    }
    public static void encode(SetDisplaySlot message, FriendlyByteBuf data){
      data.writeUtf(message.objective);
      data.writeInt(message.display_slot);
    }
    public static SetDisplaySlot decode(FriendlyByteBuf data){
      return new SetDisplaySlot(data.readUtf(), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(SetDisplaySlot message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final Objective objective = scoreboard.getObjective(message.objective);
          scoreboard.setDisplayObjective(message.display_slot, objective);
          TeamData.sync();
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class AddPlayerToTeam {
    private final String player;
    private final String team;
    public AddPlayerToTeam(String player, String team){
      this.player = player;
      this.team = team;
    }
    public static void encode(AddPlayerToTeam message, FriendlyByteBuf data){
      data.writeUtf(message.player);
      data.writeUtf(message.team);
    }
    public static AddPlayerToTeam decode(FriendlyByteBuf data){
      return new AddPlayerToTeam(data.readUtf(), data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(AddPlayerToTeam message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          if(scoreboard.addPlayerToTeam(message.player, scoreboard.getPlayerTeam(message.team))){
            TeamData.sync();
          }
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class RemovePlayerFromTeam {
    private final String player;
    private final String team_name;
    public RemovePlayerFromTeam(String player, String team){
      this.player = player;
      this.team_name = team;
    }
    public static void encode(RemovePlayerFromTeam message, FriendlyByteBuf data){
      data.writeUtf(message.player);
      data.writeUtf(message.team_name);
    }
    public static RemovePlayerFromTeam decode(FriendlyByteBuf data){
      return new RemovePlayerFromTeam(data.readUtf(), data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(RemovePlayerFromTeam message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          PlayerTeam team = scoreboard.getPlayersTeam(message.player);
          if(team != null){ // player is on a team
            if(team.getName().equals(message.team_name)){ // players team is the one we want him out of
              scoreboard.removePlayerFromTeam(message.player, team);
              TeamData.sync();
            }
          }
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class SetScore {
    private final String objective;
    private final String player;
    private final int new_score_value;
    public SetScore(String objective, String player, int new_score_value){
      this.objective = objective;
      this.player = player;
      this.new_score_value = new_score_value;
    }
    public static void encode(SetScore message, FriendlyByteBuf data){
      data.writeUtf(message.objective);
      data.writeUtf(message.player);
      data.writeInt(message.new_score_value);
    }
    public static SetScore decode(FriendlyByteBuf data){
      return new SetScore(data.readUtf(), data.readUtf(), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(SetScore message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final Objective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreatePlayerScore(message.player, objective);
          score.setScore(message.new_score_value);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class AddScore {
    private final String objective;
    private final String player;
    private final int score_to_add;
    public AddScore(String objective, String player, int score_to_add){
      this.objective = objective;
      this.player = player;
      this.score_to_add = score_to_add;
    }
    public static void encode(AddScore message, FriendlyByteBuf data){
      data.writeUtf(message.objective);
      data.writeUtf(message.player);
      data.writeInt(message.score_to_add);
    }
    public static AddScore decode(FriendlyByteBuf data){
      return new AddScore(data.readUtf(), data.readUtf(), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(AddScore message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final Objective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreatePlayerScore(message.player, objective);
          score.add(message.score_to_add);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class SubtractScore {
    private final String objective;
    private final String player;
    private final int score_to_subtract;
    public SubtractScore(String objective, String player, int score_to_subtract){
      this.objective = objective;
      this.player = player;
      this.score_to_subtract = score_to_subtract;
    }
    public static void encode(SubtractScore message, FriendlyByteBuf data){
      data.writeUtf(message.objective);
      data.writeUtf(message.player);
      data.writeInt(message.score_to_subtract);
    }
    public static SubtractScore decode(FriendlyByteBuf data){
      return new SubtractScore(data.readUtf(), data.readUtf(), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(SubtractScore message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final Objective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreatePlayerScore(message.player, objective);
          score.add(-message.score_to_subtract);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class ResetScore {
    private final String objective;
    private final String player;
    public ResetScore(String objective, String player){
      this.objective = objective;
      this.player = player;
    }
    public static void encode(ResetScore message, FriendlyByteBuf data){
      data.writeUtf(message.objective);
      data.writeUtf(message.player);
    }
    public static ResetScore decode(FriendlyByteBuf data){
      return new ResetScore(data.readUtf(), data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(ResetScore message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final Objective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreatePlayerScore(message.player, objective);
          score.reset();
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class AddObjective {
    private final String objective_id;
    private final String display_name;
    private final String criteria;
    public AddObjective(String objective_id, String display_name, String criteria){
      this.objective_id = objective_id;
      this.display_name = display_name;
      this.criteria = criteria;
    }
    public static void encode(AddObjective message, FriendlyByteBuf data){
      data.writeUtf(message.objective_id);
      data.writeUtf(message.display_name);
      data.writeUtf(message.criteria);
    }
    public static AddObjective decode(FriendlyByteBuf data){
      return new AddObjective(data.readUtf(), data.readUtf(), data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(AddObjective message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_objective(scoreboard, player, message.objective_id, message.display_name, message.criteria);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class EditObjective {
    private final String objective_id;
    private final String display_name;
    private final String criteria;
    public EditObjective(String objective_id, String display_name, String criteria){
      this.objective_id = objective_id;
      this.display_name = display_name;
      this.criteria = criteria;
    }
    public static void encode(EditObjective message, FriendlyByteBuf data){
      data.writeUtf(message.objective_id);
      data.writeUtf(message.display_name);
      data.writeUtf(message.criteria);
    }
    public static EditObjective decode(FriendlyByteBuf data){
      return new EditObjective(data.readUtf(), data.readUtf(), data.readUtf());
    }
    @SuppressWarnings("resource")
    public static void handle(EditObjective message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_objective(scoreboard, player, message.objective_id, message.display_name, message.criteria);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class AddTeam {
    private final String team_id;
    private final String display_name;
    private final boolean pvp;
    private final boolean see_invisible_allys;
    private final int team_color;
    private final int nametag_option;
    private final int death_message_option;
    private final String member_prefix;
    private final String member_suffix;
    public AddTeam(String team_id, String display_name, boolean pvp, boolean see_invisible_allys, int team_color,
                   int nametag_option, int death_message_option, String member_prefix, String member_suffix){
      this.team_id = team_id;
      this.display_name = display_name;
      this.pvp = pvp;
      this.see_invisible_allys = see_invisible_allys;
      this.team_color = team_color;
      this.nametag_option = nametag_option;
      this.death_message_option = death_message_option;
      this.member_prefix = member_prefix;
      this.member_suffix = member_suffix;
    }
    public static void encode(AddTeam message, FriendlyByteBuf data){
      data.writeUtf(message.team_id);
      data.writeUtf(message.display_name);
      data.writeBoolean(message.pvp);
      data.writeBoolean(message.see_invisible_allys);
      data.writeInt(message.team_color);
      data.writeInt(message.nametag_option);
      data.writeInt(message.death_message_option);
      data.writeUtf(message.member_prefix);
      data.writeUtf(message.member_suffix);
    }
    public static AddTeam decode(FriendlyByteBuf data){
      return new AddTeam(
        data.readUtf(),
        data.readUtf(),
        data.readBoolean(),
        data.readBoolean(),
        data.readInt(),
        data.readInt(),
        data.readInt(),
        data.readUtf(),
        data.readUtf()
      );
    }
    @SuppressWarnings("resource")
    public static void handle(AddTeam message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_team(scoreboard, player, message.team_id, message.display_name, message.pvp,
                    message.see_invisible_allys, message.team_color, message.nametag_option,
                    message.death_message_option, message.member_prefix, message.member_suffix);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  public static final class EditTeam {
    private final String team_id;
    private final String display_name;
    private final boolean pvp;
    private final boolean see_invisible_allys;
    private final int team_color;
    private final int nametag_option;
    private final int death_message_option;
    private final String member_prefix;
    private final String member_suffix;
    public EditTeam(String team_id, String display_name, boolean pvp, boolean see_invisible_allys, int team_color,
                   int nametag_option, int death_message_option, String member_prefix, String member_suffix){
      this.team_id = team_id;
      this.display_name = display_name;
      this.pvp = pvp;
      this.see_invisible_allys = see_invisible_allys;
      this.team_color = team_color;
      this.nametag_option = nametag_option;
      this.death_message_option = death_message_option;
      this.member_prefix = member_prefix;
      this.member_suffix = member_suffix;
    }
    public static void encode(EditTeam message, FriendlyByteBuf data){
      data.writeUtf(message.team_id);
      data.writeUtf(message.display_name);
      data.writeBoolean(message.pvp);
      data.writeBoolean(message.see_invisible_allys);
      data.writeInt(message.team_color);
      data.writeInt(message.nametag_option);
      data.writeInt(message.death_message_option);
      data.writeUtf(message.member_prefix);
      data.writeUtf(message.member_suffix);
    }
    public static EditTeam decode(FriendlyByteBuf data){
      return new EditTeam(
        data.readUtf(),
        data.readUtf(),
        data.readBoolean(),
        data.readBoolean(),
        data.readInt(),
        data.readInt(),
        data.readInt(),
        data.readUtf(),
        data.readUtf()
      );
    }
    @SuppressWarnings("resource")
    public static void handle(EditTeam message, Supplier<NetworkEvent.Context> context_supplier){
      final NetworkEvent.Context context = context_supplier.get();
      final ServerPlayer player = context.getSender();
      if(player != null){
        context.enqueueWork(() -> {
          final MinecraftServer server = player.getLevel().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_team(scoreboard, player, message.team_id, message.display_name, message.pvp,
                    message.see_invisible_allys, message.team_color, message.nametag_option,
                    message.death_message_option, message.member_prefix, message.member_suffix);
        });
      }
      context.setPacketHandled(true);
    }
  }
  
  private static final void edit_team(final Scoreboard scoreboard, final ServerPlayer player, final String team_name, final String display_name,
                                      final boolean pvp, final boolean see_invisible_allys, final int team_color, final int nametag_option,
                                      final int death_message_option, final String member_prefix, final String member_suffix){
    if(team_name.isEmpty()){
      MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.create_team_failed");
      return;
    }
    PlayerTeam team = scoreboard.getPlayerTeam(team_name);
    if(team == null){
      team = scoreboard.addPlayerTeam(team_name);
    }
    
    team.setDisplayName(new TextComponent(display_name.isEmpty() ? team_name : display_name));
    team.setAllowFriendlyFire(pvp);
    team.setSeeFriendlyInvisibles(see_invisible_allys);
    team.setColor(ChatFormatting.getById(team_color));
    team.setNameTagVisibility(Visibility.values()[nametag_option]);
    team.setDeathMessageVisibility(Visibility.values()[death_message_option]);
    team.setPlayerPrefix(new TextComponent(member_prefix));
    team.setPlayerSuffix(new TextComponent(member_suffix));
    
    // MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.edit_team_success", team_name);
    TeamData.sync();
  }

  private static final void edit_objective(final Scoreboard scoreboard, final ServerPlayer player, final String objective_name, final String display_name, final String criteria_name){
    if(objective_name.isEmpty()){
      MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.create_objective_failed");
      return;
    }
    final Objective existing_objective = scoreboard.getObjective(objective_name);
    final ObjectiveCriteria criteria = TeamData.getCriteria(criteria_name);
    if(existing_objective == null){
      scoreboard.addObjective(objective_name, criteria, new TextComponent(display_name), criteria.getDefaultRenderType());
    }
    else{
      // Objective exists
      if(criteria_name.equals(existing_objective.getCriteria().getName())){
        existing_objective.setDisplayName(new TextComponent(display_name));
      }
      else{
        // Can't change criteria. Must delete existing Objective and create a new one.
        scoreboard.removeObjective(existing_objective);
        scoreboard.addObjective(objective_name, criteria, new TextComponent(display_name), criteria.getDefaultRenderType());
      }
    }
    TeamData.sync();
  }

}
