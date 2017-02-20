package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandTime extends SimpleCommand {

	public CommandTime(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.time("time"), Argument.optional(Argument.world(cc, "world")));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		World world;
		if (args.has("world")) { world = args.get("world"); }
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else throw new CommandException("You must specify a world.");

		long ticks = args.get("time");

		world.getProperties().setWorldTime(ticks);
		src.sendMessage(Text.of(TextColors.GOLD, "You set the time in world ", TextColors.RED, world.getName(), TextColors.GOLD, " to ", TextColors.RED, ticks, TextColors.GOLD, " ticks."));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.time";
	}

	@Override
	public String getName() {
		return "Time";
	}
}