package me.mrdaniel.crucialcraft.utils;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;

import com.google.common.collect.Lists;

public class PermissionsUtils {

	public static List<String> getGroups(@Nonnull final Player p) {

		List<String> groups = Lists.newArrayList();
    	Subject subject = p.getContainingCollection().get(p.getIdentifier());

		for (Subject group : subject.getParents()) {
			if (!groups.contains(group.getIdentifier().toLowerCase())) groups.add(group.getIdentifier().toLowerCase());
		}
		return groups;
	}

	public static int getMaxHomes(@Nonnull final Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		String o = subject.getOption("homes").orElse(subject.getOption("home").orElse(""));

		if (o.equalsIgnoreCase("-1") || o.equalsIgnoreCase("unlimited")) return Integer.MAX_VALUE;
		try { return Integer.parseInt(o); }
		catch (final NumberFormatException e) { return 0; }
	}
}