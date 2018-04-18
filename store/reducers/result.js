const reducer = (state = { questions: [] }, action) => {
    switch (action.type) {
        case "ADD_RESULT":
            return {
                ...state,
                questions: action.payload
            };
        case "REMOVE_RESULT":
            return {
                ...state,
                questions: []
            };
    }
    return state;
};

export default reducer;