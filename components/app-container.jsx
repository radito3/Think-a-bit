import React from "react";
import Helmet from "react-helmet";
import { connect } from "react-redux";
import { Route, Switch, withRouter } from "react-router-dom";
import {
    AppBar,
    Divider,
    Drawer,
    MenuItem,
    FlatButton,
    IconButton,
    Subheader
} from "material-ui";
import NavigationClose from "material-ui/svg-icons/navigation/close";
import IndexPage from "./index-page.jsx";
import LoginPage from "./login-page.jsx";
import RegisterPage from "./register-page.jsx";
import CategoriesPage from "./categories-page.jsx";
import StagesPage from "./stages-page.jsx";
import QuestionPage from "./question-page.jsx";
import { logout } from "../store/actions/authentication";
import { addCategories, removeCategories } from "../store/actions/categories";

class AppContainer extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            open: false
        };

        this.props.addCategories([
            "Biology",
            "Literature",
            "History",
            "Maths",
            "Astronomy",
            "Geography",
            "Programming",
            "All"
        ]);
    }

    toggleDrawer() {
        this.setState(prevState => {
            return {
                open: !prevState.open
            };
        });
    }

    render() {
        const appBarButtonStyle = {
            color: "#FAFAFA"
        };

        const authentication = this.props.authentication.username ?
            <div>
                <FlatButton
                    label={this.props.authentication.username}
                    onClick={() => { }}
                    style={appBarButtonStyle}
                />
                <FlatButton
                    label="Log out"
                    onClick={() => this.props.logout()}
                    style={appBarButtonStyle}
                />
            </div> :
            <div>
                <FlatButton
                    label="Log in"
                    onClick={() => this.props.history.push("/login")}
                    style={appBarButtonStyle}
                />
                <FlatButton
                    label="Register"
                    onClick={() => this.props.history.push("/register")}
                    style={appBarButtonStyle}
                />
            </div>;

        const drawer = this.props.authentication.username ?
            <div>
                <Subheader>{this.props.authentication.username}</Subheader>
                <MenuItem>Account settings</MenuItem>
                <MenuItem onClick={() => this.props.logout()}>Log out</MenuItem>
                <Divider />
                <Subheader style={{
                    "fontSize": "30px"
                }}>Play a game:</Subheader>
                {this.props.categories.map(category =>
                    <MenuItem key={category}>{category}</MenuItem>
                )}
            </div> :
            <div>
                <MenuItem onClick={() => this.props.history.push("/login")}>Log in</MenuItem>
                <MenuItem onClick={() => this.props.history.push("/register")}>Register</MenuItem>
            </div>;

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
            <AppBar
                title="Think-a-bit"
                style={{
                    "backgroundColor": "#A1887F"
                }}
                iconElementRight={authentication}
                onLeftIconButtonClick={() => this.toggleDrawer()}
                onTitleClick={() => this.toggleDrawer()}
            />
            <Drawer
                docked={false}
                open={this.state.open}
                containerStyle={{
                    "backgroundColor": "#C8E6C9"
                }}
                overlayStyle={{
                    "opacity": "0"
                }}
                onRequestChange={open => this.setState({ open }) }
            >
                <AppBar
                    title="Think-a-bit"
                    style={{
                        "backgroundColor": "#A1887F"
                    }}
                    iconElementLeft={<IconButton><NavigationClose /></IconButton>}
                    onLeftIconButtonClick={() => this.toggleDrawer()}
                    onTitleClick={() => this.toggleDrawer()}
                />
                {drawer}
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

export default withRouter(connect(store => {
    return {
        authentication: store.authentication,
        categories: store.categories.categories
    };
}, { addCategories, removeCategories, logout })(AppContainer));