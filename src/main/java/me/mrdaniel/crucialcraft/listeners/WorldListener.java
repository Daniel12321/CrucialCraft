package me.mrdaniel.crucialcraft.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.Root;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class WorldListener extends CCObject {

	public WorldListener(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Listener
	public void onSignChange(final ChangeSignEvent e, @Root final Player p) {
		if (p.hasPermission("cc.colors.sign")) {
			SignData data = e.getText();
			for (int i = 0; i < 4; i++) {
				if (data.get(i).isPresent()) {
					data.setElement(i, TextUtils.toText(data.get(i).get().toPlain()));
				}
			}
		}
	}
}