package net.citizensnpcs.trait.versioned;

import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.TextDisplay.TextAlignment;

import net.citizensnpcs.api.command.Command;
import net.citizensnpcs.api.command.CommandContext;
import net.citizensnpcs.api.command.Flag;
import net.citizensnpcs.api.command.Requirements;
import net.citizensnpcs.api.command.exception.CommandException;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.util.Messaging;

@TraitName("textdisplaytrait")
public class TextDisplayTrait extends Trait {
    @Persist
    private TextAlignment alignment;
    @Persist
    private Color bgcolor;
    @Persist
    private Integer lineWidth;
    @Persist
    private Boolean seeThrough;
    @Persist
    private Boolean shadowed;

    public TextDisplayTrait() {
        super("textdisplaytrait");
    }

    @Override
    public void onSpawn() {
        TextDisplay display = (TextDisplay) npc.getEntity();
        if (shadowed != null) {
            display.setShadowed(shadowed);
        }
        if (seeThrough != null) {
            display.setSeeThrough(seeThrough);
        }
        if (lineWidth != null) {
            display.setLineWidth(lineWidth);
        }
        if (alignment != null) {
            display.setAlignment(alignment);
        }
        if (bgcolor != null) {
            display.setBackgroundColor(bgcolor);
        }
    }

    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
    }

    public void setBackgroundColor(Color bgcolor) {
        this.bgcolor = bgcolor;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setSeeThrough(Boolean seeThrough) {
        this.seeThrough = seeThrough;
    }

    public void setShadowed(Boolean shadowed) {
        this.shadowed = shadowed;
    }

    @Command(
            aliases = { "npc" },
            usage = "textdisplay --shadowed [true|false] --seethrough [true|false] --line_width [width]",
            desc = "",
            modifiers = { "textdisplay" },
            min = 1,
            max = 1,
            permission = "citizens.npc.textdisplay")
    @Requirements(selected = true, ownership = true, types = { EntityType.TEXT_DISPLAY })
    public static void display(CommandContext args, CommandSender sender, NPC npc, @Flag("shadowed") Boolean shadowed,
            @Flag("seethrough") Boolean seethrough, @Flag("line_width") Integer lineWidth,
            @Flag("bgcolor") Color bgcolor, @Flag("alignment") TextAlignment alignment) throws CommandException {
        TextDisplayTrait trait = npc.getOrAddTrait(TextDisplayTrait.class);
        String output = "";
        if (shadowed != null) {
            trait.setShadowed(shadowed);
        }
        if (seethrough != null) {
            trait.setSeeThrough(seethrough);
        }
        if (lineWidth != null) {
            trait.setLineWidth(lineWidth);
        }
        if (alignment != null) {
            trait.setAlignment(alignment);
        }
        if (bgcolor != null) {
            trait.setBackgroundColor(bgcolor);
        }
        trait.onSpawn();
        if (!output.isEmpty()) {
            Messaging.send(sender, output.trim());
        }
    }
}
