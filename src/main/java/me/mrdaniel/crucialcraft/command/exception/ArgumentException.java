package me.mrdaniel.crucialcraft.command.exception;

import javax.annotation.Nonnull;

public class ArgumentException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArgumentException(@Nonnull final String message) {
		super(message);
	}
}