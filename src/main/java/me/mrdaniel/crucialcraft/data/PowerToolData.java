package me.mrdaniel.crucialcraft.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class PowerToolData extends AbstractData<PowerToolData, ImmutablePowerToolData> {

	public PowerToolData(@Nonnull final String command) {
		this.command = command;

		this.registerGettersAndSetters();
	}
	private String command;

	public Value<String> getCommandValue() { return CCKeys.FACTORY.createValue(CCKeys.COMMAND, this.command); }

	@Nonnull public String getCommand() { return this.command; }
	public void setCommand(@Nonnull final String command) { this.command = command; }

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(CCKeys.COMMAND, this::getCommand);
		registerFieldSetter(CCKeys.COMMAND, this::setCommand);
		registerKeyValue(CCKeys.COMMAND, this::getCommandValue);
	}

	@Override public DataContainer toContainer() { return super.toContainer().set(CCKeys.COMMAND.getQuery(), this.command); }
	@Override public Optional<PowerToolData> fill(DataHolder dataHolder, MergeFunction overlap) { return Optional.ofNullable(checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null))); }
	@Override public Optional<PowerToolData> from(DataContainer container) { return this.from((DataView)container); }
	@Nonnull public Optional<PowerToolData> from(DataView view) { return Optional.of(new PowerToolData(view.getString(CCKeys.COMMAND.getQuery()).orElse(""))); }
	@Override public PowerToolData copy() { return new PowerToolData(this.command); }
	@Override public ImmutablePowerToolData asImmutable() { return new ImmutablePowerToolData(this.command); }
	@Override public int getContentVersion() { return 1; }
}