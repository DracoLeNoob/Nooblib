package fr.ideria.nooblib.discord.slashcommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public abstract class SlashCommand {
    private final String id;
    private final String description;
    private final List<OptionData> options;

    public SlashCommand(String id, String description) {
        this.id = id;
        this.description = description;
        this.options = new ArrayList<>();
    }

    public SlashCommand addOption(OptionData option){
        options.add(option);
        return this;
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    public String getId() { return id; }
    public String getDescription() { return description; }
    public List<OptionData> getOptions() { return options; }
}
