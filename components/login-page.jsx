import React from "react";
import { RaisedButton, TextField } from "material-ui";
import ArrowForward from "material-ui/svg-icons/navigation/arrow-forward";
import { Grid, Row, Col } from "react-flexbox-grid";

class LoginPage extends React.Component {
    render() {
        return <Grid fluid>
            <Row center="xs" middle="xs">
                <Col xs={6}>
                    <form>
                        <TextField
                            hintText="Username Field"
                            floatingLabelText="Username"
                            type="username"
                        />
                        <TextField
                            hintText="Password Field"
                            floatingLabelText="Password"
                            type="password"
                        />
                        <RaisedButton
                            label="Log in"
                            labelPosition="before"
                            primary={true}
                            icon={<ArrowForward />}
                        />
                    </form>
                    </Col>
                    </Row>
        </Grid>;
    }
}

export default LoginPage;