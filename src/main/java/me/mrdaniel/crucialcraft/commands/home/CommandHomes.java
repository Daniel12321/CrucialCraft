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
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;

public class CommandHomes extends PlayerCommand {

	public CommandHomes(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		Map<String, Teleport> homes = data.getHomes();
		if (homes.isEmpty()) { src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "There are no homes set.")); return; }

		Builder txt = Text.builder();
		for (String home : homes.keySet()) {
			Teleport tp = homes.get(home);
			if (!txt.toText().toPlain().equals("")) { txt.append(Text.of(TextColors.GOLD, ", ")); }
			txt.append(this.getHomeText(home, tp));
		}
		src.orElse(target).sendMessages(Text.of(TextColors.GOLD, "Homes:"), txt.build());
	}

	@Nonnull
	private Text getHomeText(@Nonnull final String name, @Nonnull final Teleport tp) {
		return Text.builder().append(Text.of(TextColors.RED, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Teleport to ", TextColors.RED, name, TextColors.GOLD, "."))).onClick(TextActions.executeCallback(src -> tp.teleport(super.getCrucialCraft(), (Player)src))).build();
	}

	@Override
	public String getPermission() {
		return "cc.home.list";
	}
}