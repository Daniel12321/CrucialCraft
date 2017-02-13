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
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandTPPos extends TargetPlayerCommand {

	public CommandTPPos(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		World world;
		if (args.<String>getOne("world").isPresent()) {
			Optional<World> w = super.getCrucialCraft().getGame().getServer().getWorld(args.<String>getOne("world").get());
			if (w.isPresent()) { world = w.get(); }
			else { Messages.NO_SUCH_WORLD.send(src.orElse(target)); return; }
		}
		else { world = target.getWorld(); }

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

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}