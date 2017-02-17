package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.RayUtils;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandSpawner extends PlayerCommand {

	public CommandSpawner(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Optional<Location<World>> block = RayUtils.getFirstBlock(target);
		if (!block.isPresent()) { Messages.NO_BLOCK_FOUND.send(target); return; }

		BlockState state = block.get().getBlock();
		if (state.getType() != BlockTypes.MOB_SPAWNER) { Messages.NOT_SPAWNER.send(target); return; }

		EntityType type = args.<EntityType>getOne("type").get();
		state = state.with(Keys.SPAWNABLE_ENTITY_TYPE, type).get();
		block.get().setBlock(state, ServerUtils.getCause(super.getCrucialCraft().getContainer()));

		target.sendMessage(Text.of(TextColors.GOLD, "You set the mob spawner to ", TextColors.RED, type.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.spawner";
	}
}