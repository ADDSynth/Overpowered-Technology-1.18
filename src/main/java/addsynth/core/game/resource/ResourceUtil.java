package addsynth.core.game.resource;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.recipe.RecipeCollection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

/** This class is used to update cached values whenever resources are reloaded.
 *  This happens when the server first starts / client joins a world, or whenever
 *  a Player presses F3 + T. The data contained in DataPacks (such as recipes,
 *  tags, functions, and loot tables) could've changed after a resource reload.
 *  Call {@link #addListener(Runnable)} and pass in the code you need to execute
 *  after a resource reload.
 *  {@link RecipeCollection}s use this to rebuild the recipe cache and item filter.
 *  Item tags are automatically updated internally by Minecraft.
 */
public final class ResourceUtil extends SimplePreparableReloadListener<Void> {

  private static final ArrayList<Runnable> responders = new ArrayList<>();

  public static final void addListener(final Runnable executable){
    if(responders.contains(executable)){
      ADDSynthCore.log.error("That function is already registered as an event responder.");
      Thread.dumpStack();
    }
    else{
      responders.add(executable);
    }
  }

  @Override
  protected final Void prepare(ResourceManager resource_manager, ProfilerFiller profiler){
    return null;
  }

  @Override
  protected final void apply(Void object, ResourceManager resource_manager, ProfilerFiller profiler){
    profiler.push("ADDSynthCore: Responding to Resource Reload");
    for(final Runnable responder : responders){
      responder.run();
    }
    profiler.pop();
  }

}
