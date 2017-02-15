package me.mrdaniel.crucialcraft.commands.home;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;

public class CommandHomes extends PlayerCommand {

	public CommandHomes(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		List<String> homes = file.getHomes();

		target.sendMessage(Text.of(TextColors.GOLD, "You have ", TextColors.RED, homes.size(), " / ", super.getCrucialCraft().getConfig().getMaxHomes(target), TextColors.GOLD, " homes set", homes.isEmpty() ? "." : ":"));
		if (homes.isEmpty()) { return; }

		Builder txt = Text.builder();
		for (String home : homes) {
			if (!txt.toText().toPlain().equals("")) { txt.append(Text.of(TextColors.GOLD, ", ")); }
			txt.append(this.getHomeText(home));
		}
		target.sendMessage(txt.build());
	}

	@Nonnull
	private Text getHomeText(@Nonnull final String name) {
		return Text.builder().append(Text.of(TextColors.RED, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Teleport to ", TextColors.RED, name, TextColors.GOLD, "."))).onClick(TextActions.runCommand("/home " + name)).build();
	}

	@Override
	public String getPermission() {
		return "cc.home.list";
	}
}