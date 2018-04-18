function addStages(stages, category) {
    return {
        type: "ADD_STAGES",
        payload: { stages, category }
    };
}

function selectCategory(categoryId) {
    return {
        type: "SELECT_CATEGORY",
        payload: categoryId
    };
}

function removeStages() {
    return { type: "REMOVE_STAGES" };
}

function decrementAvailableAfter() {
    return { type: "DECREMENT_AVAILABLE_AFTER" };
}

export {
    addStages,
    removeStages,
    selectCategory,
    decrementAvailableAfter
};