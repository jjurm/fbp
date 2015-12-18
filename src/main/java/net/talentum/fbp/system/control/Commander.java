package net.talentum.fbp.system.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.button.ButtonType;
import net.talentum.fbp.system.Main;

/**
 * Class for communication with clients and commanding system.
 * 
 * @author JJurM
 */
public class Commander {
	private static final Logger LOG = LogManager.getLogger();

	Map<String, Command> commands = new HashMap<String, Command>();

	static final Logger clientLogger = LogManager.getLogger("client");

	/**
	 * Basic constructor
	 */
	public Commander() {
		createCommands();
	}

	void createCommands() {
		// exit
		Command exit = (args, br, pw) -> Main.shutdown();
		commands.put("exit", exit);
		commands.put("quit", exit);
		commands.put("q", exit);

		// stream close
		commands.put("close", (args, br, pw) -> {
			throw new StreamCloseRequest();
		});

		// home
		commands.put("home", (args, br, pw) -> Main.uiManager.goHome());

		// button
		Command button = new CommandGroup() {
			{
				commands.put("l", new CommandGroup() {
					{
						Command down = (args, br, pw) -> Main.uiManager
								.buttonStateChanged(new ButtonEvent(ButtonType.LEFT, ButtonState.PRESSED));
						Command up = (args, br, pw) -> Main.uiManager
								.buttonStateChanged(new ButtonEvent(ButtonType.LEFT, ButtonState.RELEASED));
						commands.put("", down);
						commands.put("d", down);
						commands.put("u", up);
					}
				});
				commands.put("ok", new CommandGroup() {
					{
						Command down = (args, br, pw) -> Main.uiManager
								.buttonStateChanged(new ButtonEvent(ButtonType.OK, ButtonState.PRESSED));
						Command up = (args, br, pw) -> Main.uiManager
								.buttonStateChanged(new ButtonEvent(ButtonType.OK, ButtonState.RELEASED));
						commands.put("", down);
						commands.put("d", down);
						commands.put("u", up);
					}
				});
				commands.put("r", new CommandGroup() {
					{
						Command down = (args, br, pw) -> Main.uiManager
								.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT, ButtonState.PRESSED));
						Command up = (args, br, pw) -> Main.uiManager
								.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT, ButtonState.RELEASED));
						commands.put("", down);
						commands.put("d", down);
						commands.put("u", up);
					}
				});
			}
		};
		commands.put("button", button);
		commands.put("btn", button);
	}

	/**
	 * This will read command from given {@code InputStream} and perform needed
	 * actions. Another messages may be sent and received with
	 * {@code PrintWriter} and {@code InputStream}. This streams should not be
	 * in use by other threads while running this method.
	 * 
	 * @param br
	 *            Input stream
	 * @param pw
	 *            Output stream (PrintWriter must be set to auto-flush)
	 * @throws StreamCloseRequest
	 */
	public void process(BufferedReader br, PrintWriter pw) throws StreamCloseRequest {
		try {
			String line = br.readLine();
			if (line == null) {
				throw new StreamCloseRequest();
			}
			String[] parts = line.trim().split("\\s+");
			String commandName = parts[0].trim();

			Command command = commands.get(commandName);
			if (commandName.length() == 0) {
				// ignore empty line
			} else if (command != null) {
				LOG.trace(String.format("Executing command: %s", line.trim()));
				String[] args = Arrays.copyOfRange(parts, 1, parts.length);
				command.process(args, br, pw);
			} else {
				LOG.trace(String.format("Unknown command: %s", line.trim()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
