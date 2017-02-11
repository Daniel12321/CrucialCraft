package me.mrdaniel.crucialcraft.commands.warp;

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

public class CommandWarps extends PermissionCommand {

	public CommandWarps(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		List<String> warps = Lists.newArrayList(super.getCrucialCraft().getDataFile().getWarps());
		if (warps.isEmpty()) { src.sendMessage(Text.of(TextColors.GOLD, "There are no warps set.")); return; }

		Builder txt = Text.builder().append(this.getWarpText(warps.get(0)));
		for (int i = 1; i < warps.size(); i++) {
			txt.append(Text.of(TextColors.GOLD, ", "), this.getWarpText(warps.get(i)));
		}
		src.sendMessages(Text.of(TextColors.GOLD, "Warps: "), txt.build());
	}

	@Nonnull
	private Text getWarpText(@Nonnull final String name) {
		return Text.builder().append(Text.of(TextColors.RED, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Teleport to ", TextColors.RED, name, TextColors.GOLD, "."))).onClick(TextActions.runCommand("/warp " + name)).build();
	}

	@Override
	public String getPermission() {
		return "cc.warp.list";
	}
}