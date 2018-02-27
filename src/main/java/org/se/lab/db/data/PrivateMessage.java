package org.se.lab.db.data;

import org.apache.log4j.Logger;
import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


@Entity
@Table(name = "private_message")
public class PrivateMessage implements Serializable {

    public static final int MAX_TEXT_LENGTH = 1024;
    private static final long serialVersionUID = 1L;
    private static final String TOSTRING_MSG = "PrivateMessage: {id: %d, text: %s, FK_User_Sender: %s, FK_User_Receiver: %s}";

    @Transient
    private static final Logger LOG = Logger.getLogger(Post.class);

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "text")
    private String text;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "fk_user_id_sender")
    private User usersender;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "fk_user_id_receiver")
    private User userreceiver;

    public PrivateMessage(String text, User sender, User receiver) {
        LOG.debug("New Private Message");
        LOG.trace(
                String.format("\t{%n\ttext: %s,%n\tsender: %s%n\treceiver: %s%n\t",
                        text,
                        sender,
                        receiver));
        setText(text);
        setUserSender(sender);
        setUserReceiver(receiver);
    }

    public PrivateMessage() {}

    public int getID() {
        return ID;
    }


    public void setID(int id)  {
        ArgumentChecker.assertValidNumber(id,"privateMessageId");
        ID = id;
    }

    public String getText() {
        LOG.debug("getText -> " + text);
        return text;
    }

    public void setText(String text) {
        LOG.debug("setText(" + text + ")");
        ArgumentChecker.assertNotNullAndEmptyAndUnderMaxLength(text, "privateMessage", MAX_TEXT_LENGTH);
        this.text = text;
    }

    public User getUserSender() {
        return usersender;
    }

    public void setUserSender(User usersender) {
        ArgumentChecker.assertNotNull(usersender, "usersender");

        this.usersender = usersender;
        usersender.addPrivateMessageSender(this);
    }

    public User getUserReceiver() {
        return userreceiver;
    }

    public void setUserReceiver(User userreceiver) {
        ArgumentChecker.assertNotNull(userreceiver, "userreceiver");

        this.userreceiver = userreceiver;
        userreceiver.addPrivateMessageReceiver(this);
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    /**
     * Object Methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userreceiver != null) ? userreceiver.getId() : 0);
        result = prime * result + ((usersender != null) ? usersender.getId() : 0);
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PrivateMessage other = (PrivateMessage) obj;
        if (userreceiver.getId() != other.getUserReceiver().getId())
            return false;
        if (usersender.getId() != other.getUserSender().getId())
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format(TOSTRING_MSG, this.ID, this.text, this.usersender, this.userreceiver);
    }

}
