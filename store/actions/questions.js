function addQuestions(questions, stageId) {
    return {
        type: "ADD_QUESTIONS",
        payload: { questions, stageId }
    };
}

function removeQuestions() {
    return { type: "REMOVE_QUESTIONS" };
}

function selectAnswer(answer) {
    return {
        type: "SELECT_ANSWER",
        payload: answer
    };
}

function nextQuestion() {
    return { type: "NEXT_QUESTION" };
}

function previousQuestion() {
    return { type: "PREVIOUS_QUESTION" };
}

export {
    addQuestions,
    removeQuestions,
    selectAnswer,
    nextQuestion,
    previousQuestion
};