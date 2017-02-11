package me.mrdaniel.crucialcraft.commands.jail;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;

public class CommandJails extends PermissionCommand {

	public CommandJails(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		List<String> jails = Lists.newArrayList(super.getCrucialCraft().getDataFile().getJails());
		if (jails.isEmpty()) { src.sendMessage(Text.of(TextColors.GOLD, "There are no jails set.")); return; }

		Builder txt = Text.builder().append(this.getJailText(jails.get(0)));
		for (int i = 1; i < jails.size(); i++) {
			txt.append(Text.of(TextColors.GOLD, ", "), this.getJailText(jails.get(i)));
		}
		src.sendMessages(Text.of(TextColors.GOLD, "Jails:"), txt.build());
	}

	@Nonnull
	private final Text getJailText(@Nonnull final String name) {
		return Text.builder().append(Text.of(TextColors.RED, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Teleport to ", TextColors.RED, name, TextColors.GOLD, "."))).onClick(TextActions.runCommand("/tpjail " + name)).build();
	}

	@Override
	public String getPermission() {
		return "cc.jail.list";
	}
}