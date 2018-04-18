import React from "react";
import { TextField } from "material-ui";
import { Grid, Row, Col } from "react-flexbox-grid";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { login, setSessionExpired } from "../store/actions/authentication";
import formurlencoded from "form-urlencoded";
import config from "../config";
import FormSubmitButton from "./form-submit-button.jsx";

class LoginPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            isLoading: false
        };

        this.handleSubmit = this.handleSubmit.bind(this);

        this.props.setSessionExpired(false);
    }

    handleSubmit() {
        const formData = {
            username: this.state.username,
            password: this.state.password
        };

        this.setState({
            isLoading: true
        });

        fetch(`${config.url}:${config.port}/Think-a-bit/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded", },
            credentials: "same-origin",
            mode: "same-origin",
            body: formurlencoded(formData)
        }).then(response => {
            this.setState({
                isLoading: false
            });

            if (response.status === 200) {
                this.props.login({
                    username: this.state.username
                });
                this.props.history.push("/categories");
            } else if (response.status === 404) {
                // todo: show error in user interface
                console.log("Wrong credentials");
            } else {
                // todo: show error in user interface
                console.log("An unknown error occured");
            }
        }).catch(error => {
            this.setState({
                isLoading: false
            });

            console.log(error);
        })
    }

    render() {
        return <Grid fluid>
            <Row center="xs" middle="xs">
                <Col xs={6}>
                    <form>
                        <TextField
                            hintText="Username Field"
                            floatingLabelText="Username"
                            type="username"
                            onChange={(event, newValue) => this.setState({ username: newValue })}
                        />
                        <br />
                        <TextField
                            hintText="Password Field"
                            floatingLabelText="Password"
                            type="password"
                            onChange={(event, newValue) => this.setState({ password: newValue })}
                        />
                        <br />
                        <FormSubmitButton
                            label="Log in"
                            isLoading={this.state.isLoading}
                            handleSubmit={this.handleSubmit}
                        />
                    </form>
                </Col>
            </Row>
        </Grid>;
    }
}

export default withRouter(connect(store => {
    return {
        authentication: store.authentication
    };
}, { login, setSessionExpired })(LoginPage));