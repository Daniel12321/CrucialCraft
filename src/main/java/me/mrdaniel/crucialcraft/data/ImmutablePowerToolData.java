package me.mrdaniel.crucialcraft.data;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;

public class ImmutablePowerToolData extends AbstractImmutableData<ImmutablePowerToolData, PowerToolData> {

	public ImmutablePowerToolData(@Nonnull final String command) {
		this.command = command;

		this.registerGetters();
	}
	private final String command;

	@Override
	protected void registerGetters() {
		registerFieldGetter(CCKeys.COMMAND, () -> this.command);
	}

	@Override public PowerToolData asMutable() { return new PowerToolData(this.command); }
	@Override public int getContentVersion() { return 1; }
	@Override public DataContainer toContainer() { return this.asMutable().toContainer(); }
}