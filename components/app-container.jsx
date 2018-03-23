import React from "react";
import Helmet from "react-helmet";
import { Route, Switch, withRouter } from "react-router-dom";
import {
    AppBar,
    Drawer,
    MenuItem,
    FlatButton,
    IconButton
} from "material-ui";
import NavigationClose from "material-ui/svg-icons/navigation/close";
import IndexPage from "./index-page.jsx";
import LoginPage from "./login-page.jsx";
import RegisterPage from "./register-page.jsx";
import CategoriesPage from "./categories-page.jsx";
import StagesPage from "./stages-page.jsx";
import QuestionPage from "./question-page.jsx";

class AppContainer extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            open: false
        };
    }

    toggleDrawer() {
        this.setState(prevState => {
            return {
                open: !prevState.open
            };
        });
    }

    render() {
        const authentication = <div>
            <FlatButton
                label="Log in"
                onClick={() => this.props.history.push("/login")}
                style={{
                    color: "#FAFAFA"
                }}
            />
            <FlatButton
                label="Register"
                onClick={() => this.props.history.push("/register")}
                style={{
                    color: "#FAFAFA"
                }}
            />
        </div>;
        return <div>
            <Helmet><style>{`
                body {
                    margin: 0;
                }
            `}</style></Helmet>
            <AppBar
                title="Think-a-bit"
                style={{
                    "backgroundColor": "#A1887F"
                }}
                iconElementRight={authentication}
                onLeftIconButtonClick={() => this.toggleDrawer()}
            />
            <Drawer
                docked={false}
                open={this.state.open}
                containerStyle={{
                    "backgoundColor": "#A5D6A7"
                }}
            >
                <AppBar
                    title="Think-a-bit"
                    style={{
                        "backgroundColor": "#A1887F"
                    }}
                    iconElementLeft={<IconButton><NavigationClose /></IconButton>}
                    onLeftIconButtonClick={() => this.toggleDrawer()}
                />
                <MenuItem onClick={() => this.props.history.push("/login")}>Log in</MenuItem>
                <MenuItem onClick={() => this.props.history.push("/register")}>Register</MenuItem>
            </Drawer>
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

export default withRouter(AppContainer);