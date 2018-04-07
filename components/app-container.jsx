import React from "react";
import Helmet from "react-helmet";
import { Route, Switch } from "react-router-dom";
import IndexPage from "./index-page.jsx";
import LoginPage from "./login-page.jsx";
import RegisterPage from "./register-page.jsx";
import CategoriesPage from "./categories-page.jsx";
import StagesPage from "./stages-page.jsx";
import QuestionPage from "./question-page.jsx";
import SideMenu from "./side-menu.jsx";
import TopMenu from "./top-menu.jsx";

class AppContainer extends React.Component {

    render() {
        return <div>
            <Helmet><style>{`
                body {
                    margin: 0;
                    height: 100%;
                }
                body > div,
                body > div > div,
                body > div > div > div.container-fluid,
                body > div > div > div.container-fluid > div {
                    height: 100%;
                }
            `}</style></Helmet>
            <TopMenu/>
            <SideMenu/>
            <Switch>
                <Route path="/" exact component={IndexPage} />
                <Route path="/login" component={LoginPage} />
                <Route path="/register" component={RegisterPage} />
                <Route path="/categories" component={CategoriesPage} />
                <Route path="/stages" component={StagesPage} />
                <Route path="/question" component={QuestionPage} />
            </Switch>
        </div>;
    }
}

export default AppContainer;