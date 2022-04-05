package service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kinst
 */
public interface FormatService {

	Session sshLogin(String host, String port, String userName, String password) throws JSchException, IOException;

	void readImputStream(InputStream in, String CHARSET);

	void doCommand(Session session) throws JSchException;

	void SystemMemoryTotal();
}
