package me.mrdaniel.crucialcraft.commands;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.format.TextColors;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;

public class CommandNear extends PlayerCommand {

	public CommandNear(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) {
		List<Player> near = target.getWorld().getEntities().stream().filter(ent -> ent instanceof Player && ent.getLocation().getPosition().distance(target.getLocation().getPosition()) <= 150).map(ent -> (Player)ent).collect(Collectors.toList());
		near.remove(target);
		if (near.isEmpty()) { target.sendMessage(Text.of(TextColors.GOLD, "There are no players near you.")); return; }

		Builder txt = Text.builder().append(this.getPlayerText(near.get(0), target.getLocation().getPosition()));
		for (int i = 1; i < near.size(); i++) {
			txt.append(Text.of(TextColors.GOLD, ", "), this.getPlayerText(near.get(i), target.getLocation().getPosition()));
		}

		target.sendMessages(Text.of(TextColors.GOLD, "Players near you:"), txt.build());
	}

	@Nonnull
	private Text getPlayerText(@Nonnull final Player p, @Nonnull final Vector3d from) {
		return Text.builder().append(Text.of(TextColors.RED, p.getName(), TextColors.GOLD, " (", (int)p.getLocation().getPosition().distance(from), "m)")).build();
	}

	@Override
	public String getPermission() {
		return "cc.near";
	}

	@Override
	public String getName() {
		return "Near";
	}
}