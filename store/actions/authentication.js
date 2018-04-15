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

export {
    login,
    logout,
    setLoggingOut
};