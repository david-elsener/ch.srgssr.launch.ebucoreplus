// generated by com.github.vladislavsevruk:java-class-generator
package ch.srgssr.launch.editorial;

import ch.srgssr.launch.legal.assets.EditorialObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
*To define a collection / group of media resources, for example a series made
            of episodes.
        
*
*
*/@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EditorialGroup extends EditorialObject {

/**
*To provide the total number of episodes in a Series or a Season.
        
*
*/    private int totalNumberOfEpisodes;

/**
*To define a collection / group of media resources, for example a series made
            of episodes.
        
*
*/    private EditorialGroup editorialGroup;

/**
*To establish group/collection relationship between EditorialObjects.
        
*
*/    private EditorialObject member;

/**
*To provide the total number of members in a Group.
*
*/    private int totalNumberOfGroupMembers;
}
