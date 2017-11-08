package org.se.lab.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="privateMessage")
public class PrivateMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public PrivateMessage(String text, int fk_user_sender, int fk_user_receiver)
	{
		setText(text);
		setFK_User_Sender(fk_user_sender);
		setFK_User_Receiver(fk_user_receiver);
	}
	
	protected PrivateMessage()
	{
		
	}
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID;
	public int getID() {
		return ID;
	}

	public void setID(int id) {
		if (id <= 0)
			throw new IllegalArgumentException();
		ID = id;
	}
	
	@Column(name="Text")
	private String text;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null || text.trim().length() == 0)
			throw new IllegalArgumentException();
		this.text = text;
	}

	@Column(name="FK_UserID_sender")
	private int FK_User_Sender;
	public int getFK_User_Sender() {
		return FK_User_Sender;
	}

	public void setFK_User_Sender(int fK_User_Sender) {
		FK_User_Sender = fK_User_Sender;
	}

	@Column(name="FK_UserID_receiver")
	private int FK_User_Receiver;
	public int getFK_User_Receiver() {
		return FK_User_Receiver;
	}

	public void setFK_User_Receiver(int fK_User_Receiver) {
		FK_User_Receiver = fK_User_Receiver;
	}
	
	@ManyToOne
    @JoinColumn(name="FK_UserID_sender")
    private User userSender;

    public void setUserSender(User userSender) {
        if(userSender == null)
            throw new IllegalArgumentException();
        this.userSender = userSender;
        userSender.addPrivateMessageSender(this);
    }

    public User getUserSender(){
        return userSender;
    }
    
    @ManyToOne
    @JoinColumn(name="FK_UserID_receiver")
    private User userReceiver;

    public void setUserReceiver(User userReceiver) {
        if(userReceiver == null)
            throw new IllegalArgumentException();
        this.userReceiver = userReceiver;
        userReceiver.addPrivateMessageReceiver(this);
    }

    public User getUserReceiver(){
        return userReceiver;
    }
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FK_User_Receiver;
		result = prime * result + FK_User_Sender;
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
		if (FK_User_Receiver != other.FK_User_Receiver)
			return false;
		if (FK_User_Sender != other.FK_User_Sender)
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
		return "PrivateMessage [ID=" + ID + ", text=" + text + ", FK_User_Sender=" + FK_User_Sender
				+ ", FK_User_Receiver=" + FK_User_Receiver + "]";
	}

}
