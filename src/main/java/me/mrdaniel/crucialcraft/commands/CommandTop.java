package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandTop extends PlayerCommand {

	public CommandTop(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		Location<World> loc = src.getLocation();
		World world = loc.getExtent();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		for (int y = 255; y > 0; y--) {
			if (world.getBlock(x, y, z).getType() != BlockTypes.AIR) {
				PlayerFile file = super.getCrucialCraft().getPlayerData().get(src.getUniqueId());
				file.setLastLocation(new Teleport(src.getLocation(), src.getHeadRotation()));

				src.setLocation(world.getLocation(x + 0.5, y + 1.0, z + 0.5));
				src.sendMessage(Text.of(TextColors.GOLD, "You were teleported to the highest block."));
				return;
			}
		}
		throw new CommandException("No valid block was found.");
	}

	@Override
	public String getPermission() {
		return "cc.top";
	}

	@Override
	public String getName() {
		return "Top";
	}
}