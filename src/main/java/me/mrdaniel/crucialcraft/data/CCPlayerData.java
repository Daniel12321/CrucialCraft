package me.mrdaniel.crucialcraft.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.OptionalValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.collect.Maps;

import me.mrdaniel.crucialcraft.CrucialCraft;

public class CCPlayerData extends AbstractData<CCPlayerData, ImmutableCCPlayerData> {

	public CCPlayerData(@Nonnull final CrucialCraft cc) {
		this(Maps.newHashMap(), null, null, null, false);
	}

	protected CCPlayerData(@Nonnull final Map<String, Teleport> homes, @Nullable final String nick, @Nullable final Teleport last_location, @Nonnull final String last_messager, final boolean jailed) {
		this.homes = homes;
		this.nick = nick;
		this.last_location = last_location;
		this.last_messager = last_messager;
		this.jailed = jailed;

		registerGettersAndSetters();
	}
	private Map<String, Teleport> homes;
	private String nick;
	private Teleport last_location;
	private String last_messager;
	private boolean jailed;

	public MapValue<String, Teleport> getHomesValue() { return CCKeys.FACTORY.createMapValue(CCKeys.HOMES, this.homes); }
	public OptionalValue<String> getNickValue() { return CCKeys.FACTORY.createOptionalValue(CCKeys.NICK, this.nick); }
	public OptionalValue<Teleport> getLastLocationValue() { return CCKeys.FACTORY.createOptionalValue(CCKeys.LAST_LOCATION, this.last_location); }
	public OptionalValue<String> getLastMessagerValue() { return CCKeys.FACTORY.createOptionalValue(CCKeys.LAST_MESSAGER, this.last_messager); }
	public Value<Boolean> getJailedValue() { return CCKeys.FACTORY.createValue(CCKeys.JAILED, this.jailed); }

	@Nonnull public Map<String, Teleport> getHomes() { return this.homes; }
	@Nonnull public Optional<Teleport> getHome(@Nonnull final String name) { return Optional.ofNullable(this.homes.get(name)); }
	public void setHomes(@Nonnull final Map<String, Teleport> homes) { this.homes = homes; }
	public void setHome(@Nonnull final String name, @Nullable final Teleport teleport) {
		if (teleport == null) { this.homes.remove(name); }
		else { this.homes.put(name, teleport); }
	}

	@Nonnull public Optional<String> getNick() { return Optional.ofNullable(this.nick); }
	public void setNick(@Nullable final String nick) { this.nick = nick; }
	public void setNick(@Nonnull final Optional<String> nick) { this.nick = nick.orElse(null); }

	@Nonnull public Optional<Teleport> getLastLocation() { return Optional.ofNullable(this.last_location); }
	public void setLastLocation(@Nullable final Teleport location) { this.last_location = location; }
	public void setLastLocation(@Nonnull final Optional<Teleport> location) { this.last_location = location.orElse(null); }

	@Nonnull public Optional<String> getLastMessager() { return Optional.ofNullable(this.last_messager); }
	public void setLastMessager(@Nullable final String messager) { this.last_messager = messager; }
	public void setLastMessager(@Nonnull final Optional<String> messager) { this.last_messager = messager.orElse(null); }

	public boolean getJailed() { return this.jailed; }
	public void setJailed(final boolean jailed) { this.jailed = jailed; }

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(CCKeys.HOMES, this::getHomes);
		registerFieldSetter(CCKeys.HOMES, this::setHomes);
		registerKeyValue(CCKeys.HOMES, this::getHomesValue);

		registerFieldGetter(CCKeys.NICK, this::getNick);
		registerFieldSetter(CCKeys.NICK, this::setNick);
		registerKeyValue(CCKeys.NICK, this::getNickValue);

		registerFieldGetter(CCKeys.LAST_LOCATION, this::getLastLocation);
		registerFieldSetter(CCKeys.LAST_LOCATION, this::setLastLocation);
		registerKeyValue(CCKeys.LAST_LOCATION, this::getLastLocationValue);

		registerFieldGetter(CCKeys.LAST_MESSAGER, this::getLastMessager);
		registerFieldSetter(CCKeys.LAST_MESSAGER, this::setLastMessager);
		registerKeyValue(CCKeys.LAST_MESSAGER, this::getLastMessagerValue);

		registerFieldGetter(CCKeys.JAILED, this::getJailed);
		registerFieldSetter(CCKeys.JAILED, this::setJailed);
		registerKeyValue(CCKeys.JAILED, this::getJailedValue);
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	public Optional<CCPlayerData> from(DataView view) {
		Map<String, Teleport> homes = Maps.newHashMap();
		view.getMap(CCKeys.HOMES.getQuery()).ifPresent(map -> map.forEach((homename, data) -> {
			Map<String, Object> datamap = (Map<String, Object>) data;
			homes.put((String)homename, new Teleport(
					(String) datamap.get("world"),
					(double) datamap.get("x"),
					(double) datamap.get("y"),
					(double) datamap.get("z"),
					(double) datamap.get("pitch"),
					(double) datamap.get("yaw")));
		}));
		String nick = view.getString(CCKeys.NICK.getQuery()).orElse(null);
		Teleport last_location = view.getView(CCKeys.LAST_LOCATION.getQuery()).isPresent() ? TeleportTranslator.from(view.getView(CCKeys.LAST_LOCATION.getQuery()).get()) : null;
		String last_messager = view.getString(CCKeys.LAST_MESSAGER.getQuery()).orElse(null);
		boolean jailed = view.getBoolean(CCKeys.JAILED.getQuery()).orElse(false);

		return Optional.of(new CCPlayerData(homes, nick, last_location, last_messager, jailed));
	}

	@Override
	public DataContainer toContainer() {
		DataContainer container = super.toContainer()
				.set(CCKeys.HOMES.getQuery(), this.homes)
				.set(CCKeys.JAILED.getQuery(), this.jailed);
		if (this.nick != null) { container.set(CCKeys.NICK.getQuery(), this.nick); }
		if (this.last_location != null) { container.set(CCKeys.LAST_LOCATION.getQuery(), this.last_location); }
		if (this.last_messager != null) { container.set(CCKeys.LAST_MESSAGER.getQuery(), this.last_messager); }
		return container;
	}

	@Override public Optional<CCPlayerData> fill(DataHolder dataHolder, MergeFunction overlap) { return Optional.ofNullable(checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null))); }
	@Override public Optional<CCPlayerData> from(DataContainer container) { return from((DataView)container); }
	@Override public CCPlayerData copy() { return new CCPlayerData(this.homes, this.nick, this.last_location, this.last_messager, this.jailed); }
	@Override public ImmutableCCPlayerData asImmutable() { return new ImmutableCCPlayerData(this.homes, this.nick, this.last_location, this.last_messager, this.jailed); }
	@Override public int getContentVersion() { return 1; }
}