package net.bot.core;

import net.bot.jlp.EightBallBotLanguagePreloader;
import net.core.Launcher;
import net.discordbot.core.DiscordBotLauncher;

/**
 * 
 * The EightBallBotLauncher class is used for launching and working with Discord "Eight Ball Bot" bot.
 * 
 * @see Launcher
 * 
 * @author Krows
 *
 */
public class EightBallBotLauncher extends DiscordBotLauncher {

/**
 * 
 * Constructor creates Discord "EightBallBot" bot from token.
 * 
 * @param args Arguments for current launcher. Available next arguments: 
 * <blockquote>[String] token.</blockquote>
 * 
 */
	public EightBallBotLauncher(String... args) {
		
		super(args);
		
		bot = new EightBallBot(token);
	}
	
	@Override
	public void launch() {
		
		super.launch();
		
		EightBallBotLanguagePreloader.preload();
	}
}