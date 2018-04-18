const reducer = (state = { username: "", isLoggingOut: false, isSessionExpired: false }, action) => {
    switch (action.type) {
        case "LOGIN":
            return {
                ...state,
                username: action.payload.username
            };
        case "SET_LOGGING_OUT":
            return {
                ...state,
                isLoggingOut: action.payload
            };
        case "SET_SESSION_EXPIRED":
            return {
                ...state,
                isSessionExpired: action.payload
            };
        case "LOGOUT":
            return {
                ...state,
                username: ""
            };
    }
    return state;
};

export default reducer;