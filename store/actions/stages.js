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

export {
    addStages,
    removeStages,
    selectCategory
};