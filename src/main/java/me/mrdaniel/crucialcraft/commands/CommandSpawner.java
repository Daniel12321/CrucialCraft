package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.RayUtils;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandSpawner extends PlayerCommand {

	public CommandSpawner(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.entitytype(cc, "type"));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		Location<World> block = RayUtils.getFirstBlock(target).orElseThrow(() -> new CommandException("No valid block was found."));

		BlockState state = block.getBlock();
		if (state.getType() != BlockTypes.MOB_SPAWNER) { throw new CommandException("Block is not a mob spawner."); }

		EntityType type = args.get("type");
		state = state.with(Keys.SPAWNABLE_ENTITY_TYPE, type).get();
		block.setBlock(state, ServerUtils.getCause(super.getCrucialCraft().getContainer()));

		target.sendMessage(Text.of(TextColors.GOLD, "You set the mob spawner to ", TextColors.RED, type.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.spawner";
	}

	@Override
	public String getName() {
		return "Spawner";
	}
}