import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { CircularProgress, FlatButton, LinearProgress, MenuItem } from "material-ui";
import { logout, setLoggingOut } from "../store/actions/authentication";
import config from "../config.json";

class LogoutButton extends React.Component {
    constructor(props) {
        super(props);
    }

    handleLogout() {
        this.props.setLoggingOut(true);

        fetch(`${config.url}:${config.port}/Think-a-bit/users/logout`, {
            method: "POST",
            credentials: "same-origin"
        }).then(response => {
            this.props.setLoggingOut(false);
            this.props.logout();
            this.props.history.push("/login");
        }).catch(error => {
            console.log(error);
            this.props.setLoggingOut(false);
        });
    }

    render() {
        if (this.props.type === "flat") {
            if (this.props.authentication.isLoggingOut) {
                return <CircularProgress />;
            } else {
                return <FlatButton
                    label="Log out"
                    onClick={() => this.handleLogout()}
                    style={this.props.style}
                />;
            }
        } else if (this.props.type === "menuitem") {
            if (this.props.authentication.isLoggingOut) {
                return <LinearProgress mode="indeterminate" />;
            } else {
                return <MenuItem onClick={() => this.handleLogout()}>Log out</MenuItem>;
            }
        }
    }
}

LogoutButton.propTypes = {
    type: PropTypes.oneOf(["flat", "menuitem"]).isRequired,
    style: PropTypes.shape({
        color: PropTypes.string.isRequired
    })
};

export default withRouter(connect(store => {
    return {
        authentication: store.authentication
    };
}, { logout,setLoggingOut })(LogoutButton));