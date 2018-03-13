import { combineReducers, applyMiddleware, createStore } from "redux";
import reducer from "./reducers";
import { loadState, saveState } from "./actions/localStorage.js";

const persistedState = loadState();
const store = createStore(reducer, persistedState);

store.subscribe(() => {
    saveState(store.getState());
});

export default store;