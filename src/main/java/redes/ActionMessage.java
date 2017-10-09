package redes;

import com.google.gson.*;

import redes.json.Message;

public class ActionMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -196781568365662709L;

	private Action action;
	private User user;
	private Integer value;

	public String toJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ActionMessage message = (ActionMessage) o;

		if (action != message.action)
			return false;
		if (user != null ? !user.equals(message.user) : message.user != null)
			return false;
		return value != null ? value.equals(message.value) : message.value == null;
	}

	@Override
	public int hashCode() {
		int result = action != null ? action.hashCode() : 0;
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Message{" + "action=" + action + ", user=" + user + ", value=" + value + '}';
	}
}
