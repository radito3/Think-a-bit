import { combineReducers } from "redux";

import authentication from "./authentication";
import categories from "./categories";
import questions from "./questions";
import sideMenu from "./side-menu";
import stages from "./stages";
import result from "./result";

export default combineReducers({
    authentication,
    categories,
    questions,
    sideMenu,
    stages,
    result
});