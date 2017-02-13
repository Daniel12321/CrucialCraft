package me.mrdaniel.crucialcraft.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.PowerToolData;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class WorldListener extends CCObject {

	public WorldListener(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Listener
	public void onBlockChange(final ChangeBlockEvent.Pre e, @Root final Player p) {
		p.get(CCPlayerData.class).ifPresent(data -> { if (data.getJailed()) { e.setCancelled(true); } });
	}

	@Listener
	public void onInteract(final InteractEvent e, @Root final Player p) {
		p.get(CCPlayerData.class).ifPresent(data -> { if (data.getJailed()) { e.setCancelled(true); } });
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

	@Listener
	public void onPowerTool(final InteractItemEvent.Secondary e, @Root final Player p) {
		p.getItemInHand(HandTypes.MAIN_HAND).ifPresent(item -> item.get(PowerToolData.class).ifPresent(data -> { super.getCrucialCraft().getGame().getCommandManager().process(p, data.getCommand()); e.setCancelled(true); }));
	}
}