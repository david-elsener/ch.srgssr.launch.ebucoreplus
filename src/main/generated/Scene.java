// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.planning.campaigns;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Scene {

    private Take hasTake;

    private Programme hasProgramme;

    private Shot hasShot;
}
