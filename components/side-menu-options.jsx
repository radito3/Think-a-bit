import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
    CircularProgress,
    Divider,
    MenuItem,
    Subheader,
} from "material-ui";
import { addCategories, removeCategories } from "../store/actions/categories";
import { logout } from "../store/actions/authentication";
import config from "../config.json";

class SideMenuOptions extends React.Component {
    constructor(props) {
        super(props);

        this.props.removeCategories();

        fetch(`${config.url}:${config.port}/Think-a-bit/game`).then(response => {
            return response.json();
        }).then(parsed => {
            this.props.addCategories(parsed.map(category => category.name));
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        const categories = this.props.categories.length > 0 ?
            this.props.categories.map(category =>
                <MenuItem key={category}>{category}</MenuItem>
            ) :
            <CircularProgress size={80} thickness={5} />;

        return this.props.authentication.username ?
            <div>
                <Subheader>{this.props.authentication.username}</Subheader>
                <MenuItem>Account settings</MenuItem>
                <MenuItem onClick={() => this.props.logout()}>Log out</MenuItem>
                <Divider />
                <Subheader style={{
                    "fontSize": "30px"
                }}>Play a game:</Subheader>
                {categories}
            </div> :
            <div>
                <MenuItem onClick={() => this.props.history.push("/login")}>Log in</MenuItem>
                <MenuItem onClick={() => this.props.history.push("/register")}>Register</MenuItem>
            </div>;
    }
}

export default withRouter(connect(store => {
    return {
        authentication: store.authentication,
        categories: store.categories.categories
    };
}, { addCategories, removeCategories, logout })(SideMenuOptions));