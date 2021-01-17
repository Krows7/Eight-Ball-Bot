package net.krows_team.eightballbot.jlp;

import static net.coretools.core.file.FileTools.JAVA_LANGUAGE_PATH_NAME;
import static net.discordbot.core.DiscordBotPreloader.LANGUAGE_PACK_FILE;

import java.io.File;

import net.jlp.core.JLPFactory;
import net.jlp.core.JLPParser;
import net.jlp.core.JLPack;

/**
 * 
 * EightBallBotLanguagePreloader class contains method for preloading localization data if it needed for the bot.
 * 
 * @since 1.0.0
 * 
 * @author Krows
 *
 */
public final class EightBallBotLanguagePreloader {

/**
 * 
 * Closed constructor.
 * 
 */
	private EightBallBotLanguagePreloader() {
		
	}
	
/**
 * 
 * Preloads into the bot localization data from attached language pack file.
 * 
 * @see JLPack
 * 
 */
	public static void preload() {
		
		JLPFactory.encode(LANGUAGE_PACK_FILE, JLPParser.parse(new File(JAVA_LANGUAGE_PATH_NAME)));
	}
}