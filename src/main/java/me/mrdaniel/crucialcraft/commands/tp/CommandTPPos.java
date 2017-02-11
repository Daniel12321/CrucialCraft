package me.mrdaniel.crucialcraft.commands.tp;

import java.util.Optional;

import javax.annotation.Nonnull;

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

public class CommandTPPos extends PlayerCommand {

	public CommandTPPos(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<World> w = args.<World>getOne("world");
		World world = w.isPresent() ? w.get() : target.getWorld();
		double x = args.<Double>getOne("x").get();
		double y = args.<Double>getOne("y").get();
		double z = args.<Double>getOne("z").get();

		CCPlayerData data = target.get(CCPlayerData.class).get();
		data.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));
		target.offer(data);

		target.setLocation(new Location<World>(world, x, y, z));
		target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, world.getName(), " ", x, " ", y, " ", z, TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to ", TextColors.RED, world.getName(), " ", x, " ", y, " ", z, TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.tppos";
	}
}