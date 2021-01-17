package net.bot.jlp;

import static net.discordbot.core.DiscordBotPreloader.LANGUAGE_PACK_FILE;

import java.util.HashMap;

import net.jlp.core.JLPFactory;
import net.jlp.core.JLPack;
import net.jlp.core.Language;

public class EightBallBotLanguagePreloader {

	private final static String ENGLISH_HELP_TAB_TEXT = "```"
			   + "*Eight Ball's Help Tab* \r"
			   + "Next commands available: \r"
			   + "\r"
			   + "-help : Shows help tab. \r"
			   + "-[any question (or not)] : Question to which you need an answer. \r"
			   + "-clear : Clears all messages concerning this bot. \r"
			   + "-lang [abbr] : \r"
			   + "1. No abbr. - Shows list of available bot languages. \r"
			   + "2. With abbr. - Changes bot language."
			   + "```";
	private final static String ENGLISH_YES_ANSWER = "Yes";
	private final static String ENGLISH_NO_ANSWER = "No";
	private final static String ENGLISH_ANSWER_STRING = "<@%s> %s%s, with probability in %s%s";
	private final static String ENGLISH_CHANGE_LANGUAGE = "Bot language changed to english.";
	private final static String ENGLISH_NOT_AVAILABLE_LANGUAGE = "Sorry, but your entered language not available on our bot.";
	private final static String ENGLISH_AVAILABLE_LANGUAGES = "Available languages: %s";
	private final static String ENGLISH_ABBREVIATION = "en";
	
	
	
	private final static String RUSSIAN_HELP_TAB_TEXT = "```"
			   + "*Eight Ball's Справка по командам* \r"
			   + "Доступны следующие команды: \r"
			   + "\r"
			   + "-help : Показывает список команд. \r"
			   + "-[любой вопрос (или даже не вопрос)] : Вопрос, ответ на который вы хотите получить. \r"
			   + "-clear : Убирает все сообщения, касающиеся этого бота. \r"
			   + "-lang [сокр.] : \r"
			   + "1. Без аббревиатуры - Показывает список доступных боту языков. \r"
			   + "2. С аббревиатурой - Меняет язык бота."
			   + "```";
	private final static String RUSSIAN_YES_ANSWER = "Да";
	private final static String RUSSIAN_NO_ANSWER = "Нет";
	private final static String RUSSIAN_ANSWER_STRING = "<@%s> %s%s, с вероятностью в %s%s";
	private final static String RUSSIAN_CHANGE_LANGUAGE = "Язык бота изменён на русский.";
	private final static String RUSSIAN_NOT_AVAILABLE_LANGUAGE = "Извините, данный язык не доступен для бота.";
	private final static String RUSSIAN_AVAILABLE_LANGUAGES = "Доступные языки: %s";
	private final static String RUSSIAN_ABBREVIATION = "ru";
	
	
	
	private final static String UKRAINIAN_HELP_TAB_TEXT = "```"
				+ "*Eight Ball's Довідка по командам* \r"
				+ "В наявності є таке команди: \r"
				+ "\r"
				+ "-help : Показує список команд. \r"
				+ "-[будь яке питання (або навіть не питання)] : Питання, відповідь на який ви хочете отримати. \r"
				+ "-clear : Прибирає всі повідомлення, що стосуються цього бота. \r"
				+ "-lang [скор.] : \r"
				+ "1. Без абревіатури - показує список доступних боту мов. \r"
				+ "2. З абревіатурою - Змінює мову бота."
				+ "```";
	private final static String UKRAINIAN_YES_ANSWER = "Да";
	private final static String UKRAINIAN_NO_ANSWER = "Немає";
	private final static String UKRAINIAN_ANSWER_STRING = "<@%s> %s%s, з ймовірністю в %s%s";
	private final static String UKRAINIAN_CHANGE_LANGUAGE = "Мова бота змінений на українському.";
	private final static String UKRAINIAN_NOT_AVAILABLE_LANGUAGE = "Вибачте, даний язик не доступний для бота.";
	private final static String UKRAINIAN_AVAILABLE_LANGUAGES = "Доступні мови: %s";
	private final static String UKRAINIAN_ABBREVIATION = "ua";
	
	private static HashMap<String, String> englishMap;
	private static HashMap<String, String> russianMap;
	private static HashMap<String, String> ukrainianMap;
	
	private static JLPack BOT_LANGUAGE_PACK = null;
	
	public static void preload() {
		
		englishMap = new HashMap<>();
		englishMap.put("abbr", ENGLISH_ABBREVIATION);
		englishMap.put("help_tab", ENGLISH_HELP_TAB_TEXT);
		englishMap.put("yes", ENGLISH_YES_ANSWER);
		englishMap.put("no", ENGLISH_NO_ANSWER);
		englishMap.put("answer", ENGLISH_ANSWER_STRING);
		englishMap.put("change_lang", ENGLISH_CHANGE_LANGUAGE);
		englishMap.put("no_lang_msg", ENGLISH_NOT_AVAILABLE_LANGUAGE);
		englishMap.put("langs", ENGLISH_AVAILABLE_LANGUAGES);
		
		russianMap = new HashMap<>();
		russianMap.put("abbr", RUSSIAN_ABBREVIATION);
		russianMap.put("help_tab", RUSSIAN_HELP_TAB_TEXT);
		russianMap.put("yes", RUSSIAN_YES_ANSWER);
		russianMap.put("no", RUSSIAN_NO_ANSWER);
		russianMap.put("answer", RUSSIAN_ANSWER_STRING);
		russianMap.put("change_lang", RUSSIAN_CHANGE_LANGUAGE);
		russianMap.put("no_lang_msg", RUSSIAN_NOT_AVAILABLE_LANGUAGE);
		russianMap.put("langs", RUSSIAN_AVAILABLE_LANGUAGES);
		
		ukrainianMap = new HashMap<>();
		ukrainianMap.put("abbr", UKRAINIAN_ABBREVIATION);
		ukrainianMap.put("help_tab", UKRAINIAN_HELP_TAB_TEXT);
		ukrainianMap.put("yes", UKRAINIAN_YES_ANSWER);
		ukrainianMap.put("no", UKRAINIAN_NO_ANSWER);
		ukrainianMap.put("answer", UKRAINIAN_ANSWER_STRING);
		ukrainianMap.put("change_lang", UKRAINIAN_CHANGE_LANGUAGE);
		ukrainianMap.put("no_lang_msg", UKRAINIAN_NOT_AVAILABLE_LANGUAGE);
		ukrainianMap.put("langs", UKRAINIAN_AVAILABLE_LANGUAGES);
		
		HashMap<Language, HashMap<String, String>> languageMap = new HashMap<>();
		languageMap.put(Language.UKRAINIAN, ukrainianMap);
		languageMap.put(Language.ENGLISH, englishMap);
		languageMap.put(Language.RUSSIAN, russianMap);
		
		BOT_LANGUAGE_PACK = JLPack.create(languageMap);
		
		JLPFactory.decode(LANGUAGE_PACK_FILE, BOT_LANGUAGE_PACK);
	}
}