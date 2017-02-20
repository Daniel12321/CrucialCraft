package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandTPPos extends TargetSelfOrOtherPlayerCommand {

	public CommandTPPos(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.optional(Argument.world(cc, "world")), Argument.doubleNum("x"), Argument.doubleNum("y"), Argument.doubleNum("z"));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		World w;
		if (args.has("world")) { w = args.get("world"); }
		else { w = target.getWorld(); }

		double x = args.get("x");
		double y = args.get("y");
		double z = args.get("z");

		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		file.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));

		target.setLocation(new Location<World>(w, x, y, z));
		target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, w.getName(), " ", x, " ", y, " ", z, TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to ", TextColors.RED, w.getName(), " ", x, " ", y, " ", z, TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.tppos";
	}

	@Override
	public String getName() {
		return "TPPos";
	}
}