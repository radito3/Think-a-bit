const reducer = (state = { stages: [], category: "", selectedCategoryId: 0 }, action) => {
    switch (action.type) {
        case "SELECT_CATEGORY":
            return {
                ...state,
                selectedCategoryId: action.payload
            };
        case "ADD_STAGES":
            return {
                ...state,
                stages: action.payload.stages,
                category: action.payload.category
            };
        case "REMOVE_STAGES":
            return {
                ...state,
                stages: [],
                category: ""
            };
        case "DECREMENT_AVAILABLE_AFTER":
            return {
                ...state,
                stages: state.stages.map(stage => {
                    if (stage.availableAfter > 0) {
                        return {
                            ...stage,
                            availableAfter: stage.availableAfter - 1
                        };
                    } else {
                        return stage;
                    }
                })
            };
    }
    return state;
};

export default reducer;