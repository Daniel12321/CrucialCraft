package me.mrdaniel.crucialcraft.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.ArgumentException;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class SimpleCommand extends CCObject implements CommandCallable {

	private final Argument[] args;

	public SimpleCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc);

		this.args = args;
	}

	@Override
	public CommandResult process(final CommandSource src, final String arguments) {
		Arguments args = new Arguments();

		List<String> arglist = Lists.newArrayList(arguments.split(" "));
		arglist.removeIf(str -> str.equals(""));

		for (Argument arg : this.args) {
			try { args.add(arg.getKey(), arg.translate(arglist)); }
			catch (final ArgumentException exc) { src.sendMessage(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, exc.getMessage())); src.sendMessage(this.getUsage(src)); return CommandResult.success(); }
		}
		if (!src.hasPermission(this.getPermission(args))) { src.sendMessage(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You dont have permission to use that command.")); return CommandResult.success(); }
		try { this.execute(src, args); }
		catch (final CommandException exc) { src.sendMessage(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, exc.getMessage())); }
		return CommandResult.success();
	}

	@Override
	public List<String> getSuggestions(final CommandSource src, final String arguments, final Location<World> targetPosition) {
		if (this.args.length == 0) { return Lists.newArrayList(); }
		if (arguments.equals("") || arguments.equalsIgnoreCase(" ")) { return this.args[0].getChoices(); }

		String[] s = arguments.split(" ");

		if (this.args.length >= s.length) {
			if (arguments.endsWith(" ")) { return this.args[s.length].getChoices(); }
			else { return this.args[s.length-1].getChoices().stream().filter(str -> str.startsWith(s[s.length-1])).collect(Collectors.toList()); }
		}
		else { return Lists.newArrayList(); }
	}

	@Override public boolean testPermission(final CommandSource src) { return src.hasPermission(this.getPermission(new Arguments())); }
	@Override public Optional<Text> getShortDescription(final CommandSource src) { return Optional.of(Text.of(TextColors.AQUA, "CrucialCraft | ", this.getName(), " Command")); }
	@Override public Optional<Text> getHelp(final CommandSource src) { return Optional.of(Text.of(TextColors.AQUA, "CrucialCraft | ", this.getName(), " Command")); }
	@Override public Text getUsage(final CommandSource src) {
		String str = "Usage: /" + this.getName().toLowerCase();
		for (Argument arg : this.args) { str += " " + arg.getUsage(); }
		return Text.builder().append(Text.of(str)).color(TextColors.RED).onClick(TextActions.suggestCommand(str)).build();
	}

	public abstract void execute(@Nonnull final CommandSource src, @Nonnull final Arguments args) throws CommandException;
	public abstract String getName();
	public abstract String getPermission(@Nonnull final Arguments args);
}