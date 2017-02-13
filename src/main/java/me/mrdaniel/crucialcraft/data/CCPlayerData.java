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

public class CCPlayerData extends AbstractData<CCPlayerData, ImmutableCCPlayerData> {

	public CCPlayerData() {
		this(Maps.newHashMap(), null, null, null, false, false, System.currentTimeMillis(), System.currentTimeMillis(), 0);
	}

	protected CCPlayerData(@Nonnull final Map<String, Teleport> homes, @Nullable final String nick, @Nullable final Teleport last_location, @Nonnull final String last_messager, final boolean jailed, final boolean muted, final long last_login, final long last_logout, final int playtime) {
		this.homes = homes;
		this.nick = nick;
		this.last_location = last_location;
		this.last_messager = last_messager;
		this.jailed = jailed;
		this.muted = muted;
		this.last_login = last_login;
		this.last_logout = last_logout;
		this.playtime = playtime;

		registerGettersAndSetters();
	}
	private Map<String, Teleport> homes;
	@Nullable private String nick;
	@Nullable private Teleport last_location;
	@Nullable private String last_messager;
	private boolean jailed;
	private boolean muted;
	private long last_login;
	private long last_logout;
	private int playtime;

	public MapValue<String, Teleport> getHomesValue() { return CCKeys.FACTORY.createMapValue(CCKeys.HOMES, this.homes); }
	public OptionalValue<String> getNickValue() { return CCKeys.FACTORY.createOptionalValue(CCKeys.NICK, this.nick); }
	public OptionalValue<Teleport> getLastLocationValue() { return CCKeys.FACTORY.createOptionalValue(CCKeys.LAST_LOCATION, this.last_location); }
	public OptionalValue<String> getLastMessagerValue() { return CCKeys.FACTORY.createOptionalValue(CCKeys.LAST_MESSAGER, this.last_messager); }
	public Value<Boolean> getJailedValue() { return CCKeys.FACTORY.createValue(CCKeys.JAILED, this.jailed); }
	public Value<Boolean> getMutedValue() { return CCKeys.FACTORY.createValue(CCKeys.MUTED, this.muted); }
	public Value<Long> getLastLoginValue() { return CCKeys.FACTORY.createValue(CCKeys.LAST_LOGIN, this.last_login); }
	public Value<Long> getLastLogoutValue() { return CCKeys.FACTORY.createValue(CCKeys.LAST_LOGOUT, this.last_logout); }
	public Value<Integer> getPlaytimeValue() { return CCKeys.FACTORY.createValue(CCKeys.PLAYTIME, this.playtime); }

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

	public boolean getMuted() { return this.muted; }
	public void setMuted(final boolean muted) { this.muted = muted; }

	public long getLastLogin() { return this.last_login; }
	public void setLastLogin(final long last_login) { this.last_login = last_login; }

	public long getLastLogout() { return this.last_logout; }
	public void setLastLogout(final long last_logout) { this.last_logout = last_logout; }

	public int getPlaytime() { return this.playtime; }
	public int getCurrentPlaytime() { return this.playtime + ((int) ((System.currentTimeMillis() - this.last_login) / 1000)); }
	public void setPlaytime(final int playtime) { this.playtime = playtime; }

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

		registerFieldGetter(CCKeys.MUTED, this::getMuted);
		registerFieldSetter(CCKeys.MUTED, this::setMuted);
		registerKeyValue(CCKeys.MUTED, this::getMutedValue);

		registerFieldGetter(CCKeys.LAST_LOGIN, this::getLastLogin);
		registerFieldSetter(CCKeys.LAST_LOGIN, this::setLastLogin);
		registerKeyValue(CCKeys.LAST_LOGIN, this::getLastLoginValue);

		registerFieldGetter(CCKeys.LAST_LOGOUT, this::getLastLogout);
		registerFieldSetter(CCKeys.LAST_LOGOUT, this::setLastLogout);
		registerKeyValue(CCKeys.LAST_LOGOUT, this::getLastLogoutValue);

		registerFieldGetter(CCKeys.PLAYTIME, this::getPlaytime);
		registerFieldSetter(CCKeys.PLAYTIME, this::setPlaytime);
		registerKeyValue(CCKeys.PLAYTIME, this::getPlaytimeValue);
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
		boolean muted = view.getBoolean(CCKeys.MUTED.getQuery()).orElse(false);
		long last_login = view.getLong(CCKeys.LAST_LOGIN.getQuery()).orElse(System.currentTimeMillis());
		long last_logout = view.getLong(CCKeys.LAST_LOGOUT.getQuery()).orElse(System.currentTimeMillis());
		int playtime = view.getInt(CCKeys.PLAYTIME.getQuery()).orElse(0);

		return Optional.of(new CCPlayerData(homes, nick, last_location, last_messager, jailed, muted, last_login, last_logout, playtime));
	}

	@Override
	public DataContainer toContainer() {
		DataContainer container = super.toContainer()
				.set(CCKeys.HOMES.getQuery(), this.homes)
				.set(CCKeys.JAILED.getQuery(), this.jailed)
				.set(CCKeys.MUTED.getQuery(), this.muted)
				.set(CCKeys.LAST_LOGIN.getQuery(), this.last_login)
				.set(CCKeys.LAST_LOGOUT.getQuery(), this.last_logout)
				.set(CCKeys.PLAYTIME.getQuery(), this.playtime);
		if (this.nick != null) { container.set(CCKeys.NICK.getQuery(), this.nick); }
		if (this.last_location != null) { container.set(CCKeys.LAST_LOCATION.getQuery(), this.last_location); }
		if (this.last_messager != null) { container.set(CCKeys.LAST_MESSAGER.getQuery(), this.last_messager); }
		return container;
	}

	@Override public Optional<CCPlayerData> fill(DataHolder dataHolder, MergeFunction overlap) { return Optional.ofNullable(checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null))); }
	@Override public Optional<CCPlayerData> from(DataContainer container) { return from((DataView)container); }
	@Override public CCPlayerData copy() { return new CCPlayerData(this.homes, this.nick, this.last_location, this.last_messager, this.jailed, this.muted, this.last_login, this.last_logout, this.playtime); }
	@Override public ImmutableCCPlayerData asImmutable() { return new ImmutableCCPlayerData(this.homes, this.nick, this.last_location, this.last_messager, this.jailed, this.muted, this.last_login, this.last_logout, this.playtime); }
	@Override public int getContentVersion() { return 1; }
}