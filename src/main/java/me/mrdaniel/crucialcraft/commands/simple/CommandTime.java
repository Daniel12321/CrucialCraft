package me.mrdaniel.crucialcraft.commands.simple;

import java.io.Console;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;

public class CommandTime extends PermissionCommand {

	public CommandTime(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		World world;

		if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else if (src instanceof Console) {
			if (args.getOne("world").isPresent()) { world = args.<World>getOne("world").get(); }
		}

		String time = args.<String>getOne("time").get();

		
	}

	@Override
	public String getPermission() {
		return "cc.time";
	}
}