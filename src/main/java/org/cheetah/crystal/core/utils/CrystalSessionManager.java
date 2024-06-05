package org.cheetah.crystal.core.utils;

import org.cheetah.crystal.dtos.auth.User;


public class CrystalSessionManager {

	private static ThreadLocal<User> userCab = new ThreadLocal<>();

	public static void setUser(User user) {
		userCab.set(user);
	}
	
	public static User getUser() {
		return userCab.get();
	}
	
}
