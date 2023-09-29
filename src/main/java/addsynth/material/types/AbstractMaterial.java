package addsynth.material.types;

import addsynth.core.util.java.StringUtil;

/** This is the base class for all materials. */
public abstract class AbstractMaterial {

  public final String name;
  
  public AbstractMaterial(final String name){
    this.name = name;
  }

  @Override
  public String toString(){
    return "Material{Type: "+this.getClass().getSimpleName()+", Name: "+StringUtil.Capitalize(name)+"}";
  }

}
