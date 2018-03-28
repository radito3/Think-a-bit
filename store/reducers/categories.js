const reducer = (state = { categories: [] }, action) => {
    switch (action.type) {
        case "ADD_CATEGORIES":
            return {
                ...state,
                categories: action.payload
            };
        case "REMOVE_CATEGORIES":
            return {
                ...state,
                categories: []
            };
    }
    return state;
}

export default reducer;