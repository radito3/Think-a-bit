import React from "react";
import PropTypes from "prop-types";
import { CircularProgress, RaisedButton } from "material-ui";
import ArrowForward from "material-ui/svg-icons/navigation/arrow-forward";

class FormSubmitButton extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        if (this.props.isLoading) {
            return <CircularProgress />;
        } else {
            return <RaisedButton
                label={this.props.label}
                labelPosition="before"
                primary={true}
                icon={<ArrowForward />}
                onClick={this.props.handleSubmit}
            />;
        }
    }
}

FormSubmitButton.propTypes = {
    label: PropTypes.string.isRequired,
    isLoading: PropTypes.bool.isRequired,
    handleSubmit: PropTypes.func.isRequired
};

export default FormSubmitButton;