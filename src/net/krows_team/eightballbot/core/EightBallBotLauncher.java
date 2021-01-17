package net.krows_team.eightballbot.core;

import net.coretools.core.Launcher;
import net.discordbot.core.DiscordBotLauncher;
import net.krows_team.eightballbot.jlp.EightBallBotLanguagePreloader;

/**
 * 
 * The EightBallBotLauncher class is used for launching and working with Discord "Eight Ball Bot" bot.
 * 
 * @since 1.0.0
 * 
 * @author Krows
 *
 * @see Launcher
 * 
 */
public class EightBallBotLauncher extends DiscordBotLauncher {

/**
 * 
 * Constructor creates Discord "EightBallBot" bot from token.
 * 
 * @param args Arguments for current launcher. Available next arguments: 
 * <blockquote>[String] token.</blockquote>
 * <blockquote>[String] "-overwrite" localization file overwriting.</blockquote>
 * 
 */
	public EightBallBotLauncher(String... args) {
		
		super(args);
		
		if(args.length > 1 && args[1].equals("-overwrite")) EightBallBotLanguagePreloader.preload();
		
		bot = new EightBallBot(token);
	}
	
	@Override
	public void launch() {
		
		super.launch();
	}
	
	public static void main(String[] args) {

		Launcher launcher = new EightBallBotLauncher(args);
		launcher.launch();
	}
}