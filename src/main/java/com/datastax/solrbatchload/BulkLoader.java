package com.datastax.solrbatchload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.datastax.patientcare.model.User;

public interface BulkLoader {

	public void loadUsers(List<User> users)
			throws IOException;

	public File getFilePath();

	public void finish() throws IOException;
}