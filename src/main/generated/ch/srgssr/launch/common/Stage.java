// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.common;

import ch.srgssr.launch.legal.assets.Location;

import lombok.Data;
import lombok.experimental.Accessors;

/**
*To define a Stage.
*
*
*/@Accessors(chain = true)
@Data
public class Stage {

/**
*To associate a Location with a Stage.
*
*/    private Location stageLocation;

/**
*A summary of the resource.
*
*/    private String description;

/**
*The designation of the resource.
*
*/    private String name;

/**
*A locator from where the Resource can be accessed.
*
*/    private Locator locator;
}
