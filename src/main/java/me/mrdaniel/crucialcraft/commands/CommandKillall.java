package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandKillall extends SimpleCommand {

	public CommandKillall(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.entitytype(cc, "type"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		World world;
		if (args.has("world")) { world = args.get("world"); }
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else throw new CommandException("You must specify a world.");

		EntityType type = args.get("type");
		world.getEntities(e -> e.getType() == type).forEach(e -> e.remove());
		src.sendMessage(Text.of(TextColors.GOLD, "You removed all mobs of type ", TextColors.RED, type.getName(), TextColors.GOLD, " from world ", TextColors.RED, world.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.killall";
	}

	@Override
	public String getName() {
		return "KillAll";
	}
}