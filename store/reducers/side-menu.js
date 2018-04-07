const reducer = (state = { open: false }, action) => {
    switch (action.type) {
        case "TOGGLE_SIDE_MENU":
            return {
                ...state,
                open: !state.open
            };
        case "OPEN_SIDE_MENU":
            return {
                ...state,
                open: true
            };
        case "CLOSE_SIDE_MENU":
            return {
                ...state,
                open: false
            };
    }
    return state;
};

export default reducer;