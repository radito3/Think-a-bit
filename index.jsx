import "babel-polyfill";
import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import AppContainer from "./components/app-container.jsx";

import store from "./store/store";

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <MuiThemeProvider>
                <AppContainer />
            </MuiThemeProvider>
        </BrowserRouter>
    </Provider>,
    document.getElementById("content")
);