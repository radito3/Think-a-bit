import React from "react";
import { RaisedButton, TextField } from "material-ui";
import ArrowForward from "material-ui/svg-icons/navigation/arrow-forward";
import { Grid, Row, Col } from "react-flexbox-grid";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { login } from "../store/actions/authentication";

class LoginPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
        };
    }

    handleSubmit() {
        this.props.login({
            username: this.state.username
        });
        this.props.history.push("/categories");
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
                        <TextField
                            hintText="Password Field"
                            floatingLabelText="Password"
                            type="password"
                            onChange={(event, newValue) => this.setState({ password: newValue })}
                        />
                        <RaisedButton
                            label="Log in"
                            labelPosition="before"
                            primary={true}
                            icon={<ArrowForward />}
                            onClick={() => this.handleSubmit()}
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
}, { login })(LoginPage));