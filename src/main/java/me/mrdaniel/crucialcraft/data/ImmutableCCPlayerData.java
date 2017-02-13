package me.mrdaniel.crucialcraft.data;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

public class ImmutableCCPlayerData extends AbstractImmutableData<ImmutableCCPlayerData, CCPlayerData> {

	protected ImmutableCCPlayerData(@Nonnull final Map<String, Teleport> homes, @Nullable final String nick, @Nullable final Teleport last_location, @Nullable final String last_messager, final boolean jailed, final boolean muted, final long last_login, final long last_logout, final int playtime) {
		this.homes = homes;
		this.nick = nick;
		this.last_location = last_location;
		this.last_messager = last_messager;
		this.jailed = jailed;
		this.muted = muted;
		this.last_login = last_login;
		this.last_logout = last_logout;
		this.playtime = playtime;

		registerGetters();
	}
	private final Map<String, Teleport> homes;
	private final String nick;
	private final Teleport last_location;
	private final String last_messager;
	private final boolean jailed;
	private final boolean muted;
	private final long last_login;
	private final long last_logout;
	private final int playtime;

	@Override
	protected void registerGetters() {
		registerFieldGetter(CCKeys.HOMES, () -> this.homes);
		registerFieldGetter(CCKeys.NICK, () -> this.nick);
		registerFieldGetter(CCKeys.LAST_LOCATION, () -> this.last_location);
		registerFieldGetter(CCKeys.LAST_MESSAGER, () -> this.last_messager);
		registerFieldGetter(CCKeys.JAILED, () -> this.jailed);
		registerFieldGetter(CCKeys.MUTED, () -> this.muted);
		registerFieldGetter(CCKeys.LAST_LOGIN, () -> this.last_login);
		registerFieldGetter(CCKeys.LAST_LOGOUT, () -> this.last_logout);
		registerFieldGetter(CCKeys.PLAYTIME, () -> this.playtime);
	}

	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
	@Override public CCPlayerData asMutable() { return new CCPlayerData(this.homes, this.nick, this.last_location, this.last_messager, this.jailed, this.muted, this.last_login, this.last_logout, this.playtime); }
	@Override public int getContentVersion() { return 1; }
}