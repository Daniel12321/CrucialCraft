package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandTime extends PermissionCommand {

	public CommandTime(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String time = args.<String>getOne("time").get();
		World world;

		if (args.<String>getOne("world").isPresent()) {
			Optional<World> w = super.getCrucialCraft().getGame().getServer().getWorld(args.<String>getOne("world").get());
			if (w.isPresent()) { world = w.get(); }
			else { Messages.NO_SUCH_WORLD.send(src); return; }
		}
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else { Messages.MUST_SPECIFY_WORLD.send(src); return; }

		long ticks;

		if (time.equalsIgnoreCase("d") || time.equalsIgnoreCase("day")) { ticks = 0; }
		else if (time.startsWith("n")) { ticks = 13000; }
		else if (time.startsWith("du")) { ticks = 11000; }
		else if (time.startsWith("da")) { ticks = 22000; }
		else {
			try { ticks = Long.parseLong(time); }
			catch (final NumberFormatException exc) { Messages.NO_SUCH_TIME.send(src); return; }
		}

		world.getProperties().setWorldTime(ticks);
		src.sendMessage(Text.of(TextColors.GOLD, "You set the time in world ", TextColors.RED, world.getName(), TextColors.GOLD, " to ", TextColors.RED, ticks, TextColors.GOLD, " ticks."));
	}

	@Override
	public String getPermission() {
		return "cc.time";
	}
}