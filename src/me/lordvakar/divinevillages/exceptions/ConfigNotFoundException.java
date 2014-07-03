package me.lordvakar.divinevillages.exceptions;

import java.io.FileNotFoundException;

public class ConfigNotFoundException extends FileNotFoundException
{
	private static final long serialVersionUID = -8925731036412833918L;
	
	public ConfigNotFoundException()
	{
		super("The village config was not found!");
	}
}
