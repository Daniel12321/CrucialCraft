package me.mrdaniel.crucialcraft.commands.tp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandTop extends PlayerCommand {

	public CommandTop(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Location<World> loc = target.getLocation();
		World world = loc.getExtent();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		for (int y = 255; y > 0; y--) {
			if (world.getBlock(x, y, z).getType() != BlockTypes.AIR) {
				CCPlayerData data = target.get(CCPlayerData.class).get();
				data.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));
				target.offer(data);

				target.setLocation(world.getLocation(x + 0.5, y + 1.0, z + 0.5));
				target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to the highest block."));
				src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to the highest block.")));
				return;
			}
		}
		Messages.NO_BLOCK_FOUND.send(src.orElse(target));
	}

	@Override
	public String getPermission() {
		return "cc.top";
	}
}