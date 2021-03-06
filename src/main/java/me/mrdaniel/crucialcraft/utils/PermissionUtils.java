package me.mrdaniel.crucialcraft.utils;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;

import com.google.common.collect.Lists;

public class PermissionUtils {

	@Nonnull
	public static List<String> getGroups(@Nonnull final Player p) {

		List<String> groups = Lists.newArrayList();
    	Subject subject = p.getContainingCollection().get(p.getIdentifier());

		for (Subject group : subject.getParents()) {
			if (!groups.contains(group.getIdentifier().toLowerCase())) groups.add(group.getIdentifier().toLowerCase());
		}
		return groups;
	}

	@Nonnull
	public static int getMaxHomes(@Nonnull final Player p) {
		Subject subject = p.getContainingCollection().get(p.getIdentifier());
		String homes = subject.getOption("home").orElse(subject.getOption("homes").orElse(""));

		if (homes.equals("")) { return 0; }
		else if (homes.equals("-1") || homes.equalsIgnoreCase("unlimited")) { return Integer.MAX_VALUE; }
		else {
			try { return Integer.parseInt(homes); }
			catch (final NumberFormatException exc) { return 0; }
		}
	}
}