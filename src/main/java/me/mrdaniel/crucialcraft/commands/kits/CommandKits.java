package me.mrdaniel.crucialcraft.commands.kits;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKits extends PermissionCommand {

	public CommandKits(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		List<String> kits = super.getCrucialCraft().getKits().getKits();
		for (int i = kits.size() - 1; i >= 0; i--) { if (!src.hasPermission("cc.kits." + kits.get(i))) { kits.remove(i); } }

		if (kits.isEmpty()) { src.sendMessage(Text.of(TextColors.GOLD, "There are no kits set.")); return; }

		Text.Builder txt;

		if (src instanceof Player) {
			Player p = (Player)src;
			txt = Text.builder().append(this.getKitText(p, kits.get(0)));
			for (int i = 1; i < kits.size(); i++) {
				txt.append(Text.of(TextColors.GOLD, ", "), this.getKitText(p, kits.get(i)));
			}
		}
		else {
			txt = Text.builder().append(Text.of(TextColors.RED, kits.get(0)));
			for (int i = 1; i < kits.size(); i++) {
				txt.append(Text.of(TextColors.GOLD, ", "), Text.of(TextColors.RED, kits.get(i)));
			}
		}

		src.sendMessages(Text.of(TextColors.GOLD, "Kits:"), txt.build());
	}

	@Nonnull
	private Text getKitText(@Nullable final Player p, @Nonnull final String name) {
		if (p.hasPermission("cc.bypass.delay.kits")) { return this.getKitText(name); }

		boolean playtime = super.getCrucialCraft().getKits().isKitPlaytime(name);

		long time = playtime ? super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getCurrentPlaytime() : System.currentTimeMillis();
		long requiredtime = super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getLastKitUse(name) + super.getCrucialCraft().getKits().getKitDelay(name) * 1000;

		if (time > requiredtime) { return this.getKitText(name); }
		else { return this.getStrikedKitText(name, playtime, requiredtime - time); }
	}

	@Nonnull
	private Text getKitText(@Nonnull final String name) {
		return Text.builder().append(Text.of(TextColors.RED, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Get kit ", TextColors.RED, name, TextColors.GOLD, "."))).onClick(TextActions.runCommand("/kit " + name)).build();
	}

	@Nonnull
	private Text getStrikedKitText(@Nonnull final String name, final boolean playtime, final long wait) {
		return Text.builder().append(Text.of(TextColors.RED, TextStyles.STRIKETHROUGH, name)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Available in ", TextColors.RED, TextUtils.getTimeFormat(wait), TextColors.GOLD, playtime ? " ingame time." : "."))).build();
	}

	@Override
	public String getPermission() {
		return "cc.kits.list";
	}
}