package net.bot.core;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import net.core.ObjectTools;
import net.core.SystemTools;
import net.discordbot.core.DiscordBot;
import net.discordbot.core.DiscordBotLoader;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.jlp.core.Language;

/**
 * 
 * The EightBallBot class is Java Discord bot for using on servers.
 * 
 * @author Krows
 *
 */
public class EightBallBot extends DiscordBot {

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
	
/**
 * 
 * This method adds listeners which bot will handle.
 * 
 */
	protected void addListeners() {

		api.addEventListener(new ListenerAdapter() {
			
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {

				if(!checkForSelf(event.getAuthor())) {
					
					if(checkForCommand(event.getMessage().getContentDisplay())) {
						
						respondCommand(event);
					}
				}
			}
		});
	}
	
/**
 * 
 * This method responds to message requests from listeners.
 * 
 * @param event Message events from listeners.
 * 
 */
	protected void respondCommand(MessageReceivedEvent event) {
		
		MessageChannel channel = event.getChannel();
		
		try(Scanner scanner = new Scanner(event.getMessage().getContentDisplay())) {
			
			String message0 = scanner.next();

			switch(message0) {
			
			case CLEAR_COMMAND :
				
				for(Message message : channel.getIterableHistory()) {
					
					if(checkForCommand(message.getContentDisplay()) || checkForSelf(message.getAuthor())) message.delete().queue();
				}
				
				return;
				
			case HELP_COMMAND :
				
				channel.sendMessage(getText("help_tab")).queue();
				
				break;
				
			case LANGUAGE_COMMAND :
				
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
					
					channel.sendMessage(SystemTools.format(getText("langs"), Language.getLanguages())).queue();
				}
				
				break;
			
			default :

				if(message0.startsWith(SECOND_COMMAND_PREFIX)) {
					
					List<Member> memberList = new CopyOnWriteArrayList<>(event.getGuild().getMembers());
					
					for(Member member : memberList) {
						
						if(member.getOnlineStatus().equals(OnlineStatus.OFFLINE) || member.getUser().isBot()) memberList.remove(member);
					}
					
					memberList.remove(event.getGuild().getMember(api.getSelfUser()));
					
					channel.sendMessage(SystemTools.format("<@%s>,	<@%s>", event.getAuthor().getId(), ObjectTools.random(memberList.toArray(new Member[0])).getUser().getId())).queue();
					
					break;
				}
				
				String answer = ObjectTools.random(getText("no"), getText("yes"));
				
				int probability = new Random().nextInt(100);
				
				if(answer.equals(getText("no")) && probability < 40) {
					
					answer = getText("yes");
					
					probability = 100 - probability;
				}
				
				channel.sendMessage(SystemTools.format(getText("answer"), event.getAuthor().getId(), FIRST_COMMAND_PREFIX, answer, probability, "%")).queue();
				
				break;
			}
		}
	}

	protected boolean checkForCommand(String message) {

		return message.startsWith(FIRST_COMMAND_PREFIX) || message.startsWith(SECOND_COMMAND_PREFIX);
	}

/**
 * 
 * This method reutrns value of key from current language map which preloaded before the bot.
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