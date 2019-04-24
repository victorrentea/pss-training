package training.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties("mail")
public class MailConfiguration {
	
	static class ServerDetails {
		private String primary;
		private String backup;
		public String getPrimary() {
			return primary;
		}
		public void setPrimary(String primary) {
			this.primary = primary;
		}
		public String getBackup() {
			return backup;
		}
		public void setBackup(String backup) {
			this.backup = backup;
		}
		public String toString() {
			return "ServerDetails [primary=" + primary + ", backup=" + backup + "]";
		}
		
	}

	public ServerDetails server;
	
	public int port;
	
	public String user;
	
	public String password;


	public ServerDetails getServer() {
		return server;
	}

	public void setServer(ServerDetails server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
