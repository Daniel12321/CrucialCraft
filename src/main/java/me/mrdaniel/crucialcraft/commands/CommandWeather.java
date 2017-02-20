package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weather;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandWeather extends SimpleCommand {

	public CommandWeather(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.weather("weather"), Argument.optional(Argument.world(cc, "world")), Argument.optional(Argument.integer("duration")));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		World world;
		if (args.has("world")) { world = args.get("world"); }
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else throw new CommandException("You must specify a world.");

		Weather w = args.get("weather");

		if (args.has("duration")) {
			int duration = args.get("duration");
			world.setWeather(w, duration);
			src.sendMessage(Text.of(TextColors.GOLD, "You set the weather in world ", TextColors.RED, world.getName(), TextColors.GOLD, " to ", TextColors.RED, w.getName(), TextColors.GOLD, " for ", TextColors.RED, duration, TextColors.GOLD, " ticks."));
		}
		else {
			world.setWeather(w);
			src.sendMessage(Text.of(TextColors.GOLD, "You set the weather in world ", TextColors.RED, world.getName(), TextColors.GOLD, " to ", TextColors.RED, w.getName(), TextColors.GOLD, "."));
		}
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.weather";
	}

	@Override
	public String getName() {
		return "Weather";
	}
}