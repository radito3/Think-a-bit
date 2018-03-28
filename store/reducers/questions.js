const reducer = (state = { questions: [], currentQuestionIndex: 0 }, action) => {
    switch (action.type) {
        case "ADD_QUESTIONS":
            return {
                ...state,
                questions: action.payload,
                // .map(question => {
                //     return {
                //         ...question,

                //     };
                // }),
                currentQuestionIndex: 0
            };
        case "REMOVE_QUESTIONS":
            return {
                ...state,
                questions: [],
                currentQuestionIndex: 0
            };
        // case "SELECT_ANSWER":
        //     return {
        //         ...state,
        //         questions: state.questions.
        //     };
        case "NEXT_QUESTION":
            return {
                ...state,
                currentQuestionIndex: (state.currentQuestionIndex + 1 < state.questions.length) ?
                    state.currentQuestionIndex + 1 : state.currentQuestionIndex
            };
        case "PREVIOUS_QUESTION":
            return {
                ...state,
                currentQuestionIndex: (state.currentQuestionIndex - 1 >= 0) ?
                    state.currentQuestionIndex - 1 : state.currentQuestionIndex
            };
    }
    return state;
};

export default reducer;