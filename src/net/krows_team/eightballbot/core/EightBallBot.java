package net.krows_team.eightballbot.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import net.coretools.core.ObjectTools;
import net.coretools.core.time.TimeConstants;
import net.coretools.core.time.Timer;
import net.discordbot.core.DiscordBot;
import net.discordbot.core.DiscordBotLoader;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.jlp.core.JLPack;

/**
 * 
 * The EightBallBot class is Java Discord bot for using on servers.
 * 
 * @since 1.0.0
 * 
 * @author Krows
 *
 */
public class EightBallBot extends DiscordBot implements TimeConstants {

/**
 * 
 * The first command prefix which is used to communicate with this bot.
 * 
 */
	private final String FIRST_COMMAND_PREFIX = "-";

/**
 * 
 * The second command prefix which is used to communicate with this bot.
 * 
 */
	private final String SECOND_COMMAND_PREFIX = "*";
	
/**
 * 
 * The command to clear certain messages in chats. 
 * 
 */
	private final String CLEAR_COMMAND = FIRST_COMMAND_PREFIX + "clear";
	
/**
 * 
 * The command to watch and change language of bot to other available.
 * 
 */
	private final String LANGUAGE_COMMAND = FIRST_COMMAND_PREFIX + "lang";
	
/**
 * 
 * The command to show help tab everyone on chat. 
 * 
 */
	private final String HELP_COMMAND = FIRST_COMMAND_PREFIX + "help";

/**
 * 
 * Constructor creates bot and saves bot token.
 * 
 * @param token Discord "EightBallBot" bot token.
 * 
 */
	public EightBallBot(String token) {
		
		super(token);
	}
	
	@Override
	protected void addListeners() {

		api.addEventListener(new ListenerAdapter() {
			
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {

				if(!checkForSelf(event.getAuthor())) {
					
					if(checkForCommand(event.getMessage().getContentDisplay())) {
							
						if(!checkForBanned(event.getAuthor().getId())) {
					
							respondCommand(event);
						} else {
						
							event.getChannel().sendMessage(String.format(getText("ban_msg"), event.getAuthor().getId())).queue();
						}
					} 
				}
			}
		});
	}
	
	@Override
	protected void respondCommand(MessageReceivedEvent event) {
		
		TextChannel channel = event.getTextChannel();
		
		try(Scanner scanner = new Scanner(event.getMessage().getContentDisplay())) {
			
			String message = scanner.next();

			switch(message) {
			
			case CLEAR_COMMAND :
				
				clearCommand(event, channel);
				
				break;
			
			case HELP_COMMAND :
				
				helpCommand(event, channel);
				
				break;
			
			case LANGUAGE_COMMAND :
				
				languageCommand(event, scanner, channel);
				
				break;
			
			default :

				if(message.startsWith(SECOND_COMMAND_PREFIX)) {
					
					rollCommand(event, channel);
					
					break;
				}
				
				answerCommand(event, channel);
				
				break;
			}
		}
	}

/**
 * 
 * Clears all messages from specified {@link TextChannel} concering this bot.
 * 
 * @param event Event concering message receiving.
 * @param channel Source channel.
 * 
 */
	private void clearCommand(MessageReceivedEvent event, TextChannel channel) {
		
		List<Message> historyList = channel.getIterableHistory().cache(true).complete();
		List<Message> iterableList = new LinkedList<>();
		
		for(Message message : historyList) {
			
			if(checkForCommand(message.getContentDisplay()) || checkForSelf(message.getAuthor())) iterableList.add(message);
		}
		
		channel.deleteMessages(iterableList).complete();
		
		Message clearMessage = channel.sendMessage(getText("clear_msg")).complete();
		
		channel.sendMessage(clearMessage);
		
		new Timer(5 * SECOND_TO_MILLISECOND, () -> {
			
			clearMessage.delete().queue();
		}).start();
		
		historyList = null;
		
		iterableList = null;
	}
	
/**
 * 
 * Sends message in to specified {@link TextChannel} with "help_tab" data.
 * 
 * @param event Event concering message receiving.
 * @param channel Source channel.
 * 
 */
	private void helpCommand(MessageReceivedEvent event,  TextChannel channel) {
		
		channel.sendMessage(getText("help_tab")).queue();
	}
	
/**
 * 
 * Sends message in to specified {@link TextChannel} with data about containing languages in core {@link JLPack} object of the bot.
 * 
 * @param event Event concering message receiving.
 * @param scanner Source message {@link Scanner}.
 * @param channel Source channel.
 * 
 */
	private void languageCommand(MessageReceivedEvent event, Scanner scanner, TextChannel channel) {
		
		if(scanner.hasNext()) {
			
			String message = "";
			
			Map<String, String> saveMap = languageDataMap;
			
			String abbr = scanner.next();
			
			if((languageDataMap = DiscordBotLoader.loadLanguageMap(abbr)) == null) {
				
				languageDataMap = saveMap;
				
				message = getText("no_lang_msg");
			} else {
				DiscordBotLoader.changePreferenceLanguage(abbr);
				
				message = getText("change_lang");
			}						
			
			channel.sendMessage(message).queue();
		} else {
			
			channel.sendMessage(String.format(getText("langs"), languagePack.getLanguages())).queue();
		}
	}
	
/**
 * 
 * Sends message in to specified {@link TextChannel} with random answer: "yes" or "no" with random percentage.
 * 
 * @param event Event concering message receiving.
 * @param channel Source channel.
 * 
 */
	private void answerCommand(MessageReceivedEvent event, TextChannel channel) {
		
		String answer = ObjectTools.random(getText("no"), getText("yes"));
		
		int probability = new Random().nextInt(100);
		
		if(answer.equals(getText("no")) && probability < 50) {
			
			answer = getText("yes");
			
			probability = 100 - probability;
		}
		
		channel.sendMessage(String.format(getText("answer"), event.getAuthor().getId(), FIRST_COMMAND_PREFIX, answer, probability, "%")).queue();
	}
	
/**
 * 
 * Sends message in to specified {@link TextChannel} with random user from current channel(it can't be offline users or bots).
 * 
 * @param event Event concering message receiving.
 * @param channel Source channel.
 * 
 */
	private void rollCommand(MessageReceivedEvent event, TextChannel channel) {
		
		List<Member> memberList = new CopyOnWriteArrayList<>(event.getGuild().getMembers());
		
		for(Member member : memberList) {
			
			if(member.getOnlineStatus().equals(OnlineStatus.OFFLINE) || member.getUser().isBot()) memberList.remove(member);
		}
		
		memberList.remove(event.getGuild().getMember(api.getSelfUser()));
		
		channel.sendMessage(String.format("<@%s>,	<@%s>", event.getAuthor().getId(), ObjectTools.random(memberList.toArray(new Member[0])).getUser().getId())).queue();
	}
	
	@Override
	protected boolean checkForCommand(String message) {

		return message.startsWith(FIRST_COMMAND_PREFIX) || message.startsWith(SECOND_COMMAND_PREFIX);
	}

/**
 * 
 * Reutrns value of key from current language map which preloaded before the bot.
 * 
 * @param prefix String key to find data value.
 * 
 * @return Localized String data.
 * 
 */
	private String getText(String prefix) {
		
		return languageDataMap.get(prefix);
	}
}