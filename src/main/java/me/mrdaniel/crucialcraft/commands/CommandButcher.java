package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandButcher extends SimpleCommand {

	public CommandButcher(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.world(cc, "world")));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		World world;
		if (args.has("world")) { world = args.get("world"); }
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else { throw new CommandException("You must specify a world."); }

		world.getEntities(e -> e instanceof Monster).forEach(e -> e.remove());
		src.sendMessage(Text.of(TextColors.GOLD, "You removed all monsters from world ", TextColors.RED, world.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.butcher";
	}

	@Override
	public String getName() {
		return "Butcher";
	}
}