package me.mrdaniel.crucialcraft.command.exception;

import javax.annotation.Nonnull;

public class CommandException extends Exception {

	private static final long serialVersionUID = 1L;

	public CommandException(@Nonnull final String message) {
		super(message);
	}
}