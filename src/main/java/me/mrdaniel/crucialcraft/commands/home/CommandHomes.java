package me.mrdaniel.crucialcraft.commands.home;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;

public class CommandHomes extends TargetPlayerCommand {

	public CommandHomes(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		Map<String, Teleport> homes = data.getHomes();

		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "You have ", TextColors.RED, homes.size(), " / ", super.getCrucialCraft().getConfig().getMaxHomes(target), TextColors.GOLD, " homes set", homes.isEmpty() ? "." : ":"));
		if (homes.isEmpty()) { return; }

		Builder txt = Text.builder();
		for (String home : homes.keySet()) {
			if (!txt.toText().toPlain().equals("")) { txt.append(Text.of(TextColors.GOLD, ", ")); }
			txt.append(this.getHomeText(home));
		}
		src.orElse(target).sendMessage(txt.build());
	}

	@Nonnull
	private Text getHomeText(@Nonnull final String name) {
		return Text.builder().append(Text.of(TextColors.RED, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Teleport to ", TextColors.RED, name, TextColors.GOLD, "."))).onClick(TextActions.runCommand("/home " + name)).build();
	}

	@Override
	public String getPermission() {
		return "cc.home.list";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}