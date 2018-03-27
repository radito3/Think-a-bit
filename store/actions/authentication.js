function login(data) {
    return {
        type: "LOGIN",
        payload: data
    };
}

function logout() {
    return { type: "LOGOUT" };
}

export {
    login,
    logout
};