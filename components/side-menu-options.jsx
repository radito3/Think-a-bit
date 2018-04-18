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
import { selectCategory, addStages } from "../store/actions/stages";
import { setSessionExpired } from "../store/actions/authentication";
import config from "../config.json";
import LogoutButton from "./logout-button.jsx";

class SideMenuOptions extends React.Component {
    constructor(props) {
        super(props);

        this.props.removeCategories();

        fetch(`${config.url}:${config.port}/Think-a-bit/game`).then(response => {
            return response.json();
        }).then(parsed => {
            this.props.addCategories(parsed);
        }).catch(error => {
            console.log(error);
        });
    }

    handleCategoryClick(categoryId) {
        fetch(`${config.url}:${config.port}/Think-a-bit/game/category/${categoryId}`, {
            method: "GET",
            credentials: "same-origin"
        }).then(response => {
            if (response.status === 401) {
                this.props.setSessionExpired(true);
            } else if (response.status === 200) {
                return response.json();
            } else {
                console.log("Unknown error");
                console.log(response.status);
            }
        }).then(parsed => {
            console.log(parsed);
            this.props.addStages(parsed.stages, parsed.name);
            this.props.selectCategory(categoryId);
            this.props.history.push("/stages");
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        const categories = this.props.categories.length > 0 ?
            this.props.categories.map(category =>
                <MenuItem
                    key={category.id}
                    onClick={() => this.handleCategoryClick.bind(this, category.id)()}
                >{category.name}</MenuItem>
            ) :
            <CircularProgress size={80} thickness={5} />;

        return this.props.authentication.username ?
            <div>
                <Subheader>{this.props.authentication.username}</Subheader>
                <MenuItem>Account settings</MenuItem>
                <LogoutButton type="menuitem" />
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
}, {
    addCategories,
    removeCategories,
    selectCategory,
    addStages,
        setSessionExpired
})(SideMenuOptions));