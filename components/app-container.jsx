import React from "react";
import Helmet from "react-helmet";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { Route, Switch } from "react-router-dom";
import { Dialog, FlatButton } from "material-ui";
import IndexPage from "./index-page.jsx";
import LoginPage from "./login-page.jsx";
import RegisterPage from "./register-page.jsx";
import CategoriesPage from "./categories-page.jsx";
import StagesPage from "./stages-page.jsx";
import QuestionPage from "./question-page.jsx";
import SideMenu from "./side-menu.jsx";
import TopMenu from "./top-menu.jsx";
import ResultPage from "./result-page.jsx";

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
            <Dialog
                title="Session expired"
                actions={[<FlatButton
                    label="Authenticate"
                    primary={true}
                    onClick={() => this.props.history.push("/login")}
                />]}
                modal={true}
                open={this.props.authentication.isSessionExpired}
            >
                Your session has expired. Please log in again!
            </Dialog>
            <TopMenu/>
            <SideMenu/>
            <Switch>
                <Route path="/" exact component={CategoriesPage} />
                <Route path="/login" component={LoginPage} />
                <Route path="/register" component={RegisterPage} />
                <Route path="/stages" component={StagesPage} />
                <Route path="/question" component={QuestionPage} />
                <Route path="/result" component={ResultPage} />
            </Switch>
        </div>;
    }
}

export default withRouter(connect(store => {
    return {
        authentication: store.authentication
    };
})(AppContainer));