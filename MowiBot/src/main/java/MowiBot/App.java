package MowiBot;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

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
                    "Utilities:\n" +
                    "`&avatar` - display your own avatar (`&avatar @user` for another user's avatar)\n" +
                    "`&<BnS Emote Name>` - displays emote (e.g. shy, tears, giggle, grrr)\n\n" +

                    "Fun:\n" +
                    "`&ping` - returns pong\n" +
                    "`&translate` - alters a phrase's 'r' and 'l' to 'w'\n" +
                    "`&say` - repeats after you!\n" +
                    "`&MOWI` - sends a :HYPERS:\n\n" +

                    "> [GitHub Repository](https://github.com/jessicahe21/MowiBot) (pls help me improve)\n" +
                    "> [Invite Link!](https://discordapp.com/oauth2/authorize?client_id=462441653420949519&scope=bot&permissions=2146958591)",
                    false);

            builder.setFooter("made with ♥ by Mori/Jemmie", "https://cdn.discordapp.com/attachments/259093539277897728/462887438692384769/square35416241_230294411035902_3321656964320067584_n.png");

            objMsgCh.sendMessage(builder.build()).queue();
        }


        // 1. basic ping-pong
        if(objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix +  "ping")){
            objMsgCh.sendMessage(objUser.getAsMention() + " Pong!").queue(); // important to queue!
        }


        // 2. mowi translate
        if(objMsg.getContentRaw().startsWith(Reference.prefix + "translate ")){
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
        if(objMsg.getContentRaw().startsWith(Reference.prefix + "say ")){
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
        if(objMsg.getContentRaw().startsWith(Reference.prefix + "avatar")){ // self avatar
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


        // 6. BnS Emotes
        if(objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "creepy") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "embarrassed") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "expand") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "giggle") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "glare") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "grrr") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "HajoonCheer") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "HajoonHappy") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "HajoonLaugh") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "HajoonSurprised") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "HajoonWow") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "heart") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "hmph") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "inlove") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "mad") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "money") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "mori") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "NamsoyooBling") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "nosebleed") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "OneSec") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "pain") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "paper") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "phew") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "playdumb") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "please") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "proud") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "puke") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "rabid") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "resetti") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "rock") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "scaryface") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "scissors") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "shock") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "shy") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "sick") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "sleepy") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "smirk") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "smooch") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "sweat") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "tears") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "thumbsdown") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "thumbsup") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "tired") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "tmi") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "two") ||
                objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "victory") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "wink")){
            File file = new File("C:/Users/Jessica He/IdeaProjects/MowiBot/src/main/java/resources/MowiBot_Emoji/" + objMsg.getContentRaw().substring(1) + ".png");
            objMsgCh.sendFile(file).queue();
        }

        if(objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + ":D") || objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + ":P")) {
            File file = new File("C:/Users/Jessica He/IdeaProjects/MowiBot/src/main/java/resources/MowiBot_Emoji/" + objMsg.getContentRaw().substring(2) + ".png");
            objMsgCh.sendFile(file).queue();
        }
    }
}
