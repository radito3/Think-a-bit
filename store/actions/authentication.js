function login(data) {
    return {
        type: "LOGIN",
        payload: data
    };
}

function logout() {
    return { type: "LOGOUT" };
}

function setLoggingOut(isLoggingOut) {
    return {
        type: "SET_LOGGING_OUT",
        payload: isLoggingOut
    };
}

function setSessionExpired(isSessionExpired) {
    return {
        type: "SET_SESSION_EXPIRED",
        payload: isSessionExpired
    };
}

export {
    login,
    logout,
    setLoggingOut,
    setSessionExpired
};