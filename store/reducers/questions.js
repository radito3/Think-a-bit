const reducer = (state = { questions: [], currentStageId: 0, currentQuestionIndex: 0 }, action) => {
    switch (action.type) {
        case "ADD_QUESTIONS":
            return {
                ...state,
                questions: action.payload.questions,
                // .map(question => {
                //     return {
                //         ...question,

                //     };
                // }),
                currentStageId: action.payload.stageId,
                currentQuestionIndex: 0
            };
        case "REMOVE_QUESTIONS":
            return {
                ...state,
                questions: [],
                currentStageId: 0,
                currentQuestionIndex: 0
            };
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
        case "SELECT_ANSWER":
            return {
                ...state,
                questions: state.questions.map((question, index) => {
                    if (index === state.currentQuestionIndex) {
                        switch (question.type) {
                            case "CLOSED_MANY":
                                return {
                                    ...question,
                                    answers: question.answers.map(answer => {
                                        if (answer.content === action.payload) {
                                            return {
                                                ...answer,
                                                isSelected: !answer.isSelected
                                            };
                                        } else {
                                            return answer;
                                        }
                                    })
                                };
                            case "CLOSED_ONE":
                                return {
                                    ...question,
                                    answers: question.answers.map(answer => {
                                        if (answer.content === action.payload) {
                                            return {
                                                ...answer,
                                                isSelected: true
                                            };
                                        } else {
                                            return {
                                                ...answer,
                                                isSelected: false
                                            };
                                        }
                                    })
                                }
                            case "OPEN":
                                return {
                                    ...question,
                                    answer: action.payload
                                };
                            default:
                                return question;
                        }
                    } else {
                        return question;
                    }
                })
            }
    }
    return state;
};

export default reducer;