package me.mrdaniel.crucialcraft.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;

public class CommandTree extends CCObject implements CommandCallable {

	@Nullable final SimpleCommand base;
	private final Map<String, SimpleCommand> tree;

	public CommandTree(@Nonnull final CrucialCraft cc, @Nullable final SimpleCommand base) {
		super(cc);

		this.base = base;
		this.tree = Maps.newHashMap();
	}

	public CommandTree add(@Nonnull final String key, @Nonnull final SimpleCommand command) {
		this.tree.put(key, command); return this;
	}

	@Override
	public CommandResult process(final CommandSource src, final String arguments){
		for (String key : this.tree.keySet()) {
			if (arguments.startsWith(key)) { this.tree.get(key).process(src, arguments.replaceFirst(key, "")); return CommandResult.success(); }
		}
		if (this.base != null) { this.base.process(src, arguments); }
		else src.sendMessage(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No command was found for the given input."));
		
		return CommandResult.success();
	}

	@Override
	public List<String> getSuggestions(final CommandSource src, final String arguments, final Location<World> targetPosition) {
		for (String key : this.tree.keySet()) {
			if (arguments.startsWith(key)) { return this.tree.get(key).getSuggestions(src, arguments.replace(key, ""), targetPosition); }
		}
		List<String> suggestions = Lists.newArrayList(this.tree.keySet());
		if (this.base != null) { suggestions.addAll(this.base.getSuggestions(src, arguments, targetPosition)); }
		return suggestions;
	}

	@Override
	public boolean testPermission(final CommandSource src) {
		if (this.base != null) { return this.base.testPermission(src); }
		else { return this.tree.get(0).testPermission(src); }
	}

	@Override
	public Optional<Text> getShortDescription(final CommandSource src) {
		return this.base != null ? this.base.getShortDescription(src) : this.tree.get(0).getShortDescription(src);
	}

	@Override
	public Optional<Text> getHelp(final CommandSource src) {
		return this.base != null ? this.base.getHelp(src) : this.tree.get(0).getHelp(src);
	}

	@Override
	public Text getUsage(final CommandSource src) {
		return this.base != null ? this.base.getUsage(src) : this.tree.get(0).getUsage(src);
	}
}