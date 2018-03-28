function addCategories(categories) {
    return {
        type: "ADD_CATEGORIES",
        payload: categories
    };
}

function removeCategories() {
    return { type: "REMOVE_CATEGORIES" };
}

export {
    addCategories,
    removeCategories
};