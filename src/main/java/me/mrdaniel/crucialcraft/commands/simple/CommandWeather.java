package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weather;
import org.spongepowered.api.world.weather.Weathers;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandWeather extends PermissionCommand {

	public CommandWeather(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String weather = args.<String>getOne("weather").get();
		World world;

		if (args.<String>getOne("world").isPresent()) {
			Optional<World> w = super.getCrucialCraft().getGame().getServer().getWorld(args.<String>getOne("world").get());
			if (w.isPresent()) { world = w.get(); }
			else { Messages.NO_SUCH_WORLD.send(src); return; }
		}
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else { Messages.MUST_SPECIFY_WORLD.send(src); return; }

		Weather w;
		if (weather.startsWith("s") || weather.startsWith("c")) { w = Weathers.CLEAR; }
		else if (weather.startsWith("r")) { w = Weathers.RAIN; }
		else if (weather.startsWith("t") || weather.startsWith("l")) { w = Weathers.THUNDER_STORM; }
		else { Messages.NO_SUCH_WEATHER.send(src); return; }

		if (args.<Long>getOne("duration").isPresent()) {
			long duration = args.<Long>getOne("duration").get();
			world.setWeather(w, duration);
			src.sendMessage(Text.of(TextColors.GOLD, "You set the weather in world ", TextColors.RED, world.getName(), TextColors.GOLD, " to ", TextColors.RED, w.getName(), TextColors.GOLD, " for ", TextColors.RED, duration, TextColors.GOLD, " ticks."));
		}
		else {
			world.setWeather(w);
			src.sendMessage(Text.of(TextColors.GOLD, "You set the weather in world ", TextColors.RED, world.getName(), TextColors.GOLD, " to ", TextColors.RED, w.getName(), TextColors.GOLD, "."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.weather";
	}
}