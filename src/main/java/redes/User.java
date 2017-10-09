package redes;

public class User {

	private String account;
	private String password;

	public User(String account, String password) {
		this.account = account;
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		return account != null ? account.equals(user.account) : user.account == null;
	}

	@Override
	public int hashCode() {
		return account != null ? account.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "User{" + "account='" + account + '\'' + ',' + "password='" + password + '\'' + '}';
	}

	public String getAccount() {
		return this.account;
	}

	public String getPassword() {
		return this.password;
	}
}
