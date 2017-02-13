package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandKillall extends PermissionCommand {

	public CommandKillall(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		World world;
		if (args.<String>getOne("world").isPresent()) {
			Optional<World> w = super.getCrucialCraft().getGame().getServer().getWorld(args.<String>getOne("world").get());
			if (w.isPresent()) { world = w.get(); }
			else { Messages.NO_SUCH_WORLD.send(src); return; }
		}
		else if (src instanceof Player) { world = ((Player)src).getWorld(); }
		else { Messages.MUST_SPECIFY_WORLD.send(src); return; }

		EntityType type = args.<EntityType>getOne("type").get();
		world.getEntities(e -> e.getType() == type).forEach(e -> e.remove());
		src.sendMessage(Text.of(TextColors.GOLD, "You removed all mobs of type ", TextColors.RED, type.getName(), TextColors.GOLD, " from world ", TextColors.RED, world.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.killall";
	}
}