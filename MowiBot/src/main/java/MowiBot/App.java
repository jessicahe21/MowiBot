package MowiBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class App extends ListenerAdapter{

    public static void main( String[] args ) throws Exception{
        JDA jda = new JDABuilder(AccountType.BOT).setToken(Reference.token).buildBlocking();
        jda.addEventListener(new App());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent evt){
        // Objects
        User objUser = evt.getAuthor();
        MessageChannel objMsgCh = evt.getChannel();
        Message objMsg = evt.getMessage();


        // help
        if(objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "help")){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.pink);
            builder.setAuthor("About Mowi Bot", null, "https://cdn.discordapp.com/attachments/259093539277897728/462897717375401986/381483726179270666.png" + "?size=256"); // (getName, url, iconUrl);
            builder.setThumbnail("https://cdn.discordapp.com/attachments/259093539277897728/462897717375401986/381483726179270666.png");

            builder.setTitle("Hello!");
            builder.setDescription("This is Mowi Bot :3\n\n\n");

            builder.addField("Commands (prefix: &)",
                    "`&ping` - returns pong\n" +
                    "`&translate` - alters a phrase's 'r' and 'l' to 'w'\n" +
                    "`&say` - repeats after you!\n" +
                    "`&MOWI` - sends a :HYPERS:\n" +
                    "`&avatar` - display your own avatar\n" +
                    "(`&avatar @user` for another user's avatar)\n\n" +
                    "GitHub repository coming soon! (pls help me improve)\n" +
                    "I have yet to figure out how to add an url in embedded message so no inv link for now",
                    false);

            builder.setFooter("made with ♥ by Mori/Jemmie", "https://cdn.discordapp.com/attachments/259093539277897728/462887438692384769/square35416241_230294411035902_3321656964320067584_n.png");

            objMsgCh.sendMessage(builder.build()).queue();
        }


        // 1. basic ping-pong
        if(objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix +  "ping")){
            objMsgCh.sendMessage(objUser.getAsMention() + " Pong!").queue(); // important to queue!
        }


        // 2. mowi translate
        if(objMsg.getContentRaw().startsWith("&translate ")){
            String message = objMsg.getContentRaw().substring(11);
            char[] translate = message.toCharArray();

            for(int i = 0; i < translate.length; i++){
                if(translate[i] == 'r' || translate[i] == 'l') {
                    translate[i] = 'w';
                }
                else if(translate[i] == 'R' || translate[i] == 'L') {
                    translate[i] = 'W';
                }
            }
            String mowiText = new String(translate);

            objMsgCh.sendMessage("Mowi twanslate says: " + mowiText + " <:HYPERS:462476123331100682>").queue();
        }


        // 3. say (repeats after user)
        if(objMsg.getContentRaw().startsWith("&say ")){
            objMsgCh.sendMessage("*" + objMsg.getContentRaw().substring(5) + "*").queue();
        }


        // 4. MOWI! :HYPERS:
        if(objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix +  "mowi")){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.pink);

            builder.setTitle(":HYPERS:");
            builder.setImage("https://cdn.discordapp.com/attachments/259093539277897728/462898728110391296/Webp.net-resizeimage.png");

            objMsgCh.sendMessage(builder.build()).queue();
        }


        // 5. get user avatar
        if(objMsg.getContentRaw().startsWith("&avatar")){ // self avatar
            String avatarURL;
            String title;
            String username;

            if (objMsg.getMentionedUsers().isEmpty()) { // self avatar
                title = "Your avatar!";
                avatarURL = objUser.getAvatarUrl();
            }
            else{ // mentioned user's avatar
                username = objMsg.getMentionedUsers().get(0).getName();

                title = username + "'s avatar!";
                avatarURL = objMsg.getMentionedUsers().get(0).getAvatarUrl();

                if(avatarURL == null){
                    title = username + " doesn't seem to have a unique avatar.";
                }
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.pink);

            builder.setTitle(title);
            builder.setImage(avatarURL);

            objMsgCh.sendMessage(builder.build()).queue();
        }
    }
}