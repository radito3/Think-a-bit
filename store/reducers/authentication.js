const reducer = (state = { username: "" }, action) => {
    switch (action.type) {
        case "LOGIN":
            return {
                ...state,
                username: action.payload.username
            }
        case "LOGOUT":
            return {
                ...state,
                username: ""
            };
    }
    return state;
}

export default reducer;