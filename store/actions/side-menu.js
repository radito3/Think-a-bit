function toggleSideMenu() {
    return { type: "TOGGLE_SIDE_MENU" };
}

function openSideMenu() {
    return { type: "OPEN_SIDE_MENU" };
}

function closeSideMenu() {
    return { type: "CLOSE_SIDE_MENU" };
}

export {
    toggleSideMenu,
    openSideMenu,
    closeSideMenu
};