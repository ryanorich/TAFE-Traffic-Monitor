package library;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCombination;
import javafx.util.Duration;

/**
 * Helper class for JavaFX
 * 
 * @author Ryan Rich
 *
 */
public class FXHelper
{
    /**
     * Configures a Menu item
     * 
     * @param name Display Name
     * @param acc keyboard shortcut
     * @param action lamda expression for action to take.
     * @return the MenuItem
     */
    public static MenuItem makeMenuItem (String name, String acc, EventHandler<ActionEvent> action)
    {
       MenuItem mi = new MenuItem (name);
       if (acc != "") mi.setAccelerator(KeyCombination.keyCombination(acc));
       mi.setOnAction(action);
       return mi;
    }
    
    /**
     * Hack to set the delay times for tooltops in Java 8
     * Reference - https://coderanch.com/t/622070/java/control-Tooltip-visible-time-duration
     * 
     * @param openDelayInMillis         Hover time before displaying
     * @param visibleDurationInMillis   Display time before closing
     * @param closeDelayInMillis        Time after loosing focus before closing
     */
    @SuppressWarnings({"rawtypes", "unchecked"}) 
    public static void setupCustomTooltipBehavior(int openDelayInMillis, int visibleDurationInMillis, int closeDelayInMillis) {
        try {
             
            
            Class TTBehaviourClass = null;
            Class<?>[] declaredClasses = Tooltip.class.getDeclaredClasses();
            for (Class c:declaredClasses) {
                if (c.getCanonicalName().equals("javafx.scene.control.Tooltip.TooltipBehavior")) {
                    TTBehaviourClass = c;
                    break;
                }
            }
            if (TTBehaviourClass == null) 
            {// abort
                return;
            }
            
            Constructor constructor = TTBehaviourClass.getDeclaredConstructor(
                    Duration.class, Duration.class, Duration.class, boolean.class);
            if (constructor == null) 
            {// abort
                return;
            }
            constructor.setAccessible(true);
            Object newTTBehaviour = constructor.newInstance(
                    new Duration(openDelayInMillis), new Duration(visibleDurationInMillis), 
                    new Duration(closeDelayInMillis), false);
            if (newTTBehaviour == null) 
            {// abort
                return;
            }
            Field ttbehaviourField = Tooltip.class.getDeclaredField("BEHAVIOR");
            if (ttbehaviourField == null) 
            {// abort
                return;
            }
            ttbehaviourField.setAccessible(true);
             
            // Cache the default behavior if needed.
            //Object defaultTTBehavior = ttbehaviourField.get(Tooltip.class);
            ttbehaviourField.set(Tooltip.class, newTTBehaviour);
             
        } catch (Exception e) {
            System.out.println("Aborted setup due to error:" + e.getMessage());
        }
    }
}
