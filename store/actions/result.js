function addResult(results) {
    return {
        type: "ADD_RESULT",
        payload: results
    };
}

function removeResult() {
    return { type: "REMOVE_RESULT" };
}

export {
    addResult,
    removeResult
};