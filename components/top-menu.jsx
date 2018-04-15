import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
    AppBar,
    FlatButton
} from "material-ui";
import { toggleSideMenu } from "../store/actions/side-menu";
import LogoutButton from "./logout-button.jsx";

class TopMenu extends React.Component {
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
                <LogoutButton
                    type="flat"
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

        return <AppBar
            title="Think-a-bit"
            style={{
                "backgroundColor": "#A1887F"
            }}
            iconElementRight={authentication}
            onLeftIconButtonClick={() => this.props.toggleSideMenu()}
            onTitleClick={() => this.props.toggleSideMenu()}
        />;
    }
}

export default withRouter(connect(store => {
    return {
        authentication: store.authentication
    }
}, { toggleSideMenu })(TopMenu));