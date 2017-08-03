package utils;

import org.jsoup.select.Elements;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.Properties;

public class EmailClient
{
    final static String EMAIL_USERNAME = new ReadProperties().readProperties("EMAIL_USERNAME");
    final static String EMAIL_PASSWORD = new ReadProperties().readProperties("EMAIL_PASSWORD");
    Folder inbox;
    Store store;

    public Store getConnection(String username, String password)
    {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try
        {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", username, password);
            return store;
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
        }
        return null;
    }

    public Folder getInboxFolder() throws MessagingException, IOException
    {
        store = getConnection(EMAIL_USERNAME, EMAIL_PASSWORD);
        if (store != null)
        {
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            return inbox;
        }
        else
        {
            return null;
        }
    }
    public int getMessageCount(String mailSubject, String mailContent)
    {
        int i = 0;
        int count = 5;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message [] msgs = inbox.getMessages();
                    for(Message msg : msgs)
                    {
                        if(msg.getSubject().contains(mailSubject))
                        {
                            Multipart mp = (Multipart) msg.getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            bp.getContent();
                            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
                            String bodyContent = getTextFromMimeMultipart(mimeMultipart);
                            if (bodyContent.contains(mailContent))
                            {
                                deleteInboxMasseges();
                                i++;
                            }
                        }
                    }
                    return i;
                }count--;
            }
            catch (Exception e)
            {
                count--;
            }
        }
        return i;
    }

    public boolean isMailPresent(String mailSubject, String mailContent) throws IOException, MessagingException
    {
        int count = 5;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message[] msgs = inbox.getMessages();
                    for (Message msg : msgs)
                    {
                        if (msg.getSubject().contains(mailSubject))
                        {
                            Multipart mp = (Multipart) msg.getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            bp.getContent();
                            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
                            String bodyContent = getTextFromMimeMultipart(mimeMultipart);
                            if (bodyContent.contains(mailContent))
                            {
                                deleteInboxMasseges();
                                return true;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {

            }
            finally
            {
                count--;
            }
            try
            {
                Thread.sleep(20000);
            }
            catch (InterruptedException e)
            {
            }
        }
        return false;
    }

    public String getMailBodyContent(String mailSubject) throws IOException, MessagingException
    {
        int count = 5;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message[] msgs = inbox.getMessages();
                    for (Message msg : msgs)
                    {
                        if (msg.getSubject().contains(mailSubject))
                        {
                            Multipart mp = (Multipart) msg.getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            bp.getContent();
                            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
                            String bodyContent = getTextFromMimeMultipart(mimeMultipart);
                            return bodyContent;
                        }
                    }
                    return null;
                }
                count--;
            }
            catch (Exception e)
            {
                count--;
            }
        }
        return null;
    }

    public boolean verifyTheEmailToAndReplyTo(String mailSubject,String replyTo, String to)
            throws IOException, MessagingException, InterruptedException
    {
        int count = 10;
        boolean replyToFound = false;
        boolean toFound = false;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message[] msgs = inbox.getMessages();
                    for (Message msg : msgs)
                    {
                        if (msg.getSubject().contains(mailSubject))
                        {
                            Address[] recipients = msg.getRecipients(Message.RecipientType.TO);
                            for (Address address : recipients)
                            {
                                if(to.equalsIgnoreCase(address.toString()))
                                    toFound = true;
                            }
                            recipients = msg.getReplyTo();
                            for (Address address : recipients)
                            {
                                if(replyTo.equalsIgnoreCase(address.toString()))
                                    replyToFound = true;
                            }

                        }
                    }
                    Thread.sleep(2000);
                    count--;
                }
                Thread.sleep(2000);
                count--;
            }
            catch (Exception e)
            {
                Thread.sleep(2000);
                count--;
            }
        }
        return (replyToFound && toFound);
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception
    {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++)
        {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain"))
            {
                result = result + "\n" + bodyPart.getContent();
                break;
            }
            else if (bodyPart.isMimeType("text/html"))
            {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            }
            else if (bodyPart.getContent() instanceof MimeMultipart)
            {
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    public void deleteInboxMasseges() throws IOException, MessagingException
    {
        if (store == null)
        {
            Folder inboxFolder = getInboxFolder();
        }
        if (store != null)
        {
            //Copy the content of trask folder
            Message[] messages = inbox.getMessages();
            Folder trash = store.getFolder("[Gmail]/Trash");
            inbox.copyMessages(messages, trash);
            inbox.close(true);

            Folder allMail = store.getFolder("[Gmail]/All Mail");
            allMail.open(Folder.READ_WRITE);
            allMail.copyMessages(allMail.getMessages(), trash);
            allMail.close(true);

            //delete from trash
            trash.open(Folder.READ_WRITE);
            Message[] messagesFromTrash = trash.getMessages();
            for (Message message : messagesFromTrash)
            {
                message.setFlag(Flags.Flag.DELETED, true);
            }
            trash.close(true);
        }
    }

    public static void main(String[] args) throws IOException, MessagingException
    {
        EmailClient pop3EmailClient = new EmailClient();
        pop3EmailClient.getInboxFolder();
        pop3EmailClient.deleteInboxMasseges();
    }
    public int countInboxMessages(String mailSubject) throws IOException, MessagingException
    {
        int inboxMessageCount = 0;
        if (store != null)
        {
            Message[] messages = inbox.getMessages();
            for(Message msg : messages)
            {
                if(msg.getSubject().equalsIgnoreCase(mailSubject))
                {
                    inboxMessageCount++;
                }
            }
            deleteInboxMasseges();
        }
        return inboxMessageCount;
    }

    public String getUrlLink(String mailSubject, String urlText)
            throws IOException, MessagingException, InterruptedException
    {
        int count = 10;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message[] msgs = inbox.getMessages();
                    for (Message msg : msgs)
                    {
                        if (msg.getSubject().contains(mailSubject))
                        {
                            Multipart mp = (Multipart) msg.getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            bp.getContent();
                            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
                            String urlLink = getUrlLink(mimeMultipart, urlText);
                            if (urlLink != null && urlLink.length() != 0)
                            {
                                deleteInboxMasseges();
                                return urlLink;
                            }
                            else
                            {
                                throw new Exception("urlLink is not available");
                            }
                        }
                    }
                    Thread.sleep(2000);
                    count--;
                }
                Thread.sleep(2000);
                count--;
            }
            catch (Exception e)
            {
                Thread.sleep(2000);
                count--;
            }
        }
        return null;
    }

    private String getUrlLink(MimeMultipart mimeMultipart, String urlText) throws Exception
    {
        String result = "";
        int count = mimeMultipart.getCount();

        for (int i = 0; i < count; i++)
        {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (!(bodyPart.getContent() instanceof MimeMultipart))
            {
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse((String) bodyPart.getContent());
                Elements links = doc.select("a");
                for (org.jsoup.nodes.Element eachLink : links)
                {
                    if (eachLink.html().equals(urlText))
                    {
                        return eachLink.attr("href");
                    }
                }
            }

            if (bodyPart.isMimeType("text/plain"))
            {
                result = result + "\n" + bodyPart.getContent();
                break;
            }
            else if (bodyPart.isMimeType("text/html"))
            {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            }
            else if (bodyPart.getContent() instanceof MimeMultipart)
            {
                result = result + getUrlLink((MimeMultipart) bodyPart.getContent(), urlText);
            }
        }
        return result;
    }

    public void deleteAllMessageBasedOnMessages(final String mailSubject) throws IOException, MessagingException
    {
        store = getConnection(EMAIL_USERNAME, EMAIL_PASSWORD);
        if (store != null)
        {
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);

            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm()
            {
                @Override
                public boolean match(Message message)
                {
                    try
                    {
                        if (message.getSubject().contains(mailSubject))
                        {
                            return true;
                        }
                    }
                    catch (MessagingException ex)
                    {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };

            // performs search through the folder
            Message[] foundMessages = folderInbox.search(searchCondition);

            for (int i = 0; i < foundMessages.length; i++)
            {
                Message message = foundMessages[i];
                String subject = message.getSubject();
                if (message.getSubject().equalsIgnoreCase(mailSubject))
                {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
            }

        }
    }

    public boolean isMailPresent(String mailSubject, String mailContent, boolean needDelete) throws IOException, MessagingException
    {
        int count = 5;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message[] msgs = inbox.getMessages();
                    for (Message msg : msgs)
                    {
                        String messageSubject = msg.getSubject();
                        if (msg.getSubject().contains(mailSubject))
                        {
                            Multipart mp = (Multipart) msg.getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            bp.getContent();
                            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
                            String bodyContent = getTextFromMimeMultipart(mimeMultipart);
                            if (bodyContent.contains(mailContent))
                            {
                                if(needDelete)
                                {
                                    deleteInboxMasseges();
                                }
                                return true;
                            }
                        }
                    }
                    return false;
                }
                count--;
            }
            catch (Exception e)
            {
                count--;
            }
            try
            {
                Thread.sleep(20000);
            }
            catch (InterruptedException e)
            {
            }
        }
        return false;
    }

    private String getUrlLinkFromSpan(MimeMultipart mimeMultipart, String urlText) throws Exception
    {
        String result = "";
        int count = mimeMultipart.getCount();

        for (int i = 0; i < count; i++)
        {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (!(bodyPart.getContent() instanceof MimeMultipart))
            {
                org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse((String) bodyPart.getContent());
                Elements links = doc.select("a > span");
                for (org.jsoup.nodes.Element eachLink : links)
                {
                    if (eachLink.html().equals(urlText))
                    {
                        Elements links1 = doc.select("a");

                        for (org.jsoup.nodes.Element eachLink1 : links1)
                        {
                            return eachLink1.attr("href");
                        }
                    }
                }
            }

            if (bodyPart.isMimeType("text/plain"))
            {
                result = result + "\n" + bodyPart.getContent();
                break;
            }
            else if (bodyPart.isMimeType("text/html"))
            {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            }
            else if (bodyPart.getContent() instanceof MimeMultipart)
            {
                result = result + getUrlLink((MimeMultipart) bodyPart.getContent(), urlText);
            }
        }
        return result;
    }

    public String getUrlLinkFromSpan(String mailSubject, String urlText)
            throws IOException, MessagingException, InterruptedException
    {
        int count = 10;
        while (count > 0)
        {
            try
            {
                if (getInboxFolder() != null)
                {
                    Message[] msgs = inbox.getMessages();
                    for (Message msg : msgs)
                    {
                        if (msg.getSubject().contains(mailSubject))
                        {
                            Multipart mp = (Multipart) msg.getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            bp.getContent();
                            MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
                            String urlLink = getUrlLinkFromSpan(mimeMultipart, urlText);
                            if (urlLink != null && urlLink.length() != 0)
                            {
                                return urlLink;
                            }
                            else
                            {
                                throw new Exception("urlLink is not available");
                            }
                        }
                    }
                    Thread.sleep(2000);
                    count--;
                }
                Thread.sleep(2000);
                count--;
            }
            catch (Exception e)
            {
                Thread.sleep(2000);
                count--;
            }
        }
        return null;
    }
}