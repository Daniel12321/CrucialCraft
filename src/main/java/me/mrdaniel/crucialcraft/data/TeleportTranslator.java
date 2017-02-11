package me.mrdaniel.crucialcraft.data;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.google.common.reflect.TypeToken;

public class TeleportTranslator implements DataTranslator<Teleport> {

	@Nonnull
	public static String serialize(@Nonnull final Teleport tp) {
		return tp.world + ":" + tp.x + ":" + tp.y + ":" + tp.z + ":" + tp.pitch + ":" + tp.yaw;
	}

	@Nonnull
	public static Optional<Teleport> deserialize(@Nullable final String str) {
		try {
			String[] s = str.split(":");
			return Optional.of(new Teleport(s[0], Double.valueOf(s[1]), Double.valueOf(s[2]), Double.valueOf(s[3]), Double.valueOf(s[4]), Double.valueOf(s[5])));
		}
		catch (final Exception exc) { return Optional.empty(); }
	}

	@Nonnull
	public static Teleport from(@Nonnull final DataView view) {
		return new Teleport(
				(String) view.get(CCKeys.WORLD.getQuery()).orElse("world"),
				(double) view.get(CCKeys.X.getQuery()).orElse(0.0),
				(double) view.get(CCKeys.Y.getQuery()).orElse(0.0),
				(double) view.get(CCKeys.Z.getQuery()).orElse(0.0),
				(double) view.get(CCKeys.PITCH.getQuery()).orElse(0.0),
				(double) view.get(CCKeys.YAW.getQuery()).orElse(0.0));
	}

	@Override
	public Teleport translate(DataView view) throws InvalidDataException {
		return from(view);
	}

	@Override
	public DataContainer translate(Teleport teleport) throws InvalidDataException {
		return new MemoryDataContainer()
				.set(CCKeys.WORLD.getQuery(), teleport.world)
				.set(CCKeys.X.getQuery(), teleport.x)
				.set(CCKeys.Y.getQuery(), teleport.y)
				.set(CCKeys.Z.getQuery(), teleport.z)
				.set(CCKeys.PITCH.getQuery(), teleport.pitch)
				.set(CCKeys.YAW.getQuery(), teleport.yaw);
	}

	@Override public String getId() { return "cc:teleport"; }
	@Override public String getName() { return "CrucialCraft Teleport"; }
	@Override public TypeToken<Teleport> getToken() { return TypeToken.of(Teleport.class); }
}