package hr.fer.unifier.backend.config.core;
public class UserLocalThread {
    private final static ThreadLocal<UserInfo> coreUserThreadLocals = new ThreadLocal<>();

    public static void setUserInfo(Long userId) {
        coreUserThreadLocals.set(new UserInfo(userId));
    }

    public static Long getUserId(){
        return coreUserThreadLocals.get().userId();
    }

    public static void deleteUserInfo(){
        coreUserThreadLocals.remove();
    }
}
