import { combineReducers } from "redux";

import authentication from "./authentication";
import categories from "./categories";
import questions from "./questions";

export default combineReducers({
    authentication,
    categories,
    questions
});